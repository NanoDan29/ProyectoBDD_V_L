package ventanas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

public class Metodos {

    // Variable mensaje que guardará información de errores de la BD
    String mensaje = "";

    public String[] nombresBD() {
        String cadena;
        String cadena2 = "";
        String cadena3[] = null;
        try {
            FileReader f = new FileReader("C:/oracle/product/10.2.0/orcl/network/ADMIN/tnsnames.ora");
            BufferedReader b = new BufferedReader(f);
            while ((cadena = b.readLine()) != null) {
                if (!cadena.equals("")) {
                    if (!cadena.substring(0, 1).equals("#")) {
                        if (!cadena.substring(0, 1).equals(" ")) {
                            cadena2 += cadena.substring(0, cadena.length() - 2) + "\n";
                        }
                    }
                }
            }
            cadena3 = cadena2.split("\n");
            b.close();
        } catch (Exception e) {
        }
        return cadena3;
    }

    // Metodo de prueba de conexion a la BD
    public boolean con(String usuario, String clave, String base) {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection connection;
            //   connection.setAutoCommit(false);
            if (base.equals("ORCL1")) {
                connection = DriverManager.getConnection("jdbc:oracle:thin:@10.24.8.85:1521:orcl", usuario, clave);
            } else {
                connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:" + base, usuario, clave);
            }
            Statement statement = connection.createStatement();
            mensaje = "Conexión exitosa.";
            return true;
        } catch (SQLException e) {
            mensaje = e.getMessage();
            return false;
        }
    }

    // Método para conectarce a la BD
    public Statement conexion(String usuario, String clave, String base) {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection connection;
            if (base.equals("ORCL1")) {
                connection = DriverManager.getConnection("jdbc:oracle:thin:@10.24.8.85:1521:orcl", usuario, clave);
            } else {
                connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:" + base, usuario, clave);
            }
            Statement statement = connection.createStatement();
            mensaje = "Conexión exitosa.";
            return statement;
        } catch (SQLException e) {
            mensaje = e.getMessage();
            return null;
        }
    }

    // Método para obtener un arrayList de todas la tablas
    public ArrayList<String> getTablas(String usuario, String clave, String base) {
        ArrayList<String> listaTablas = new ArrayList<String>();
        try {
            Statement st = this.conexion(usuario, clave, base);
            ResultSet rs = st.executeQuery("select TABLE_NAME from all_tables where owner like '" + usuario.substring(0, 2) + "%'");
            System.out.println(" asdasd " + usuario.substring(0, 2));
            String tabla = null;
            while (rs.next()) {
                tabla = new String(rs.getString(1));
                listaTablas.add(tabla);
            }
            rs.close();
            st.close();
            mensaje = "Select exitoso.";
            return listaTablas;
        } catch (SQLException e) {
            mensaje = e.getMessage();
            return null;
        }
    }

    // Método para obtener un registros de una tabla recursiva
    public ArrayList<Registros> ObtenerUnRegistroTabla(String usuario, String clave, String base, String nomTabla, String idDepto) {
        ArrayList<Registros> listaDepartamentos = new ArrayList<Registros>();
        try {
            Object nomcol[] = obtenerNomColumnas(usuario, clave, base, nomTabla);
            int cont = nomcol.length;
            Statement st = this.conexion(usuario, clave, base);
            ResultSet rs = st.executeQuery("select * from " + nomTabla + " where " + nomcol[0] + " = '" + idDepto + "'");
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
            st.close();
            mensaje = "select exitoso.";
            return listaDepartamentos;
        } catch (SQLException ex) {
            mensaje = ex.getMessage();
            return null;
        }
    }

    public ArrayList<Registros> ObtenerRegistrosTabla(String usuario, String clave, String base, String nomTabla) {
        ArrayList<Registros> listaDepartamentos = new ArrayList<Registros>();
        try {
            Statement st = this.conexion(usuario, clave, base);
            ResultSet rs = st.executeQuery("select column_name from all_tab_columns where table_name = '" + nomTabla + "'");

            int cont = 0;
            while (rs.next()) {
                cont++;
            }
            Object nomCol[] = obtenerNomColumnas(usuario, clave, base, nomTabla);
            if (nomTabla.equals("PRUEBA_DEPORTIST") || nomTabla.equals("DEPORTIST")) {

                rs = st.executeQuery("select * from " + nomTabla.substring(4, nomTabla.length()) + " ORDER BY " + nomCol[0]);
            } else {
                //IF()
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
            st.close();
            mensaje = "select exitoso.";
            return listaDepartamentos;
        } catch (SQLException ex) {
            mensaje = ex.getMessage();
            return null;
        }
    }

    // Método para obtener todos los registros de una tabla recursiva
    public ArrayList<Registros> obtenerTablaRecursiva(String usuario, String clave, String base, String nomTable) {
        ArrayList<Registros> listaDepartamentos = new ArrayList<Registros>();
        try {
            String ID = "";
            String IDP = "";
            Statement st = conexion(usuario, clave, base);
            //SELECT QUE ME MUESTRA LOS DATOS QUE TIENE LA TABLAS 
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
            st.close();
            mensaje = "Select exitoso.";
            return listaDepartamentos;
        } catch (SQLException ex) {
            mensaje = ex.getMessage();
            return null;
        }
    }

    // Método para borrar un registro
    public void borrar(String usuario, String clave, String base, String idDepto, String nomTabla) {
        try {
            Statement st = this.conexion(usuario, clave, base);
            Object nomColumnas[] = obtenerNomColumnas(usuario, clave, base, nomTabla);
            st.executeQuery("delete from " + nomTabla + " where " + nomColumnas[0] + " = '" + idDepto + "'");
            st.close();
            mensaje = "Borrado exitoso.";
        } catch (SQLException ex) {
            mensaje = ex.getMessage();
        }
    }

    // Método para guardar un registro
    public void guardar(String usuario, String clave, String base, String nomTabla, String registros[]) {
        try {
            Statement st = this.conexion(usuario, clave, base);
            //this.conexion(usuario, clave, base)

            Object nomColumnas[] = obtenerNomColumnas(usuario, clave, base, nomTabla);

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
                if (tipoDato[x].equals("VARCHAR2") || tipoDato[x].equals("DATE")) {
                    if (x == registros.length - 1) {
                        nomParam += "'" + registro + "'";
                    } else {
                        nomParam += "'" + registro + "',";
                    }
                } else if (x == registros.length - 1) {
                    nomParam += registro;
                } else {
                    nomParam += registro + ",";
                }
                x++;
            }
            st.executeQuery("insert into " + nomTabla + " (" + nomCol + ") values (" + nomParam + ")");
            rs.close();
            st.close();
            mensaje = "Ingreso exitoso.";
        } catch (Exception ex) {
            mensaje = ex.getMessage();
        }
    }

    // Metodo para acualizar un registro
    public void actualizar(String usuario, String clave, String base, String nomTabla, String registros[]) {
        try {
            Statement st = this.conexion(usuario, clave, base);
            String tipoDato[] = new String[14];
            int i = 0;
            ResultSet rs = st.executeQuery("select data_type from all_tab_columns where table_name = '" + nomTabla + "'");
            while (rs.next()) {
                tipoDato[i] = rs.getString(1);
                i++;
            }
            Object nomColumnas[] = obtenerNomColumnas(usuario, clave, base, nomTabla);
            String datosAct = "";
            int x = 0;
            for (String registro : registros) {
                if (x > 0) {
                    if (tipoDato[x].equals("VARCHAR2") || tipoDato[x].equals("DATE")) {
                        if (x == registros.length - 1) {
                            datosAct += nomColumnas[x] + " = '" + registro + "'";
                        } else {
                            datosAct += nomColumnas[x] + " = '" + registro + "',";
                        }
                    } else if (x == registros.length - 1) {
                        datosAct += nomColumnas[x] + " = " + registro;
                    } else {
                        datosAct += nomColumnas[x] + " = " + registro + ",";
                    }
                }
                x++;
            }

            st.executeQuery("update " + nomTabla + " set " + datosAct + " where " + nomColumnas[0] + " = '" + registros[0] + "'");
            //System.out.println("update " + nomTabla + " set " + datosAct + " where " + nomColumnas[0] + " = '" + registros[0] + "'");
            rs.close();
            st.close();
            mensaje = "Actualización exitosa.";
        } catch (SQLException ex) {
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

    // Método para obtener el nombre de las columnas de las tablas y los devuelbe en un Vector
    public Object[] obtenerNomColumnas(String usuario, String clave, String base, String nomTabla) {
        try {
            Object nomColumnas[] = null;
            Statement st = this.conexion(usuario, clave, base);
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
            st.close();
            return nomColumnas;
        } catch (Exception e) {
            return null;
        }
    }

    // Método para obtener el nombre de las columnas de las tablas y los devuelbe en un Matriz
    public Object[][] obtenerDatos(String usuario, String clave, String base, String nomTabla) {
        try {
            Object Datos[][] = null;
            Statement st = this.conexion(usuario, clave, base);
            ResultSet rs = st.executeQuery("select column_name from all_tab_columns where table_name = '" + nomTabla + "'");
            int col = 0;
            while (rs.next()) {
                col++;
            }
            if (nomTabla.equals("DEPO") || nomTabla.equals("PRUEBA_DEP")) {
                rs = st.executeQuery("select * from " + nomTabla.substring(4, nomTabla.length()));
            } else {
                rs = st.executeQuery("select * from " + nomTabla);
            }
            int fil = 0;
            while (rs.next()) {
                fil++;
            }
            if (nomTabla.equals("DEPORT") || nomTabla.equals("PRUEBA_")) {
                rs = st.executeQuery("select * from " + nomTabla.substring(4, nomTabla.length()));
            } else {
                rs = st.executeQuery("select * from " + nomTabla);
            }
            Datos = new Object[fil][col];
            for (int i = 0; rs.next(); i++) {
                for (int j = 0; j < col; j++) {
                    Datos[i][j] = rs.getString(j + 1);
                }
            }
            rs.close();
            st.close();
            return Datos;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * <b>numeroColunmasTabla</b>
     * <p>
     * Método para obtener el número de columnas que tiene una tabla
     * </p><br>
     * <b>Retorna:</b>
     * <p>
     * - Un entero con el número de columnas</p>
     * <p>
     * - O un entero con 0 si no hay columnas</p>
     */
    public int numeroColunmasTabla(String usuario, String clave, String base, String nomTabla) {
        int col = 0;
        try {
            Statement st = this.conexion(usuario, clave, base);
            ResultSet rs = st.executeQuery("select column_name from all_tab_columns where table_name = '" + nomTabla + "'");
            while (rs.next()) {
                col++;
            }
            rs.close();
            st.close();
            return col;
        } catch (SQLException ex) {
            return col;
        }
    }

    public Object[][] buscar(String usuario, String clave, String base, String nomTabla, String nomColumna, String cadenaBusqueda) {
        Object busqueda[][] = null;
        try {
            Statement st = conexion(usuario, clave, base);
            ResultSet rs = null;
            Object columnas[] = obtenerNomColumnas(usuario, clave, base, nomTabla);
            int col = columnas.length;
            if (nomTabla.equals("DEPORTIS") || nomTabla.equals("PRUEBA_DEPORTIS")) {
                System.out.println("select * from " + nomTabla.substring(4, nomTabla.length()) + " where " + nomColumna + " like '" + cadenaBusqueda + "'");
                rs = st.executeQuery("select * from " + nomTabla.substring(4, nomTabla.length()) + " where " + nomColumna + " like '" + cadenaBusqueda + "'");
            } else {
                rs = st.executeQuery("select * from " + nomTabla + " where " + nomColumna + " like '" + cadenaBusqueda + "'");
            }
            int fil = 0;
            while (rs.next()) {
                fil++;
            }
            if (nomTabla.equals("DEPORTIS") || nomTabla.equals("PRUEBA_DEPORTIS")) {
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
            st.close();
            mensaje = "Busqueda exitosa.";
            return busqueda;
        } catch (SQLException e) {
            mensaje = e.getMessage();
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
    public boolean existenCheckColumna(String usuario, String clave, String base, String nomTabla, String nomColumna) {
        boolean existe = false;
        try {
            Statement st = this.conexion(usuario, clave, base);
            ResultSet rs = st.executeQuery("select * from all_constraints where table_name='" + nomTabla + "' and constraint_name like 'CKC_" + nomColumna + "%'");
            int cont = 0;
            while (rs.next()) {
                cont++;
            }
            if (cont > 0) {
                existe = true;
            }
            rs.close();
            st.close();
            return existe;
        } catch (SQLException e) {
            return existe;
        }
    }

    /**
     * <b>checksColumna</b>
     * <p>
     * Método para obtener todas las condiciones del check en el nomColumna
     * especificado
     * </p><br>
     * <b>Retorna:</b>
     * <p>
     * - Un vector de Strings con las condiciones</p>
     */
    public String[] checksColumna(String usuario, String clave, String base, String nomTabla, String nomColumna) {
        String nomChecks[] = null;
        try {
            Statement st = this.conexion(usuario, clave, base);
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
            st.close();
            return nomChecks;
        } catch (SQLException e) {
            System.out.println("Error: " + e);
            return null;
        }
    }

    public ArrayList<MostrarInstitucion> getMostrarEstructura(String usuario, String clave, String hostString, String idInst) {
        ArrayList<MostrarInstitucion> listaDepartamentos = new ArrayList<MostrarInstitucion>();
        try {
            //System.out.println(usuario+", "+clave);
            Statement st = this.conexion(usuario, clave, hostString);
            ResultSet rs = st.executeQuery("select a.NOMFUNCION,b.IDCONTRATO,c.CEDULA,c.NOMBRE,c.APELLIDOP,d.NOMBRE,e.NOMBRE,f.VALOR \n"
                    + "FROM EST_INSTITUCIONAL a,CAT_CONTRATO b,CAT_EMPLEADOS c,FUNCIONES d,CAT_CARGOS e,CAT_SUELDOS f\n"
                    + "WHERE a.IDINST=b.IDINST\n"
                    + "AND b.IDPERSONA=c.IDPERSONA\n"
                    + "AND b.IDFUNCION=d.IDFUNCION\n"
                    + "AND b.IDCARGO=e.IDCARGO\n"
                    + "AND b.IDSUELDO=f.IDSUELDO\n"
                    + "AND a.IDINST='" + idInst + "'");
            MostrarInstitucion func = null;
            while (rs.next()) {
                func = new MostrarInstitucion(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8));
                listaDepartamentos.add(func);
                //  System.out.println(" lista"+listaDepartamentos);
            }
            return listaDepartamentos;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "Error", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<MostrarCargo> getMostrarCargo(String usuario, String clave, String hostString, String idCarg) {
        ArrayList<MostrarCargo> listaDepartamentos = new ArrayList<MostrarCargo>();
        try {
            //System.out.println(usuario+", "+clave);
            Statement st = this.conexion(usuario, clave, hostString);
            ResultSet rs = st.executeQuery("select  d.CEDULA,d.NOMBRE,d.APELLIDOP,b.NOMBRE,a.NOMBRE\n"
                    + "from CAT_CARGOS a,FUNCIONES b,CAT_CONTRATO c,CAT_EMPLEADOS d\n"
                    + "where a.IDCARGO=b.IDCARGO\n"
                    + "AND b.IDFUNCION=c.IDFUNCION\n"
                    + "AND c.IDPERSONA=d.IDPERSONA\n"
                    + "AND a.IDCARGO='" + idCarg + "'");
            MostrarCargo func = null;
            while (rs.next()) {
                func = new MostrarCargo(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                listaDepartamentos.add(func);
                //  System.out.println(" lista"+listaDepartamentos);
            }
            return listaDepartamentos;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "Error", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<MostrarFuncion> getMostrarFuncion(String usuario, String clave, String hostString, String idFun) {
        ArrayList<MostrarFuncion> listaDepartamentos = new ArrayList<MostrarFuncion>();
        try {
            //System.out.println(usuario+", "+clave);
            Statement st = this.conexion(usuario, clave, hostString);
            ResultSet rs = st.executeQuery("SELECT a.NOMBRE,b.IDCONTRATO,c.CEDULA,c.NOMBRE,c.APELLIDOP\n"
                    + "FROM FUNCIONES a,CAT_CONTRATO b,CAT_EMPLEADOS c\n"
                    + "WHERE a.IDFUNCION=b.IDFUNCION\n"
                    + "AND b.IDPERSONA=c.IDPERSONA\n"
                    + "AND a.IDFUNCION='" + idFun + "'");
            MostrarFuncion func = null;
            while (rs.next()) {
                func = new MostrarFuncion(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                listaDepartamentos.add(func);
                //  System.out.println(" lista"+listaDepartamentos);
            }
            return listaDepartamentos;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "Error", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<MostrarParametros> getMostrarParametros(String usuario, String clave, String hostString, String idPar) {
        ArrayList<MostrarParametros> listaDepartamentos = new ArrayList<MostrarParametros>();
        try {
            //System.out.println(usuario+", "+clave);
            Statement st = this.conexion(usuario, clave, hostString);
            ResultSet rs = st.executeQuery("SELECT a.DESCRIPCION, d.NOMFUNCION,c.CEDULA,c.NOMBRE,c.APELLIDOP \n"
                    + "FROM PARAMETROS a,CAT_CONTRATO b,CAT_EMPLEADOS c,EST_INSTITUCIONAL d\n"
                    + "WHERE a.IDPARAMETRO=b.IDPARAMETRO\n"
                    + "AND b.IDINST=d.IDINST\n"
                    + "AND b.IDPERSONA=c.IDPERSONA\n"
                    + "AND a.IDPARAMETRO='" + idPar + "'");
            MostrarParametros func = null;
            while (rs.next()) {
                func = new MostrarParametros(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                listaDepartamentos.add(func);
                //  System.out.println(" lista"+listaDepartamentos);
            }
            return listaDepartamentos;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "Error", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<MostrarSueldo> getMostrarSueldo(String usuario, String clave, String hostString, String idPar) {
        ArrayList<MostrarSueldo> listaDepartamentos = new ArrayList<MostrarSueldo>();
        try {
            //System.out.println(usuario+", "+clave);
            Statement st = this.conexion(usuario, clave, hostString);
            ResultSet rs = st.executeQuery("SELECT a.DESCRIPCION, a.VALOR, b.IDCONTRATO,c.CEDULA,c.NOMBRE,c.APELLIDOP\n"
                    + "FROM CAT_SUELDOS a,CAT_CONTRATO b,CAT_EMPLEADOS c\n"
                    + "WHERE a.IDSUELDO=b.IDSUELDO\n"
                    + "AND b.IDPERSONA=c.IDPERSONA\n"
                    + "AND a.IDSUELDO='" + idPar + "'");
            MostrarSueldo func = null;
            while (rs.next()) {
                func = new MostrarSueldo(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                listaDepartamentos.add(func);
                //  System.out.println(" lista"+listaDepartamentos);
            }
            return listaDepartamentos;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "Error", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<MostrarEmpleados> getMostrarEmpleado(String usuario, String clave, String hostString, String idPar) {
        ArrayList<MostrarEmpleados> listaDepartamentos = new ArrayList<MostrarEmpleados>();
        try {
            //System.out.println(usuario+", "+clave);
            Statement st = this.conexion(usuario, clave, hostString);
            ResultSet rs = st.executeQuery("select a.CEDULA,a.NOMBRE,a.APELLIDOP,a.APELLIDOM,a.EMAIL,a.GENERO,b.NROCONTRATO,b.FECHAINICIO,b.FECHAFIN,d.NOMBRE,SUM(e.VALOR),f.NOMFUNCION\n"
                    + "from CAT_EMPLEADOS a,CAT_CONTRATO b,FUNCIONES c,CAT_CARGOS d,CAT_SUELDOS e,EST_INSTITUCIONAL f\n"
                    + "where a.IDPERSONA=b.IDPERSONA\n"
                    + "AND b.IDFUNCION=c.IDFUNCION\n"
                    + "AND c.IDCARGO=d.IDCARGO\n"
                    + "AND b.IDSUELDO=b.IDSUELDO\n"
                    + "AND b.IDINST=f.IDINST\n"
                    + "AND a.IDPERSONA='" + idPar + "'\n"
                    + "GROUP BY a.CEDULA,a.NOMBRE,a.APELLIDOP,a.APELLIDOM,a.EMAIL,a.GENERO,b.NROCONTRATO,b.FECHAINICIO,b.FECHAFIN,d.NOMBRE,f.NOMFUNCION ");
            MostrarEmpleados func = null;
            while (rs.next()) {
                func = new MostrarEmpleados(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11), rs.getString(12));
                listaDepartamentos.add(func);
                //  System.out.println(" lista"+listaDepartamentos);
            }
            return listaDepartamentos;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "Error", JOptionPane.ERROR);
            return null;
        }
    }

    public ArrayList<MostrarContrato> getMostrarContrato(String usuario, String clave, String hostString, String idPar) {
        ArrayList<MostrarContrato> listaDepartamentos = new ArrayList<MostrarContrato>();
        try {
            //System.out.println(usuario+", "+clave);
            Statement st = this.conexion(usuario, clave, hostString);
            ResultSet rs = st.executeQuery("select a.IDCONTRATO,b.NOMFUNCION,c.DESCRIPCION,g.VALOR,f.NOMBRE,d.NOMBRE,a.NROCONTRATO,a.FECHAINICIO,a.FECHAFIN,a.ESTADOCONTRATO,a.NROHORAS\n"
                    + "FROM CAT_CONTRATO a,EST_INSTITUCIONAL b,PARAMETROS c,CAT_EMPLEADOS d,FUNCIONES e,CAT_CARGOS f,CAT_SUELDOS g\n"
                    + "WHERE a.IDINST=b.IDINST\n"
                    + "AND a.IDFUNCION=e.IDFUNCION\n"
                    + "AND a.IDCARGO=f.IDCARGO\n"
                    + "AND a.IDSUELDO=g.IDSUELDO\n"
                    + "AND a.IDPARAMETRO=c.IDPARAMETRO\n"
                    + "AND a.IDPERSONA=d.IDPERSONA\n"
                    + "AND a.IDCONTRATO='" + idPar + "'");
            MostrarContrato func = null;
            while (rs.next()) {
                func = new MostrarContrato(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6),
                        rs.getInt(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11));
                listaDepartamentos.add(func);
                //  System.out.println(" lista"+listaDepartamentos);
            }
            return listaDepartamentos;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "Error", JOptionPane.ERROR);
            return null;
        }
    }

}
