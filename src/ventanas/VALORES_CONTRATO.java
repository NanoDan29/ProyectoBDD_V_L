
package ventanas;

public class VALORES_CONTRATO {
    private String IDSUELDO;
    private String IDPERSONA;
    private double VALOR;

    public VALORES_CONTRATO() {
    }

    public VALORES_CONTRATO(String IDSUELDO, String IDPERSONA, double VALOR) {
        this.IDSUELDO = IDSUELDO;
        this.IDPERSONA = IDPERSONA;
        this.VALOR = VALOR;
    }

    public String getIDSUELDO() {
        return IDSUELDO;
    }

    public void setIDSUELDO(String IDSUELDO) {
        this.IDSUELDO = IDSUELDO;
    }

    public String getIDPERSONA() {
        return IDPERSONA;
    }

    public void setIDPERSONA(String IDPERSONA) {
        this.IDPERSONA = IDPERSONA;
    }

    public double getVALOR() {
        return VALOR;
    }

    public void setVALOR(double VALOR) {
        this.VALOR = VALOR;
    }
    
    
    
    
}
