/* 
 * Copyright 2021 José A. Pacheco Ondoño - japolabs@gmail.com.
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

import java.util.Properties;
import org.japo.java.entities.Credencial;
import org.japo.java.exceptions.ConnectivityException;
import org.japo.java.layers.services.S2Bussiness;
import org.japo.java.layers.services.S1User;
import org.japo.java.libraries.UtilesEntrada;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public final class M1User implements S1User {

    //<editor-fold defaultstate="collapsed" desc="--- User Interface Manager ---">
    // Propiedades Credencial
    public static final String PRP_CONN_MODE = "jdbc.conn.mode";
    public static final String PRP_CONN_USER = "jdbc.conn.user";
    public static final String PRP_CONN_PASS = "jdbc.conn.pass";

    // Propiedades Aplicación
    private final Properties prp;

    // Gestor Lógica de Negocio
    private final S2Bussiness bs;

    // Constructor Parametrizado
    public M1User(Properties prp, S2Bussiness bs) {
        // Propiedades Aplicación
        this.prp = prp;

        // Gestor Lógica de Negocio
        this.bs = bs;
    }

    // Validación de Usuario
    @Override
    public final void loginApp() throws ConnectivityException {
        // Usuario > Credenciales
        String user = prp.getProperty(PRP_CONN_USER);
        String pass = prp.getProperty(PRP_CONN_PASS);

        // Modo de Conexión
        String mode = prp.getProperty(PRP_CONN_MODE);

        // Evaluación del modo de conexión
        if (mode != null && mode.equals("login")) {
            // Cabecera
            System.out.println("Acceso a la Aplicación");
            System.out.println("======================");

            // Entrada de Campos
            user = UtilesEntrada.leerTexto("Usuario ..............: ");
            pass = UtilesEntrada.leerTexto("Contraseña ...........: ");

            // Separador
            System.out.println("---");
        }

        // Creación de Entidad Credencial
        Credencial c = new Credencial(user, pass);

        // Conexión de Credenciales
        bs.loginApp(c);

        // Mensaje - Bitácora ( Comentar en producción )
        System.out.println("Patrón de Diseño Estructural de Capas Funcionales");
        System.out.println("=================================================");
        System.out.println("Acceso Establecido");
        System.out.println("---");
    }

    // Cierre de la Aplicación
    @Override
    public final void closeApp() throws ConnectivityException {
        // Cierre Base de Datos
        bs.closeApp();

        // Mensaje - Bitácora ( Comentar en producción )
        System.out.println("Acceso Finalizado");

        // Despedida
        System.out.println("---");
        System.out.println("Copyright JAPO Labs - Servicios Informáticos");
    }
    //</editor-fold>

    // Ejecución de la Aplicación
    @Override
    public final void launchApp() {
        // ---
    }

    // Lógica de Usuario Adicional
}
