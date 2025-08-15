/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.cCruzProgramacionNCapasSpring.DAO;

import com.digis01.cCruzProgramacionNCapasSpring.ML.Municipio;
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
public class MunicipioDAOImplementation implements IEstado{

      @Autowired
      JdbcTemplate jdbcTemplate = new JdbcTemplate();
    
      @Override
    public Result GetAll(int idEstado) {
        
        Result result = new Result();
        try {
            jdbcTemplate.execute("{CALL MunicipioGetAll(?,?)}",(CallableStatementCallback<Boolean>) callableStatement -> {
            callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
            callableStatement.setInt(2, idEstado);
            callableStatement.execute();
            
            ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
            
            result.objects = new ArrayList<>();
            
                while (resultSet.next()) {
                    
                    Municipio municipio = new Municipio();
                    
                    municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    municipio.setNombreMunicipio(resultSet.getString("Nombre"));
                    
                    result.objects.add(municipio);
                    
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
