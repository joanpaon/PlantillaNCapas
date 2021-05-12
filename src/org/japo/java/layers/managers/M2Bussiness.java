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
import org.japo.java.layers.services.S3Data;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public final class M2Bussiness implements S2Bussiness {

    //<editor-fold defaultstate="collapsed" desc="--- Bussiness Logic Manager ---">
    // Propiedades de la Aplicación
    private final Properties prp;

    // Servicios de Acceso a Datos
    private final S3Data ds;

    // Constructor Parametrizado
    public M2Bussiness(Properties prp, S3Data ds) {
        // Propiedades Aplicación
        this.prp = prp;

        // Gestor de Acceso a Datos + Iniciar conexión BD
        this.ds = ds;
    }

    // Conectar BD
    @Override
    public final void abrirAccesoDatos(Credencial c)
            throws ConnectivityException {
        ds.abrirAccesoDatos(c);
    }

    // Desconectar BD
    @Override
    public final void cerrarAccesoDatos() throws ConnectivityException {
        ds.cerrarAccesoDatos();
    }
    //</editor-fold>

    // Lógica de Negocio Adicional
}
