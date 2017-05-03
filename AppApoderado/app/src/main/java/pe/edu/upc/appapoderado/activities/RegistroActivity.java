package pe.edu.upc.appapoderado.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.upc.appapoderado.AppApoderado;
import pe.edu.upc.appapoderado.R;
import pe.edu.upc.appapoderado.models.Usuario;
import pe.edu.upc.appapoderado.network.NanaApi;
import pe.edu.upc.appapoderado.util.Preferencias;

public class RegistroActivity extends AppCompatActivity {

    TextView txtNombre;
    TextView txtApellido;
    TextView txtDni;
    TextView txtCelular;
    TextView txtCorreoElectronico;
    TextView txtContrasenia;
    TextView txtReContrasenia;
    Button btnRegistrarse;
    String TAG="SOEN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtNombre =(TextView)findViewById(R.id.txtNombre);
        txtApellido =(TextView)findViewById(R.id.txtApellido);
        txtDni = (TextView)findViewById(R.id.txtDni);
        txtCelular = (TextView)findViewById(R.id.txtCelular);
        txtCorreoElectronico= (TextView)findViewById(R.id.txtCorreoElectronico);
        txtContrasenia = (TextView)findViewById(R.id.txtContrasenia);
        txtReContrasenia = (TextView)findViewById(R.id.txtReContrasenia);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnRegistrarse=(Button)findViewById(R.id.btnRegistrarse);
        btnRegistrarse.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(ValidaRegistro()==true){

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("tipo", "1");
                        jsonObject.put("nombre", txtNombre.getText().toString().trim());
                        jsonObject.put("apellido", txtApellido.getText().toString().trim());
                        jsonObject.put("celular", txtCelular.getText().toString().trim());
                        jsonObject.put("dni", txtDni.getText().toString().trim());
                        jsonObject.put("correo", txtCorreoElectronico.getText().toString().trim());
                        jsonObject.put("password", txtContrasenia.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    AndroidNetworking.post(NanaApi.USER_REGISTER_URL)
                            .addJSONObjectBody(jsonObject)
                            .setPriority(Priority.LOW)
                            .setTag(TAG)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        if (response.getString("estado").equalsIgnoreCase("error")) {
                                            Log.d(TAG, response.getString("mensaje"));

                                            Toast.makeText(getApplication(), response.getString("mensaje"),
                                                    Toast.LENGTH_SHORT).show();

                                            return;
                                        }else{

                                            //crear objeto usuario
                                            Usuario user= new Usuario();
                                            user = Usuario.build(response);
                                            AppApoderado.getInstance().setCurrentUsuario(user);

                                            Preferencias.RegistrarUsuario(
                                                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()),
                                                        1, user.getToken());

                                            Intent intent = new Intent(getApplication(), PrincipalActivity.class);
                                            startActivity(intent);
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(ANError anError) {
                                    Log.d(TAG, anError.getLocalizedMessage());
                                }
                            });



                }
            }
        });
    }

    private boolean ValidaRegistro(){

        boolean bValida = true;
        String nombre = txtNombre.getText().toString();
        String apellido = txtApellido.getText().toString();
        String email = txtCorreoElectronico.getText().toString();
        String password = txtContrasenia.getText().toString();
        String repassword = txtReContrasenia.getText().toString();
        String dni = txtDni.getText().toString();
        String celular = txtCelular.getText().toString();

        if(nombre.isEmpty() || nombre.toString().length()==0){
            txtNombre.setError("Por favor ingrese su nombre.");
         }

        if(apellido.isEmpty() || apellido.toString().length()==0){
            txtApellido.setError("Por favor ingrese su apellido.");
         }

        if(dni.isEmpty() || dni.toString().length()==0){
            txtDni.setError("Por favor ingrese su DNI.");
        }

        if(celular.isEmpty() || celular.toString().length()==0){
            txtCelular.setError("Por favor ingrese su ceular.");
        }

        if (email.isEmpty()){
            txtCorreoElectronico.setError("Por favor ingrese una cuenta de correo");
            bValida = false;
        }else {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                txtCorreoElectronico.setError("Por favor ingrese una cuenta de correo válida.");
                bValida = false;
            }
        }

        if (password.isEmpty()) {
            txtContrasenia.setError("Por favor ingrese una contraseña");
            //Toast.makeText(getApplicationContext(),"Por favor ingrese una contraseña.",Toast.LENGTH_SHORT).show();
            bValida = false;
        }else{
            if (password.length() < 4 || password.length() > 10) {
                txtContrasenia.setError("su contraseña debe ser entre 4 y 11 caracteres");
                bValida = false;
            }
        }

        if (repassword.isEmpty()) {
            txtReContrasenia.setError("Por favor ingrese una contraseña");
            bValida = false;
        }else{
            if (repassword.length() < 4 || repassword.length() > 10) {
                txtReContrasenia.setError("su contraseña debe ser entre 4 y 11 caracteres");
                bValida = false;
            }
        }

        if(bValida==true) {

            if (!password.trim().equals(repassword.trim())) {
                Toast.makeText(getBaseContext(), "Las contraseñas no coinciden, por favor verifique.", Toast.LENGTH_SHORT).show();
                bValida = false;
                return bValida;
            }
        }else{
            Toast.makeText(getBaseContext(), "No se puede registrar su cuenta, prueba de nuevo", Toast.LENGTH_SHORT).show();
        }
        return bValida;
    }


}
