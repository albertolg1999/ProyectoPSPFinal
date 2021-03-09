/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import java.util.Date;
/**
 *
 * @author disen
 */
public class Perfil implements Serializable{
    private String name;
    private byte[] foto;
    private int edad;
    private Date nacimiento;

    public Perfil(String name, byte[] foto, int edad, Date nacimiento) {
        this.name = name;
        this.foto = foto;
        this.edad = edad;
        this.nacimiento = nacimiento;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Date getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }
    
    
    
}
