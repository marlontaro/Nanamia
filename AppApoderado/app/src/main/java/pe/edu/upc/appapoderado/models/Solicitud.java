package pe.edu.upc.appapoderado.models;

/**
 * Created by Marlon Cordova on 3/05/2017.
 */

public class Solicitud {
    private int tipoContrato;
    private boolean PrimerosAuxilio ;
    private boolean CuidadoEspecial;
    private double SueldoMinimo ;
    private double SueldoMaximo ;
    private String Direccion ;
    private String Longitud;
    private String Latitud;
    private int IdNana ;
    private String Token;

    public int getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(int tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public boolean isPrimerosAuxilio() {
        return PrimerosAuxilio;
    }

    public void setPrimerosAuxilio(boolean primerosAuxilio) {
        PrimerosAuxilio = primerosAuxilio;
    }

    public boolean isCuidadoEspecial() {
        return CuidadoEspecial;
    }

    public void setCuidadoEspecial(boolean cuidadoEspecial) {
        CuidadoEspecial = cuidadoEspecial;
    }

    public double getSueldoMinimo() {
        return SueldoMinimo;
    }

    public void setSueldoMinimo(double sueldoMinimo) {
        SueldoMinimo = sueldoMinimo;
    }

    public double getSueldoMaximo() {
        return SueldoMaximo;
    }

    public void setSueldoMaximo(double sueldoMaximo) {
        SueldoMaximo = sueldoMaximo;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }

    public String getLatitud() {
        return Latitud;
    }

    public void setLatitud(String latitud) {
        Latitud = latitud;
    }

    public int getIdNana() {
        return IdNana;
    }

    public void setIdNana(int idNana) {
        IdNana = idNana;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
