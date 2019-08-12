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
public class MostrarInstitucion {
    private String  nombreInstitucion;
    private String idcontrato;
    private String cedula;
    private String nombrePersona;
    private String apellidoPaterno;
    private String nombreFuncion;
    private String nombreCargo;
    private int valor;

    public MostrarInstitucion(String nombreInstitucion, String idcontrato, String cedula, String nombrePersona, String apellidoPaterno, String nombreFuncion, String nombreCargo, int valor) {
        this.nombreInstitucion = nombreInstitucion;
        this.idcontrato = idcontrato;
        this.cedula = cedula;
        this.nombrePersona = nombrePersona;
        this.apellidoPaterno = apellidoPaterno;
        this.nombreFuncion = nombreFuncion;
        this.nombreCargo = nombreCargo;
        this.valor = valor;
    }

    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    public void setNombreInstitucion(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    public String getIdcontrato() {
        return idcontrato;
    }

    public void setIdcontrato(String idcontrato) {
        this.idcontrato = idcontrato;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getNombreFuncion() {
        return nombreFuncion;
    }

    public void setNombreFuncion(String nombreFuncion) {
        this.nombreFuncion = nombreFuncion;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
    
    
    
}
