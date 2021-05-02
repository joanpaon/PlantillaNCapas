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
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import org.japo.java.exceptions.AccessException;
import org.japo.java.exceptions.ConnectivityException;
import org.japo.java.libraries.UtilesApp;
import org.japo.java.layers.services.S3Data;
import org.japo.java.layers.services.S2Bussiness;
import org.japo.java.layers.services.S1User;

/**
 *
 * @author José A. Pacheco Ondoño
 */
public final class Main {

    //<editor-fold defaultstate="collapsed" desc="--- Main Body ---">
    // Clave de Acceso
    private static final String PRP_APP_PASSWORD = "app.password";

    // Estructura de Capas - Propiedades
    private static final String PRP_LAYER_MANAGER_USER = "layer.manager.user";
    private static final String PRP_LAYER_MANAGER_BNES = "layer.manager.bnes";
    private static final String PRP_LAYER_MANAGER_DATA = "layer.manager.data";

    // Estructura de Capas - Valores por defecto
    private static final String DEF_LAYER_MANAGER_USER = "org.japo.java.layers.managers.M1User";
    private static final String DEF_LAYER_MANAGER_BNES = "org.japo.java.layers.managers.M2Bussiness";
    private static final String DEF_LAYER_MANAGER_DATA = "org.japo.java.layers.managers.M3Data";

    // Constructor Oculto
    private Main() {
    }

    // Punto de Entrada al Programa
    public static final void main(String[] args) {
        try {
            // Propiedades de la Aplicación
            Properties prpInt = UtilesApp.importarPropiedadesRecurso();
            Properties prpExt = UtilesApp.importarPropiedadesFichero();
            Properties prp = new Properties();
            prp.putAll(prpInt);
            prp.putAll(prpExt);

            // Nombres de Clases
            String dName = prp.getProperty(PRP_LAYER_MANAGER_DATA, DEF_LAYER_MANAGER_DATA);
            String bName = prp.getProperty(PRP_LAYER_MANAGER_BNES, DEF_LAYER_MANAGER_BNES);
            String uName = prp.getProperty(PRP_LAYER_MANAGER_USER, DEF_LAYER_MANAGER_USER);

            // Clases
            Class<?> dClass = Class.forName(dName);
            Class<?> bClass = Class.forName(bName);
            Class<?> uClass = Class.forName(uName);

            // Constructores
            Constructor dCons = dClass.getConstructor(Properties.class);
            Constructor bCons = bClass.getConstructor(Properties.class, S3Data.class);
            Constructor uCons = uClass.getConstructor(Properties.class, S2Bussiness.class);

            // Instanciación de Capas
            S3Data ds = (S3Data) dCons.newInstance(prp);
            S2Bussiness bs = (S2Bussiness) bCons.newInstance(prp, ds);
            S1User us = (S1User) uCons.newInstance(prp, bs);

            // Properties > Password
            String pass = prp.getProperty(PRP_APP_PASSWORD);

            // Validar Contraseña
            if (args.length > 0 && args[0] != null && args[0].equals(pass)) {
                // Acceso a Datos
                us.loginApp();

                // Ejecuta la Aplicación
                us.launchApp();

                // Cierra La Aplicación
                us.closeApp();
            } else {
                throw new AccessException("Credenciales de Aplicación NO válidas");
            }
        } catch (AccessException | ConnectivityException | NullPointerException
                | ClassNotFoundException | IllegalAccessException
                | IllegalArgumentException | InstantiationException
                | NoSuchMethodException | SecurityException
                | InvocationTargetException e) {
            System.out.printf("ERROR: %s%n", e.getMessage());
            System.out.println("---");
            System.out.println("Contacte con el Servicio Técnico");
        }
    }
//</editor-fold>
}
