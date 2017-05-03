package pe.edu.upc.appapoderado.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import pe.edu.upc.appapoderado.AppApoderado;
import pe.edu.upc.appapoderado.R;
import pe.edu.upc.appapoderado.StartActivity;
import pe.edu.upc.appapoderado.models.Usuario;
import pe.edu.upc.appapoderado.util.Preferencias;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        InicioFragment.OnFragmentInteractionListener,
        ServicioFragment.OnFragmentInteractionListener{

    Usuario usuario;
    TextView txtMenuNombre;
    TextView txtMenuCorreo;
    CircularImageView imgAvatar;
    Button btnOtro;
    String TAG="Soen";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usuario= AppApoderado.getInstance().getCurrentUsuario();
        Log.d(TAG,AppApoderado.getInstance().getCurrentUsuario().getNombre());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View v = navigationView.getHeaderView(0);
        if(v!=null) {

            txtMenuNombre = (TextView)v.findViewById(R.id.txtMenuNombre);
            txtMenuCorreo = (TextView)v.findViewById(R.id.txtMenuCorreo);
            txtMenuNombre.setText(usuario.getNombre() + " " + usuario.getApellido());
            txtMenuCorreo.setText(usuario.getCorreo());
            imgAvatar = (CircularImageView)v.findViewById(R.id.imgAvatar);

            int tipo= Preferencias.DevolverTipo(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
            Log.d(TAG,String.valueOf(tipo));

            if(tipo==2) {
                FacebookSdk.sdkInitialize(getApplicationContext());
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

                                            Picasso.with(getApplicationContext()).load(profilePicUrl).placeholder(R.drawable.avatar).into(imgAvatar);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).executeAsync();

            }else if(tipo==1){
                String url = usuario.getAvatar();

                if (url.isEmpty() == false){

//                    Picasso.with(context).load(url).into(imgAvatar);
                    Picasso.with(getApplicationContext()).load(url).placeholder(R.drawable.avatar).into(imgAvatar);


                }
            }
        }

        InicioFragment frmHome =new InicioFragment();
        IniciarFragment(frmHome);

        btnOtro = (Button)findViewById(R.id.btnOtro);
        if(btnOtro!=null){
            btnOtro.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(getApplicationContext(),ServicioFragment.class);
                    startActivity(intent);

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mnuInicio) {
            InicioFragment frmHome =new InicioFragment();
            IniciarFragment(frmHome);
            // Handle the camera action
        } else if (id == R.id.mnuSolicitar) {
            ServicioFragment frmHome =new ServicioFragment();
            IniciarFragment(frmHome);
        } else if (id == R.id.mnuEntrevistar) {

        } else if (id == R.id.mnuPerfil) {

        } else if (id == R.id.mnuCerrar) {

            int tipo = Preferencias.DevolverTipo(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
            Preferencias.RegistrarUsuario(
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()),
                    0, "");

            if(tipo==2){
                FacebookSdk.sdkInitialize(getApplicationContext());
                LoginManager.getInstance().logOut();
            }

            Intent intent = new Intent(getApplication(), StartActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void IniciarFragment(Fragment frm){

        FragmentManager fm = getSupportFragmentManager();
        if (fm != null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frmPrincipal, frm);
            ft.commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
