/* 
 * Copyright 2021 José A. Pacheco Ondoño - joanpaon@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.japo.java.libraries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public final class UtilesBD {

    // Conexión BBDD - Propiedades 
    public static final String PRP_CONN_PROT = "jdbc.conn.prot";
    public static final String PRP_CONN_HOST = "jdbc.conn.host";
    public static final String PRP_CONN_PORT = "jdbc.conn.port";
    public static final String PRP_CONN_DBNM = "jdbc.conn.name";
    public static final String PRP_CONN_USER = "jdbc.conn.user";
    public static final String PRP_CONN_PASS = "jdbc.conn.pass";
    public static final String PRP_CONN_CPAT = "jdbc.conn.cpat";

    // Conexión BBDD - Valores Predeterminados 
    private static final String DEF_CONN_PROT = "jdbc:mysql";
    private static final String DEF_CONN_HOST = "localhost";
    private static final String DEF_CONN_PORT = "3306";
    private static final String DEF_CONN_DBNM = "agenda";
    private static final String DEF_CONN_USER = "usuario";
    private static final String DEF_CONN_PASS = "usuario";
    private static final String DEF_CONN_CPAT = "%s://%s:%s/%s?user=%s&password=%s";

    // Sentencia BBDD - Tipo de Acceso
    public static final String STMT_ACCT_TFLY = "TYPE_FORWARD_ONLY";
    public static final String STMT_ACCT_TSIN = "TYPE_SCROLL_INSENSITIVE";
    public static final String STMT_ACCT_TSSE = "TYPE_SCROLL_SENSITIVE";
    private static final String DEF_STMT_TACC = STMT_ACCT_TSSE;
    public static final String PRP_STMT_TACC = "jdbc.stmt.tacc";

    // Sentencia BBDD - Concurrencia
    public static final String STMT_CONC_RNLY = "CONCUR_READ_ONLY";
    public static final String STMT_CONC_UPDT = "CONCUR_UPDATABLE";
    private static final String DEF_STMT_CONC = STMT_CONC_UPDT;
    public static final String PRP_STMT_CONC = "jdbc.stmt.conc";

    // Conexión con BD - Cadena de Conexión
    public static final Connection conectar(String cadena) {
        // Referencia Conexión
        Connection conn;

        // Obtiene Conexión
        try {
            conn = DriverManager.getConnection(cadena);
        } catch (SQLException | NullPointerException e) {
            conn = null;
        }

        // Devuelve la Conexión
        return conn;
    }

    // Conexión con BD - Properties
    public static final Connection conectar(Properties prp) {
        // Referencia Conexión
        Connection conn;

        // Obtiene Conexión
        try {
            // Cadena de Conexión
            String sConn = obtenerCadenaConexion(prp);

            // Realizar la conexión
            conn = conectar(sConn);
        } catch (NullPointerException e) {
            conn = null;
        }

        // Devuelve la Conexión
        return conn;
    }

    // Conexión con BD - Predeterminada
    public static final Connection conectar() {
        // Devolver Conexión
        return conectar(new Properties());
    }

    // Conexión con BD - Parámetros
    public static final Connection conectar(
            String prot,
            String host,
            String port,
            String dbnm,
            String user,
            String pass,
            String cpat) {
        // Definir cadena de conexión
        String sConn = obtenerCadenaConexion(cpat, prot, host, port, dbnm, user, pass);

        // Realizar + Devolver Conexión
        return UtilesBD.conectar(sConn);
    }

    // Obtener Cadena de Conexión - Propiedades
    private static String obtenerCadenaConexion(Properties prp) {
        // Parámetros Conexión
        String prot = prp != null ? prp.getProperty(PRP_CONN_PROT, DEF_CONN_PROT) : DEF_CONN_PROT;
        String host = prp != null ? prp.getProperty(PRP_CONN_HOST, DEF_CONN_HOST) : DEF_CONN_HOST;
        String port = prp != null ? prp.getProperty(PRP_CONN_PORT, DEF_CONN_PORT) : DEF_CONN_PORT;
        String dbnm = prp != null ? prp.getProperty(PRP_CONN_DBNM, DEF_CONN_DBNM) : DEF_CONN_DBNM;
        String user = prp != null ? prp.getProperty(PRP_CONN_USER, DEF_CONN_USER) : DEF_CONN_USER;
        String pass = prp != null ? prp.getProperty(PRP_CONN_PASS, DEF_CONN_PASS) : DEF_CONN_PASS;
        String cpat = prp != null ? prp.getProperty(PRP_CONN_CPAT, DEF_CONN_CPAT) : DEF_CONN_CPAT;

        // Construir + Devolver Cadena de Conexión
        return String.format(cpat, prot, host, port, dbnm, user, pass);
    }

    // Obtener Cadena de Conexión - Parámetros
    private static String obtenerCadenaConexion(
            String prot,
            String host,
            String port,
            String dbnm,
            String user,
            String pass,
            String cpat) {

        // Construir + Devolver Cadena de Conexión
        return String.format(cpat, prot, host, port, dbnm, user, pass);
    }

    // Conexión + Access + Concurrency > Statement
    public static final Statement vincular(Connection conn, int tacc, int conc) {
        // Referencia Sentencia
        Statement stmt;

        // Obtener Sentencia
        try {
            stmt = conn.createStatement(tacc, conc);
        } catch (SQLException | NullPointerException e) {
            stmt = null;
        }

        // Devuelva Sentencia
        return stmt;
    }

    // Conexión + Access + Concurrency > Statement
    public static final Statement vincular(Connection conn, Properties prp) {
        // Tipo de Acceso
        int tacc = obtenerTipoAcceso(prp);

        // Concurrencia
        int conc = obtenerConcurrencia(prp);

        // Connection + Access Type + Concurrency > Statement
        return vincular(conn, tacc, conc);
    }

    private static int obtenerTipoAcceso(Properties prp) {
        // ---- TIPOS DE ACCESO ----
        // ResultSet.TYPE_FORWARD_ONLY (*) - Indica que el objeto ResultSet se
        //      puede recorrer unicamente hacia adelante.
        // ResultSet.TYPE_SCROLL_INSENSITIVE - Indica que el objeto ResultSet se
        //      puede recorrer, pero en general no es sensible a los cambios en
        //      los datos que subyacen en él.
        // ResultSet.TYPE_SCROLL_SENSITIVE - Indica que el objeto ResultSet se
        //      puede  recorrer, y además, los cambios en él repercuten
        //      en la base de datos subyacente.
        //
        // Properties > Selector Tipo de Acceso 
        String selTacc = prp != null ? prp.getProperty(PRP_STMT_TACC, DEF_STMT_TACC) : DEF_STMT_TACC;

        // Tipo de Acceso
        int tacc;

        // Obtener valor
        switch (selTacc) {
            case STMT_ACCT_TFLY:
                tacc = ResultSet.TYPE_FORWARD_ONLY;
                break;
            case STMT_ACCT_TSIN:
                tacc = ResultSet.TYPE_SCROLL_INSENSITIVE;
                break;
            case STMT_ACCT_TSSE:
                tacc = ResultSet.TYPE_SCROLL_SENSITIVE;
                break;
            default:
                tacc = ResultSet.TYPE_FORWARD_ONLY;
        }

        // Devolver Tipo de Acceso
        return tacc;
    }

    private static int obtenerConcurrencia(Properties prp) {
        // ---- MODOS DE CONCURRENCIA ----
        // ResultSet.CONCUR_READ_ONLY (*) - Indica que en el modo de concurrencia
        //      para el objeto ResultSet éste no puede ser actualizado.
        // ResultSet.CONCUR_UPDATABLE - Indica que en el modo de concurrencia
        //      para el objeto ResultSet éste podria ser actualizado.
        //        
        // Properties > Selector Concurrencia
        String selConc = prp != null ? prp.getProperty(PRP_STMT_CONC, DEF_STMT_CONC) : DEF_STMT_CONC;

        // Concurrencia
        int conc;
        switch (selConc) {
            case STMT_CONC_RNLY:
                conc = ResultSet.CONCUR_READ_ONLY;
                break;
            case STMT_CONC_UPDT:
                conc = ResultSet.CONCUR_UPDATABLE;
                break;
            default:
                conc = ResultSet.CONCUR_READ_ONLY;
        }

        // Devolver Concurrencia
        return conc;
    }

    // Statement + SQL > ResultSet
    public static final ResultSet consultar(Statement stmt, String sql) throws SQLException {
        // Statement + Select SQL
        ResultSet rs;

        if (stmt != null) {
            rs = stmt.executeQuery(sql);
        } else {
            throw new SQLException("ERROR: Sentencia no disponible");
        }

        // Devolver ResultSet
        return rs;
    }

    // Cierre JDBC Connection
    public static final void cerrar(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException | NullPointerException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    // Cierre JDBC Statement
    public static final void cerrar(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException | NullPointerException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    // Cierre JDBC ResultSet
    public static final void cerrar(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException | NullPointerException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
