package pe.edu.upc.appapoderado;


import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import pe.edu.upc.appapoderado.models.Usuario;
import pe.edu.upc.appapoderado.network.NanaApi;

public class AppApoderado extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
    }

    private static AppApoderado instance;
    private NanaApi nanaApi= new NanaApi();
    public AppApoderado() {
        super();
        instance = this;
    }

    public static AppApoderado getInstance() {
        return instance;
    }


    public void setCurrentUsuario(Usuario source) {
        nanaApi.setCurrentUsuario(source);
    }

    public Usuario getCurrentUsuario() {
        return nanaApi.getCurrentUsuario();
    }

    private void ImprimirHash(){

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "pe.edu.upc.appapoderado",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

}
