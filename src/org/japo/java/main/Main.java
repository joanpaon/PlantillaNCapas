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
package org.japo.java.main;

import java.lang.reflect.Constructor;
import java.util.Properties;
import org.japo.java.exceptions.AccessException;
import org.japo.java.exceptions.ConnectivityException;
import org.japo.java.layers.managers.M1User;
import org.japo.java.layers.managers.M2Bnes;
import org.japo.java.layers.managers.M3Data;
import org.japo.java.libraries.UtilesApp;
import org.japo.java.layers.services.S3Data;
import org.japo.java.layers.services.S1User;
import org.japo.java.layers.services.S2Bnes;

/**
 *
 * @author José A. Pacheco Ondoño
 */
public final class Main {

    //<editor-fold defaultstate="collapsed" desc="--- Main Body ---">
    // Clave de Acceso
    private static final String PRP_APP_PASSWORD = "app.password";

    // Estructura de Capas - Propiedades
    private static final String PRP_LAYER_MANAGER_USER = "layer.manager.user.class";
    private static final String PRP_LAYER_MANAGER_BNES = "layer.manager.bnes.class";
    private static final String PRP_LAYER_MANAGER_DATA = "layer.manager.data.class";

    // Constructor Oculto
    private Main() {
    }

    // Punto de Entrada al Programa
    public static final void main(String[] args) {
        // Propiedades de la Aplicación
        Properties prp = new Properties();
        Properties prpInt = UtilesApp.importarPropiedadesRecurso();
        Properties prpExt = UtilesApp.importarPropiedadesFichero();
        prp.putAll(prpInt);
        prp.putAll(prpExt);

        // Capa de Datos
        S3Data ds;
        try {
            // Properties > Nombre Cualificado
            String dName = prp.getProperty(PRP_LAYER_MANAGER_DATA);

            // Nombre Cualificado > Clase
            Class<?> dClass = Class.forName(dName);

            // Clase > Constructor
            Constructor dCons = dClass.getConstructor(Properties.class);

            // Constructor > Objeto
            ds = (S3Data) dCons.newInstance(prp);
        } catch (Exception e) {
            // Predeterminado
            ds = new M3Data(prp);
        }

        // Capa de Negocio
        S2Bnes bs;
        try {
            // Properties > Nombre Cualificado
            String bName = prp.getProperty(PRP_LAYER_MANAGER_BNES);

            // Nombre Cualificado > Clase
            Class<?> bClass = Class.forName(bName);

            // Clase > Constructor
            Constructor bCons = bClass.getConstructor(Properties.class, S3Data.class);

            // Constructor > Objeto
            bs = (S2Bnes) bCons.newInstance(prp, ds);
        } catch (Exception e) {
            // Predeterminado
            bs = new M2Bnes(prp, ds);
        }

        // Capa de Usuario
        S1User us;
        try {
            // Properties > Nombre Cualificado
            String uName = prp.getProperty(PRP_LAYER_MANAGER_USER);

            // Nombre Cualificado > Clase
            Class<?> uClass = Class.forName(uName);

            // Clase > Constructor
            Constructor uCons = uClass.getConstructor(Properties.class, S2Bnes.class);

            // Constructor > Objeto
            us = (S1User) uCons.newInstance(prp, bs);
        } catch (Exception e) {
            // Predeterminado
            us = new M1User(prp, bs);
        }

        // Properties > Contraseña Aplicación
        String appPass = prp.getProperty(PRP_APP_PASSWORD);

        // Validar Acceso Aplicación
        try {
            if (args.length > 0 && args[0] != null && args[0].equals(appPass)) {
                // Acceso a Datos
                us.loginApp();

                // Ejecuta la Aplicación
                us.launchApp();

                // Cierra La Aplicación
                us.closeApp();
            } else {
                throw new AccessException("Acceso Denegado a la Aplicación");
            }
        } catch (ConnectivityException | AccessException e) {
            System.out.printf("Bitácora: %s%n", e.getMessage());
            System.out.println("---");
            System.out.println("Contacte con el Servicio Técnico");
        }
    }
    //</editor-fold>
}
