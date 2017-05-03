package pe.edu.upc.appapoderado;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

/**
 * A placeholder fragment containing a simple view.
 */
public class StartActivityFragment extends Fragment {

    public StartActivityFragment() {
    }

    CallbackManager callbackmanager;
    String Correo="";
    String Persona ="";
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

        Button button = (Button)v.findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onFblogin();
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
                                            Persona = object.getString("name");
                                            Correo = object.getString("email");
                                            Id= object.getString("id");
                                            Token = AccessToken.getCurrentAccessToken().getToken();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

                                        Bundle params = new Bundle();
                                        params.putString("fields", "id,email,gender,cover,picture.type(large)");
                                        new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                                                new GraphRequest.Callback() {
                                                    @Override
                                                    public void onCompleted(GraphResponse response) {
                                                        if (response != null) {
                                                            try {
                                                                JSONObject data = response.getJSONObject();
                                                                if (data.has("picture")) {
                                                                    String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                                                    if (profilePicUrl.isEmpty() == false) {

                                                                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                                                        SharedPreferences.Editor editor = preferences.edit();
                                                                        editor.putString("fbimgURL",profilePicUrl);
                                                                        editor.apply();
                                                                    }

                                                                    /*GuardarRedes(Persona,Correo,Id,Token,2);
                                                                    Preferencias.RegistrarTipoUsuario(getActivity().getSharedPreferences(Variables.Archivo,0),2);*/


                                                                }
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    }
                                                }).executeAsync();




                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email");
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
