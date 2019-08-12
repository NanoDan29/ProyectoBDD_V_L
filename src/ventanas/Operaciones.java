package ventanas;

import java.security.Principal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class Operaciones {

    public String msm = "";
    ////1521

    ///METODOS QUE PERMITE LA CONEXION CON LA BASE DE DATOS  --->>>> "jdbc:oracle:thin:@localhost:1521:orcl"
    public boolean conexionOracle(String usuario, String contraseña, String host, String puerto, String ip) {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String url = "";
            url = "jdbc:oracle:thin:@" + ip + ":" + puerto + ":" + host;
            Connection connection = DriverManager.getConnection(url, usuario, contraseña);
            Statement statement = connection.createStatement();
            msm = "Conexión Exitosa";
            return true;
        } catch (SQLException e) {
            msm = e.getMessage();
            return false;
        }
    }

    public Statement conexionOp(String usuario, String contraseña, String host, String puerto, String ip) {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String url = "";
            url = "jdbc:oracle:thin:@" + ip + ":" + puerto + ":" + host;
            Connection connection = DriverManager.getConnection(url, usuario, contraseña);
            Statement statement = connection.createStatement();
            msm = "Conexión Exitosa";
            return statement;
        } catch (SQLException e) {
            msm = e.getMessage();
            return null;
        }
    }

    public ArrayList<String> getTablas(String usuario, String contraseña, String host, String puerto, String ip) {
        ArrayList<String> listaTablas = new ArrayList<String>();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("select TABLE_NAME from all_tables where owner = '" + usuario + "'");
            String tabla = null;
            while (rs.next()) {
                tabla = new String(rs.getString(1));
                listaTablas.add(tabla);
            }
            return listaTablas;
        } catch (SQLException e) {
            msm = e.getMessage();
            return null;
        }
    }

    public ArrayList<EST_INSTITUCION> getInstitucion(String usuario, String contraseña, String host, String puerto, String ip) {
        ArrayList<EST_INSTITUCION> lista = new ArrayList<EST_INSTITUCION>();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("select * from EST_INSTITUCIONAL ORDER BY IDINST");
            EST_INSTITUCION inst = null;
            while (rs.next()) {
                inst = new EST_INSTITUCION(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                lista.add(inst);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    /// METODO QUE PERMITE OBTENER EL REGISTRO DE LA TABLA DE LA BASE DE DATOS POR LA ID
    public ArrayList<EST_INSTITUCION> getInstitucions(String usuario, String contraseña, String host, String puerto, String ip, String id) {
        ArrayList<EST_INSTITUCION> listas = new ArrayList<EST_INSTITUCION>();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("select * from EST_INSTITUCIONAL where IDINST = '" + id + "'");
            EST_INSTITUCION inst = null;
            while (rs.next()) {
                inst = new EST_INSTITUCION(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                listas.add(inst);
            }
            return listas;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    /// METODO PARA GUARDAR UN REGISTRO
    public void Guardar(String usuario, String contraseña, String host, String puerto, String ip, String id, String idP, String nombre, String nivel, String ultNivel) {
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            st.executeQuery("insert into EST_INSTITUCIONAL (IDINST, IDINSTP, NOMFUNCION, NIVEL, ULTNIVEL)\n"
                    + "  values ('" + id + "', '" + idP + "', '" + nombre + "', " + nivel + ", '" + ultNivel + "')");
            msm = "SE HA INGRESADO";
        } catch (SQLException ex) {
            msm = ex.getMessage();
        }
    }

    /// METODO PARA BORRAR UN REGISTRO
    public void Borrar(String usuario, String contraseña, String host, String puerto, String ip, String id) {
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            st.executeQuery("delete from EST_INSTITUCIONAL where IDINST = '" + id + "'");
            msm = "SE HA BORRADO";
        } catch (SQLException ex) {
            msm = ex.getMessage();
        }
    }

    ///METODO PARA ACTUALIZAR UN REGISTRO
    public void Actualizar(String usuario, String contraseña, String host, String puerto, String ip, String id, String nombre, String ultNivel) {
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            st.executeQuery("update EST_INSTITUCIONAL set NOMFUNCION='" + nombre + "', ULTNIVEL='" + ultNivel
                    + "' where IDINST = '" + id + "'");
            msm = "SE HA ACTUALIDADO";
        } catch (SQLException ex) {
            msm = ex.getMessage();
        }
    }

    //==============EMPLEADOS===========//////////
    ////METODO MOSTRAR
    public ArrayList<CAT_EMPLEADO> ListEmpleado(String usuario, String contraseña, String host, String puerto, String ip) {
        ArrayList<CAT_EMPLEADO> Empleado = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT * FROM CAT_EMPLEADOS ORDER BY IDPERSONA ASC");
            while (rs.next()) {
                CAT_EMPLEADO est = new CAT_EMPLEADO();
                est.setId_Persona(rs.getString("IDPERSONA"));
                est.setCedula(rs.getString("CEDULA"));
                est.setNombre(rs.getString("NOMBRE"));
                est.setApellidoP(rs.getString("APELLIDOP"));
                est.setApellidoM(rs.getString("APELLIDOM"));
                est.setFechaNac(rs.getString("FECHANAC"));
                est.setLugarRes(rs.getString("LUGARRESI"));
                est.setDireccion(rs.getString("DIRECCION"));
                est.setTelefono(rs.getString("TELEFONO"));
                est.setEmail(rs.getString("EMAIL"));
                est.setGenero(rs.getString("GENERO"));
                est.setLugarNac(rs.getString("LUGARNAC"));
                est.setEstadoCivil(rs.getString("ESTADOCIVIL"));
                est.setTipoSangre(rs.getString("TIPOSANGRE"));
                Empleado.add(est);
            }
            return Empleado;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    public void IngresarEmpleado(String usuario, String contraseña, String host, String puerto, String ip, int id, String cedula, String nombre, String apellidop, String apellidom, String fechanac, String lugarresi, String direccion, String telefono, String email, String genero, String lugarnac, String estadocivil, String tiposangre) {
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            st.executeQuery("insert into CAT_EMPLEADOS (IDPERSONA,CEDULA,NOMBRE,APELLIDOP,APELLIDOM,FECHANAC,LUGARRESI,DIRECCION,TELEFONO,EMAIL,GENERO,LUGARNAC,ESTADOCIVIL,TIPOSANGRE)\n"
                    + "  values ('" + id + "', '" + cedula + "','" + nombre + "','" + apellidop + "','" + apellidom + "','" + fechanac + "','" + lugarresi + "','" + direccion + "','" + telefono + "','" + email + "','" + genero + "','" + lugarnac + "','" + estadocivil + "','" + tiposangre + "')");
            msm = "SE HA INGRESADO";
        } catch (SQLException ex) {
            msm = ex.getMessage();
        }
    }
///METODO PARA BORRAR

    public void BorrarEmpleado(String usuario, String contraseña, String host, String puerto, String ip, String id) {
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            st.executeQuery("delete from CAT_EMPLEADOS where IDPERSONA = '" + id + "'");
            msm = "SE HA BORRADO";
        } catch (SQLException ex) {
            msm = ex.getMessage();
        }
    }

    ///METODO PARA ACTUALIZAR
    public void ActualizarEmpleado(String usuario, String contraseña, String host, String puerto, String ip, int id, String cedula, String nombre, String apellidop, String apellidom, String fechanac, String lugarresi, String direccion, String telefono, String email, String genero, String lugarnac, String estadocivil, String tiposangre) {
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            st.executeQuery("update CAT_EMPLEADOS set CEDULA='" + cedula + "', NOMBRE='" + nombre + "', APELLIDOP='" + apellidop + "',APELLIDOM='" + apellidom + "',FECHANAC='" + fechanac + "',LUGARRESI='" + lugarresi + "',DIRECCION='" + direccion + "',TELEFONO='" + telefono + "',EMAIL='" + email + "',GENERO='" + genero + "',LUGARNAC='" + lugarnac + "',ESTADOCIVIL='" + estadocivil + "',TIPOSANGRE='" + tiposangre + "' where IDPERSONA = '" + id + "'");
            msm = "SE HA ACTUALIDADO";
        } catch (SQLException ex) {
            msm = ex.getMessage();
        }
    }

    //BUSCAR EMPLEADO
    public ArrayList<CAT_EMPLEADO> FiltrarIdE(String usuario, String contraseña, String host, String puerto, String ip, String nombre) {
        ArrayList<CAT_EMPLEADO> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM CAT_EMPLEADOS WHERE IDPERSONA LIKE '" + nombre + "%'");
            while (rs.next()) {
                CAT_EMPLEADO est = new CAT_EMPLEADO();
                est.setId_Persona(rs.getString("IDPERSONA"));
                est.setCedula(rs.getString("CEDULA"));
                est.setNombre(rs.getString("NOMBRE"));
                est.setApellidoP(rs.getString("APELLIDOP"));
                est.setApellidoM(rs.getString("APELLIDOM"));
                est.setFechaNac(rs.getString("FECHANAC"));
                est.setLugarRes(rs.getString("LUGARRESI"));
                est.setDireccion(rs.getString("DIRECCION"));
                est.setTelefono(rs.getString("TELEFONO"));
                est.setEmail(rs.getString("EMAIL"));
                est.setGenero(rs.getString("GENERO"));
                est.setLugarNac(rs.getString("LUGARNAC"));
                est.setEstadoCivil(rs.getString("ESTADOCIVIL"));
                est.setTipoSangre(rs.getString("TIPOSANGRE"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<CAT_EMPLEADO> FiltrarNombreE(String usuario, String contraseña, String host, String puerto, String ip, String nombre) {
        ArrayList<CAT_EMPLEADO> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM CAT_EMPLEADOS WHERE NOMBRE LIKE '" + nombre + "%'");
            while (rs.next()) {
                CAT_EMPLEADO est = new CAT_EMPLEADO();
                est.setId_Persona(rs.getString("IDPERSONA"));
                est.setCedula(rs.getString("CEDULA"));
                est.setNombre(rs.getString("NOMBRE"));
                est.setApellidoP(rs.getString("APELLIDOP"));
                est.setApellidoM(rs.getString("APELLIDOM"));
                est.setFechaNac(rs.getString("FECHANAC"));
                est.setLugarRes(rs.getString("LUGARRESI"));
                est.setDireccion(rs.getString("DIRECCION"));
                est.setTelefono(rs.getString("TELEFONO"));
                est.setEmail(rs.getString("EMAIL"));
                est.setGenero(rs.getString("GENERO"));
                est.setLugarNac(rs.getString("LUGARNAC"));
                est.setEstadoCivil(rs.getString("ESTADOCIVIL"));
                est.setTipoSangre(rs.getString("TIPOSANGRE"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<CAT_EMPLEADO> FiltrarApellidoPE(String usuario, String contraseña, String host, String puerto, String ip, String nombre) {
        ArrayList<CAT_EMPLEADO> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM CAT_EMPLEADOS WHERE APELLIDOP LIKE '" + nombre + "%'");
            while (rs.next()) {
                CAT_EMPLEADO est = new CAT_EMPLEADO();
                est.setId_Persona(rs.getString("IDPERSONA"));
                est.setCedula(rs.getString("CEDULA"));
                est.setNombre(rs.getString("NOMBRE"));
                est.setApellidoP(rs.getString("APELLIDOP"));
                est.setApellidoM(rs.getString("APELLIDOM"));
                est.setFechaNac(rs.getString("FECHANAC"));
                est.setLugarRes(rs.getString("LUGARRESI"));
                est.setDireccion(rs.getString("DIRECCION"));
                est.setTelefono(rs.getString("TELEFONO"));
                est.setEmail(rs.getString("EMAIL"));
                est.setGenero(rs.getString("GENERO"));
                est.setLugarNac(rs.getString("LUGARNAC"));
                est.setEstadoCivil(rs.getString("ESTADOCIVIL"));
                est.setTipoSangre(rs.getString("TIPOSANGRE"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<CAT_EMPLEADO> FiltrarApellidoME(String usuario, String contraseña, String host, String puerto, String ip, String nombre) {
        ArrayList<CAT_EMPLEADO> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM CAT_EMPLEADOS WHERE APELLIDOM LIKE '" + nombre + "%'");
            while (rs.next()) {
                CAT_EMPLEADO est = new CAT_EMPLEADO();
                est.setId_Persona(rs.getString("IDPERSONA"));
                est.setCedula(rs.getString("CEDULA"));
                est.setNombre(rs.getString("NOMBRE"));
                est.setApellidoP(rs.getString("APELLIDOP"));
                est.setApellidoM(rs.getString("APELLIDOM"));
                est.setFechaNac(rs.getString("FECHANAC"));
                est.setLugarRes(rs.getString("LUGARRESI"));
                est.setDireccion(rs.getString("DIRECCION"));
                est.setTelefono(rs.getString("TELEFONO"));
                est.setEmail(rs.getString("EMAIL"));
                est.setGenero(rs.getString("GENERO"));
                est.setLugarNac(rs.getString("LUGARNAC"));
                est.setEstadoCivil(rs.getString("ESTADOCIVIL"));
                est.setTipoSangre(rs.getString("TIPOSANGRE"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<CAT_EMPLEADO> FiltrarFachaNacE(String usuario, String contraseña, String host, String puerto, String ip, String nombre) {
        ArrayList<CAT_EMPLEADO> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM CAT_EMPLEADOS WHERE FECHANAC LIKE '" + nombre + "%'");
            while (rs.next()) {
                CAT_EMPLEADO est = new CAT_EMPLEADO();
                est.setId_Persona(rs.getString("IDPERSONA"));
                est.setCedula(rs.getString("CEDULA"));
                est.setNombre(rs.getString("NOMBRE"));
                est.setApellidoP(rs.getString("APELLIDOP"));
                est.setApellidoM(rs.getString("APELLIDOM"));
                est.setFechaNac(rs.getString("FECHANAC"));
                est.setLugarRes(rs.getString("LUGARRESI"));
                est.setDireccion(rs.getString("DIRECCION"));
                est.setTelefono(rs.getString("TELEFONO"));
                est.setEmail(rs.getString("EMAIL"));
                est.setGenero(rs.getString("GENERO"));
                est.setLugarNac(rs.getString("LUGARNAC"));
                est.setEstadoCivil(rs.getString("ESTADOCIVIL"));
                est.setTipoSangre(rs.getString("TIPOSANGRE"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<CAT_EMPLEADO> FiltrarLugarResiE(String usuario, String contraseña, String host, String puerto, String ip, String nombre) {
        ArrayList<CAT_EMPLEADO> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM CAT_EMPLEADOS WHERE LUGARRESI LIKE '" + nombre + "%'");
            while (rs.next()) {
                CAT_EMPLEADO est = new CAT_EMPLEADO();
                est.setId_Persona(rs.getString("IDPERSONA"));
                est.setCedula(rs.getString("CEDULA"));
                est.setNombre(rs.getString("NOMBRE"));
                est.setApellidoP(rs.getString("APELLIDOP"));
                est.setApellidoM(rs.getString("APELLIDOM"));
                est.setFechaNac(rs.getString("FECHANAC"));
                est.setLugarRes(rs.getString("LUGARRESI"));
                est.setDireccion(rs.getString("DIRECCION"));
                est.setTelefono(rs.getString("TELEFONO"));
                est.setEmail(rs.getString("EMAIL"));
                est.setGenero(rs.getString("GENERO"));
                est.setLugarNac(rs.getString("LUGARNAC"));
                est.setEstadoCivil(rs.getString("ESTADOCIVIL"));
                est.setTipoSangre(rs.getString("TIPOSANGRE"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<CAT_EMPLEADO> FiltrarDireccionE(String usuario, String contraseña, String host, String puerto, String ip, String nombre) {
        ArrayList<CAT_EMPLEADO> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM CAT_EMPLEADOS WHERE DIRECCION LIKE '" + nombre + "%'");
            while (rs.next()) {
                CAT_EMPLEADO est = new CAT_EMPLEADO();
                est.setId_Persona(rs.getString("IDPERSONA"));
                est.setCedula(rs.getString("CEDULA"));
                est.setNombre(rs.getString("NOMBRE"));
                est.setApellidoP(rs.getString("APELLIDOP"));
                est.setApellidoM(rs.getString("APELLIDOM"));
                est.setFechaNac(rs.getString("FECHANAC"));
                est.setLugarRes(rs.getString("LUGARRESI"));
                est.setDireccion(rs.getString("DIRECCION"));
                est.setTelefono(rs.getString("TELEFONO"));
                est.setEmail(rs.getString("EMAIL"));
                est.setGenero(rs.getString("GENERO"));
                est.setLugarNac(rs.getString("LUGARNAC"));
                est.setEstadoCivil(rs.getString("ESTADOCIVIL"));
                est.setTipoSangre(rs.getString("TIPOSANGRE"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<CAT_EMPLEADO> FiltrarTelefonoE(String usuario, String contraseña, String host, String puerto, String ip, String nombre) {
        ArrayList<CAT_EMPLEADO> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM CAT_EMPLEADOS WHERE TELEFONO LIKE '" + nombre + "%'");
            while (rs.next()) {
                CAT_EMPLEADO est = new CAT_EMPLEADO();
                est.setId_Persona(rs.getString("IDPERSONA"));
                est.setCedula(rs.getString("CEDULA"));
                est.setNombre(rs.getString("NOMBRE"));
                est.setApellidoP(rs.getString("APELLIDOP"));
                est.setApellidoM(rs.getString("APELLIDOM"));
                est.setFechaNac(rs.getString("FECHANAC"));
                est.setLugarRes(rs.getString("LUGARRESI"));
                est.setDireccion(rs.getString("DIRECCION"));
                est.setTelefono(rs.getString("TELEFONO"));
                est.setEmail(rs.getString("EMAIL"));
                est.setGenero(rs.getString("GENERO"));
                est.setLugarNac(rs.getString("LUGARNAC"));
                est.setEstadoCivil(rs.getString("ESTADOCIVIL"));
                est.setTipoSangre(rs.getString("TIPOSANGRE"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<CAT_EMPLEADO> FiltrarEmailE(String usuario, String contraseña, String host, String puerto, String ip, String nombre) {
        ArrayList<CAT_EMPLEADO> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM CAT_EMPLEADOS WHERE EMAIL LIKE '" + nombre + "%'");
            while (rs.next()) {
                CAT_EMPLEADO est = new CAT_EMPLEADO();
                est.setId_Persona(rs.getString("IDPERSONA"));
                est.setCedula(rs.getString("CEDULA"));
                est.setNombre(rs.getString("NOMBRE"));
                est.setApellidoP(rs.getString("APELLIDOP"));
                est.setApellidoM(rs.getString("APELLIDOM"));
                est.setFechaNac(rs.getString("FECHANAC"));
                est.setLugarRes(rs.getString("LUGARRESI"));
                est.setDireccion(rs.getString("DIRECCION"));
                est.setTelefono(rs.getString("TELEFONO"));
                est.setEmail(rs.getString("EMAIL"));
                est.setGenero(rs.getString("GENERO"));
                est.setLugarNac(rs.getString("LUGARNAC"));
                est.setEstadoCivil(rs.getString("ESTADOCIVIL"));
                est.setTipoSangre(rs.getString("TIPOSANGRE"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<CAT_EMPLEADO> FiltrarGeneroE(String usuario, String contraseña, String host, String puerto, String ip, String nombre) {
        ArrayList<CAT_EMPLEADO> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM CAT_EMPLEADOS WHERE GENERO LIKE '" + nombre + "%'");
            while (rs.next()) {
                CAT_EMPLEADO est = new CAT_EMPLEADO();
                est.setId_Persona(rs.getString("IDPERSONA"));
                est.setCedula(rs.getString("CEDULA"));
                est.setNombre(rs.getString("NOMBRE"));
                est.setApellidoP(rs.getString("APELLIDOP"));
                est.setApellidoM(rs.getString("APELLIDOM"));
                est.setFechaNac(rs.getString("FECHANAC"));
                est.setLugarRes(rs.getString("LUGARRESI"));
                est.setDireccion(rs.getString("DIRECCION"));
                est.setTelefono(rs.getString("TELEFONO"));
                est.setEmail(rs.getString("EMAIL"));
                est.setGenero(rs.getString("GENERO"));
                est.setLugarNac(rs.getString("LUGARNAC"));
                est.setEstadoCivil(rs.getString("ESTADOCIVIL"));
                est.setTipoSangre(rs.getString("TIPOSANGRE"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<CAT_EMPLEADO> FiltrarLugarNacE(String usuario, String contraseña, String host, String puerto, String ip, String nombre) {
        ArrayList<CAT_EMPLEADO> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM CAT_EMPLEADOS WHERE LUGARNAC LIKE '" + nombre + "%'");
            while (rs.next()) {
                CAT_EMPLEADO est = new CAT_EMPLEADO();
                est.setId_Persona(rs.getString("IDPERSONA"));
                est.setCedula(rs.getString("CEDULA"));
                est.setNombre(rs.getString("NOMBRE"));
                est.setApellidoP(rs.getString("APELLIDOP"));
                est.setApellidoM(rs.getString("APELLIDOM"));
                est.setFechaNac(rs.getString("FECHANAC"));
                est.setLugarRes(rs.getString("LUGARRESI"));
                est.setDireccion(rs.getString("DIRECCION"));
                est.setTelefono(rs.getString("TELEFONO"));
                est.setEmail(rs.getString("EMAIL"));
                est.setGenero(rs.getString("GENERO"));
                est.setLugarNac(rs.getString("LUGARNAC"));
                est.setEstadoCivil(rs.getString("ESTADOCIVIL"));
                est.setTipoSangre(rs.getString("TIPOSANGRE"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<CAT_EMPLEADO> FiltrarEstadoCivilE(String usuario, String contraseña, String host, String puerto, String ip, String nombre) {
        ArrayList<CAT_EMPLEADO> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM CAT_EMPLEADOS WHERE ESTADOCIVIL LIKE '" + nombre + "%'");
            while (rs.next()) {
                CAT_EMPLEADO est = new CAT_EMPLEADO();
                est.setId_Persona(rs.getString("IDPERSONA"));
                est.setCedula(rs.getString("CEDULA"));
                est.setNombre(rs.getString("NOMBRE"));
                est.setApellidoP(rs.getString("APELLIDOP"));
                est.setApellidoM(rs.getString("APELLIDOM"));
                est.setFechaNac(rs.getString("FECHANAC"));
                est.setLugarRes(rs.getString("LUGARRESI"));
                est.setDireccion(rs.getString("DIRECCION"));
                est.setTelefono(rs.getString("TELEFONO"));
                est.setEmail(rs.getString("EMAIL"));
                est.setGenero(rs.getString("GENERO"));
                est.setLugarNac(rs.getString("LUGARNAC"));
                est.setEstadoCivil(rs.getString("ESTADOCIVIL"));
                est.setTipoSangre(rs.getString("TIPOSANGRE"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<CAT_EMPLEADO> FiltrarTipoSangreE(String usuario, String contraseña, String host, String puerto, String ip, String nombre) {
        ArrayList<CAT_EMPLEADO> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM CAT_EMPLEADOS WHERE TIPOSANGRE LIKE '" + nombre + "%'");
            while (rs.next()) {
                CAT_EMPLEADO est = new CAT_EMPLEADO();
                est.setId_Persona(rs.getString("IDPERSONA"));
                est.setCedula(rs.getString("CEDULA"));
                est.setNombre(rs.getString("NOMBRE"));
                est.setApellidoP(rs.getString("APELLIDOP"));
                est.setApellidoM(rs.getString("APELLIDOM"));
                est.setFechaNac(rs.getString("FECHANAC"));
                est.setLugarRes(rs.getString("LUGARRESI"));
                est.setDireccion(rs.getString("DIRECCION"));
                est.setTelefono(rs.getString("TELEFONO"));
                est.setEmail(rs.getString("EMAIL"));
                est.setGenero(rs.getString("GENERO"));
                est.setLugarNac(rs.getString("LUGARNAC"));
                est.setEstadoCivil(rs.getString("ESTADOCIVIL"));
                est.setTipoSangre(rs.getString("TIPOSANGRE"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<CAT_EMPLEADO> FiltrarCedulaE(String usuario, String contraseña, String host, String puerto, String ip, String nombre) {
        ArrayList<CAT_EMPLEADO> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM CAT_EMPLEADOS WHERE CEDULA LIKE '" + nombre + "%'");
            while (rs.next()) {
                CAT_EMPLEADO est = new CAT_EMPLEADO();
                est.setId_Persona(rs.getString("IDPERSONA"));
                est.setCedula(rs.getString("CEDULA"));
                est.setNombre(rs.getString("NOMBRE"));
                est.setApellidoP(rs.getString("APELLIDOP"));
                est.setApellidoM(rs.getString("APELLIDOM"));
                est.setFechaNac(rs.getString("FECHANAC"));
                est.setLugarRes(rs.getString("LUGARRESI"));
                est.setDireccion(rs.getString("DIRECCION"));
                est.setTelefono(rs.getString("TELEFONO"));
                est.setEmail(rs.getString("EMAIL"));
                est.setGenero(rs.getString("GENERO"));
                est.setLugarNac(rs.getString("LUGARNAC"));
                est.setEstadoCivil(rs.getString("ESTADOCIVIL"));
                est.setTipoSangre(rs.getString("TIPOSANGRE"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    //==============PARAMETROS===========//////////
    ///METODO MOSTRAR
    public ArrayList<PARAMETROS> ListParametros(String usuario, String contraseña, String host, String puerto, String ip) {
        ArrayList<PARAMETROS> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT DISTINCT IDPARAMETRO,DESCRIPCION"
                    + " FROM PARAMETROS ORDER BY IDPARAMETRO ASC");
            while (rs.next()) {
                PARAMETROS est = new PARAMETROS();
                est.setIdParametro(rs.getString("IDPARAMETRO"));
                est.setDescripcion(rs.getString("DESCRIPCION"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    //METODO PARA BUSCAR
    public ArrayList<PARAMETROS> FiltrarNombreP(String usuario, String contraseña, String host, String puerto, String ip, String descripcion) {
        ArrayList<PARAMETROS> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM PARAMETROS WHERE DESCRIPCION LIKE '" + descripcion + "%'");
            while (rs.next()) {
                PARAMETROS est = new PARAMETROS();
                est.setIdParametro(rs.getString("IDPARAMETRO"));
                est.setDescripcion(rs.getString("DESCRIPCION"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<PARAMETROS> FiltrarIdP(String usuario, String contraseña, String host, String puerto, String ip, String descripcion) {
        ArrayList<PARAMETROS> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM PARAMETROS WHERE IDPARAMETRO LIKE '" + descripcion + "%'");
            while (rs.next()) {
                PARAMETROS est = new PARAMETROS();
                est.setIdParametro(rs.getString("IDPARAMETRO"));
                est.setDescripcion(rs.getString("DESCRIPCION"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    /// METODO PARA GUARDAR
    public void IngresarParametro(String usuario, String contraseña, String host, String puerto, String ip, String id, String descripcion) {
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            st.executeQuery("insert into PARAMETROS (IDPARAMETRO, DESCRIPCION)\n"
                    + "  values ('" + id + "', '" + descripcion + "')");
            msm = "SE HA INGRESADO";
        } catch (SQLException ex) {
            msm = ex.getMessage();
        }
    }
    ///METODO PARA BORRAR

    public void BorrarParametro(String usuario, String contraseña, String host, String puerto, String ip, String id) {
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            st.executeQuery("delete from PARAMETROS where IDPARAMETRO = '" + id + "'");
            msm = "SE HA BORRADO";
        } catch (SQLException ex) {
            msm = ex.getMessage();
        }
    }

    ///METODO PARA ACTUALIZAR
    public void ActualizarParametro(String usuario, String contraseña, String host, String puerto, String ip, String id, String descripcion) {
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            st.executeQuery("update PARAMETROS set DESCRIPCION='" + descripcion + "' where IDPARAMETRO = '" + id + "'");
            msm = "SE HA ACTUALIDADO";
        } catch (SQLException ex) {
            msm = ex.getMessage();
        }
    }

    //==============SUELDO===========//////////
    ///METODO MOSTRAR
    public ArrayList<CAT_SUELDOS> ListSueldo(String usuario, String contraseña, String host, String puerto, String ip) {
        ArrayList<CAT_SUELDOS> lista = new ArrayList();

        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT DISTINCT IDSUELDO,DESCRIPCION, VALOR"
                    + " FROM CAT_SUELDOS ORDER BY IDSUELDO ASC");
            while (rs.next()) {
                CAT_SUELDOS est = new CAT_SUELDOS();
                est.setIdSueldo(rs.getString("IDSUELDO"));
                est.setDescripcion(rs.getString("DESCRIPCION"));
                est.setValor(rs.getDouble("VALOR"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    //METODO PARA BUSCAR
    public ArrayList<CAT_SUELDOS> FiltrarDescripcionS(String usuario, String contraseña, String host, String puerto, String ip, String descripcion) {
        ArrayList<CAT_SUELDOS> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM CAT_SUELDOS WHERE DESCRIPCION LIKE '" + descripcion + "%'");
            while (rs.next()) {
                CAT_SUELDOS est = new CAT_SUELDOS();
                est.setIdSueldo(rs.getString("IDSUELDO"));
                est.setDescripcion(rs.getString("DESCRIPCION"));
                est.setValor(rs.getDouble("VALOR"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<CAT_SUELDOS> FiltrarIdS(String usuario, String contraseña, String host, String puerto, String ip, String descripcion) {
        ArrayList<CAT_SUELDOS> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM CAT_SUELDOS WHERE IDSUELDO LIKE '" + descripcion + "%'");
            while (rs.next()) {
                CAT_SUELDOS est = new CAT_SUELDOS();
                est.setIdSueldo(rs.getString("IDSUELDO"));
                est.setDescripcion(rs.getString("DESCRIPCION"));
                est.setValor(rs.getDouble("VALOR"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<CAT_SUELDOS> FiltrarValorS(String usuario, String contraseña, String host, String puerto, String ip, String descripcion) {
        ArrayList<CAT_SUELDOS> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM CAT_SUELDOS WHERE VALOR LIKE '" + descripcion + "%'");
            while (rs.next()) {
                CAT_SUELDOS est = new CAT_SUELDOS();
                est.setIdSueldo(rs.getString("IDSUELDO"));
                est.setDescripcion(rs.getString("DESCRIPCION"));
                est.setValor(rs.getDouble("VALOR"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    /// METODO PARA GUARDAR
    public void GuardarSueldo(String usuario, String contraseña, String host, String puerto, String ip, String id, String descripcion, double valor) {
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            st.executeQuery("insert into CAT_SUELDOS (IDSUELDO, DESCRIPCION, VALOR)\n"
                    + "  values ('" + id + "', '" + descripcion + "', '" + valor + "')");
            msm = "SE HA INGRESADO";
        } catch (SQLException ex) {
            msm = ex.getMessage();
        }
    }
    ///METODO PARA BORRAR

    public void BorrarSueldo(String usuario, String contraseña, String host, String puerto, String ip, String id) {
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            st.executeQuery("delete from CAT_SUELDOS where IDSUELDO = '" + id + "'");
            msm = "SE HA BORRADO";
        } catch (SQLException ex) {
            msm = ex.getMessage();
        }
    }

    ///METODO PARA ACTUALIZAR
    public void ActualizarSueldo(String usuario, String contraseña, String host, String puerto, String ip, String id, String descripcion, double valor) {
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            st.executeQuery("update CAT_SUELDOS set DESCRIPCION='" + descripcion + "', VALOR='" + valor + "' where IDSUELDO = '" + id + "'");
            msm = "SE HA ACTUALIDADO";
        } catch (SQLException ex) {
            msm = ex.getMessage();
        }
    }

    //==============VALORES_CONTRATO===========//////////
    ///METODO MOSTRAR
    public ArrayList<VALORES_CONTRATO> ListValoresContrato(String usuario, String contraseña, String host, String puerto, String ip) {
        ArrayList<VALORES_CONTRATO> lista = new ArrayList();

        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT DISTINCT IDSUELDO,IDPERSONA, VALOR"
                    + " FROM VALORES_CONTRATO ORDER BY IDSUELDO ASC");
            while (rs.next()) {
                VALORES_CONTRATO est = new VALORES_CONTRATO();
                est.setIDSUELDO(rs.getString("IDSUELDO"));
                est.setIDPERSONA(rs.getString("IDPERSONA"));
                est.setVALOR(rs.getDouble("VALOR"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    /// METODO PARA GUARDAR
    public void GuardarValores(String usuario, String contraseña, String host, String puerto, String ip, String idSueldo, String idPersona, double valor) {
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            st.executeQuery("insert into VALORES_CONTRATO (IDSUELDO, IDPERSONA, VALOR)\n"
                    + "  values ('" + idSueldo + "', '" + idPersona + "', '" + valor + "')");
            msm = "SE HA INGRESADO";
        } catch (SQLException ex) {
            msm = ex.getMessage();
        }
    }
    ///METODO PARA BORRAR

    public void BorrarValores(String usuario, String contraseña, String host, String puerto, String ip, String idSueldo, String idPersona) {
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            st.executeQuery("delete from VALORES_CONTRATO where IDSUELDO = '" + idSueldo + "' AND IDPERSONA = '" + idPersona + "'");
            msm = "SE HA BORRADO";
        } catch (SQLException ex) {
            msm = ex.getMessage();
        }
    }

    ///METODO PARA ACTUALIZAR
    public void ActualizarValores(String usuario, String contraseña, String host, String puerto, String ip, String id, String idPersona, double valor) {
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            st.executeQuery("update VALORES_CONTRATO set VALOR='" + valor + "' where IDSUELDO = '" + id + "' AND IDPERSONA = '" + idPersona + "'");
            msm = "SE HA ACTUALIDADO";
        } catch (SQLException ex) {
            msm = ex.getMessage();
        }
    }

    ///LLENAR COMBOBOX
    public ArrayList<CAT_CARGO> LlenarComboIDCARGO(String usuario, String contraseña, String host, String puerto, String ip) {
        ArrayList<CAT_CARGO> cargo = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT IDCARGO FROM CAT_CARGOS");
            while (rs.next()) {
                CAT_CARGO est = new CAT_CARGO();
                est.setID_CARGO(rs.getString("IDCARGO"));
                cargo.add(est);
            }
            return cargo;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    //==============CARGO===========//////////
    ///METODO MOSTRAR
    public ArrayList<CAT_CARGO> ListCargo(String usuario, String contraseña, String host, String puerto, String ip) {
        ArrayList<CAT_CARGO> Cargo = new ArrayList();

        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT DISTINCT IDCARGO,NOMBRE"
                    + " FROM CAT_CARGOS ORDER BY IDCARGO ASC");
            while (rs.next()) {
                CAT_CARGO est = new CAT_CARGO();
                est.setID_CARGO(rs.getString("IDCARGO"));
                est.setNOMBRE(rs.getString("NOMBRE"));
                Cargo.add(est);
            }
            return Cargo;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }

    }

    /// METODO PARA INGRESAR
    public void IngresarCargo(String usuario, String contraseña, String host, String puerto, String ip, String id, String nombre) {
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            st.executeQuery("insert into CAT_CARGOS (IDCARGO, NOMBRE)\n"
                    + "  values ('" + id + "', '" + nombre + "')");
            msm = "SE HA INGRESADO";
        } catch (SQLException ex) {
            msm = ex.getMessage();
        }
    }
///METODO PARA BORRAR

    public void BorrarCargo(String usuario, String contraseña, String host, String puerto, String ip, String id) {
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            st.executeQuery("delete from CAT_CARGOS where IDCARGO = '" + id + "'");
            msm = "SE HA BORRADO";
        } catch (SQLException ex) {
            msm = ex.getMessage();
        }
    }
public ArrayList<CAT_EMPLEADO> BuscarPer(String usuario, String contraseña, String host, String puerto, String ip, String nombre) {
        ArrayList<CAT_EMPLEADO> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM CAT_EMPLEADOS WHERE IDPERSONA LIKE '" + nombre + "%'");
            while (rs.next()) {
                CAT_EMPLEADO est = new CAT_EMPLEADO();
               est.setId_Persona(rs.getString("IDPERSONA"));
                est.setCedula(rs.getString("CEDULA"));
                est.setNombre(rs.getString("NOMBRE"));
                est.setApellidoP(rs.getString("APELLIDOP"));
                est.setApellidoM(rs.getString("APELLIDOM"));
                est.setFechaNac(rs.getString("FECHANAC"));
                est.setLugarRes(rs.getString("LUGARRESI"));
                est.setDireccion(rs.getString("DIRECCION"));
                est.setTelefono(rs.getString("TELEFONO"));
                est.setEmail(rs.getString("EMAIL"));
                est.setGenero(rs.getString("GENERO"));
                est.setLugarNac(rs.getString("LUGARNAC"));
                est.setEstadoCivil(rs.getString("ESTADOCIVIL"));
                est.setTipoSangre(rs.getString("TIPOSANGRE"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }
    ///METODO PARA ACTUALIZAR
    public void ActualizarCargo(String usuario, String contraseña, String host, String puerto, String ip, String id, String nombre) {
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            st.executeQuery("update CAT_CARGOS set NOMBRE='" + nombre + "' where IDCARGO = '" + id + "'");
            msm = "SE HA ACTUALIDADO";
        } catch (SQLException ex) {
            msm = ex.getMessage();
        }
    }

    //METODO PARA BUSCAR
    public ArrayList<CAT_CARGO> BuscarNombreC(String usuario, String contraseña, String host, String puerto, String ip, String nombre) {
        ArrayList<CAT_CARGO> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM CAT_CARGOS WHERE NOMBRE LIKE '" + nombre + "%'");
            while (rs.next()) {
                CAT_CARGO est = new CAT_CARGO();
                est.setID_CARGO(rs.getString("IDCARGO"));
                est.setNOMBRE(rs.getString("NOMBRE"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<CAT_CARGO> BuscarIdC(String usuario, String contraseña, String host, String puerto, String ip, String nombre) {
        ArrayList<CAT_CARGO> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM CAT_CARGOS WHERE IDCARGO LIKE '" + nombre + "%'");
            while (rs.next()) {
                CAT_CARGO est = new CAT_CARGO();
                est.setID_CARGO(rs.getString("IDCARGO"));
                est.setNOMBRE(rs.getString("NOMBRE"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    ///==================FUNCIONES===========////
    //METODO MOSTRAR
    public ArrayList<CAT_FUNCIONES> ListFunciones(String usuario, String contraseña, String host, String puerto, String ip) {
        ArrayList<CAT_FUNCIONES> Funciones = new ArrayList();

        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT DISTINCT IDFUNCION,IDCARGO,NOMBRE"
                    + " FROM FUNCIONES ORDER BY IDFUNCION ASC");
            while (rs.next()) {
                CAT_FUNCIONES est = new CAT_FUNCIONES();
                est.setIDFUNCION(rs.getString("IDFUNCION"));
                est.setIDCARGO(rs.getString("IDCARGO"));
                est.setNOMBRE(rs.getString("NOMBRE"));
                Funciones.add(est);
            }
            return Funciones;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    public void IngresarFunciones(String usuario, String contraseña, String host, String puerto, String ip, String id, String cargo, String nombre) {
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            st.executeQuery("insert into FUNCIONES (IDFUNCION, IDCARGO, NOMBRE)\n"
                    + "  values ('" + id + "', '" + cargo + "','" + nombre + "')");
            msm = "SE HA INGRESADO";
        } catch (SQLException ex) {
            msm = ex.getMessage();
        }
    }
///METODO PARA BORRAR

    public void BorrarFunciones(String usuario, String contraseña, String host, String puerto, String ip, String idFuncion, String idCargo) {
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            st.executeQuery("delete from FUNCIONES where IDFUNCION = '" + idFuncion + "' AND IDCARGO = '" + idCargo + "'");
            msm = "SE HA BORRADO";
        } catch (SQLException ex) {
            msm = ex.getMessage();
        }
    }

    ///METODO PARA ACTUALIZAR
    public void ActualizarFunciones(String usuario, String contraseña, String host, String puerto, String ip, String idFuncion, String idCargo, String nombre) {
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            st.executeQuery("update FUNCIONES set NOMBRE='" + nombre + "' where IDFUNCION = '" + idFuncion + "' AND IDCARGO = '" + idCargo + "'");
            msm = "SE HA ACTUALIDADO";
        } catch (SQLException ex) {
            msm = ex.getMessage();
        }
    }

    ///BUSCAR
    public ArrayList<CAT_FUNCIONES> BuscarIdFunF(String usuario, String contraseña, String host, String puerto, String ip, String id) {
        ArrayList<CAT_FUNCIONES> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM FUNCIONES WHERE IDFUNCION LIKE '" + id + "%'");
            while (rs.next()) {
                CAT_FUNCIONES est = new CAT_FUNCIONES();
                est.setIDFUNCION(rs.getString("IDFUNCION"));
                est.setIDCARGO(rs.getString("IDCARGO"));
                est.setNOMBRE(rs.getString("NOMBRE"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<CAT_FUNCIONES> BuscarIdCargoF(String usuario, String contraseña, String host, String puerto, String ip, String id) {
        ArrayList<CAT_FUNCIONES> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM FUNCIONES WHERE IDCARGO LIKE '" + id + "%'");
            while (rs.next()) {
                CAT_FUNCIONES est = new CAT_FUNCIONES();
                est.setIDFUNCION(rs.getString("IDFUNCION"));
                est.setIDCARGO(rs.getString("IDCARGO"));
                est.setNOMBRE(rs.getString("NOMBRE"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    //BUSCAR VALORES CONTRATO
    public ArrayList<VALORES_CONTRATO> FiltrarNombreV(String usuario, String contraseña, String host, String puerto, String ip, String persona) {
        ArrayList<VALORES_CONTRATO> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM VALORES_CONTRATO WHERE IDPERSONA LIKE '" + persona + "%'");
            while (rs.next()) {
                VALORES_CONTRATO est = new VALORES_CONTRATO();
                est.setIDSUELDO(rs.getString("IDSUELDO"));
                est.setIDPERSONA(rs.getString("IDPERSONA"));
                est.setVALOR(rs.getInt("VALOR"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<CAT_FUNCIONES> BuscarNombreF(String usuario, String contraseña, String host, String puerto, String ip, String id) {
        ArrayList<CAT_FUNCIONES> lista = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT *FROM FUNCIONES WHERE NOMBRE LIKE '" + id + "%'");
            while (rs.next()) {
                CAT_FUNCIONES est = new CAT_FUNCIONES();
                est.setIDFUNCION(rs.getString("IDFUNCION"));
                est.setIDCARGO(rs.getString("IDCARGO"));
                est.setNOMBRE(rs.getString("NOMBRE"));
                lista.add(est);
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    ///LLENAR COMBOBOX
    public ArrayList<CAT_SUELDOS> LlenarComboIDSueldo(String usuario, String contraseña, String host, String puerto, String ip) {
        ArrayList<CAT_SUELDOS> cargo = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT IDSUELDO FROM CAT_SUELDOS");
            while (rs.next()) {
                CAT_SUELDOS est = new CAT_SUELDOS();
                est.setIdSueldo(rs.getString("IDSUELDO"));
                cargo.add(est);
            }
            return cargo;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    ///LLENAR COMBOBOX
    public ArrayList<CAT_EMPLEADO> LlenarComboIDPersona(String usuario, String contraseña, String host, String puerto, String ip) {
        ArrayList<CAT_EMPLEADO> cargo = new ArrayList();
        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT IDPERSONA FROM CAT_EMPLEADOS");
            while (rs.next()) {
                CAT_EMPLEADO est = new CAT_EMPLEADO();
                est.setId_Persona(rs.getString("IDPERSONA"));
                cargo.add(est);
            }
            return cargo;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }

    ///===========CONTRATO============////
    //METODO mostrar CONTRATO
    public ArrayList<CAT_CONTRATO> ListContrato(String usuario, String contraseña, String host, String puerto, String ip) {
        ArrayList<CAT_CONTRATO> Contrato = new ArrayList();

        try {
            Statement st = this.conexionOp(usuario, contraseña, host, puerto, ip);
            ResultSet rs = st.executeQuery("SELECT DISTINCT IDCONTRATO,IDINST,IDPARAMETRO,IDSUELDO,IDFUNCION,IDCARGO,IDPERSONA,EMPLEADOR,NROCONTRATO,FECHAINICIO,FECHAFIN,ESTADOCONTRATO,NROHORAS"
                    + " FROM CAT_CONTRATO ORDER BY IDCONTRATO ASC");
            while (rs.next()) {
                CAT_CONTRATO est = new CAT_CONTRATO();
                est.setIDCONTRATO(rs.getString("IDCONTRATO"));
                est.setIDINST(rs.getString("IDCARGO"));
                est.setIDPARAMETRO(rs.getString("IDPARAMETRO"));
                est.setIDSUELDO(rs.getString("IDSUELDO"));
                est.setIDFUNCION(rs.getString("IDFUNCION"));
                est.setIDCARGO(rs.getString("IDCARGO"));
                est.setIDPERSONA(rs.getString("IDPERSONA"));
                est.setEMPLEADOR(rs.getString("EMPLEADOR"));
                est.setNROCONTRATO(rs.getString("NROCONTRATO"));
                est.setFECHAINICIO(rs.getDate("FECHAINICIO").toString());
                est.setFECHAFIN(rs.getDate("FECHAFIN").toString());
                est.setESTADOCONTRATO(rs.getString("ESTADOCONTRATO"));
                est.setNROHORAS(rs.getInt("NROHORAS"));
                Contrato.add(est);
            }
            return Contrato;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "ERROR", JOptionPane.ERROR);
            return null;
        }
    }
}
