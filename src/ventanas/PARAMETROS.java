package ventanas;

public class PARAMETROS {
    private String idParametro;
    private String descripcion;

    public PARAMETROS() {
    }

    public PARAMETROS(String idParametro, String descripcion) {
        this.idParametro = idParametro;
        this.descripcion = descripcion;
    }

    public String getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(String idParametro) {
        this.idParametro = idParametro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
//    
//    @Override
//    public String toString() {
//        return idParametro + " " + descripcion;
//    } 
}
