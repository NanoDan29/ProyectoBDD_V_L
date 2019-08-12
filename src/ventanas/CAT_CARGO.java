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
public class CAT_CARGO {
    private String ID_CARGO;
    private String NOMBRE;

    public CAT_CARGO(String ID_CARGO, String NOMBRE) {
        this.ID_CARGO = ID_CARGO;
        this.NOMBRE = NOMBRE;
    }

    public CAT_CARGO() {
    }

    public String getID_CARGO() {
        return ID_CARGO;
    }

    public void setID_CARGO(String ID_CARGO) {
        this.ID_CARGO = ID_CARGO;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }
    
    
}
