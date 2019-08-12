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
public class CAT_CONTRATO {
    private String IDCONTRATO;
    private String IDINST;
    private String IDPARAMETRO;
    private String IDSUELDO;
    private String IDFUNCION;
    private String IDCARGO;
    private String IDPERSONA;
    private String EMPLEADOR;
    private String NROCONTRATO;
    private String FECHAINICIO;
    private String FECHAFIN;
    private String ESTADOCONTRATO;
    private int NROHORAS;

    public CAT_CONTRATO(String IDCONTRATO, String IDINST, String IDPARAMETRO, String IDSUELDO, String IDFUNCION, String IDCARGO, String IDPERSONA, String EMPLEADOR, String NROCONTRATO, String FECHAINICIO, String FECHAFIN, String ESTADOCONTRATO, int NROHORAS) {
        this.IDCONTRATO = IDCONTRATO;
        this.IDINST = IDINST;
        this.IDPARAMETRO = IDPARAMETRO;
        this.IDSUELDO = IDSUELDO;
        this.IDFUNCION = IDFUNCION;
        this.IDCARGO = IDCARGO;
        this.IDPERSONA = IDPERSONA;
        this.EMPLEADOR = EMPLEADOR;
        this.NROCONTRATO = NROCONTRATO;
        this.FECHAINICIO = FECHAINICIO;
        this.FECHAFIN = FECHAFIN;
        this.ESTADOCONTRATO = ESTADOCONTRATO;
        this.NROHORAS = NROHORAS;
    }

    public CAT_CONTRATO() {
    }

    public String getIDCONTRATO() {
        return IDCONTRATO;
    }

    public void setIDCONTRATO(String IDCONTRATO) {
        this.IDCONTRATO = IDCONTRATO;
    }

    public String getIDINST() {
        return IDINST;
    }

    public void setIDINST(String IDINST) {
        this.IDINST = IDINST;
    }

    public String getIDPARAMETRO() {
        return IDPARAMETRO;
    }

    public void setIDPARAMETRO(String IDPARAMETRO) {
        this.IDPARAMETRO = IDPARAMETRO;
    }

    public String getIDSUELDO() {
        return IDSUELDO;
    }

    public void setIDSUELDO(String IDSUELDO) {
        this.IDSUELDO = IDSUELDO;
    }

    public String getIDFUNCION() {
        return IDFUNCION;
    }

    public void setIDFUNCION(String IDFUNCION) {
        this.IDFUNCION = IDFUNCION;
    }

    public String getIDCARGO() {
        return IDCARGO;
    }

    public void setIDCARGO(String IDCARGO) {
        this.IDCARGO = IDCARGO;
    }

    public String getIDPERSONA() {
        return IDPERSONA;
    }

    public void setIDPERSONA(String IDPERSONA) {
        this.IDPERSONA = IDPERSONA;
    }

    public String getEMPLEADOR() {
        return EMPLEADOR;
    }

    public void setEMPLEADOR(String EMPLEADOR) {
        this.EMPLEADOR = EMPLEADOR;
    }

    public String getNROCONTRATO() {
        return NROCONTRATO;
    }

    public void setNROCONTRATO(String NROCONTRATO) {
        this.NROCONTRATO = NROCONTRATO;
    }

    public String getFECHAINICIO() {
        return FECHAINICIO;
    }

    public void setFECHAINICIO(String FECHAINICIO) {
        this.FECHAINICIO = FECHAINICIO;
    }

    public String getFECHAFIN() {
        return FECHAFIN;
    }

    public void setFECHAFIN(String FECHAFIN) {
        this.FECHAFIN = FECHAFIN;
    }

    public String getESTADOCONTRATO() {
        return ESTADOCONTRATO;
    }

    public void setESTADOCONTRATO(String ESTADOCONTRATO) {
        this.ESTADOCONTRATO = ESTADOCONTRATO;
    }

    public int getNROHORAS() {
        return NROHORAS;
    }

    public void setNROHORAS(int NROHORAS) {
        this.NROHORAS = NROHORAS;
    }
    
    
}
