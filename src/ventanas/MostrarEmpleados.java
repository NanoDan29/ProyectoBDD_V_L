/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

/**
 *
 * @author Susana
 */
public class MostrarEmpleados {
    private String cedula;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMat;
    private String email;
    private String genero;
    private String nroContrato;
    private String fechaInicio;
    private String fechaFinal;
    private String nombreFun;
    private int valor;
    private String nombreEstr;

    public MostrarEmpleados(String cedula, String nombre, String apellidoPaterno, String apellidoMat, String email, String genero, String nroContrato, String fechaInicio, String fechaFinal, String nombreFun, int valor, String nombreEstr) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMat = apellidoMat;
        this.email = email;
        this.genero = genero;
        this.nroContrato = nroContrato;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.nombreFun = nombreFun;
        this.valor = valor;
        this.nombreEstr = nombreEstr;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMat() {
        return apellidoMat;
    }

    public void setApellidoMat(String apellidoMat) {
        this.apellidoMat = apellidoMat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNroContrato() {
        return nroContrato;
    }

    public void setNroContrato(String nroContrato) {
        this.nroContrato = nroContrato;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getNombreFun() {
        return nombreFun;
    }

    public void setNombreFun(String nombreFun) {
        this.nombreFun = nombreFun;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getNombreEstr() {
        return nombreEstr;
    }

    public void setNombreEstr(String nombreEstr) {
        this.nombreEstr = nombreEstr;
    }
    
    
    
    
}
