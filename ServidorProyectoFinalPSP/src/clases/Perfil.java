/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
/**
 *
 * @author disen
 */
public class Perfil implements Serializable{
    private String name;
    private File foto;
    private byte[] imagen;
    private int id;
    
    private int edad;
    private String contraseña;
    private String Localidad;

    public Perfil(String name,int id, File foto, int edad, String Localidad) {
        this.name = name;
        this.id=id;
        this.foto = foto;
        this.edad = edad;
        this.Localidad = Localidad;
    }
    
    public Perfil(String name, byte[] imagen, int edad, String Localidad) {
        this.name = name;
        this.imagen = imagen;
        this.edad = edad;
        this.Localidad = Localidad;
    }

    public Perfil() {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    public File getFoto() {
        return foto;
    }

    public void setFoto(File foto) {
        this.foto = foto;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

     

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getLocalidad() {
        return Localidad;
    }

    public void setLocalidad(String Localidad) {
        this.Localidad = Localidad;
    }
    
    
    
}
