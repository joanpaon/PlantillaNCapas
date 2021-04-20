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
package org.japo.java.layers.bussiness;

import java.util.Properties;
import org.japo.java.services.BServices;
import org.japo.java.services.DServices;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public final class BManager implements BServices {

    //<editor-fold defaultstate="collapsed" desc="--- Bussiness Logic Manager ---">
    // Propiedades de la Aplicación
    private final Properties prp;

    // Servicios de Acceso a Datos
    private final DServices ds;

    // Constructor Parametrizado
    public BManager(Properties prp, DServices ds) {
        // Propiedades Aplicación
        this.prp = prp;

        // Gestor de Acceso a Datos + Iniciar conexión BD
        this.ds = ds;
    }

    // VAlidar Usuario
    @Override
    public final boolean validarUsuario(String user, String pass) {
        return ds.validarUsuario(user, pass);
    }

    // Terminar Conexión BD
    @Override
    public final void cerrarBD() {
        ds.cerrarBD();
    }
    //</editor-fold>

    // Lógica de Negocio Adicional
}
