/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.cCruzProgramacionNCapasSpring.DAO;

import com.digis01.cCruzProgramacionNCapasSpring.ML.Result;
import com.digis01.cCruzProgramacionNCapasSpring.ML.Rol;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

/**
 *
 * @author Alien 15
 */
@Repository //Indicamos que este archivo contendra logica de BD.
public class RolDAOImplementation implements IRol { //Indicamos que implemente de intefaz IRol

    @Autowired //Inyectamos coneccion de BD
    private JdbcTemplate jdbcTemplate;

    @Override //Implementamos metodos de interfaz. 
    public Result GetAll() {
        Result result = new Result(); //Intanciamos un result para guardar datos de operaciones exitosas. 
        //Encerramos logica en try catch:

        try {

            result.correct = jdbcTemplate.execute("{CALL RolGetAll(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);

                //Aqui solo ejecutamos el procedimiento:
                callableStatement.execute();

                //Recuerda ResultSet es quien guarda los resultados de la consulta o procedimiento:
                //El 1 representa el parametro de salida del SP:
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                result.objects = new ArrayList<>(); // Inicializamos el atributo Objects de entidad result, como una lista.

                while (resultSet.next()) { //Mientras haya resultados

                    //Creamos una Instancia de Rol con parametros:
                    Rol rol = new Rol(resultSet.getInt("IdRol"), resultSet.getString("Nombre"));

                    result.objects.add(rol);

                }
                return true;

            });
        } catch (Exception e) {
            
        result.correct = false;
        result.errorMessage = e.getLocalizedMessage();
        result.ex = e;

        }
        return result;
    }
}
