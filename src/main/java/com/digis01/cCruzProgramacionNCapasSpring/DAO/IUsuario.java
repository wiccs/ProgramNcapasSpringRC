/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.digis01.cCruzProgramacionNCapasSpring.DAO;

import com.digis01.cCruzProgramacionNCapasSpring.ML.Result;
import com.digis01.cCruzProgramacionNCapasSpring.ML.Usuario;

/**
 *
 * @author Alien 15
 */
interface IUsuario {
            Result GetAll(); //Esto es un metodo abstracto, no lleva implementacion en una interfaz.
            Result GetDetail(int idAlumno);
            Result Add(Usuario usuario);
            Result Update(Usuario usuario);
}
