/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.cCruzProgramacionNCapasSpring.ML;

/**
 *
 * @author Alien 15
 */
public class Pais {
    public int IdPais;
    public String NombrePais;
    
    public Pais(){};

    public Pais(int IdPais, String NombrePais) {
        this.IdPais = IdPais;
        this.NombrePais = NombrePais;
    }
    
    
    

    public int getIdPais() {
        return IdPais;
    }

    public void setIdPais(int IdPais) {
        this.IdPais = IdPais;
    }

    public String getNombrePais() {
        return NombrePais;
    }

    public void setNombrePais(String NombrePais) {
        this.NombrePais = NombrePais;
    }
    
 
    
}
