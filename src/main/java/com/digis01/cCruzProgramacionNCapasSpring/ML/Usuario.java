/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.cCruzProgramacionNCapasSpring.ML;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 *
 * @author Alien 15
 */
public class Usuario {

    private int IdUsuario;

//    @Size(min = 3, max = 20, message = "Texto de entre 2 y 20 letras")
//    @NotEmpty(message = "Nombre Necesario ^o^")
    private String Nombre;

//    @Size(min = 3, max = 20, message = "Texto de entre 2 y 20 letras")
//    @NotEmpty(message = "Apellido paterno Necesario ^o^")
    private String ApellidoPaterno;

//    @Size(min = 3, max = 20, message = "Texto de entre 2 y 20 letras")
//    @NotEmpty(message = "Apellido materno Necesario ^o^")
    private String ApellidoMaterno;

//    @Min(10)
//    @Max(150)
    private int Edad; //Recuerda quitar edad ya que se calcula con la fecha!!!

    private String Sexo;
    
//    @Past
    private Date FechaNacimiento;
    
//    @NotBlank
    private String Username;
    
//    @Pattern(regexp="[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}", message = "Ingrese un email valido ┬┬﹏┬┬")
    private String Email;
    
    private String Password;
    
//    @Size(min = 10, max = 13, message = "entre 10 y 12 numeros")
    private String Telefono;
    
//     @Size(min = 10, max = 13, message = "entre 10 y 12 numeros")
    private String Celular;
    
//     @Size(min = 18, max = 18, message = "entre 10 y 12 numeros")
    private String Curp;
     
    public Rol Rol; //Propiedad Almacenada

    public List<Direccion> Direcciones;

    public Usuario() {
    }

    public Usuario(String Nombre, String ApellidoPaterno, String ApellidoMaterno, int Edad, String Sexo, Date FechaNacimiento, String Username, String Email,
            String Password, String Telefono, String Celular, String Curp) {
//    this.IdUsuario = IdUsuario;
        this.Nombre = Nombre;
        this.ApellidoPaterno = ApellidoPaterno;
        this.ApellidoMaterno = ApellidoMaterno;
        this.Edad = Edad;
        this.Sexo = Sexo;
        this.FechaNacimiento = FechaNacimiento;
        this.Username = Username;
        this.Email = Email;
        this.Password = Password;
        this.Telefono = Telefono;
        this.Celular = Celular;
        this.Curp = Curp;
    }

    public Usuario(String Nombre, String ApellidoPaterno, String ApellidoMaterno, int Edad, String Sexo, Date FechaNacimiento, String Username, String Email, String Password, String Telefono, String Celular, String Curp, int IdRol) {
        this.Nombre = Nombre;
        this.ApellidoPaterno = ApellidoPaterno;
        this.ApellidoMaterno = ApellidoMaterno;
        this.Edad = Edad;
        this.Sexo = Sexo;
        this.FechaNacimiento = FechaNacimiento;
        this.Username = Username;
        this.Email = Email;
        this.Password = Password;
        this.Telefono = Telefono;
        this.Celular = Celular;
        this.Curp = Curp;
//       this.Rol = new ML.Rol(); Para poder usar la propiedad alamcenada debemos siempre inicializar la clase. Pero ya se inicializo desde otro lado. 
        this.Rol.setIdRol(IdRol);
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int Edad) {
        this.Edad = Edad;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String Celular) {
        this.Celular = Celular;
    }

    public String getCurp() {
        return Curp;
    }

    public void setCurp(String Curp) {
        this.Curp = Curp;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public Rol getRol() {
        return Rol;
    }

    public void setRol(Rol Rol) {
        this.Rol = Rol;
    }

    public List<Direccion> getDirecciones() {
        return Direcciones;
    }

    public void setDirecciones(List<Direccion> Direcciones) {
        this.Direcciones = Direcciones;
    }

}
