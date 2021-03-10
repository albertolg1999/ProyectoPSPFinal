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
public class Usuario implements Serializable{
    
    private String name;
    private String email;
    private String pwd;
    private boolean activado;
    private String rol;
    private int id;

    public Usuario( String name, String email, String pwd, boolean activado, String rol) {
        this.name = name;
        this.email = email;
        this.pwd = pwd;
        this.activado = activado;
        this.rol = rol;
    }
    
    public Usuario( String name, String email, String pwd, boolean activado) {
        this.name = name;
        this.email = email;
        this.pwd = pwd;
        this.activado = activado;
    }
    
    

    public Usuario(String name, String email, String pwdR) {
        this.name = name;
        this.email = email;
        this.pwd = pwdR;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
    public Usuario(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
    }

    /**
     *
     * @param email
     * @param name
     */
    public Usuario(String email, String name,int id) {
        this.email = email;
        this.name = name;
        this.id=id;
    }
    public Usuario() {
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean isActivado() {
        return activado;
    }

    public void setActivado(boolean activado) {
        this.activado = activado;
    }
}
