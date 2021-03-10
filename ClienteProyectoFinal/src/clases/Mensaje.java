/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;

/**
 *
 * @author disen
 */
public class Mensaje implements Serializable{
    
    private int idUsLog;
    private int idUsAmigo;
    private String mensaje;

    public Mensaje(int idUsLog, int idUsAmigo, String mensaje) {
        this.idUsLog = idUsLog;
        this.idUsAmigo = idUsAmigo;
        this.mensaje = mensaje;
    }

    public int getIdUsLog() {
        return idUsLog;
    }

    public void setIdUsLog(int idUsLog) {
        this.idUsLog = idUsLog;
    }

    public int getIdUsAmigo() {
        return idUsAmigo;
    }

    public void setIdUsAmigo(int idUsAmigo) {
        this.idUsAmigo = idUsAmigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
}
