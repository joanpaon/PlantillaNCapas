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
package org.japo.java.layers.user;

import java.util.Properties;
import org.japo.java.services.BServices;
import org.japo.java.services.UServices;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public final class UManager implements UServices {

    //<editor-fold defaultstate="collapsed" desc="--- User Interface Manager ---">
    // Propiedades Credencial
    public static final String PRP_CONN_USER = "jdbc.conn.user";
    public static final String PRP_CONN_PASS = "jdbc.conn.pass";

    // Propiedades Aplicación
    private final Properties prp;

    // Gestor Lógica de Negocio
    private final BServices bs;

    // Constructor Parametrizado
    public UManager(Properties prp, BServices bs) {
        // Propiedades Aplicación
        this.prp = prp;

        // Gestor Lógica de Negocio
        this.bs = bs;
    }

    // Validación de Usuario
    @Override
    public final boolean loginApp() {
        // Usuario > Credenciales
        String user = prp.getProperty(PRP_CONN_USER);
        String pass = prp.getProperty(PRP_CONN_PASS);

        return bs.validarUsuario(user, pass);
    }

    // Cierre de la Aplicación
    @Override
    public final void closeApp() {
        // Cierre Base de Datos
        bs.cerrarBD();

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
