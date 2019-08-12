/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

/**
 *
 * @author Usuario
 */
public class CAT_EMPLEADO {
    private String Id_Persona;
    private String Cedula;
    private String Nombre;
    private String ApellidoP;
    private String ApellidoM;
    private String FechaNac;
    private String LugarRes;
    private String Direccion;
    private String Telefono;
    private String Email;
    private String Genero;
    private String LugarNac;
    private String EstadoCivil;
    private String TipoSangre;

    public CAT_EMPLEADO(String Id_Persona, String Cedula, String Nombre, String ApellidoP, String ApellidoM, String FechaNac, String LugarRes, String Direccion, String Telefono, String Email, String Genero, String LugarNac, String EstadoCivil, String TipoSangre) {
        this.Id_Persona = Id_Persona;
        this.Cedula = Cedula;
        this.Nombre = Nombre;
        this.ApellidoP = ApellidoP;
        this.ApellidoM = ApellidoM;
        this.FechaNac = FechaNac;
        this.LugarRes = LugarRes;
        this.Direccion = Direccion;
        this.Telefono = Telefono;
        this.Email = Email;
        this.Genero = Genero;
        this.LugarNac = LugarNac;
        this.EstadoCivil = EstadoCivil;
        this.TipoSangre = TipoSangre;
    }

    public CAT_EMPLEADO() {
    }

    public String getId_Persona() {
        return Id_Persona;
    }

    public void setId_Persona(String Id_Persona) {
        this.Id_Persona = Id_Persona;
    }

    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String Cedula) {
        this.Cedula = Cedula;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidoP() {
        return ApellidoP;
    }

    public void setApellidoP(String ApellidoP) {
        this.ApellidoP = ApellidoP;
    }

    public String getApellidoM() {
        return ApellidoM;
    }

    public void setApellidoM(String ApellidoM) {
        this.ApellidoM = ApellidoM;
    }

    public String getFechaNac() {
        return FechaNac;
    }

    public void setFechaNac(String FechaNac) {
        this.FechaNac = FechaNac;
    }

    public String getLugarRes() {
        return LugarRes;
    }

    public void setLugarRes(String LugarRes) {
        this.LugarRes = LugarRes;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String Genero) {
        this.Genero = Genero;
    }

    public String getLugarNac() {
        return LugarNac;
    }

    public void setLugarNac(String LugarNac) {
        this.LugarNac = LugarNac;
    }

    public String getEstadoCivil() {
        return EstadoCivil;
    }

    public void setEstadoCivil(String EstadoCivil) {
        this.EstadoCivil = EstadoCivil;
    }

    public String getTipoSangre() {
        return TipoSangre;
    }

    public void setTipoSangre(String TipoSangre) {
        this.TipoSangre = TipoSangre;
    }
    
//     @Override
//    public String toString() {
//        return Id_Persona + " " + ApellidoP + " " + ApellidoM;
//    } 
    
    
    
    
    
    
    
    
    
}
