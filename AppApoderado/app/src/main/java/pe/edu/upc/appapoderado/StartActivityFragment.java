package pe.edu.upc.appapoderado;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import pe.edu.upc.appapoderado.activities.LoginActivity;
import pe.edu.upc.appapoderado.activities.PrincipalActivity;
import pe.edu.upc.appapoderado.activities.RegistroActivity;
import pe.edu.upc.appapoderado.models.Usuario;
import pe.edu.upc.appapoderado.network.NanaApi;
import pe.edu.upc.appapoderado.util.Preferencias;

/**
 * A placeholder fragment containing a simple view.
 */
public class StartActivityFragment extends Fragment {

    public StartActivityFragment() {

    }

    CallbackManager callbackmanager;
    String TAG="Soen";
    TextView txtRegistrarse;
    Button btnFBLogin;
    Button btnEntrarCorreo;

    String Correo="";
    String Nombre ="";
    String Apellido ="";
    String Id="";
    String Token="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_start, container, false);
        txtRegistrarse = (TextView)v.findViewById(R.id.txtRegistrarse);
        txtRegistrarse.setMovementMethod(LinkMovementMethod.getInstance());
        txtRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), RegistroActivity.class);
                startActivity(intent);
            }
        });

        btnFBLogin = (Button)v.findViewById(R.id.login_button);
        btnFBLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onFblogin();
            }
        });

        btnEntrarCorreo = (Button)v.findViewById(R.id.btnEntrarCorreo);
        btnEntrarCorreo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });
        return v;

    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

      /*  btnEntrarCorreo= (Button)view.findViewById(R.id.btnEntrarCorreo);

        btnEntrarCorreo.setOnClickListener( new View.OnClickListener() {

            public void onClick(View view){

                Intent intent = new Intent(getActivity(), IniciarSesion.class);
                startActivity(intent);
                getActivity().finish();
            }

        });

        imgNuevaCuenta= (ImageView)view.findViewById(R.id.imgNuevaCuenta);
        imgNuevaCuenta.setOnClickListener( new View.OnClickListener() {

            public void onClick(View view){
                Intent intent = new Intent(getActivity(), Registrece.class);
                startActivity(intent);
            }

        });*/
    }

    private void onFblogin()
    {
        callbackmanager = CallbackManager.Factory.create();

        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"));

        LoginManager.getInstance().registerCallback(callbackmanager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {


                                        try {
                                            //Persona = object.getString("name");
                                            Nombre =  object.getString("first_name");
                                            Apellido =  object.getString("last_name");
                                            Correo = object.getString("email");
                                            Id= object.getString("id");
                                            Token = AccessToken.getCurrentAccessToken().getToken();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

                                        Bundle params = new Bundle();
                                        params.putString("fields", "id,email,first_name,last_name,gender,cover,picture.type(large)");
                                        new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                                                new GraphRequest.Callback() {
                                                    @Override
                                                    public void onCompleted(GraphResponse response) {
                                                        if (response != null) {
                                                            try {
                                                                JSONObject data = response.getJSONObject();
                                                                Log.d(TAG,data.toString());


                                                                if (data.has("picture")) {
                                                                    String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");

                                                                    if (profilePicUrl.isEmpty() == false) {

                                                                    }




                                                                }

                                                                JSONObject jsonObject = new JSONObject();
                                                                try {
                                                                    jsonObject.put("tipo", "2");
                                                                    jsonObject.put("nombre", Nombre);
                                                                    jsonObject.put("apellido", Apellido);
                                                                    jsonObject.put("celular", "");
                                                                    jsonObject.put("dni", "");
                                                                    jsonObject.put("correo", Correo);
                                                                    jsonObject.put("password", Token);
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

                                                                                        Toast.makeText(getContext(), response.getString("mensaje"),
                                                                                                Toast.LENGTH_SHORT).show();

                                                                                        return;
                                                                                    }else{

                                                                                        //crear objeto usuario
                                                                                        Usuario user= new Usuario();
                                                                                        user = Usuario.build(response);
                                                                                        AppApoderado.getInstance().setCurrentUsuario(user);

                                                                                        Preferencias.RegistrarUsuario(
                                                                                                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()),
                                                                                                2, user.getToken());

                                                                                        Intent intent = new Intent(getActivity(), PrincipalActivity.class);
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




                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    }
                                                }).executeAsync();




                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, name, email,first_name,last_name");
                        request.setParameters(parameters);
                        request.executeAsync();




                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
    }

    public boolean isFBIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackmanager.onActivityResult(requestCode, resultCode, data);
    }
}
