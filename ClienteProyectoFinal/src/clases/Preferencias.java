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
public class Preferencias implements Serializable{
    
    private int id;
    private int arte;
    private int deporte;
    private int politica;
    private boolean tHijos;
    private boolean qHijos;
    private String relacion;
    private String interes;

    public Preferencias() {
    }

    public Preferencias(int id, int arte, int deporte, int politica, boolean tHijos, boolean qHijos, String relacion, String interes) {
        this.id = id;
        this.arte = arte;
        this.deporte = deporte;
        this.politica = politica;
        this.tHijos = tHijos;
        this.qHijos = qHijos;
        this.relacion = relacion;
        this.interes = interes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArte() {
        return arte;
    }

    public void setArte(int arte) {
        this.arte = arte;
    }

    public int getDeporte() {
        return deporte;
    }

    public void setDeporte(int deporte) {
        this.deporte = deporte;
    }

    public int getPolitica() {
        return politica;
    }

    public void setPolitica(int politica) {
        this.politica = politica;
    }

    public boolean istHijos() {
        return tHijos;
    }

    public void settHijos(boolean tHijos) {
        this.tHijos = tHijos;
    }

    public boolean isqHijos() {
        return qHijos;
    }

    public void setqHijos(boolean qHijos) {
        this.qHijos = qHijos;
    }

    public String getRelacion() {
        return relacion;
    }

    public void setRelacion(String relacion) {
        this.relacion = relacion;
    }

    public String getInteres() {
        return interes;
    }

    public void setInteres(String interes) {
        this.interes = interes;
    }
}
