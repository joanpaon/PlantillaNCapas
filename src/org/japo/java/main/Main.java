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
import org.japo.java.libraries.UtilesApp;
import org.japo.java.services.BServices;
import org.japo.java.services.DServices;
import org.japo.java.services.UServices;

/**
 *
 * @author José A. Pacheco Ondoño
 */
public final class Main {

    //<editor-fold defaultstate="collapsed" desc="--- Main Body ---">
    // Clave de Acceso
    private static final String PRP_APP_PASSWORD = "app.password";

    // Estructura de Capas - Propiedades
    private static final String PRP_LAYERS_DATA_CLASS = "layers.data.class";
    private static final String PRP_LAYERS_BUSSINESS_CLASS = "layers.bussiness.class";
    private static final String PRP_LAYERS_USER_CLASS = "layers.user.class";

    // Estructura de Capas - Valores por defecto
    private static final String DEF_LAYERS_DATA_CLASS = "org.japo.java.layers.data.DManager";
    private static final String DEF_LAYERS_BUSSINESS_CLASS = "org.japo.java.layers.bussiness.BManager";
    private static final String DEF_LAYERS_USER_CLASS = "org.japo.java.layers.user.UManager";

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
            String dName = prp.getProperty(PRP_LAYERS_DATA_CLASS, DEF_LAYERS_DATA_CLASS);
            String bName = prp.getProperty(PRP_LAYERS_BUSSINESS_CLASS, DEF_LAYERS_BUSSINESS_CLASS);
            String uName = prp.getProperty(PRP_LAYERS_USER_CLASS, DEF_LAYERS_USER_CLASS);

            // Clases
            Class<?> dClass = Class.forName(dName);
            Class<?> bClass = Class.forName(bName);
            Class<?> uClass = Class.forName(uName);

            // Constructores
            Constructor dCons = dClass.getConstructor(Properties.class);
            Constructor bCons = bClass.getConstructor(Properties.class, DServices.class);
            Constructor uCons = uClass.getConstructor(Properties.class, BServices.class);

            // Instanciación de Capas
            DServices ds = (DServices) dCons.newInstance(prp);
            BServices bs = (BServices) bCons.newInstance(prp, ds);
            UServices us = (UServices) uCons.newInstance(prp, bs);

            // Properties > Password
            String pass = prp.getProperty(PRP_APP_PASSWORD);

            // Validar Contraseña
            if (args[0].equals(pass)) {
                // Identificaciónd e Usuario
                if (us.loginApp()) {
                    // Ejecuta la Aplicación
                    us.launchApp();

                    // Cierra La Aplicación
                    us.closeApp();
                } else {
                    throw new AccessException();
                }
            } else {
                throw new AccessException();
            }
        } catch (AccessException e) {
            System.out.println("ERROR: Acceso Denegado");
            System.out.println("---");
            System.out.println("Contacte con el Administrador del Sistema");
        } catch (NullPointerException
                | ClassNotFoundException | IllegalAccessException
                | IllegalArgumentException | InstantiationException
                | NoSuchMethodException | SecurityException
                | InvocationTargetException e) {
            System.out.println("ERROR: Inicialización abortada");
            System.out.println("---");
            System.out.println("Contacte con el Servicio Técnico");
        }
    }
    //</editor-fold>
}
