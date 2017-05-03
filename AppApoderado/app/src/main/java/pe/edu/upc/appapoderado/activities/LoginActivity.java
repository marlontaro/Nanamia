package pe.edu.upc.appapoderado.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import pe.edu.upc.appapoderado.AppApoderado;
import pe.edu.upc.appapoderado.R;
import pe.edu.upc.appapoderado.StartActivity;
import pe.edu.upc.appapoderado.models.Usuario;
import pe.edu.upc.appapoderado.network.NanaApi;
import pe.edu.upc.appapoderado.util.Preferencias;

public class LoginActivity extends AppCompatActivity {
    TextView txtRegistrarse;
    EditText txtCorreoElectronico;
    EditText txtContrasenia;
    Button btnInicieSesion;
    String TAG="SOEN";
    public static final String REGEX = "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtRegistrarse=(TextView)findViewById(R.id.txtRegistrarse);
        txtRegistrarse.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),RegistroActivity.class);
                startActivity(intent);

            }
        });

        txtCorreoElectronico=(EditText)findViewById(R.id.txtCorreoElectronico);
        txtContrasenia=(EditText)findViewById(R.id.txtContrasenia);

        btnInicieSesion=(Button)findViewById(R.id.btnInicieSesion);
        btnInicieSesion.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(ValidaRegistro()==true) {

                    AndroidNetworking.get(NanaApi.LOGIN_URL)
                            .addQueryParameter("tipo", "1")
                            .addQueryParameter("usuario", txtCorreoElectronico.getText().toString().trim())
                            .addQueryParameter("password", txtContrasenia.getText().toString().trim())
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
                                        }
                                        Usuario user= new Usuario();
                                        user = Usuario.build(response);
                                        AppApoderado.getInstance().setCurrentUsuario(user);

                                        Preferencias.RegistrarUsuario(
                                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()),
                                                1, user.getToken());

                                        Intent intent = new Intent(getApplication(), PrincipalActivity.class);
                                        startActivity(intent);

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
        String email = txtCorreoElectronico.getText().toString();
        String password = txtContrasenia.getText().toString();
        if (email.isEmpty()){
            txtCorreoElectronico.setError("Por favor ingrese una cuenta de correo");
            //Toast.makeText(getApplicationContext(),"Por favor ingrese una cuenta de correo.",Toast.LENGTH_SHORT).show();
            bValida = false;
        }

        if (password.isEmpty()) {
            txtContrasenia.setError("Por favor ingrese una contraseña");
            //Toast.makeText(getApplicationContext(),"Por favor ingrese una contraseña.",Toast.LENGTH_SHORT).show();
            bValida = false;
        }else{
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                txtCorreoElectronico.setError("Por favor ingrese una cuenta de correo válida.");
                bValida = false;
            }
        }

        if(bValida==false){

            Toast.makeText(getBaseContext(), "No se puede loguear, prueba de nuevo.", Toast.LENGTH_SHORT).show();
        }
        return bValida;
    }

}
