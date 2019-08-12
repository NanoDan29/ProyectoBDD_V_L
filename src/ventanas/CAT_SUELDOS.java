package ventanas;

public class CAT_SUELDOS {

    private String idSueldo;
    private String descripcion;
    private double valor;

    public CAT_SUELDOS() {
    }

    public CAT_SUELDOS(String idSueldo, String descripcion, double valor) {
        this.idSueldo = idSueldo;
        this.descripcion = descripcion;
        this.valor = valor;
    }

    public String getIdSueldo() {
        return idSueldo;
    }

    public void setIdSueldo(String idSueldo) {
        this.idSueldo = idSueldo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
