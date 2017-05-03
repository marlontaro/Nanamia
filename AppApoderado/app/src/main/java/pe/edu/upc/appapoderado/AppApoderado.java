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

public class AppApoderado extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
        ImprimirHash();
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
