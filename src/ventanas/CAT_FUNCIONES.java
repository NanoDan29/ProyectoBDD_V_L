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
public class CAT_FUNCIONES {
    private String IDFUNCION;
    private String IDCARGO;
    private String NOMBRE;

    public CAT_FUNCIONES(String IDFUNCION, String IDCARGO, String NOMBRE) {
        this.IDFUNCION = IDFUNCION;
        this.IDCARGO = IDCARGO;
        this.NOMBRE = NOMBRE;
    }

    public CAT_FUNCIONES() {
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

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }
    
    
}
