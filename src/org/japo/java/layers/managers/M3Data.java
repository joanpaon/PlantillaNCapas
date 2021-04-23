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
package org.japo.java.layers.managers;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;
import org.japo.java.libraries.UtilesBD;
import org.japo.java.layers.services.S3Data;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public final class M3Data implements S3Data {

    //<editor-fold defaultstate="collapsed" desc="--- Data Access Manager ---">
    // Propiedades Credencial
    public static final String PRP_CONN_USER = "jdbc.conn.user";
    public static final String PRP_CONN_PASS = "jdbc.conn.pass";

    // Referencias
    private final Properties prp;

    // Artefactos Base de Datos
    private Connection conn = null;
    private Statement stmt = null;

    // Constructor Parametrizado - Properties
    public M3Data(Properties prp) {
        this.prp = prp;
    }

    @Override
    public boolean validarUsuario(String user, String pass) {
        // Semáforo validación
        boolean usuarioOK;

        try {
            // User + Pass > Properties
            prp.setProperty(PRP_CONN_USER, user);
            prp.setProperty(PRP_CONN_PASS, pass);

            // Conectar BD
            conn = UtilesBD.conectar(prp);

            // Validar Conexión BD
            if (conn != null) {
                // Establecer Sentencia BD
                stmt = UtilesBD.vincular(conn, prp);

                // Validar Sentencia BD
                usuarioOK = stmt != null;
            } else {
                usuarioOK = false;
            }
        } catch (NullPointerException e) {
            usuarioOK = false;
        }

        // No Validación > Eliminar Propiedades
        if (!usuarioOK) {
            //prp.remove(PRP_CONN_USER);
            //prp.remove(PRP_CONN_PASS);
        }

        // Devolver validación
        return usuarioOK;
    }

    // Cerrar Artefactos BD
    @Override
    public final void cerrarBD() {
        // Cerrando Artefactos
        UtilesBD.cerrar(stmt);
        UtilesBD.cerrar(conn);
    }
    //</editor-fold>

    // Lógica de Acceso a Datos Adicional
}
