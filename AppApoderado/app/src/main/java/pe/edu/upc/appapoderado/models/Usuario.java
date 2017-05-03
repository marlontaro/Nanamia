package pe.edu.upc.appapoderado.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Marlon Cordova on 3/05/2017.
 */

public class Usuario {

    private String nombre;
    private String apellido;
    private String dni;
    private String celular ;
    private String correo;
    private String token;
    private String avatar;


    public String getNombre() {
        return nombre;
    }

    public Usuario setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getApellido() {
        return apellido;
    }

    public Usuario setApellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public String getDni() {
        return dni;
    }

    public Usuario setDni(String dni) {
        this.dni = dni;
        return this;
    }

    public String getCelular() {
        return celular;
    }

    public Usuario setCelular(String celular) {
        this.celular = celular;
        return this;
    }

    public String getCorreo() {
        return correo;
    }

    public Usuario setCorreo(String correo) {
        this.correo = correo;
        return this;
    }

    public String getToken() {
        return token;
    }

    public Usuario setToken(String token) {
        this.token = token;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public Usuario setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public static Usuario build(JSONObject jsonSource) {
        if(jsonSource == null) return null;
        Usuario user = new Usuario();
        try {

            user.setNombre(jsonSource.getString("nombre"))
                    .setApellido(jsonSource.getString("apellido"))
                    .setCelular(jsonSource.getString("celular"))
                    .setDni(jsonSource.getString("dni"))
                    .setCorreo(jsonSource.getString("correo"))
                    .setToken(jsonSource.getString("token"))
                    .setAvatar(jsonSource.getString("avatar"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
