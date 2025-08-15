/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.cCruzProgramacionNCapasSpring.DAO;

import com.digis01.cCruzProgramacionNCapasSpring.ML.Estado;
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
public class EstadoDAOImplementation implements IEstado{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll(int idPais) {
        
        Result result = new Result();
        try {
            jdbcTemplate.execute("{CALL EstadoGetAll(?,?)}",(CallableStatementCallback<Boolean>) callableStatement -> {
            callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
            callableStatement.setInt(2, idPais);
            callableStatement.execute();
            
            ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
            
            result.objects = new ArrayList<>();
            
                while (resultSet.next()) {
                    
                    Estado estado = new Estado();
                    
                    estado.setIdEstado(resultSet.getInt("IdEstado"));
                    estado.setNombreEstado(resultSet.getString("Nombre"));
                    
                    result.objects.add(estado);
                    
                }
                result.correct = true;
                
                return true;
            
            });
            
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
        }
        return result;
    }
    
}
