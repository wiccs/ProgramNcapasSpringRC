/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.cCruzProgramacionNCapasSpring.DAO;

import com.digis01.cCruzProgramacionNCapasSpring.ML.Pais;
import com.digis01.cCruzProgramacionNCapasSpring.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alien 15
 */
@Repository
public class PaisDAOImplementation implements IPais {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override //Implementamos metodos de interfaz. 
    public Result GetAll() {
        Result result = new Result(); //Intanciamos un result para guardar datos de operaciones exitosas. 
        //Encerramos logica en try catch:

        try {

            result.correct = jdbcTemplate.execute("{CALL PaisGetAll(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);

                //Aqui solo ejecutamos el procedimiento:
                callableStatement.execute();

                //Recuerda ResultSet es quien guarda los resultados de la consulta o procedimiento:
                //El 1 representa el parametro de salida del SP:
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                result.objects = new ArrayList<>(); // Inicializamos el atributo Objects de entidad result, como una lista.

                while (resultSet.next()) { //Mientras haya resultados

                    //Creamos una Instancia de Rol con parametros:
                    Pais pais = new Pais(resultSet.getInt("IdPais"), resultSet.getString("Nombre"));

                    result.objects.add(pais);

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


