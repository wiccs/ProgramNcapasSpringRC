/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.cCruzProgramacionNCapasSpring.Configuration;


import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author Alien 15
 */
@Configuration
class DataSourceConfig {
    
       @Bean //Usamos bean por que esta configuracion necesita crearse manualmente.
    public DataSource dataSource(){ //Este metodo tiene los credenciales para conectarse a BD
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        
        dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:orcl");
        dataSource.setUsername("CCruzProgramacionNCapas");
        dataSource.setPassword("password1");
        
        return dataSource; 
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate (DataSource dataSource){ //Pasamos como parametro la conexion a jdbcTemplate
        return new JdbcTemplate(dataSource);//El bean nos permite usarla donde queramos automaticamente 
    }
}
