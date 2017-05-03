package pe.edu.upc.appapoderado.network;

import pe.edu.upc.appapoderado.models.Usuario;

/**
 * Created by Marlon Cordova on 3/05/2017.
 */

public class NanaApi {

    public static String LOGIN_URL = "http://nanamia-001-site1.etempurl.com/api/loginapoderado";
    public static String USER_REGISTER_URL = "http://nanamia-001-site1.etempurl.com/api/usuarioapoderado";
    public static String NANA_URL = "http://nanamia-001-site1.etempurl.com/api/nana";
    public static String SOLICITUD_URL = "http://nanamia-001-site1.etempurl.com/api/solicitud";

    private Usuario currentUser;


    public Usuario getCurrentUsuario() {
        return currentUser;
    }

    public void setCurrentUsuario(Usuario currentUser) {
        this.currentUser = currentUser;
    }
}
