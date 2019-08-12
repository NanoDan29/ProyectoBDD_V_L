package ventanas;

import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class Aplicacion2 extends javax.swing.JFrame {

    Metodos m = new Metodos();
    private Connection conn;
    private Statement st;
    private String mensaje = "";
    boolean row_is_selected = false;
    int index;
    DefaultTableModel model;

    public Aplicacion2() {
        initComponents();
        this.setLocationRelativeTo(null);//Pone a la aplicacion en el medio de la pantalla
        this.getContentPane().setBackground(Color.WHITE);//Cambia el color del JFrame
        txtUser.setText("JBPAI");
        txtUser.setEditable(false);
        pwdClave.setText("jbpai");
        pwdClave.setEditable(false);
        txthost.setText("ORCL");
        txthost.setEditable(false);
        pMensaje.setVisible(false);

//        jPanel_Inicio.setVisible(false);
//        String BDs[] = m.nombresBD();
//        for (int i = 0; i < BDs.length; i++) {
//            cobxBases.addItem(BDs[i]);
//        }
//        pAdmin.setVisible(false);
//        pHerramientas.setVisible(false);
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("EST_INSTITUCIONAL");
        trRegistros.setModel(modeloArbol("EST_INSTITUCIONAL", raiz, 1, 0, "null"));
        trRegistros.setSelectionRow(0);
        pAdmin.setVisible(true);
        pHerramientas.setVisible(true);
        pIngreso.setVisible(false);
        lbMensaje.setText(mensaje);
        txtUsuario.setText(txtUser.getText());
        txthost1.setText(txthost.getText());
        conn = conexion(txtUser.getText(), pwdClave.getText(), txthost.getText());
        if (!(conn + "").equals("null")) {
            try {
                st = conn.createStatement();
            } catch (SQLException e) {
            }

        } else {
            lbMensaje.setText(mensaje);
        }

        if (tbpAdmin.getSelectedIndex() == 0) {
//            DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("EST_INSTITUCIONAL");
            trRegistros.setModel(modeloArbol("EST_INSTITUCIONAL", raiz, 1, 0, "null"));
            trRegistros.setSelectionRow(0);
        }
//        if (tbpAdmin.getSelectedIndex() == 1) {
//            verTabla(tblPARAMETROS, "PARAMETROS", 0, obtenerDatos("PARAMETROS", "PARAMETROS"));
//            Operaciones op = new Operaciones();
////            ArrayList<PARAMETROS> list = op.ListParametros(txtUsuario.getText(), txtContraseña.getText(), txthost.getText(), "1521", "localhost");
//
//        }
        if (tbpAdmin.getSelectedIndex() == 1) {
            verTabla(tblCAT_EMPLEADOS, "CAT_EMPLEADOS", 0, obtenerDatos("CAT_EMPLEADOS", "CAT_EMPLEADOS"));
        }
        if (tbpAdmin.getSelectedIndex() == 2) {
            verTabla(tblCAT_SUELDOS, "CAT_SUELDOS", 0, obtenerDatos("CAT_SUELDOS", "CAT_SUELDOS"));
        }
        if (tbpAdmin.getSelectedIndex() == 3) {
            verTabla(tblVALORES_CONTRATO, "VALORES_CONTRATO", -1, obtenerDatos("VALORES_CONTRATO", "VALORES_CONTRATO"));
        }
        if (tbpAdmin.getSelectedIndex() == 4) {
            verTabla(tblCAT_CARGOS, "CAT_CARGOS", 0, obtenerDatos("CAT_CARGOS", "CAT_CARGOS"));
            verTabla(tblFUNCIONES, "FUNCIONES", 1, obtenerDatos("FUNCIONES", "FUNCIONES"));
        }
        if (tbpAdmin.getSelectedIndex() == 5) {
            verTabla(tblCAT_CONTRATO, "CAT_CONTRATO", 0, obtenerDatos("CAT_CONTRATO", "CAT_CONTRATO"));
        }
    }

    // Método conexion
    private Connection conexion(String usuario, String clave, String base) {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection connection;
            if (base.equals("ORCL1")) {
                connection = DriverManager.getConnection("jdbc:oracle:thin:@10.24.8.85:1521:orcl", usuario, clave);
            } else {
                connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:" + base, usuario, clave);
            }
            connection.setAutoCommit(false);
            mensaje = "Conexión exitosa.";
            return connection;
        } catch (SQLException e) {
            mensaje = e.getMessage();
            return null;
        }
    }

    // Metodo pata obtener los nombres de las tablas
    private ArrayList<String> obtenerNombreTablas(String usuario) {
        ArrayList<String> nombreTablas = new ArrayList<String>();
        try {
            ResultSet rs = st.executeQuery("select TABLE_NAME from all_tables where owner like '" + usuario.substring(0, 2) + "%' order by TABLE_NAME");
            String nombreTabla = null;
            while (rs.next()) {
                nombreTabla = new String(rs.getString(1));
                nombreTablas.add(nombreTabla);
            }
            rs.close();
            return nombreTablas;
        } catch (SQLException e) {
            mensaje = e.getMessage();
            return null;
        }
    }

    //Metodo recursivo para mostrar los datos de la BD en un Jtree
    private DefaultTreeModel modeloArbol(String nomTabla, DefaultMutableTreeNode raiz, int nivelDepto, int contador, String idDepto) {
        DefaultTreeModel modelo = new DefaultTreeModel(raiz);
        try {
            ArrayList<Registros> departamentos = obtenerTablaRecursiva(nomTabla);
            String n = nivelDepto + "";
            for (Registros depto : departamentos) {
                if (depto.getRegidtro4().equals(n) && (depto.getRegidtro2() + "").equals(idDepto)) {
                    //System.out.println(depto.getRegidtro1()+", "+depto.getRegidtro2()+", "+depto.getRegidtro3()+", "+depto.getRegidtro4()+", "+depto.getRegidtro5());
                    DefaultMutableTreeNode nivel1 = new DefaultMutableTreeNode(depto.getRegidtro1() + " " + depto.getRegidtro3());
                    modelo.insertNodeInto(nivel1, raiz, contador);
                    contador++;
                    modeloArbol(nomTabla, nivel1, (nivelDepto + 1), 0, depto.getRegidtro1());
                }
            }
        } catch (Exception e) {
        }

        return modelo;
    }

    // Método para obtener todos los registros de una tabla recursiva
    private ArrayList<Registros> obtenerTablaRecursiva(String nomTable) {
        ArrayList<Registros> listaDepartamentos = new ArrayList<Registros>();
        try {
            String ID = "";
            String IDP = "";

            ResultSet rs = st.executeQuery("select column_name from all_tab_columns where table_name = '" + nomTable + "'");
            int cont = 0;
            while (rs.next()) {
                if (cont == 0) {
                    ID = rs.getString(1);
                }
                if (cont == 1) {
                    IDP = rs.getString(1);
                }
                cont++;
            }

            rs = st.executeQuery("select * from " + nomTable + " start with " + IDP + " is null connect by prior " + ID + "=" + IDP);
            Registros departamento = null;
            while (rs.next()) {
                if (cont == 1) {
                    departamento = new Registros(rs.getString(1));
                } else if (cont == 2) {
                    departamento = new Registros(rs.getString(1), rs.getString(2));
                } else if (cont == 3) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3));
                } else if (cont == 4) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                } else if (cont == 5) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                } else if (cont == 6) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                } else if (cont == 7) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
                } else if (cont == 8) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
                } else if (cont == 9) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
                } else if (cont == 10) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
                } else if (cont == 11) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11));
                } else if (cont == 12) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12));
                } else if (cont == 13) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13));
                } else {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14));
                }
                listaDepartamentos.add(departamento);
            }
            rs.close();
            //st.close();
            mensaje = "Select exitoso.";
            return listaDepartamentos;
        } catch (SQLException ex) {
            mensaje = ex.getMessage();
            return null;
        }
    }

    // Método para obtener el nombre de las columnas de las tablas y los devuelbe en un Vector
    private Object[] obtenerNomColumnas(String nomTabla) {
        try {
            Object nomColumnas[] = null;
            //Statement st = this.conexion(usuario, clave, base).createStatement();
            ResultSet rs = st.executeQuery("select column_name from all_tab_columns where table_name = '" + nomTabla + "'");
            int cont = 0;
            while (rs.next()) {
                cont++;
            }
            nomColumnas = new Object[cont];
            rs = st.executeQuery("select column_name from all_tab_columns where table_name = '" + nomTabla + "'");
            int cont2 = 0;
            while (rs.next()) {
                nomColumnas[cont2] = rs.getString(1);
                cont2++;
            }
            rs.close();
            //st.close();
            return nomColumnas;
        } catch (Exception e) {
            return null;
        }
    }

    // Método para obtener el nombre de las columnas de las tablas y los devuelbe en un Matriz
    private Object[][] obtenerDatos(String nomTabla, String nomTabla2) {
        try {
            Object Datos[][] = null;
            //Statement st = this.conexion(usuario, clave, base);
            Object nomCol[] = obtenerNomColumnas(nomTabla);
            ResultSet rs = st.executeQuery("select column_name from all_tab_columns where table_name = '" + nomTabla2 + "'");
            int col = 0;
            while (rs.next()) {
                col++;
            }
            rs = st.executeQuery("select * from " + nomTabla);
            int fil = 0;
            while (rs.next()) {
                fil++;
            }
            rs = st.executeQuery("Select * from " + nomTabla + " order by " + nomCol[0]);
            Datos = new Object[fil][col];
            for (int i = 0; rs.next(); i++) {
                for (int j = 0; j < col; j++) {
                    Datos[i][j] = rs.getString(j + 1);
                }
            }
            rs.close();
            //st.close();
            return Datos;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * <b>existenCheckColumna</b>
     * <p>
     * Método para saber si en existe un check en el nomColumna especificado
     * </p><br>
     * <b>Retorna:</b>
     * <p>
     * - True: si existe algun ckeck</p>
     * <p>
     * - False si no existe algun ckeck</p>
     */
    private boolean existenCheckColumna(String nomTabla, String nomColumna) {
        boolean existe = false;
        try {
            ResultSet rs = st.executeQuery("select * from all_constraints where table_name='" + nomTabla + "' and constraint_name like 'CKC_" + nomColumna + "%'");
            int cont = 0;
            while (rs.next()) {
                cont++;
            }
            if (cont > 0) {
                existe = true;
            }
            rs.close();
            //st.close();
            return existe;
        } catch (SQLException e) {
            return existe;
        }
    }

    /**
     * * <b>existeForeingKeyColumna</b>
     * <p>
     * Método para saber si en existe un foreing key en el nomColumna
     * especificado
     * </p><br>
     * <b>Retorna:</b>
     * <p>
     * - True: si la columna es foreing key</p>
     * <p>
     * - False si la columna no es foreing key</p>
     */
    private boolean existeForeingKeyColumna(String nomTabla, String nomColumna) {
        boolean res = false;
        try {
            ResultSet rs = st.executeQuery("SELECT t1.constraint_name, t2.TABLE_NAME, t2.column_name, t3.TABLE_NAME, t3.column_name \n"
                    + "FROM all_constraints t1, all_cons_columns t2, all_cons_columns t3 \n"
                    + "WHERE t2.constraint_name = t1.constraint_name AND t2.owner = t1.owner AND \n"
                    + "T3.constraint_name = t1.r_constraint_name AND t3.owner = t1.owner AND \n"
                    + "T3.constraint_name = t1.r_constraint_name AND t3.owner = t1.owner AND \n"
                    + "t1.constraint_type = 'R' AND t2.POSITION = t3.POSITION AND \n"
                    + "t2.column_name = '" + nomColumna + "' AND t1.TABLE_NAME LIKE '" + nomTabla + "'");
            int x = 0;
            while (rs.next()) {
                x++;
            }
            if (x > 0) {
                res = true;
            } else {
                res = false;
            }
            rs.close();
            return res;
        } catch (Exception e) {
            return res;
        }
    }

    // Metodo para dar un combo box a las tablas que contengan un check(Condiciones) 
    private void getBoxTableCheck(JTable tabla, TableColumn columnaTabla, String nomTabla, String nomColumna) {
        JComboBox c = new JComboBox();
        String nomChecks[] = null;
        try {
            ResultSet rs = st.executeQuery("select SEARCH_CONDITION from all_constraints where table_name='" + nomTabla + "' and constraint_name like 'CKC_" + nomColumna + "%'");
            String primerSplit[] = null;
            while (rs.next()) {
                primerSplit = rs.getString(1).split("'");
            }
            String texto = "";
            for (int i = 1; i < primerSplit.length - 1; i++) {
                texto += primerSplit[i];
            }
            nomChecks = texto.split(",");
            rs.close();
            for (int i = 0; i < nomChecks.length; i++) {
                c.addItem(nomChecks[i]);
            }
            columnaTabla.setCellEditor(new DefaultCellEditor(c));
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setToolTipText("Seleccione un " + nomColumna);
            columnaTabla.setCellRenderer(renderer);

        } catch (Exception e) {
        }
    }

    // Metodo para dar un combo box a las tablas que contengan un check(Condiciones) 
    private void getBoxTableForengKey(JTable tabla, TableColumn columnaTabla, String nomTabla, String nomColumna) {
        JComboBox c = new JComboBox();
        try {
            String tablaReferencia = "";
            String campoReferencia = "";
            ResultSet rs = st.executeQuery("SELECT t1.constraint_name, t2.TABLE_NAME, t2.column_name, t3.TABLE_NAME, t3.column_name \n"
                    + "FROM all_constraints t1, all_cons_columns t2, all_cons_columns t3 \n"
                    + "WHERE t2.constraint_name = t1.constraint_name AND t2.owner = t1.owner AND \n"
                    + "T3.constraint_name = t1.r_constraint_name AND t3.owner = t1.owner AND \n"
                    + "T3.constraint_name = t1.r_constraint_name AND t3.owner = t1.owner AND \n"
                    + "t1.constraint_type = 'R' AND t2.POSITION = t3.POSITION AND \n"
                    + "t2.column_name = '" + nomColumna + "' AND t1.TABLE_NAME LIKE '" + nomTabla + "'");
            while (rs.next()) {
                tablaReferencia = rs.getString(4);
                campoReferencia = rs.getString(5);
            }
            rs = st.executeQuery("select " + campoReferencia + " from " + tablaReferencia);
            while (rs.next()) {
                c.addItem(rs.getString(1));
            }
            columnaTabla.setCellEditor(new DefaultCellEditor(c));
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setToolTipText("Seleccione un " + nomColumna);
            columnaTabla.setCellRenderer(renderer);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Metodo para mostrar tablas
    private void verTabla(JTable jTable, String nomTabla, final int numColIzqBloqueadas, Object contenidoTabla[][]) {
        Object V[] = obtenerNomColumnas(nomTabla);
        Object M[][] = contenidoTabla;
//        JTable jt = (JTable) ae.getSource();
//        int row = jt.getSelectedRow();
//        int col = jt.getSelectedColumn();

        DefaultTableModel mTabla = new DefaultTableModel(M, V) {
            @Override
            public boolean isCellEditable(int filas, int columnas) {
                if (columnas > numColIzqBloqueadas) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        jTable.setModel(mTabla);
//        jTable.editCellAt(row, 1, null);

        //Focus will be rerouted to the editor via this call:
        // jt.requestFocus();
        for (int j = 0; j < V.length; j++) {
            if (existenCheckColumna(nomTabla, V[j] + "")) {
                getBoxTableCheck(jTable, jTable.getColumnModel().getColumn(j), nomTabla, V[j] + "");
            }
            if (existeForeingKeyColumna(nomTabla, V[j] + "")) {
                getBoxTableForengKey(jTable, jTable.getColumnModel().getColumn(j), nomTabla, V[j] + "");
            }
        }
        jTable.getSelectionModel().setSelectionInterval(0, 0);
    }

    // Metodo para hacer una bussqueda
    public Object[][] buscar(String nomTabla, String nomColumna, String cadenaBusqueda) {
        Object busqueda[][] = null;
        try {
            //Statement st = conexion(usuario, clave, base);
            ResultSet rs = null;
            Object columnas[] = obtenerNomColumnas(nomTabla);
            int col = columnas.length;
            if (nomTabla.equals("CAT_AUTORES") || nomTabla.equals("CAT_LIBROS")) {
                System.out.println("select * from " + nomTabla.substring(4, nomTabla.length()) + " where " + nomColumna + " like '" + cadenaBusqueda + "'");
                rs = st.executeQuery("select * from " + nomTabla.substring(4, nomTabla.length()) + " where " + nomColumna + " like '" + cadenaBusqueda + "'");
            } else {
                rs = st.executeQuery("select * from " + nomTabla + " where " + nomColumna + " like '" + cadenaBusqueda + "'");
            }
            int fil = 0;
            while (rs.next()) {
                fil++;
            }
            if (nomTabla.equals("CAT_AUTORES") || nomTabla.equals("CAT_LIBROS")) {
                rs = st.executeQuery("select * from " + nomTabla.substring(4, nomTabla.length()) + " where " + nomColumna + " like '" + cadenaBusqueda + "'");
            } else {
                rs = st.executeQuery("select * from " + nomTabla + " where " + nomColumna + " like '" + cadenaBusqueda + "'");
            }
            busqueda = new Object[fil][col];
            for (int i = 0; rs.next(); i++) {
                for (int j = 0; j < col; j++) {
                    //System.out.print(rs.getString(j + 1)+"\t");
                    busqueda[i][j] = rs.getString(j + 1);
                }
                //System.out.println("");
            }
            rs.close();
            //st.close();
            mensaje = "Busqueda exitosa.";
            return busqueda;
        } catch (SQLException e) {
            mensaje = e.getMessage();
            return null;
        }
    }

    // Metodo para obtener los registros de una tabla de la BD
    public ArrayList<Registros> ObtenerRegistrosTabla(String nomTabla) {
        ArrayList<Registros> listaDepartamentos = new ArrayList<Registros>();
        try {
            //Statement st = this.conexion(usuario, clave, base);
            ResultSet rs = st.executeQuery("select column_name from all_tab_columns where table_name = '" + nomTabla + "'");

            int cont = 0;
            while (rs.next()) {
                cont++;
            }
            Object nomCol[] = obtenerNomColumnas(nomTabla);
            if (nomTabla.equals("CAT_AUTORES") || nomTabla.equals("CAT_LIBROS")) {

                rs = st.executeQuery("select * from " + nomTabla.substring(4, nomTabla.length()) + " ORDER BY " + nomCol[0]);
            } else {

                rs = st.executeQuery("select * from " + nomTabla + " ORDER BY " + nomCol[0]);
            }

            Registros departamento = null;
            while (rs.next()) {
                if (cont == 1) {
                    departamento = new Registros(rs.getString(1));
                } else if (cont == 2) {
                    departamento = new Registros(rs.getString(1), rs.getString(2));
                } else if (cont == 3) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3));
                } else if (cont == 4) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                } else if (cont == 5) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                } else if (cont == 6) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                } else if (cont == 7) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
                } else if (cont == 8) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
                } else if (cont == 9) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
                } else if (cont == 10) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
                } else if (cont == 11) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11));
                } else if (cont == 12) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12));
                } else if (cont == 13) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13));
                } else {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14));
                }
                listaDepartamentos.add(departamento);
            }
            rs.close();
            //st.close();
            mensaje = "select exitoso.";
            return listaDepartamentos;
        } catch (SQLException ex) {
            mensaje = ex.getMessage();
            return null;
        }
    }

    // Metodo que me indica si es o no una tabla de la BD 
    private boolean esTabla(String usuario, String nomTabla) {
        boolean res = false;
        ArrayList<String> tablas = obtenerTablas(usuario);
        for (String tabla : tablas) {
            if (tabla.equals(nomTabla)) {
                res = true;
                break;
            }
        }
        return res;
    }

    // Método para obtener un arrayList de todas la tablas
    public ArrayList<String> obtenerTablas(String usuario) {
        ArrayList<String> listaTablas = new ArrayList<String>();
        try {
            //Statement st = this.conexion(usuario, clave, base);
            ResultSet rs = st.executeQuery("select TABLE_NAME from all_tables where owner like '" + usuario.substring(0, 2) + "%'");
            String tabla = null;
            while (rs.next()) {
                tabla = new String(rs.getString(1));
                listaTablas.add(tabla);
            }
            rs.close();
            //st.close();
            mensaje = "Select exitoso.";
            return listaTablas;
        } catch (SQLException e) {
            mensaje = e.getMessage();
            return null;
        }
    }

    // Método para obtener un registros de una tabla recursiva
    public ArrayList<Registros> ObtenerUnRegistroTabla(String nomTabla, String idDepto) {
        ArrayList<Registros> listaDepartamentos = new ArrayList<Registros>();
        try {
            Object nomcol[] = obtenerNomColumnas(nomTabla);
            int cont = nomcol.length;
            //Statement st = this.conexion(usuario, clave, base);
            ResultSet rs = null;
            if (nomTabla.equals("CAT_AUTORES") || nomTabla.equals("CAT_LIBROS")) {

                rs = st.executeQuery("select * from " + nomTabla.substring(4, nomTabla.length()) + " where " + nomcol[0] + " = '" + idDepto + "'");
            } else {

                rs = st.executeQuery("select * from " + nomTabla + " where " + nomcol[0] + " = '" + idDepto + "'");
            }
            //ResultSet rs = st.executeQuery("select * from " + nomTabla + " where " + nomcol[0] + " = '" + idDepto + "'");
            Registros departamento = null;
            while (rs.next()) {
                if (cont == 1) {
                    departamento = new Registros(rs.getString(1));
                } else if (cont == 2) {
                    departamento = new Registros(rs.getString(1), rs.getString(2));
                } else if (cont == 3) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3));
                } else if (cont == 4) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                } else if (cont == 5) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                } else if (cont == 6) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                } else if (cont == 7) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
                } else if (cont == 8) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
                } else if (cont == 9) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
                } else if (cont == 10) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
                } else if (cont == 11) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11));
                } else if (cont == 12) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12));
                } else if (cont == 13) {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13));
                } else {
                    departamento = new Registros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14));
                }
                listaDepartamentos.add(departamento);
            }
            rs.close();
            //st.close();
            mensaje = "select exitoso.";
            return listaDepartamentos;
        } catch (SQLException ex) {
            mensaje = ex.getMessage();
            return null;
        }
    }

    // Método para guardar un registro
    public void guardar(String nomTabla, String registros[]) {
        try {
            //Statement st = this.conexion(usuario, clave, base);
            Object nomColumnas[] = obtenerNomColumnas(nomTabla);

            String nomCol = "";
            for (int i = 0; i < nomColumnas.length; i++) {
                if (i == nomColumnas.length - 1) {
                    nomCol += nomColumnas[i] + "";
                } else {
                    nomCol += nomColumnas[i] + ",";
                }
            }
            String tipoDato[] = new String[14];
            int i = 0;
            ResultSet rs = st.executeQuery("select data_type from all_tab_columns where table_name = '" + nomTabla + "'");
            while (rs.next()) {
                tipoDato[i] = rs.getString(1);
                i++;
            }
            String nomParam = "";
            int x = 0;
            for (String registro : registros) {
                if (tipoDato[x].equals("VARCHAR2") || tipoDato[x].equals("CHAR")) {
                    if (x == registros.length - 1) {
                        nomParam += "'" + registro + "'";
                    } else {
                        nomParam += "'" + registro + "',";
                    }
                } else if (tipoDato[x].equals("DATE")) {
                    if (x == registros.length - 1) {
                        nomParam += "TO_DATE('" + registro + "','DD/MM/YYYY')";
                    } else {
                        nomParam += "TO_DATE('" + registro + "','DD/MM/YYYY'),";
                    }
                } else if (x == registros.length - 1) {
                    nomParam += registro;
                } else {
                    nomParam += registro + ",";
                }
                x++;
            }
            if (nomTabla.equals("CAT_AUTORES") || nomTabla.equals("CAT_LIBROS")) {
                st.executeQuery("insert into " + nomTabla.substring(4, nomTabla.length()) + " (" + nomCol + ") values (" + nomParam + ")");
            } else {
                System.out.println("insert into " + nomTabla + " (" + nomCol + ") values (" + nomParam + ")");
                st.executeQuery("insert into " + nomTabla + " (" + nomCol + ") values (" + nomParam + ")");
            }
            //st.executeQuery("insert into " + nomTabla + " (" + nomCol + ") values (" + nomParam + ")");
            rs.close();
            //st.close();
            mensaje = "Ingreso exitoso.";
        } catch (Exception ex) {
            mensaje = ex.getMessage();
        }
    }

    //Método para burcar un nodo dentro de un jTree
    public DefaultMutableTreeNode burcarNodo(DefaultMutableTreeNode root, String nodeStr) {
        DefaultMutableTreeNode node = null;
        Enumeration enumeration = root.breadthFirstEnumeration();
        while (enumeration.hasMoreElements()) {
            node = (DefaultMutableTreeNode) enumeration.nextElement();
            if (nodeStr.equals(node.getUserObject().toString())) {
                return node;
            }
        }
        return null;
    }

    // Metodo para acualizar un registro
    public void actualizar(String nomTabla, String registros[]) {
        try {
            //Connection conn = conexion(usuario, clave, base);
            //Statement st = conn.createStatement();
            String tipoDato[] = new String[14];
            int i = 0;
            ResultSet rs = st.executeQuery("select data_type from all_tab_columns where table_name = '" + nomTabla + "'");
            while (rs.next()) {
                tipoDato[i] = rs.getString(1);
                i++;
            }
            Object nomColumnas[] = obtenerNomColumnas(nomTabla);
            String datosAct = "";
            int x = 0;
            for (String registro : registros) {
                if (x > 0) {
                    if (tipoDato[x].equals("VARCHAR2") || tipoDato[x].equals("CHAR")) {
                        if (x == registros.length - 1) {
                            datosAct += nomColumnas[x] + " = '" + registro + "'";
                        } else {
                            datosAct += nomColumnas[x] + " = '" + registro + "',";
                        }
                    } else if (tipoDato[x].equals("DATE")) {
                        if (x == registros.length - 1) {
                            datosAct += nomColumnas[x] + " = TO_DATE('" + registro + "','DD/MM/YYYY')";
                        } else {
                            datosAct += nomColumnas[x] + " = TO_DATE('" + registro + "','DD/MM/YYYY'),";
                        }
                    } else if (x == registros.length - 1) {
                        datosAct += nomColumnas[x] + " = " + registro;
                    } else {
                        datosAct += nomColumnas[x] + " = " + registro + ",";
                    }
                }
                x++;
            }
            if (nomTabla.equals("CAT_AUTORES") || nomTabla.equals("CAT_LIBROS")) {

                st.executeQuery("update " + nomTabla.substring(4, nomTabla.length()) + " set " + datosAct + " where " + nomColumnas[0] + " = '" + registros[0] + "'");
            } else {

                st.executeQuery("update " + nomTabla + " set " + datosAct + " where " + nomColumnas[0] + " = '" + registros[0] + "'");
            }
            //st.executeQuery("update " + nomTabla + " set " + datosAct + " where " + nomColumnas[0] + " = '" + registros[0] + "'");
            System.out.println("update " + nomTabla + " set " + datosAct + " where " + nomColumnas[0] + " = '" + registros[0] + "'");
            rs.close();
            mensaje = "Actualización exitosa.";
        } catch (SQLException ex) {
            mensaje = ex.getMessage();
        }
    }

    // Método para borrar un registro
    public void borrar(String nomTabla, String idDepto) {
        try {
            //Statement st = this.conexion(usuario, clave, base);
            Object nomColumnas[] = obtenerNomColumnas(nomTabla);
            if (nomTabla.equals("CAT_AUTORES") || nomTabla.equals("CAT_LIBROS")) {
                st.executeQuery("delete from " + nomTabla.substring(4, nomTabla.length()) + " where " + nomColumnas[0] + " = '" + idDepto + "'");
            } else {

                st.executeQuery("delete from " + nomTabla + " where " + nomColumnas[0] + " = '" + idDepto + "'");
            }
            //st.executeQuery("delete from " + nomTabla + " where " + nomColumnas[0] + " = '" + idDepto + "'");
            //st.close();
            mensaje = "Borrado exitoso.";
        } catch (SQLException ex) {
            mensaje = ex.getMessage();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pIngreso1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtUser1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        pwdClave1 = new javax.swing.JPasswordField();
        jLabel13 = new javax.swing.JLabel();
        btnIngreso1 = new javax.swing.JButton();
        txthost2 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        pIngreso = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        pwdClave = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        btnIngreso = new javax.swing.JButton();
        txthost = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        pMensaje = new javax.swing.JPanel();
        txthost1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        pHerramientas = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnRollback = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        pAdmin = new javax.swing.JPanel();
        tbpAdmin = new javax.swing.JTabbedPane();
        pEST_INSTITUCIONAL = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        trRegistros = new javax.swing.JTree();
        jLabel5 = new javax.swing.JLabel();
        txtIdInst = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNomFun = new javax.swing.JTextField();
        chbUltNivel = new javax.swing.JCheckBox();
        pCAT_EMPLEADOS = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblCAT_EMPLEADOS = new javax.swing.JTable();
        pVALORES_CONTRATO = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblCAT_SUELDOS = new javax.swing.JTable();
        pCAT_SUELDOS = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblVALORES_CONTRATO = new javax.swing.JTable();
        pCAT_CARGOS_Y_FUNCIONES = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblCAT_CARGOS = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblFUNCIONES = new javax.swing.JTable();
        pCAT_CONTRATO = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblCAT_CONTRATO = new javax.swing.JTable();
        lbMensaje = new javax.swing.JLabel();

        pIngreso1.setBackground(new java.awt.Color(255, 255, 255));
        pIngreso1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Forte", 0, 16)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("INICIAR SESIÓN");

        jLabel11.setFont(new java.awt.Font("Forte", 0, 14)); // NOI18N
        jLabel11.setText("Username:");

        txtUser1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Forte", 0, 14)); // NOI18N
        jLabel12.setText("Password:");

        pwdClave1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Forte", 0, 14)); // NOI18N
        jLabel13.setText("Database:");

        btnIngreso1.setBackground(new java.awt.Color(0, 0, 0));
        btnIngreso1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnIngreso1.setForeground(new java.awt.Color(255, 255, 255));
        btnIngreso1.setText("INGRESAR");
        btnIngreso1.setMinimumSize(new java.awt.Dimension(85, 25));
        btnIngreso1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngreso1btnIngresoActionPerformed(evt);
            }
        });

        txthost2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Forte", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(204, 0, 0));
        jLabel14.setText("Universidad Ténica del Norte");

        jLabel15.setFont(new java.awt.Font("Forte", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(153, 153, 153));
        jLabel15.setText("FICA - CISIC");

        javax.swing.GroupLayout pIngreso1Layout = new javax.swing.GroupLayout(pIngreso1);
        pIngreso1.setLayout(pIngreso1Layout);
        pIngreso1Layout.setHorizontalGroup(
            pIngreso1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pIngreso1Layout.createSequentialGroup()
                .addGroup(pIngreso1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pIngreso1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pIngreso1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtUser1)
                            .addComponent(pwdClave1)
                            .addComponent(btnIngreso1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txthost2)
                            .addGroup(pIngreso1Layout.createSequentialGroup()
                                .addGroup(pIngreso1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(pIngreso1Layout.createSequentialGroup()
                        .addGroup(pIngreso1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pIngreso1Layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pIngreso1Layout.createSequentialGroup()
                                .addGap(113, 113, 113)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pIngreso1Layout.setVerticalGroup(
            pIngreso1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pIngreso1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtUser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pwdClave1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(txthost2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnIngreso1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pIngreso.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Forte", 0, 16)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("INICIAR SESIÓN");

        jLabel2.setFont(new java.awt.Font("Forte", 0, 14)); // NOI18N
        jLabel2.setText("Username:");

        txtUser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Forte", 0, 14)); // NOI18N
        jLabel3.setText("Password:");

        pwdClave.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Forte", 0, 14)); // NOI18N
        jLabel4.setText("Database:");

        btnIngreso.setBackground(new java.awt.Color(0, 0, 0));
        btnIngreso.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnIngreso.setForeground(new java.awt.Color(255, 255, 255));
        btnIngreso.setText("INGRESAR");
        btnIngreso.setMinimumSize(new java.awt.Dimension(85, 25));
        btnIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresoActionPerformed(evt);
            }
        });

        txthost.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Forte", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 0, 0));
        jLabel7.setText("Universidad Ténica del Norte");

        jLabel9.setFont(new java.awt.Font("Forte", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(153, 153, 153));
        jLabel9.setText("FICA - CISIC");

        javax.swing.GroupLayout pIngresoLayout = new javax.swing.GroupLayout(pIngreso);
        pIngreso.setLayout(pIngresoLayout);
        pIngresoLayout.setHorizontalGroup(
            pIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pIngresoLayout.createSequentialGroup()
                .addGroup(pIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pIngresoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtUser)
                            .addComponent(pwdClave)
                            .addComponent(btnIngreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txthost)
                            .addGroup(pIngresoLayout.createSequentialGroup()
                                .addGroup(pIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(pIngresoLayout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 1, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pIngresoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        pIngresoLayout.setVerticalGroup(
            pIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pIngresoLayout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(14, 14, 14)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pwdClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(txthost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pMensaje.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txthost1.setEditable(false);
        txthost1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("@");

        txtUsuario.setEditable(false);
        txtUsuario.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        javax.swing.GroupLayout pMensajeLayout = new javax.swing.GroupLayout(pMensaje);
        pMensaje.setLayout(pMensajeLayout);
        pMensajeLayout.setHorizontalGroup(
            pMensajeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMensajeLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(txthost1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pMensajeLayout.setVerticalGroup(
            pMensajeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMensajeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pMensajeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txthost1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pHerramientas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnNuevo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos2/mas.png"))); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnBorrar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos2/boton-x.png"))); // NOI18N
        btnBorrar.setBorder(null);
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos2/guardar.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnBuscar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/lupa.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnRollback.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnRollback.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/base-de-datos.png"))); // NOI18N
        btnRollback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRollbackActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/hacia-atras.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/flecha-de-avance-rapido.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos2/flecha-hacia-arriba.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jButton3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton3KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jButton3KeyReleased(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/flecha-hacia-abajo.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos2/base-de-datos2.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        pAdmin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tbpAdmin.setBackground(new java.awt.Color(0, 0, 0));
        tbpAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbpAdminMouseClicked(evt);
            }
        });

        pEST_INSTITUCIONAL.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        trRegistros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                trRegistrosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(trRegistros);

        jLabel5.setText("ID INSTIRUCION:");

        jLabel6.setText("NOMBRE FUNCIÓN:");

        chbUltNivel.setText("     ÚLTIMO NIVEL");

        javax.swing.GroupLayout pEST_INSTITUCIONALLayout = new javax.swing.GroupLayout(pEST_INSTITUCIONAL);
        pEST_INSTITUCIONAL.setLayout(pEST_INSTITUCIONALLayout);
        pEST_INSTITUCIONALLayout.setHorizontalGroup(
            pEST_INSTITUCIONALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pEST_INSTITUCIONALLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(pEST_INSTITUCIONALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pEST_INSTITUCIONALLayout.createSequentialGroup()
                        .addGroup(pEST_INSTITUCIONALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pEST_INSTITUCIONALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdInst)
                            .addComponent(txtNomFun)))
                    .addGroup(pEST_INSTITUCIONALLayout.createSequentialGroup()
                        .addComponent(chbUltNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 592, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pEST_INSTITUCIONALLayout.setVerticalGroup(
            pEST_INSTITUCIONALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pEST_INSTITUCIONALLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pEST_INSTITUCIONALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pEST_INSTITUCIONALLayout.createSequentialGroup()
                        .addGroup(pEST_INSTITUCIONALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtIdInst)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pEST_INSTITUCIONALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNomFun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(chbUltNivel)))
                .addContainerGap())
        );

        tbpAdmin.addTab("EST_INSTITUCIONAL", pEST_INSTITUCIONAL);

        pCAT_EMPLEADOS.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblCAT_EMPLEADOS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tblCAT_EMPLEADOS);

        javax.swing.GroupLayout pCAT_EMPLEADOSLayout = new javax.swing.GroupLayout(pCAT_EMPLEADOS);
        pCAT_EMPLEADOS.setLayout(pCAT_EMPLEADOSLayout);
        pCAT_EMPLEADOSLayout.setHorizontalGroup(
            pCAT_EMPLEADOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCAT_EMPLEADOSLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1215, Short.MAX_VALUE)
                .addContainerGap())
        );
        pCAT_EMPLEADOSLayout.setVerticalGroup(
            pCAT_EMPLEADOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCAT_EMPLEADOSLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbpAdmin.addTab("CAT_EMPLEADOS", pCAT_EMPLEADOS);

        pVALORES_CONTRATO.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblCAT_SUELDOS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblCAT_SUELDOS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblCAT_SUELDOSKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tblCAT_SUELDOS);

        javax.swing.GroupLayout pVALORES_CONTRATOLayout = new javax.swing.GroupLayout(pVALORES_CONTRATO);
        pVALORES_CONTRATO.setLayout(pVALORES_CONTRATOLayout);
        pVALORES_CONTRATOLayout.setHorizontalGroup(
            pVALORES_CONTRATOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pVALORES_CONTRATOLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1215, Short.MAX_VALUE)
                .addContainerGap())
        );
        pVALORES_CONTRATOLayout.setVerticalGroup(
            pVALORES_CONTRATOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pVALORES_CONTRATOLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbpAdmin.addTab("CAT_SUELDOS", pVALORES_CONTRATO);

        pCAT_SUELDOS.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblVALORES_CONTRATO.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(tblVALORES_CONTRATO);

        javax.swing.GroupLayout pCAT_SUELDOSLayout = new javax.swing.GroupLayout(pCAT_SUELDOS);
        pCAT_SUELDOS.setLayout(pCAT_SUELDOSLayout);
        pCAT_SUELDOSLayout.setHorizontalGroup(
            pCAT_SUELDOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCAT_SUELDOSLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1215, Short.MAX_VALUE)
                .addContainerGap())
        );
        pCAT_SUELDOSLayout.setVerticalGroup(
            pCAT_SUELDOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCAT_SUELDOSLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbpAdmin.addTab("VALORES_CONTRATO", pCAT_SUELDOS);

        pCAT_CARGOS_Y_FUNCIONES.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblCAT_CARGOS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblCAT_CARGOS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCAT_CARGOSMouseClicked(evt);
            }
        });
        tblCAT_CARGOS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblCAT_CARGOSKeyReleased(evt);
            }
        });
        jScrollPane7.setViewportView(tblCAT_CARGOS);

        tblFUNCIONES.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane8.setViewportView(tblFUNCIONES);

        javax.swing.GroupLayout pCAT_CARGOS_Y_FUNCIONESLayout = new javax.swing.GroupLayout(pCAT_CARGOS_Y_FUNCIONES);
        pCAT_CARGOS_Y_FUNCIONES.setLayout(pCAT_CARGOS_Y_FUNCIONESLayout);
        pCAT_CARGOS_Y_FUNCIONESLayout.setHorizontalGroup(
            pCAT_CARGOS_Y_FUNCIONESLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCAT_CARGOS_Y_FUNCIONESLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pCAT_CARGOS_Y_FUNCIONESLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1215, Short.MAX_VALUE))
                .addContainerGap())
        );
        pCAT_CARGOS_Y_FUNCIONESLayout.setVerticalGroup(
            pCAT_CARGOS_Y_FUNCIONESLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCAT_CARGOS_Y_FUNCIONESLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbpAdmin.addTab("CAT_CARGOS Y FUNCIONES", pCAT_CARGOS_Y_FUNCIONES);

        pCAT_CONTRATO.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblCAT_CONTRATO.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(tblCAT_CONTRATO);

        javax.swing.GroupLayout pCAT_CONTRATOLayout = new javax.swing.GroupLayout(pCAT_CONTRATO);
        pCAT_CONTRATO.setLayout(pCAT_CONTRATOLayout);
        pCAT_CONTRATOLayout.setHorizontalGroup(
            pCAT_CONTRATOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCAT_CONTRATOLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1215, Short.MAX_VALUE)
                .addContainerGap())
        );
        pCAT_CONTRATOLayout.setVerticalGroup(
            pCAT_CONTRATOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCAT_CONTRATOLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbpAdmin.addTab("CAT_CONTRATO", pCAT_CONTRATO);

        javax.swing.GroupLayout pAdminLayout = new javax.swing.GroupLayout(pAdmin);
        pAdmin.setLayout(pAdminLayout);
        pAdminLayout.setHorizontalGroup(
            pAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pAdminLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tbpAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 1242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        pAdminLayout.setVerticalGroup(
            pAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pAdminLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tbpAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pHerramientasLayout = new javax.swing.GroupLayout(pHerramientas);
        pHerramientas.setLayout(pHerramientasLayout);
        pHerramientasLayout.setHorizontalGroup(
            pHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pHerramientasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pHerramientasLayout.createSequentialGroup()
                        .addComponent(btnNuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRollback)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addGap(606, 606, 606)
                        .addComponent(jButton5)
                        .addContainerGap(26, Short.MAX_VALUE))
                    .addComponent(pAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        pHerramientasLayout.setVerticalGroup(
            pHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pHerramientasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRollback, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBorrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbMensaje.setText(".");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pHerramientas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(pIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(256, 256, 256))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pHerramientas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(pMensaje, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(pIngreso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private boolean estaEnCAT_CARGOS = false;

    private void seleccionarRegistroIngreso(JTable jTable, DefaultTableModel modeloT, String idIngresada) {
        int numFil = jTable.getRowCount();
        //DefaultTableModel modeloT = (DefaultTableModel) jTable.getModel();
        int cont = 0;
        for (int i = 0; i < numFil; i++) {
            String fila = modeloT.getValueAt(i, 0) + "";
            System.out.println(modeloT.getValueAt(i, 0) + "     " + idIngresada);
            if (fila.equals(idIngresada)) {
                System.out.println(modeloT.getValueAt(i, 0) + "       " + i);
                cont = i;
            }
        }
        jTable.getSelectionModel().setSelectionInterval(cont, cont);
    }

    // Metodo para boorar un registro de la tabla VALORES_CONTRATO
    private void boorrarVALORES_CONTRATO(String nomTabla, String id1, String id2) {
        try {
            Object nomCol[] = obtenerNomColumnas(nomTabla);
            st.executeQuery("delete from " + nomTabla + " where " + nomCol[0] + " = '" + id1 + "' and "
                    + nomCol[1] + " = '" + id2 + "'");
            mensaje = "Borrado exitoso.";
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
    }

    // Metodo para saber si existe Valores_Contrato en la tabla
    private boolean exitePKValores_Contrato(String nomTabla, String pk1, String pk2) {
        boolean res = false;
        Object nomCol[] = obtenerNomColumnas(nomTabla);
        try {
            ResultSet rs = st.executeQuery("select * from " + nomTabla + " where " + nomCol[0] + " = '" + pk1 + "' and "
                    + nomCol[1] + " = " + pk2);
            int cont = 0;
            while (rs.next()) {
                cont++;
            }
            if (cont > 0) {
                res = true;
            } else {
                res = false;
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return res;
        }
    }

    // Metodo para actualizar la tabla Valores_Contrato
    private void actualizarValores_Contrato(String nomTabla, String registro[]) {
        try {
            Object nomCol[] = obtenerNomColumnas(nomTabla);
            st.executeQuery("update " + nomTabla + " set " + nomCol[2] + " = " + registro[2] + " where " + nomCol[0] + " = '"
                    + registro[0] + "' and " + nomCol[1] + " = '" + registro[1] + "'");
            mensaje = "Actualización exitosa.";
        } catch (SQLException e) {
            mensaje = e.getMessage();
        }
    }

    private void tblCAT_CARGOSKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_UP) {
            if (tbpAdmin.getSelectedIndex() == 5) {
                siguiente(tblFUNCIONES);

                int fila = tblCAT_CARGOS.getSelectedRow();
                String claveP = tblCAT_CARGOS.getModel().getValueAt(fila, 0).toString();
                verTabla(tblFUNCIONES, "FUNCIONES", 1, buscar("FUNCIONES", "IDCARGO", claveP));

            }
        } else if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN) {
            siguiente(tblFUNCIONES);

            int fila = tblCAT_CARGOS.getSelectedRow();
            String claveP = tblCAT_CARGOS.getModel().getValueAt(fila, 0).toString();
            verTabla(tblFUNCIONES, "FUNCIONES", 1, buscar("FUNCIONES", "IDCARGO", claveP));

        } else if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            siguiente(tblFUNCIONES);

            int fila = tblCAT_CARGOS.getSelectedRow();
            String claveP = tblCAT_CARGOS.getModel().getValueAt(fila, 0).toString();
            verTabla(tblFUNCIONES, "FUNCIONES", 1, buscar("FUNCIONES", "IDCARGO", claveP));

        }

    }

    private void tblFUNCIONESFocusGained(java.awt.event.FocusEvent evt) {
        // TODO add your handling code here:
        estaEnCAT_CARGOS = false;
    }

    private void tblCAT_CARGOSFocusGained(java.awt.event.FocusEvent evt) {
        // TODO add your handling code here:
        estaEnCAT_CARGOS = true;
    }
    private void btnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresoActionPerformed
        ///        jPanel_Inicio.setVisible(true);
        conn = conexion(txtUser.getText(), pwdClave.getText(), txthost.getText());
        if (!(conn + "").equals("null")) {
            try {
                st = conn.createStatement();
            } catch (SQLException e) {
            }

        } else {
            lbMensaje.setText(mensaje);
        }
    }//GEN-LAST:event_btnIngresoActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        if (tbpAdmin.getSelectedIndex() == 0) {
            DefaultMutableTreeNode nodoSelecionado = (DefaultMutableTreeNode) trRegistros.getSelectionPath().getLastPathComponent();
            String V[] = nodoSelecionado.getUserObject().toString().split(" ");
            String idDepto = "";
            String numDepto = "";
            if (nodoSelecionado.getUserObject().toString().equals("EST_INSTITUCIONAL")) {
                ArrayList<Registros> departamentos = ObtenerRegistrosTabla(trRegistros.getModel().getRoot() + "");
                int cont = 0;
                for (Registros depto : departamentos) {
                    if (depto.getRegidtro4().equals("1") && (depto.getRegidtro2() + "").equals("null")) {
                        cont++;
                    }
                }
                txtIdInst.setText((cont + 1) + ".");
                txtNomFun.setText("");
                chbUltNivel.setSelected(false);
                idDepto = txtIdInst.getText();
                numDepto = txtNomFun.getText();
                String registro[] = {txtIdInst.getText(), "", txtNomFun.getText(), "1", "N"};
                guardar("EST_INSTITUCIONAL", registro);
                lbMensaje.setText(mensaje);
            } else {
                ArrayList<Registros> departamentos = ObtenerRegistrosTabla(trRegistros.getModel().getRoot() + "");
                int cont = 0;
                for (Registros depto : departamentos) {
                    if (Integer.parseInt(depto.getRegidtro4() + "") >= 2 && (depto.getRegidtro2() + "").equals(V[0])) {
                        cont++;
                    }
                }
                txtIdInst.setText(V[0] + (cont + 1) + ".");
                txtNomFun.setText("");
                chbUltNivel.setSelected(false);
                idDepto = txtIdInst.getText();
                numDepto = txtNomFun.getText();
                ArrayList<Registros> departamentos2 = ObtenerUnRegistroTabla(trRegistros.getModel().getRoot() + "", V[0]);
                Registros depto = departamentos2.get(0);
                int n = Integer.parseInt(depto.getRegidtro4().toString()) + 1;
                String nivel = n + "";
                String registro[] = {txtIdInst.getText(), V[0], txtNomFun.getText(), nivel, "N"};
                guardar("EST_INSTITUCIONAL", registro);
                lbMensaje.setText(mensaje);
            }
            DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("EST_INSTITUCIONAL");
            DefaultTreeModel mod = modeloArbol("EST_INSTITUCIONAL", raiz, 1, 0, "null");
            trRegistros.setModel(mod);
            DefaultMutableTreeNode nodOBuscado = burcarNodo(raiz, idDepto + " null");
            TreeNode[] nodes = ((DefaultTreeModel) trRegistros.getModel()).getPathToRoot(nodOBuscado);
            TreePath tpath = new TreePath(nodes);
            trRegistros.expandRow(nodoSelecionado.getIndex(nodOBuscado));
            trRegistros.scrollPathToVisible(tpath);
            trRegistros.setSelectionPath(tpath);
            //    verTabla(tblEST_INSTITUCIONAL, "EST_INSTITUCIONAL", 4, obtenerDatos("EST_INSTITUCIONAL", "EST_INSTITUCIONAL"));
            //  DefaultTableModel modT = (DefaultTableModel) tblEST_INSTITUCIONAL.getModel();
            //  seleccionarRegistroIngreso(tblEST_INSTITUCIONAL,modT, idDepto);
        }
//        if (tbpAdmin.getSelectedIndex() == 1) {
//            ArrayList<Registros> departamentos = ObtenerRegistrosTabla("PARAMETROS");
//            String cadena = departamentos.get(0).getRegidtro1();
//            String texto = cadena.substring(0, cadena.length() - 1);
//            String registro[] = {texto + (departamentos.size() + 1), ""};
//            guardar("PARAMETROS", registro);
//            verTabla(tblPARAMETROS, "PARAMETROS", 0, obtenerDatos("PARAMETROS", "PARAMETROS"));
//            lbMensaje.setText(mensaje);
//            DefaultTableModel modT = (DefaultTableModel) tblPARAMETROS.getModel();
//            seleccionarRegistroIngreso(tblPARAMETROS, modT, registro[0]);
//        }
        if (tbpAdmin.getSelectedIndex() == 1) {
            ArrayList<Registros> departamentos = ObtenerRegistrosTabla("CAT_EMPLEADOS");
            String cadena = departamentos.get(0).getRegidtro1();
            String texto = cadena.substring(0, cadena.length() - 1);
            String registro[] = {texto + (departamentos.size() + 1), "" + "", "", "", "", "", "", "", "", "", "", "", "", ""};
            guardar("CAT_EMPLEADOS", registro);
            verTabla(tblCAT_EMPLEADOS, "CAT_EMPLEADOS", 0, obtenerDatos("CAT_EMPLEADOS", "CAT_EMPLEADOS"));

            lbMensaje.setText(mensaje);
            DefaultTableModel modT = (DefaultTableModel) tblCAT_EMPLEADOS.getModel();
            seleccionarRegistroIngreso(tblCAT_EMPLEADOS, modT, registro[0]);
        }
        if (tbpAdmin.getSelectedIndex() == 2) {
            ArrayList<Registros> departamentos = ObtenerRegistrosTabla("CAT_SUELDOS");
            String cadena = departamentos.get(0).getRegidtro1();
            String texto = cadena.substring(0, cadena.length() - 1);
            String registro[] = {texto + (departamentos.size() + 1), "", "0"};
            guardar("CAT_SUELDOS", registro);
            verTabla(tblCAT_SUELDOS, "CAT_SUELDOS", 0, obtenerDatos("CAT_SUELDOS", "CAT_SUELDOS"));
            lbMensaje.setText(mensaje);
            DefaultTableModel modT = (DefaultTableModel) tblCAT_SUELDOS.getModel();
            seleccionarRegistroIngreso(tblCAT_SUELDOS, modT, registro[0]);
        }
        if (tbpAdmin.getSelectedIndex() == 3) {
            DefaultTableModel modeloT = (DefaultTableModel) tblVALORES_CONTRATO.getModel();
            modeloT.addRow(new Object[]{"", "", "0"});
            int numFil = tblVALORES_CONTRATO.getRowCount();
            tblVALORES_CONTRATO.getSelectionModel().setSelectionInterval(numFil - 1, numFil - 1);
        }
        if (tbpAdmin.getSelectedIndex() == 4) {
            if (estaEnCAT_CARGOS == false) {
                int fila = tblCAT_CARGOS.getSelectedRow();
                String claveP = tblCAT_CARGOS.getModel().getValueAt(fila, 0).toString();
                ArrayList<Registros> departamentos = ObtenerRegistrosTabla("FUNCIONES");
                String cadena = departamentos.get(0).getRegidtro1();
                String texto = cadena.substring(0, cadena.length() - 1);
                String registro[] = {texto + (departamentos.size() + 1), claveP, ""};
                guardar("FUNCIONES", registro);
                lbMensaje.setText(mensaje);
                verTabla(tblFUNCIONES, "FUNCIONES", 1, buscar("FUNCIONES", "IDCARGO", claveP));
                DefaultTableModel modT = (DefaultTableModel) tblFUNCIONES.getModel();
                seleccionarRegistroIngreso(tblFUNCIONES, modT, registro[0]);
            } else if (estaEnCAT_CARGOS == true) {
                ArrayList<Registros> departamentos = ObtenerRegistrosTabla("CAT_CARGOS");
                String cadena = departamentos.get(0).getRegidtro1();
                String texto = cadena.substring(0, cadena.length() - 1);
                String registro[] = {texto + (departamentos.size() + 1), ""};
                guardar("CAT_CARGOS", registro);
                verTabla(tblCAT_CARGOS, "CAT_CARGOS", 0, obtenerDatos("CAT_CARGOS", "CAT_CARGOS"));
                lbMensaje.setText(mensaje);
                DefaultTableModel modT = (DefaultTableModel) tblCAT_CARGOS.getModel();
                seleccionarRegistroIngreso(tblCAT_CARGOS, modT, registro[0]);

                if (tblFUNCIONES.getRowCount() == 0) {
                    DefaultTableModel model = (DefaultTableModel) tblFUNCIONES.getModel();
                    model.addRow(new Object[]{"", "", ""});
                }
            }

        }
        if (tbpAdmin.getSelectedIndex() == 5) {
            ArrayList<Registros> departamentos = ObtenerRegistrosTabla("CAT_CONTRATO");
            String cadena = departamentos.get(0).getRegidtro1();
            String texto = cadena.substring(0, cadena.length() - 1);
            String registro[] = {texto + (departamentos.size() + 1), "", "", "", "", "", "1", "1", "", "", "", "", "0"};
            guardar("CAT_CONTRATO", registro);
            verTabla(tblCAT_CONTRATO, "CAT_CONTRATO", 0, obtenerDatos("CAT_CONTRATO", "CAT_CONTRATO"));
            lbMensaje.setText(mensaje);
            DefaultTableModel modT = (DefaultTableModel) tblCAT_CONTRATO.getModel();
            seleccionarRegistroIngreso(tblCAT_CONTRATO, modT, registro[0]);
        }
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        if (tbpAdmin.getSelectedIndex() == 0) {
            if ((trRegistros.getModel().getRoot() + "").equals("EST_INSTITUCIONAL")) {
                DefaultMutableTreeNode nodoSelecionado = (DefaultMutableTreeNode) trRegistros.getSelectionPath().getLastPathComponent();
                String V[] = nodoSelecionado.getUserObject().toString().split(" ");
                DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) nodoSelecionado.getParent();
                DefaultMutableTreeNode nodoAnterior = (DefaultMutableTreeNode) parentNode.getChildBefore(nodoSelecionado);
                txtIdInst.setText("");
                txtNomFun.setText("");
                chbUltNivel.setSelected(false);
                borrar(trRegistros.getModel().getRoot() + "", V[0]);
                if (mensaje.equals("Borrado exitoso.")) {
                    lbMensaje.setText(mensaje);
                } else {
                    lbMensaje.setText(mensaje);
                }
                DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(trRegistros.getModel().getRoot() + "");
                DefaultTreeModel mod = modeloArbol(trRegistros.getModel().getRoot() + "", raiz, 1, 0, "null");
                if ((parentNode.getChildBefore(nodoSelecionado) + "").equals("null")) {
                    DefaultMutableTreeNode nodoPadre = new DefaultMutableTreeNode(nodoSelecionado.getParent() + "");
                    DefaultMutableTreeNode nodOBuscado = burcarNodo(raiz, nodoPadre + "");
                    TreeNode[] nodes = ((DefaultTreeModel) trRegistros.getModel()).getPathToRoot(nodOBuscado);
                    TreePath tpath = new TreePath(nodes);
                    trRegistros.setModel(mod);
                    trRegistros.expandRow(nodoSelecionado.getIndex(nodOBuscado));
                    trRegistros.scrollPathToVisible(tpath);
                    trRegistros.setSelectionPath(tpath);
                } else {
                    DefaultMutableTreeNode nodOBuscado = burcarNodo(raiz, nodoAnterior + "");
                    TreeNode[] nodes = ((DefaultTreeModel) trRegistros.getModel()).getPathToRoot(nodOBuscado);
                    TreePath tpath = new TreePath(nodes);
                    trRegistros.setModel(mod);
                    trRegistros.expandRow(nodoSelecionado.getIndex(nodOBuscado));
                    trRegistros.scrollPathToVisible(tpath);
                    trRegistros.setSelectionPath(tpath);
                }
            }
        }
//        if (tbpAdmin.getSelectedIndex() == 1) {
//            int filSelect = tblPARAMETROS.getSelectedRow();
//            DefaultTableModel modeloT = (DefaultTableModel) tblPARAMETROS.getModel();
//            String id = modeloT.getValueAt(filSelect, 0) + "";
//            borrar("PARAMETROS", id);
//            lbMensaje.setText(mensaje);
//            verTabla(tblPARAMETROS, "PARAMETROS", 0, obtenerDatos("PARAMETROS", "PARAMETROS"));
//            tblPARAMETROS.getSelectionModel().setSelectionInterval(filSelect - 1, filSelect - 1);
//        }
        if (tbpAdmin.getSelectedIndex() == 1) {
            int filSelect = tblCAT_EMPLEADOS.getSelectedRow();
            DefaultTableModel modeloT = (DefaultTableModel) tblCAT_EMPLEADOS.getModel();
            String id = modeloT.getValueAt(filSelect, 0) + "";
            borrar("CAT_EMPLEADOS", id);
            lbMensaje.setText(mensaje);
            verTabla(tblCAT_EMPLEADOS, "CAT_EMPLEADOS", 0, obtenerDatos("CAT_EMPLEADOS", "CAT_EMPLEADOS"));
            tblCAT_EMPLEADOS.getSelectionModel().setSelectionInterval(filSelect - 1, filSelect - 1);
        }
        if (tbpAdmin.getSelectedIndex() == 2) {
            int filSelect = tblCAT_SUELDOS.getSelectedRow();
            DefaultTableModel modeloT = (DefaultTableModel) tblCAT_SUELDOS.getModel();
            String id = modeloT.getValueAt(filSelect, 0) + "";
            borrar("CAT_SUELDOS", id);
            lbMensaje.setText(mensaje);
            verTabla(tblCAT_SUELDOS, "CAT_SUELDOS", 0, obtenerDatos("CAT_SUELDOS", "CAT_SUELDOS"));
            tblCAT_SUELDOS.getSelectionModel().setSelectionInterval(filSelect - 1, filSelect - 1);
        }
        if (tbpAdmin.getSelectedIndex() == 3) {
            int filSelect = tblVALORES_CONTRATO.getSelectedRow();
            DefaultTableModel modeloT = (DefaultTableModel) tblVALORES_CONTRATO.getModel();
            String id = modeloT.getValueAt(filSelect, 0) + "";
            String id2 = modeloT.getValueAt(filSelect, 1) + "";
            if (exitePKValores_Contrato("VALORES_CONTRATO", modeloT.getValueAt(filSelect, 0) + "", modeloT.getValueAt(filSelect, 1) + "")) {
                boorrarVALORES_CONTRATO("VALORES_CONTRATO", id, id2);
                lbMensaje.setText(mensaje);
                verTabla(tblVALORES_CONTRATO, "VALORES_CONTRATO", -1, obtenerDatos("VALORES_CONTRATO", "VALORES_CONTRATO"));
                tblVALORES_CONTRATO.getSelectionModel().setSelectionInterval(filSelect - 1, filSelect - 1);
            } else {
                modeloT.removeRow(filSelect);
                tblVALORES_CONTRATO.getSelectionModel().setSelectionInterval(filSelect - 1, filSelect - 1);
            }
        }
        if (tbpAdmin.getSelectedIndex() == 4) {
            String botones[] = {"CAT_CARGOS", "FUNCIONES"};
            int eleccion = JOptionPane.showOptionDialog(rootPane, "¿En qué tabla desea borrar el registro seleccionado?",
                    "Ingreso de registro", 0, JOptionPane.INFORMATION_MESSAGE, null, botones, this);
            if (eleccion == JOptionPane.NO_OPTION) {
                int filSelect = tblFUNCIONES.getSelectedRow();
                DefaultTableModel modeloT = (DefaultTableModel) tblFUNCIONES.getModel();
                String id = modeloT.getValueAt(filSelect, 0) + "";
                String claveP = modeloT.getValueAt(filSelect, 1) + "";
                borrar("FUNCIONES", id);
                lbMensaje.setText(mensaje);
                verTabla(tblFUNCIONES, "FUNCIONES", 1, buscar("FUNCIONES", "IDCARGO", claveP));
                tblFUNCIONES.getSelectionModel().setSelectionInterval(filSelect - 1, filSelect - 1);
            } else if (eleccion == JOptionPane.YES_OPTION) {
                int filSelect = tblCAT_CARGOS.getSelectedRow();
                DefaultTableModel modeloT = (DefaultTableModel) tblCAT_CARGOS.getModel();
                String id = modeloT.getValueAt(filSelect, 0) + "";
                borrar("CAT_CARGOS", id);
                verTabla(tblCAT_CARGOS, "CAT_CARGOS", 0, obtenerDatos("CAT_CARGOS", "CAT_CARGOS"));
                lbMensaje.setText(mensaje);
                tblCAT_CARGOS.getSelectionModel().setSelectionInterval(filSelect - 1, filSelect - 1);
            }
        }
        if (tbpAdmin.getSelectedIndex() == 5) {
            int filSelect = tblCAT_CONTRATO.getSelectedRow();
            DefaultTableModel modeloT = (DefaultTableModel) tblCAT_CONTRATO.getModel();
            String id = modeloT.getValueAt(filSelect, 0) + "";
            borrar("CAT_CONTRATO", id);
            lbMensaje.setText(mensaje);
            verTabla(tblCAT_CONTRATO, "CAT_CONTRATO", 0, obtenerDatos("CAT_CONTRATO", "CAT_CONTRATO"));
            tblCAT_CONTRATO.getSelectionModel().setSelectionInterval(filSelect - 1, filSelect - 1);
        }
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (tbpAdmin.getSelectedIndex() == 0) {
            DefaultMutableTreeNode nodoSelecionado = (DefaultMutableTreeNode) trRegistros.getSelectionPath().getLastPathComponent();
            String V[] = nodoSelecionado.getUserObject().toString().split(" ");
            String idDepto = "";
            String nomDepto = "";
            if (nodoSelecionado.getUserObject().toString().equals("EST_INSTITUCIONAL")) {
                trRegistros.setSelectionRow(0);
            } else {
                idDepto = txtIdInst.getText();
                nomDepto = txtNomFun.getText();
                String idDeptoP = "";
                String ultNinel = "";
                if (chbUltNivel.isSelected()) {
                    ultNinel = "S";
                } else {
                    ultNinel = "N";
                }
                ArrayList<Registros> departamentos = ObtenerUnRegistroTabla(trRegistros.getModel().getRoot() + "", V[0]);
                Registros depto = departamentos.get(0);
                if ((departamentos.get(0).getRegidtro2() + "").equals("null")) {
                    idDeptoP = "";
                } else {
                    idDeptoP = departamentos.get(0).getRegidtro2() + "";
                }
                int n = Integer.parseInt(depto.getRegidtro4().toString());
                String nivel = n + "";
                String registro[] = {idDepto, idDeptoP, nomDepto, nivel, ultNinel};
                actualizar("EST_INSTITUCIONAL", registro);
                lbMensaje.setText(mensaje);

                DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("EST_INSTITUCIONAL");
                DefaultTreeModel mod = modeloArbol("EST_INSTITUCIONAL", raiz, 1, 0, "null");
                trRegistros.setModel(mod);
                DefaultMutableTreeNode nodOBuscado = burcarNodo(raiz, idDepto + " " + nomDepto);
                TreeNode[] nodes = ((DefaultTreeModel) trRegistros.getModel()).getPathToRoot(nodOBuscado);
                TreePath tpath = new TreePath(nodes);
                trRegistros.expandRow(nodoSelecionado.getIndex(nodOBuscado));
                trRegistros.scrollPathToVisible(tpath);
                trRegistros.setSelectionPath(tpath);
            }
        }
//        if (tbpAdmin.getSelectedIndex() == 1) {
//            int filSelect = tblPARAMETROS.getSelectedRow();
//            DefaultTableModel modeloT = (DefaultTableModel) tblPARAMETROS.getModel();
//            String registro[] = {modeloT.getValueAt(filSelect, 0) + "", modeloT.getValueAt(filSelect, 1) + ""};
//            actualizar("PARAMETROS", registro);
//            lbMensaje.setText(mensaje);
//            verTabla(tblPARAMETROS, "PARAMETROS", 0, obtenerDatos("PARAMETROS", "PARAMETROS"));
//            tblPARAMETROS.getSelectionModel().setSelectionInterval(filSelect, filSelect);
//        }
        if (tbpAdmin.getSelectedIndex() == 1) {
            int filSelect = tblCAT_EMPLEADOS.getSelectedRow();
            DefaultTableModel modeloT = (DefaultTableModel) tblCAT_EMPLEADOS.getModel();
            String registro[] = {modeloT.getValueAt(filSelect, 0) + "", modeloT.getValueAt(filSelect, 1) + "", modeloT.getValueAt(filSelect, 2) + "", modeloT.getValueAt(filSelect, 3) + "", modeloT.getValueAt(filSelect, 4) + "", modeloT.getValueAt(filSelect, 5) + "", modeloT.getValueAt(filSelect, 6) + "", modeloT.getValueAt(filSelect, 7) + "", modeloT.getValueAt(filSelect, 8) + "", modeloT.getValueAt(filSelect, 9) + "", modeloT.getValueAt(filSelect, 10) + "", modeloT.getValueAt(filSelect, 11) + "", modeloT.getValueAt(filSelect, 12) + "", modeloT.getValueAt(filSelect, 13) + ""};
            actualizar("CAT_EMPLEADOS", registro);
            lbMensaje.setText(mensaje);
            verTabla(tblCAT_EMPLEADOS, "CAT_EMPLEADOS", 0, obtenerDatos("CAT_EMPLEADOS", "CAT_EMPLEADOS"));
            tblCAT_EMPLEADOS.getSelectionModel().setSelectionInterval(filSelect, filSelect);
        }
        if (tbpAdmin.getSelectedIndex() == 2) {
            int filSelect = tblCAT_SUELDOS.getSelectedRow();
            DefaultTableModel modeloT = (DefaultTableModel) tblCAT_SUELDOS.getModel();
            String registro[] = {modeloT.getValueAt(filSelect, 0) + "", modeloT.getValueAt(filSelect, 1) + "", modeloT.getValueAt(filSelect, 2) + ""};
            actualizar("CAT_SUELDOS", registro);
            lbMensaje.setText(mensaje);
            verTabla(tblCAT_SUELDOS, "CAT_SUELDOS", 0, obtenerDatos("CAT_SUELDOS", "CAT_SUELDOS"));
            tblCAT_SUELDOS.getSelectionModel().setSelectionInterval(filSelect, filSelect);
        }
        if (tbpAdmin.getSelectedIndex() == 3) {
            int filSelect = tblVALORES_CONTRATO.getSelectedRow();
            DefaultTableModel modeloT = (DefaultTableModel) tblVALORES_CONTRATO.getModel();
            String registro[] = {modeloT.getValueAt(filSelect, 0) + "", modeloT.getValueAt(filSelect, 1) + "", modeloT.getValueAt(filSelect, 2) + ""};
            if (exitePKValores_Contrato("VALORES_CONTRATO", modeloT.getValueAt(filSelect, 0) + "", modeloT.getValueAt(filSelect, 1) + "")) {
                actualizarValores_Contrato("VALORES_CONTRATO", registro);
            } else {
                guardar("VALORES_CONTRATO", registro);
            }
            lbMensaje.setText(mensaje);
            verTabla(tblVALORES_CONTRATO, "VALORES_CONTRATO", -1, obtenerDatos("VALORES_CONTRATO", "VALORES_CONTRATO"));
            tblVALORES_CONTRATO.getSelectionModel().setSelectionInterval(filSelect, filSelect);
        }
        if (tbpAdmin.getSelectedIndex() == 4) {
            String botones[] = {"CAT_CARGOS", "FUNCIONES"};
            int eleccion = JOptionPane.showOptionDialog(rootPane, "¿En qué tabla desea guardar un registro?",
                    "Ingreso de registro", 0, JOptionPane.INFORMATION_MESSAGE, null, botones, this);
            if (eleccion == JOptionPane.NO_OPTION) {
                int filSelect = tblFUNCIONES.getSelectedRow();
                DefaultTableModel modeloT = (DefaultTableModel) tblFUNCIONES.getModel();
                String registro[] = {modeloT.getValueAt(filSelect, 0) + "", modeloT.getValueAt(filSelect, 1) + "", modeloT.getValueAt(filSelect, 2) + ""};
                actualizar("FUNCIONES", registro);
                lbMensaje.setText(mensaje);
                tblFUNCIONES.getSelectionModel().setSelectionInterval(filSelect, filSelect);
            } else if (eleccion == JOptionPane.YES_OPTION) {
                int filSelect = tblCAT_CARGOS.getSelectedRow();
                DefaultTableModel modeloT = (DefaultTableModel) tblCAT_CARGOS.getModel();
                String registro[] = {modeloT.getValueAt(filSelect, 0) + "", modeloT.getValueAt(filSelect, 1) + ""};
                actualizar("CAT_CARGOS", registro);
                verTabla(tblCAT_CARGOS, "CAT_CARGOS", 0, obtenerDatos("CAT_CARGOS", "CAT_CARGOS"));
                lbMensaje.setText(mensaje);
                tblCAT_CARGOS.getSelectionModel().setSelectionInterval(filSelect, filSelect);
            }
        }
        if (tbpAdmin.getSelectedIndex() == 5) {
            int filSelect = tblCAT_CONTRATO.getSelectedRow();
            DefaultTableModel modeloT = (DefaultTableModel) tblCAT_CONTRATO.getModel();
            String registro[] = {modeloT.getValueAt(filSelect, 0) + "", modeloT.getValueAt(filSelect, 1) + "", modeloT.getValueAt(filSelect, 2) + "", modeloT.getValueAt(filSelect, 3) + "", modeloT.getValueAt(filSelect, 4) + "", modeloT.getValueAt(filSelect, 5) + "", modeloT.getValueAt(filSelect, 6) + "", modeloT.getValueAt(filSelect, 7) + "", modeloT.getValueAt(filSelect, 8) + "", modeloT.getValueAt(filSelect, 9) + "", modeloT.getValueAt(filSelect, 10) + "", modeloT.getValueAt(filSelect, 11) + "", modeloT.getValueAt(filSelect, 12) + ""};
            actualizar("CAT_CONTRATO", registro);
            lbMensaje.setText(mensaje);
            verTabla(tblCAT_CONTRATO, "CAT_CONTRATO", 0, obtenerDatos("CAT_CONTRATO", "CAT_CONTRATO"));
            tblCAT_CONTRATO.getSelectionModel().setSelectionInterval(filSelect, filSelect);
        }
        try {
            Calendar fecha = new GregorianCalendar();
            int año = fecha.get(Calendar.YEAR);
            int mes = fecha.get(Calendar.MONTH);
            int dia = fecha.get(Calendar.DAY_OF_MONTH);
            int hora = fecha.get(Calendar.HOUR_OF_DAY);
            int minuto = fecha.get(Calendar.MINUTE);
            int segundo = fecha.get(Calendar.SECOND);
            conn.commit();
            lbMensaje.setText("Commit exitoso " + dia + "/" + (mes + 1) + "/" + año + " " + hora + ":" + minuto + ":" + segundo);
        } catch (SQLException e) {
            lbMensaje.setText(e.getMessage());
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
//        if (tbpAdmin.getSelectedIndex() == 1) {
//            Object nomCol[] = obtenerNomColumnas("PARAMETROS");
//            int numfil = tblPARAMETROS.getSelectedRow();
//            DefaultTableModel modeloT = (DefaultTableModel) tblPARAMETROS.getModel();
//            String registro[] = {modeloT.getValueAt(numfil, 0) + "", modeloT.getValueAt(numfil, 1) + ""};
//            for (int i = 0; i < nomCol.length; i++) {
//                if (!registro[i].equals("")) {
//                    System.out.println(nomCol[i] + ", " + registro[i]);
//                    verTabla(tblPARAMETROS, "PARAMETROS", 0, buscar("PARAMETROS", nomCol[i] + "", registro[i]));
//                }
//            }
//        }
        if (tbpAdmin.getSelectedIndex() == 1) {
            Object nomCol[] = obtenerNomColumnas("CAT_EMPLEADOS");
            int numfil = tblCAT_EMPLEADOS.getSelectedRow();
            DefaultTableModel modeloT = (DefaultTableModel) tblCAT_EMPLEADOS.getModel();
            String registro[] = {modeloT.getValueAt(numfil, 0) + "", modeloT.getValueAt(numfil, 1) + "", modeloT.getValueAt(numfil, 2) + "", modeloT.getValueAt(numfil, 3) + "", modeloT.getValueAt(numfil, 4) + "", modeloT.getValueAt(numfil, 5) + "", modeloT.getValueAt(numfil, 6) + "", modeloT.getValueAt(numfil, 7) + "", modeloT.getValueAt(numfil, 8) + "", modeloT.getValueAt(numfil, 9) + "", modeloT.getValueAt(numfil, 10) + "", modeloT.getValueAt(numfil, 11) + "", modeloT.getValueAt(numfil, 12) + "", modeloT.getValueAt(numfil, 13) + ""};
            for (int i = 0; i < nomCol.length; i++) {
                if (!registro[i].equals("")) {
                    System.out.println(nomCol[i] + ", " + registro[i]);
                    verTabla(tblCAT_EMPLEADOS, "CAT_EMPLEADOS", 0, buscar("CAT_EMPLEADOS", nomCol[i] + "", registro[i]));
                }
            }
        }
        if (tbpAdmin.getSelectedIndex() == 2) {
            Object nomCol[] = obtenerNomColumnas("CAT_SUELDOS");
            int numfil = tblCAT_SUELDOS.getSelectedRow();
            DefaultTableModel modeloT = (DefaultTableModel) tblCAT_SUELDOS.getModel();
            String registro[] = {modeloT.getValueAt(numfil, 0) + "", modeloT.getValueAt(numfil, 1) + "", modeloT.getValueAt(numfil, 2) + ""};
            for (int i = 0; i < nomCol.length; i++) {
                if (!registro[i].equals("")) {
                    System.out.println(nomCol[i] + ", " + registro[i]);
                    verTabla(tblCAT_SUELDOS, "CAT_SUELDOS", 0, buscar("CAT_SUELDOS", nomCol[i] + "", registro[i]));
                }
            }
        }
        if (tbpAdmin.getSelectedIndex() == 3) {
            Object nomCol[] = obtenerNomColumnas("VALORES_CONTRATO");
            int numfil = tblVALORES_CONTRATO.getSelectedRow();
            DefaultTableModel modeloT = (DefaultTableModel) tblVALORES_CONTRATO.getModel();
            String registro[] = {modeloT.getValueAt(numfil, 0) + "", modeloT.getValueAt(numfil, 1) + "", modeloT.getValueAt(numfil, 2) + ""};
            for (int i = 0; i < nomCol.length; i++) {
                if (!registro[i].equals("")) {
                    System.out.println(nomCol[i] + ", " + registro[i]);
                    verTabla(tblVALORES_CONTRATO, "VALORES_CONTRATO", -1, buscar("VALORES_CONTRATO", nomCol[i] + "", registro[i]));
                }
            }
        }
        if (tbpAdmin.getSelectedIndex() == 4) {
            String botones[] = {"CAT_CARGOS", "FUNCIONES"};
            int eleccion = JOptionPane.showOptionDialog(rootPane, "¿En qué tabla desea buscar registro?",
                    "Ingreso de registro", 0, JOptionPane.INFORMATION_MESSAGE, null, botones, this);
            if (eleccion == JOptionPane.NO_OPTION) {
                Object nomCol[] = obtenerNomColumnas("FUNCIONES");
                int numfil = tblFUNCIONES.getSelectedRow();
                DefaultTableModel modeloT = (DefaultTableModel) tblFUNCIONES.getModel();
                String registro[] = {modeloT.getValueAt(numfil, 0) + "", modeloT.getValueAt(numfil, 1) + "", modeloT.getValueAt(numfil, 2) + ""};
                for (int i = 0; i < nomCol.length; i++) {
                    if (!registro[i].equals("")) {
                        System.out.println(nomCol[i] + ", " + registro[i]);
                        verTabla(tblFUNCIONES, "FUNCIONES", 1, buscar("FUNCIONES", nomCol[i] + "", registro[i]));
                    }
                }
            } else if (eleccion == JOptionPane.YES_OPTION) {
                Object nomCol[] = obtenerNomColumnas("CAT_CARGOS");
                int numfil = tblCAT_CARGOS.getSelectedRow();
                DefaultTableModel modeloT = (DefaultTableModel) tblCAT_CARGOS.getModel();
                String registro[] = {modeloT.getValueAt(numfil, 0) + "", modeloT.getValueAt(numfil, 1) + ""};
                for (int i = 0; i < nomCol.length; i++) {
                    if (!registro[i].equals("")) {
                        System.out.println(nomCol[i] + ", " + registro[i]);
                        verTabla(tblCAT_CARGOS, "CAT_CARGOS", 0, buscar("CAT_CARGOS", nomCol[i] + "", registro[i]));
                    }
                }
            }
        }
        if (tbpAdmin.getSelectedIndex() == 5) {
            Object nomCol[] = obtenerNomColumnas("CAT_CONTRATO");
            int numfil = tblCAT_CONTRATO.getSelectedRow();
            DefaultTableModel modeloT = (DefaultTableModel) tblCAT_CONTRATO.getModel();
            String registro[] = {modeloT.getValueAt(numfil, 0) + "", modeloT.getValueAt(numfil, 1) + "", modeloT.getValueAt(numfil, 2) + "", modeloT.getValueAt(numfil, 3) + "", modeloT.getValueAt(numfil, 4) + "", modeloT.getValueAt(numfil, 5) + "", modeloT.getValueAt(numfil, 6) + "", modeloT.getValueAt(numfil, 7) + "", modeloT.getValueAt(numfil, 8) + "", modeloT.getValueAt(numfil, 9) + "", modeloT.getValueAt(numfil, 10) + "", modeloT.getValueAt(numfil, 11) + "", modeloT.getValueAt(numfil, 12) + ""};
            for (int i = 0; i < nomCol.length; i++) {
                if (!registro[i].equals("")) {
                    System.out.println(nomCol[i] + ", " + registro[i]);
                    verTabla(tblCAT_CONTRATO, "CAT_CONTRATO", 0, buscar("CAT_CONTRATO", nomCol[i] + "", registro[i]));
                }
            }
        }

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnRollbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRollbackActionPerformed
        try {
            Calendar fecha = new GregorianCalendar();
            int año = fecha.get(Calendar.YEAR);
            int mes = fecha.get(Calendar.MONTH);
            int dia = fecha.get(Calendar.DAY_OF_MONTH);
            int hora = fecha.get(Calendar.HOUR_OF_DAY);
            int minuto = fecha.get(Calendar.MINUTE);
            int segundo = fecha.get(Calendar.SECOND);
            conn.rollback();
            lbMensaje.setText("Rollback exitoso " + dia + "/" + (mes + 1) + "/" + año + " " + hora + ":" + minuto + ":" + segundo);
            if (tbpAdmin.getSelectedIndex() == 0) {
                String nomTabla = trRegistros.getModel().getRoot() + "";
                if (nomTabla.equals("EST_INSTITUCIONAL")) {
                    //pTablas.setVisible(false); pHerramientas.setVisible(true); pAdmin.setVisible(true);
                    DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(nomTabla);
                    trRegistros.setModel(modeloArbol(nomTabla, raiz, 1, 0, "null"));
                    txtIdInst.setText("");
                    txtNomFun.setText("");
                    chbUltNivel.setSelected(false);
                    trRegistros.setSelectionRow(0);
                }
            }
//            if (tbpAdmin.getSelectedIndex() == 1) {
//                verTabla(tblPARAMETROS, "PARAMETROS", 0, obtenerDatos("PARAMETROS", "PARAMETROS"));
//            }
            if (tbpAdmin.getSelectedIndex() == 1) {
                verTabla(tblCAT_EMPLEADOS, "CAT_EMPLEADOS", 0, obtenerDatos("CAT_EMPLEADOS", "CAT_EMPLEADOS"));
            }
            if (tbpAdmin.getSelectedIndex() == 2) {
                verTabla(tblCAT_SUELDOS, "CAT_SUELDOS", 0, obtenerDatos("CAT_SUELDOS", "CAT_SUELDOS"));
            }
            if (tbpAdmin.getSelectedIndex() == 3) {
                verTabla(tblVALORES_CONTRATO, "VALORES_CONTRATO", 0, obtenerDatos("VALORES_CONTRATO", "VALORES_CONTRATO"));
            }
            if (tbpAdmin.getSelectedIndex() == 4) {
                verTabla(tblCAT_CARGOS, "CAT_CARGOS", 0, obtenerDatos("CAT_CARGOS", "CAT_CARGOS"));
                verTabla(tblFUNCIONES, "FUNCIONES", 0, obtenerDatos("FUNCIONES", "FUNCIONES"));
            }
            if (tbpAdmin.getSelectedIndex() == 5) {
                verTabla(tblCAT_CONTRATO, "CAT_CONTRATO", 0, obtenerDatos("CAT_CONTRATO", "CAT_CONTRATO"));
            }
        } catch (SQLException e) {
            lbMensaje.setText(e.getMessage());
        }
    }//GEN-LAST:event_btnRollbackActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//        if (tbpAdmin.getSelectedIndex() == 1) {
//            tblPARAMETROS.getSelectionModel().setSelectionInterval(0, 0);
//        }
        if (tbpAdmin.getSelectedIndex() == 1) {
            tblCAT_EMPLEADOS.getSelectionModel().setSelectionInterval(0, 0);
        }
        if (tbpAdmin.getSelectedIndex() == 2) {
            tblCAT_SUELDOS.getSelectionModel().setSelectionInterval(0, 0);
        }
        if (tbpAdmin.getSelectedIndex() == 3) {
            tblVALORES_CONTRATO.getSelectionModel().setSelectionInterval(0, 0);
        }
        if (tbpAdmin.getSelectedIndex() == 4) {
            tblCAT_CARGOS.getSelectionModel().setSelectionInterval(0, 0);
            tblFUNCIONES.getSelectionModel().setSelectionInterval(0, 0);
        }
        if (tbpAdmin.getSelectedIndex() == 5) {
            tblCAT_CONTRATO.getSelectionModel().setSelectionInterval(0, 0);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

//        if (tbpAdmin.getSelectedIndex() == 1) {
//            int ultFil = tblPARAMETROS.getRowCount();
//            tblPARAMETROS.getSelectionModel().setSelectionInterval(ultFil - 1, ultFil - 1);
//        }
        if (tbpAdmin.getSelectedIndex() == 1) {
            int ultFil = tblCAT_EMPLEADOS.getRowCount();
            tblCAT_EMPLEADOS.getSelectionModel().setSelectionInterval(ultFil - 1, ultFil - 1);
        }
        if (tbpAdmin.getSelectedIndex() == 2) {
            int ultFil = tblCAT_SUELDOS.getRowCount();
            tblCAT_SUELDOS.getSelectionModel().setSelectionInterval(ultFil - 1, ultFil - 1);
        }
        if (tbpAdmin.getSelectedIndex() == 3) {
            int ultFil = tblVALORES_CONTRATO.getRowCount();
            tblVALORES_CONTRATO.getSelectionModel().setSelectionInterval(ultFil - 1, ultFil - 1);
        }
        if (tbpAdmin.getSelectedIndex() == 4) {
            int ultFil = tblCAT_CARGOS.getRowCount();
            tblCAT_CARGOS.getSelectionModel().setSelectionInterval(ultFil - 1, ultFil - 1);
            int ultFil2 = tblFUNCIONES.getRowCount();
            tblFUNCIONES.getSelectionModel().setSelectionInterval(ultFil2 - 1, ultFil2 - 1);
        }
        if (tbpAdmin.getSelectedIndex() == 5) {
            int ultFil = tblCAT_CONTRATO.getRowCount();
            tblCAT_CONTRATO.getSelectionModel().setSelectionInterval(ultFil - 1, ultFil - 1);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
//        if (tbpAdmin.getSelectedIndex() == 1) {
//            anterior(tblPARAMETROS);
//            int fila = tblCAT_CARGOS.getSelectedRow();
//            String claveP = tblCAT_CARGOS.getModel().getValueAt(fila, 0).toString();
//            verTabla(tblFUNCIONES, "FUNCIONES", 1, buscar("FUNCIONES", "IDCARGO", claveP));
//
//        }
        if (tbpAdmin.getSelectedIndex() == 1) {
            anterior(tblCAT_EMPLEADOS);

        }
        if (tbpAdmin.getSelectedIndex() == 2) {
            anterior(tblCAT_SUELDOS);

        }
        if (tbpAdmin.getSelectedIndex() == 3) {
            anterior(tblVALORES_CONTRATO);

        }
        if (tbpAdmin.getSelectedIndex() == 4) {
            if (estaEnCAT_CARGOS) {
                anterior(tblCAT_CARGOS);
                int fila = tblCAT_CARGOS.getSelectedRow();
                String claveP = tblCAT_CARGOS.getModel().getValueAt(fila, 0).toString();
                verTabla(tblFUNCIONES, "FUNCIONES", 1, buscar("FUNCIONES", "IDCARGO", claveP));
            } else {
                anterior(tblFUNCIONES);
                int fila = tblCAT_CARGOS.getSelectedRow();
                String claveP = tblCAT_CARGOS.getModel().getValueAt(fila, 0).toString();
                verTabla(tblFUNCIONES, "FUNCIONES", 1, buscar("FUNCIONES", "IDCARGO", claveP));
            }
        }
        if (tbpAdmin.getSelectedIndex() == 5) {
            anterior(tblCAT_CONTRATO);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3KeyPressed

    private void jButton3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton3KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3KeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        //   if (tbpAdmin.getSelectedIndex() == 0) siguiente(tblEST_INSTITUCIONAL);
//        if (tbpAdmin.getSelectedIndex() == 1) {
//            siguiente(tblPARAMETROS);
//        }
        if (tbpAdmin.getSelectedIndex() == 1) {
            siguiente(tblCAT_EMPLEADOS);
        }
        if (tbpAdmin.getSelectedIndex() == 2) {
            siguiente(tblCAT_SUELDOS);
        }
        if (tbpAdmin.getSelectedIndex() == 3) {
            siguiente(tblVALORES_CONTRATO);
        }
        if (tbpAdmin.getSelectedIndex() == 4) {
            if (estaEnCAT_CARGOS) {
                siguiente(tblCAT_CARGOS);
                int fila = tblCAT_CARGOS.getSelectedRow();
                String claveP = tblCAT_CARGOS.getModel().getValueAt(fila, 0).toString();
                verTabla(tblFUNCIONES, "FUNCIONES", 1, buscar("FUNCIONES", "IDCARGO", claveP));

            } else {
                siguiente(tblFUNCIONES);

                int fila = tblCAT_CARGOS.getSelectedRow();
                String claveP = tblCAT_CARGOS.getModel().getValueAt(fila, 0).toString();
                verTabla(tblFUNCIONES, "FUNCIONES", 1, buscar("FUNCIONES", "IDCARGO", claveP));
            }
        }
        if (tbpAdmin.getSelectedIndex() == 5) {
            siguiente(tblCAT_CONTRATO);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnIngreso1btnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngreso1btnIngresoActionPerformed
        ///        jPanel_Inicio.setVisible(true);
        conn = conexion(txtUser.getText(), pwdClave.getText(), txthost.getText());
        if (!(conn + "").equals("null")) {
            try {
                st = conn.createStatement();
            } catch (SQLException e) {
            }

        } else {
            lbMensaje.setText(mensaje);
        }
    }//GEN-LAST:event_btnIngreso1btnIngresoActionPerformed

    private void trRegistrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_trRegistrosMouseClicked
        try {
            DefaultMutableTreeNode nodoSelecionado = (DefaultMutableTreeNode) trRegistros.getSelectionPath().getLastPathComponent();
            String V[] = nodoSelecionado.getUserObject().toString().split(" ");
            if (esTabla(txtUser.getText(), V[0])) {
                txtIdInst.setText("");
                txtNomFun.setText("");
                chbUltNivel.setSelected(false);
            } else {
                ArrayList<Registros> departamentos = ObtenerUnRegistroTabla(trRegistros.getModel().getRoot() + "", V[0]);
                for (Registros departamento : departamentos) {
                    txtIdInst.setText(departamento.getRegidtro1());
                    txtNomFun.setText(departamento.getRegidtro3());
                    if (departamento.getRegidtro5().equals("S")) {
                        chbUltNivel.setSelected(true);
                    } else {
                        chbUltNivel.setSelected(false);
                    }
                }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_trRegistrosMouseClicked

    private void tblCAT_SUELDOSKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblCAT_SUELDOSKeyReleased
        // TODO add your handling code here:
        if ((evt.getKeyCode() == KeyEvent.VK_DOWN || evt.getKeyCode() == KeyEvent.VK_UP)) {
            tblCAT_SUELDOS.getSelectedRow();
        }
    }//GEN-LAST:event_tblCAT_SUELDOSKeyReleased

    private void tblCAT_CARGOSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCAT_CARGOSMouseClicked
        int fila = tblCAT_CARGOS.getSelectedRow();
        String claveP = tblCAT_CARGOS.getModel().getValueAt(fila, 0).toString();
        verTabla(tblFUNCIONES, "FUNCIONES", 1, buscar("FUNCIONES", "IDCARGO", claveP));
    }//GEN-LAST:event_tblCAT_CARGOSMouseClicked

    private void tblCAT_CARGOSKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblCAT_CARGOSKeyReleased
        // TODO add your handling code here:
        if ((evt.getKeyCode() == KeyEvent.VK_DOWN || evt.getKeyCode() == KeyEvent.VK_UP)) {
            int fila = tblCAT_CARGOS.getSelectedRow();
            String claveP = tblCAT_CARGOS.getModel().getValueAt(fila, 0).toString();
            verTabla(tblFUNCIONES, "FUNCIONES", 1, buscar("FUNCIONES", "IDCARGO", claveP));
        }
    }//GEN-LAST:event_tblCAT_CARGOSKeyReleased

    private void tbpAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbpAdminMouseClicked
        if (tbpAdmin.getSelectedIndex() == 0) {
            DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("EST_INSTITUCIONAL");
            trRegistros.setModel(modeloArbol("EST_INSTITUCIONAL", raiz, 1, 0, "null"));
            trRegistros.setSelectionRow(0);
        }
        //        if (tbpAdmin.getSelectedIndex() == 1) {
        //            verTabla(tblPARAMETROS, "PARAMETROS", 0, obtenerDatos("PARAMETROS", "PARAMETROS"));
        //            Operaciones op = new Operaciones();
        ////            ArrayList<PARAMETROS> list = op.ListParametros(txtUsuario.getText(), txtContraseña.getText(), txthost.getText(), "1521", "localhost");
        //
        //        }
        if (tbpAdmin.getSelectedIndex() == 1) {
            verTabla(tblCAT_EMPLEADOS, "CAT_EMPLEADOS", 0, obtenerDatos("CAT_EMPLEADOS", "CAT_EMPLEADOS"));
        }
        if (tbpAdmin.getSelectedIndex() == 2) {
            verTabla(tblCAT_SUELDOS, "CAT_SUELDOS", 0, obtenerDatos("CAT_SUELDOS", "CAT_SUELDOS"));
        }
        if (tbpAdmin.getSelectedIndex() == 3) {
            verTabla(tblVALORES_CONTRATO, "VALORES_CONTRATO", -1, obtenerDatos("VALORES_CONTRATO", "VALORES_CONTRATO"));
        }
        if (tbpAdmin.getSelectedIndex() == 4) {
            verTabla(tblCAT_CARGOS, "CAT_CARGOS", 0, obtenerDatos("CAT_CARGOS", "CAT_CARGOS"));
            verTabla(tblFUNCIONES, "FUNCIONES", 1, obtenerDatos("FUNCIONES", "FUNCIONES"));
        }
        if (tbpAdmin.getSelectedIndex() == 5) {
            verTabla(tblCAT_CONTRATO, "CAT_CONTRATO", 0, obtenerDatos("CAT_CONTRATO", "CAT_CONTRATO"));
        }
    }//GEN-LAST:event_tbpAdminMouseClicked
//    public void LimpiarTablaP() {
//        int filas = tblPARAMETROS.getRowCount();
//        for (int i = 0; i < filas; i++) {
//            tblPARAMETROS.remove(0);
//        }
//    }

    private void anterior(JTable jtabla) {
        int numfila = jtabla.getSelectedRow();
        if (numfila != 0) {
            jtabla.getSelectionModel().setSelectionInterval(numfila - 1, numfila - 1);
        }
    }

    // Metodo para moverse al anterior fila de la tabla
    private void siguiente(JTable jtabla) {
        int numfila = jtabla.getSelectedRow();
        if (numfila != jtabla.getRowCount() - 1) {
            jtabla.getSelectionModel().setSelectionInterval(numfila + 1, numfila + 1);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Aplicacion2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Aplicacion2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Aplicacion2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Aplicacion2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Aplicacion2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnIngreso;
    private javax.swing.JButton btnIngreso1;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnRollback;
    private javax.swing.JCheckBox chbUltNivel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lbMensaje;
    public static javax.swing.JPanel pAdmin;
    private javax.swing.JPanel pCAT_CARGOS_Y_FUNCIONES;
    private javax.swing.JPanel pCAT_CONTRATO;
    private javax.swing.JPanel pCAT_EMPLEADOS;
    private javax.swing.JPanel pCAT_SUELDOS;
    private javax.swing.JPanel pEST_INSTITUCIONAL;
    public static javax.swing.JPanel pHerramientas;
    private javax.swing.JPanel pIngreso;
    private javax.swing.JPanel pIngreso1;
    private javax.swing.JPanel pMensaje;
    private javax.swing.JPanel pVALORES_CONTRATO;
    public static javax.swing.JPasswordField pwdClave;
    public static javax.swing.JPasswordField pwdClave1;
    private javax.swing.JTable tblCAT_CARGOS;
    private javax.swing.JTable tblCAT_CONTRATO;
    private javax.swing.JTable tblCAT_EMPLEADOS;
    private javax.swing.JTable tblCAT_SUELDOS;
    private javax.swing.JTable tblFUNCIONES;
    private javax.swing.JTable tblVALORES_CONTRATO;
    private javax.swing.JTabbedPane tbpAdmin;
    public static javax.swing.JTree trRegistros;
    private javax.swing.JTextField txtIdInst;
    private javax.swing.JTextField txtNomFun;
    public static javax.swing.JTextField txtUser;
    public static javax.swing.JTextField txtUser1;
    public static javax.swing.JTextField txtUsuario;
    public static javax.swing.JTextField txthost;
    public static javax.swing.JTextField txthost1;
    public static javax.swing.JTextField txthost2;
    // End of variables declaration//GEN-END:variables
}
