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
public class MostrarFuncion {
    private String nombreFun;
    private String idcontrato;
    private String cedula;
    private String nombre;
    private String apellido;

    public MostrarFuncion(String nombreFun, String idcontrato, String cedula, String nombre, String apellido) {
        this.nombreFun = nombreFun;
        this.idcontrato = idcontrato;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getNombreFun() {
        return nombreFun;
    }

    public void setNombreFun(String nombreFun) {
        this.nombreFun = nombreFun;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    
    
}
