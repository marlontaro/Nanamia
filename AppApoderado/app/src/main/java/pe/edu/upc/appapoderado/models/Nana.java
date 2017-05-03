package pe.edu.upc.appapoderado.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Nana {

    private int id;
    private String nombre;
    private String apellido;
    private int edad;
    private double puntaje;
    private String experiencia;
    private String avatar;
    private double salarioMinimo;
    private double salarioMaximo;
    private boolean primerosAuxilio;
    private boolean cuidadoEspecial;

    public int getId() {
        return id;
    }

    public Nana setId(int id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public Nana setNombre(String nombre) {
        this.nombre = nombre;
        return  this;
    }

    public String getApellido() {
        return apellido;
    }

    public Nana setApellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public int getEdad() {
        return edad;
    }

    public Nana setEdad(int edad) {
        this.edad = edad;
        return this;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public Nana setPuntaje(double puntaje) {
        this.puntaje = puntaje;
        return this;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public Nana setExperiencia(String experiencia) {
        this.experiencia = experiencia;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public Nana setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public double getSalarioMinimo() {
        return salarioMinimo;
    }

    public Nana setSalarioMinimo(double salarioMinimo) {
        this.salarioMinimo = salarioMinimo;
        return this;
    }

    public double getSalarioMaximo() {
        return salarioMaximo;
    }

    public Nana setSalarioMaximo(double salarioMaximo) {
        this.salarioMaximo = salarioMaximo;
        return this;
    }

    public boolean isPrimerosAuxilio() {
        return primerosAuxilio;
    }

    public Nana setPrimerosAuxilio(boolean primerosAuxilio) {
        this.primerosAuxilio = primerosAuxilio;
        return this;
    }

    public boolean isCuidadoEspecial() {
        return cuidadoEspecial;
    }

    public Nana setCuidadoEspecial(boolean cuidadoEspecial) {
        this.cuidadoEspecial = cuidadoEspecial;
        return this;
    }

    public static Nana build(JSONObject jsonSource) {
        if(jsonSource == null) return null;
        Nana user = new Nana();
        try {
                    /*"Id": 34,
                    "Nombre": "Mary",
                    "Apellido": "Poppins",
                    "Edad": 42,
                    "Puntaje": 4.60,
                    "Experiencia": "Mas de 20 años cuidando nenes.",
                    "Avatar": "1.jpg",
                    "SalarioMinimo": 1200.00,
                    "SalarioMaximo": 1500.00,
                    "PrimerosAuxilio": true,
                    "CuidadoEspecial": true*/

            user.setId(jsonSource.getInt("Id"))
                    .setNombre(jsonSource. getString("Nombre"))
                    .setApellido(jsonSource.getString("Apellido"))
                    .setEdad(jsonSource.getInt("Edad"))
                    .setPuntaje(jsonSource.getDouble("Puntaje"))
                    .setExperiencia(jsonSource.getString("Experiencia"))
                    .setAvatar(jsonSource.getString("Avatar"))
                    .setSalarioMinimo(jsonSource.getDouble("SalarioMinimo"))
                    .setSalarioMaximo(jsonSource.getDouble("SalarioMaximo"))
                    .setPrimerosAuxilio(jsonSource.getBoolean("PrimerosAuxilio"))
                    .setCuidadoEspecial(jsonSource.getBoolean("CuidadoEspecial"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
