package ventanas;

public class EST_INSTITUCION {

    private String idInst;
    private String idInstP;
    private String nombreFuncion;
    private String nivel;
    private String ultimoNivel;

    public EST_INSTITUCION(String idInst, String idInstP, String nombreFuncion, String nivel, String ultimoNivel) {
        this.idInst = idInst;
        this.idInstP = idInstP;
        this.nombreFuncion = nombreFuncion;
        this.nivel = nivel;
        this.ultimoNivel = ultimoNivel;
    }

    public EST_INSTITUCION() {
    }

    public String getIdInst() {
        return idInst;
    }

    public void setIdInst(String idInst) {
        this.idInst = idInst;
    }

    public String getIdInstP() {
        return idInstP;
    }

    public void setIdInstP(String idInstP) {
        this.idInstP = idInstP;
    }

    public String getNombreFuncion() {
        return nombreFuncion;
    }

    public void setNombreFuncion(String nombreFuncion) {
        this.nombreFuncion = nombreFuncion;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getUltimoNivel() {
        return ultimoNivel;
    }

    public void setUltimoNivel(String ultimoNivel) {
        this.ultimoNivel = ultimoNivel;
    }

    @Override
    public String toString() {
        return idInst + " " + nombreFuncion;
    }  
}