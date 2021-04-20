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

import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public final class UtilesEntrada {

    // Scanner + Codificación Windows
    public static final Scanner SCN
            = new Scanner(System.in, "Windows-1252")
                    .useLocale(Locale.ENGLISH).useDelimiter("\\s+");

    // Mensaje de Error por Defecto
    public static final String MSG_ERR = "ERROR: Entrada incorrecta";

    // Consola >> Entero
    public static final int leerEntero(String msgUsr, String msgErr) {
        // Dato a introducir
        int dato = 0;

        // Proceso de lectura
        boolean lecturaOK = false;
        do {
            try {
                // Entrada dato
                System.out.print(msgUsr);
                dato = SCN.nextInt();

                // Marca el semáforo
                lecturaOK = true;
            } catch (Exception e) {
                System.out.println(msgErr);
            } finally {
                SCN.nextLine();
            }
        } while (!lecturaOK);

        // Devolver dato
        return dato;
    }

    // Consola >> Entero
    public static final int leerEntero(String msgUsr) {
        return leerEntero(msgUsr, MSG_ERR);
    }

    // Consola >> Entero [min .. max]
    public static final int leerEntero(String msgUsr, String msgErr, int min, int max) {
        // Número a Devolver
        int dato;

        // Semáforo Validación
        boolean rangoOK;

        // Bucle Validación
        do {
            // Introducir Entero
            dato = leerEntero(msgUsr, msgErr);

            // Validar Rango
            rangoOK = dato >= min && dato <= max;

            // Rango incorrecto >> Mensaje de error
            if (!rangoOK) {
                System.out.println(msgErr);
            }
        } while (!rangoOK);

        // Devolver número
        return dato;
    }

    // Consola >> Entero [min .. max]
    public static final int leerEntero(String msgUsr, int min, int max) {
        return leerEntero(msgUsr, MSG_ERR, min, max);
    }

    // Consola >> Real
    public static final double leerReal(String msgUsr, String msgErr) {
        // Dato a introducir
        double dato = 0;

        // Proceso de lectura
        boolean lecturaOK = false;
        do {
            try {
                // Entrada dato
                System.out.print(msgUsr);
                dato = SCN.nextDouble();

                // Marca el semáforo
                lecturaOK = true;
            } catch (Exception e) {
                System.out.println(msgErr);
            } finally {
                SCN.nextLine();
            }
        } while (!lecturaOK);

        // Devolver dato
        return dato;
    }

    // Consola >> Real
    public static final double leerReal(String msgUsr) {
        return leerReal(msgUsr, MSG_ERR);
    }

    // Consola >> Real [min .. max]
    public static final double leerReal(String msgUsr, String msgErr, double min, double max) {
        // Número a Devolver
        double dato;

        // Semáforo Validación
        boolean rangoOK;

        // Bucle Validación
        do {
            // Introducir Entero
            dato = leerReal(msgUsr, msgErr);

            // Validar Rango Entero
            rangoOK = dato >= min && dato <= max;

            // Rango Erróneo >> Mensaje de error
            if (!rangoOK) {
                System.out.println(msgErr);
            }
        } while (!rangoOK);

        // Devolver número
        return dato;
    }

    // Consola >> Real [min .. max]
    public static final double leerReal(String msgUsr, double min, double max) {
        return leerReal(msgUsr, MSG_ERR, min, max);
    }

    // Consola >> Carácter
    public static final char leerCaracter(String msgUsr, String msgErr) {
        // Dato a introducir
        char dato = 0;

        // Proceso de lectura
        boolean lecturaOK = false;
        do {
            try {
                // Entrada dato
                System.out.print(msgUsr);
                dato = SCN.nextLine().charAt(0);

                // Marca el semáforo
                lecturaOK = true;
            } catch (Exception e) {
                System.out.println(msgErr);
            }
        } while (!lecturaOK);

        // Devolver dato
        return dato;
    }

    // Consola >> Carácter
    public static final char leerCaracter(String msgUsr) {
        return leerCaracter(msgUsr, MSG_ERR);
    }

    // Opciones + Consola > Opcion
    public static final char leerCaracter(String msgUsr, String msgErr, String opciones) {
        char opcion;
        boolean opcionOK = false;
        do {
            opcion = leerCaracter(msgUsr, "");
            if (opciones.contains(opcion + "")) {
                opcionOK = true;
            } else {
                System.out.println("---");
                System.out.println(msgErr);
                System.out.println("---");
            }
        } while (!opcionOK);
        return opcion;
    }

    // Consola >> Carácter [min .. max]
    public static final char leerCaracter(String msgUsr, String msgErr, char min, char max) {
        // Dato a introducir
        char dato;

        // Semáforo Validación
        boolean rangoOK;

        // Bucle Validación
        do {
            // Introducir Caracter
            dato = leerCaracter(msgUsr, msgErr);

            // Validar Rango Caracter
            rangoOK = dato >= min && dato <= max;

            // Rango Caracter Erróneo >> Mensaje de error
            if (!rangoOK) {
                System.out.println(msgErr);
            }
        } while (!rangoOK);

        // Devolver carácter
        return dato;
    }

    // Consola >> Carácter [min .. max]
    public static final char leerCaracter(String msgUsr, char min, char max) {
        return leerCaracter(msgUsr, MSG_ERR, min, max);
    }

    // Consola >> Texto
    public static final String leerTexto(String msgUsr) {
        System.out.print(msgUsr);
        return SCN.nextLine();
    }

    // Pausa + MSG >> INTRO
    public static final void hacerPausa(String msgUsr) {
        System.out.println("---");
        System.out.println(msgUsr);
        hacerPausa();
    }

    // Pausa >> INTRO
    public static final void hacerPausa() {
        System.out.println("---");
        System.out.print("Pulse INTRO para continuar ...");
        SCN.nextLine();
        System.out.println("---");
    }

    // Confirmación S/N + Defecto > Boolean
    public static final boolean confirmarProceso(String msgUsr, boolean defectoOK) {
        // Semáforo
        boolean confirmacionOK = defectoOK;

        // Consola > Caracter
        String entrada = leerTexto(msgUsr);

        // Analisis Entrada
        if (entrada.length() > 0) {
            // Entrada > Caracter 1
            char c = entrada.charAt(0);

            // Caracter [Ss | Nn] > Boolean
            confirmacionOK = "Ss".contains(c + "");
        }

        // Devolución Confirmación
        return confirmacionOK;
    }
}
