package pe.edu.upc.appapoderado.util;

import android.content.SharedPreferences;



public class Preferencias {

    public static void RegistrarUsuario(SharedPreferences settingActivity, int tipo,String token){
        SharedPreferences setting = settingActivity;
        SharedPreferences.Editor editor = setting.edit();
        editor.putInt("tipo",tipo);
        editor.putString("token",token);
        editor.commit();
        editor.apply();
    }


    public static int DevolverTipo (SharedPreferences settingActivity){
        int tipo=0;
        SharedPreferences setting = settingActivity;
        tipo=setting.getInt("tipo",0);
        return tipo;
    }

    public static String DevolverToken (SharedPreferences settingActivity){
        String token="";
        SharedPreferences setting = settingActivity;
        token=setting.getString("token","");
        return token;
    }
}
