package pe.edu.upc.appapoderado;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.upc.appapoderado.activities.LoginActivity;
import pe.edu.upc.appapoderado.activities.PrincipalActivity;
import pe.edu.upc.appapoderado.models.Usuario;
import pe.edu.upc.appapoderado.network.NanaApi;
import pe.edu.upc.appapoderado.util.Preferencias;

public class StartActivity extends AppCompatActivity {
    private String TAG="Soen";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String token= Preferencias.DevolverToken(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        //Log.d(TAG,token );


        if(token.trim().length()!=0){
            AndroidNetworking.get(NanaApi.LOGIN_URL)
                    .addQueryParameter("token", token)
                    .setPriority(Priority.LOW)
                    .setTag(TAG)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if(response.getString("estado").equalsIgnoreCase("error")) {
                                    Toast.makeText(getApplication(), response.getString("mensaje"),
                                            Toast.LENGTH_SHORT).show();

                                    return;
                                }

                                Usuario user= new Usuario();
                                user = Usuario.build(response);
                                AppApoderado.getInstance().setCurrentUsuario(user);
                                Log.d(TAG,AppApoderado.getInstance().getCurrentUsuario().getNombre());
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

}
