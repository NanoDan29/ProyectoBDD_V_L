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
public class MostrarContrato {
    private String idcontrato;
    private String nombrefuncion;
    private String descripcion;
    private int valor;
    private String nombre;
    private String nombreem;
    private int nrocontra;
    private String fechai;
    private String fechaf;
    private String estado;
    private int nroHoras;

    public MostrarContrato(String idcontrato, String nombrefuncion, String descripcion, int valor, String nombre, String nombreem, int nrocontra, String fechai, String fechaf, String estado, int nroHoras) {
        this.idcontrato = idcontrato;
        this.nombrefuncion = nombrefuncion;
        this.descripcion = descripcion;
        this.valor = valor;
        this.nombre = nombre;
        this.nombreem = nombreem;
        this.nrocontra = nrocontra;
        this.fechai = fechai;
        this.fechaf = fechaf;
        this.estado = estado;
        this.nroHoras = nroHoras;
    }

    public String getIdcontrato() {
        return idcontrato;
    }

    public void setIdcontrato(String idcontrato) {
        this.idcontrato = idcontrato;
    }

    public String getNombrefuncion() {
        return nombrefuncion;
    }

    public void setNombrefuncion(String nombrefuncion) {
        this.nombrefuncion = nombrefuncion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreem() {
        return nombreem;
    }

    public void setNombreem(String nombreem) {
        this.nombreem = nombreem;
    }

    public int getNrocontra() {
        return nrocontra;
    }

    public void setNrocontra(int nrocontra) {
        this.nrocontra = nrocontra;
    }

    public String getFechai() {
        return fechai;
    }

    public void setFechai(String fechai) {
        this.fechai = fechai;
    }

    public String getFechaf() {
        return fechaf;
    }

    public void setFechaf(String fechaf) {
        this.fechaf = fechaf;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getNroHoras() {
        return nroHoras;
    }

    public void setNroHoras(int nroHoras) {
        this.nroHoras = nroHoras;
    }
    
}
