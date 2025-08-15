/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.cCruzProgramacionNCapasSpring.DAO;

import com.digis01.cCruzProgramacionNCapasSpring.ML.Colonia;
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
public class ColoniaDAOImplementation implements IColonia{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll(int idMunicipio) {
        Result result = new Result();
        
        try {
            jdbcTemplate.execute("{CALL ColoniaGetAll(?,?)}", (CallableStatementCallback<Boolean>)callableStatement ->{
            callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
            callableStatement.setInt(2, idMunicipio);
            callableStatement.execute();
            
            ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
            
            result.objects = new ArrayList<>();
            
            while(resultSet.next()){
            
                Colonia colonia = new Colonia();
                
                colonia.setIdColonia(resultSet.getInt("IdColonia")); 
                colonia.setNombreColonia(resultSet.getString("Nombre"));
                colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                 result.objects.add(colonia);
            }
           result.correct = true;
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
