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
package org.japo.java.libraries;

import java.io.FileReader;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public final class UtilesApp {

    // Valores por Defecto
    public static final String DEF_FICHERO_PRP = "app.properties";
    public static final String DEF_RECURSO_PRP = "config/app.properties";

    // Fichero (Por defecto) > Propiedades
    public static final Properties importarPropiedadesFichero() {
        return importarPropiedadesFichero(DEF_FICHERO_PRP);
    }

    // Fichero Propiedades > Objeto Propiedades
    public static final Properties importarPropiedadesFichero(String fichero) {
        // Objeto de Propiedades Vacio
        Properties prp = new Properties();

        // Cargar Fichero de Propiedades
        try (FileReader fr = new FileReader(fichero)) {
            prp.load(fr);
        } catch (Exception e) {
            System.out.println("ERROR: Acceso al fichero " + fichero);
        }

        // Devolver Propiedades
        return prp;
    }

    // Recurso Propiedades > Objeto Propiedades
    public static final Properties importarPropiedadesRecurso(String recurso) {
        // Objeto de Propiedades Vacio
        Properties prp = new Properties();

        // Cargar Fichero de Propiedades
        try (InputStream is = ClassLoader.getSystemResourceAsStream(recurso)) {
            prp.load(is);
        } catch (Exception e) {
            System.out.println("ERROR: Acceso al recurso de propiedades " + recurso);
        }

        // Devolver Propiedades
        return prp;
    }

    // Recurso Propiedades ( Predefinido ) > Objeto Propiedades
    public static final Properties importarPropiedadesRecurso() {
        return importarPropiedadesRecurso(DEF_RECURSO_PRP);
    }
}
