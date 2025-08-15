/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.cCruzProgramacionNCapasSpring.DAO;

/**
 *
 * @author Alien 15
 */

import com.digis01.cCruzProgramacionNCapasSpring.DAO.IUsuario;
import com.digis01.cCruzProgramacionNCapasSpring.ML.Usuario;
import com.digis01.cCruzProgramacionNCapasSpring.ML.Colonia;
import com.digis01.cCruzProgramacionNCapasSpring.ML.Direccion;
import com.digis01.cCruzProgramacionNCapasSpring.ML.Estado;
import com.digis01.cCruzProgramacionNCapasSpring.ML.Municipio;
import com.digis01.cCruzProgramacionNCapasSpring.ML.Pais;
import com.digis01.cCruzProgramacionNCapasSpring.ML.Result;
import com.digis01.cCruzProgramacionNCapasSpring.ML.Rol;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.digis01.cCruzProgramacionNCapasSpring.ML.Direccion;
import java.util.List;

/**
 *
 * @author wiccs
 */
@Repository // define que una clase tiene logica de base de datos 
public class UsuarioDAOImplementation implements IUsuario {

    @Autowired //inyeccion de dependencias 
private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {
        Result result = new Result();

        try {

            // clases Wrapper int - INTEGER, double, float, char
            jdbcTemplate.execute("{CALL GetAllDireccionesSP(?)}", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                result.objects = new ArrayList<>();
                while (resultSet.next()) {

                    //ML.Alumno alumnotest = (ML.Alumno) (result.objects.get(result.objects.size() - 1));
                    int idUsuario = resultSet.getInt("IdUsuario");

                    if (!result.objects.isEmpty() && idUsuario == ((Usuario) (result.objects.get(result.objects.size() - 1))).getIdUsuario()) {
                        //que la lista no sea vacia, que el id se repita 

                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        //resto de datos
                        direccion.colonia = new Colonia();
                        direccion.colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.colonia.setNombreColonia(resultSet.getString("Colonia"));
                        // resto de datos
                        direccion.colonia.Municipio = new Municipio();
                        direccion.colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.colonia.Municipio.setNombreMunicipio(resultSet.getString("Municipio"));

                        direccion.colonia.Municipio.Estado = new Estado();
                        direccion.colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        direccion.colonia.Municipio.Estado.setNombreEstado(resultSet.getString("Estado"));
                        direccion.colonia.Municipio.Estado.Pais = new Pais();
                        direccion.colonia.Municipio.Estado.Pais.setNombrePais(resultSet.getString("Pais"));

                        ((Usuario) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);

                    } else {

                        Usuario usuario = new Usuario();

                        usuario.setIdUsuario(idUsuario);
                        usuario.setNombre(resultSet.getString("Nombre"));
                        
                        usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        
                        if(resultSet.getString("ApellidoMaterno") == null){
                        usuario.setApellidoMaterno(" ");}
                        
                        usuario.setUsername(resultSet.getString("Username"));
                        usuario.setEmail(resultSet.getString("Email"));
                        usuario.setTelefono(resultSet.getString("Telefono"));
                        
                        if(resultSet.getString("Celular") == null){
                        usuario.setCelular(" ");}
                        
                      
                        
                        usuario.Rol = new Rol();
                        usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                        usuario.Rol.setNombre(resultSet.getString("Rol"));

                        int idDireccion;
                        if ((idDireccion = resultSet.getInt("IdDireccion")) != 0) {

                            usuario.Direcciones = new ArrayList<>();

                            Direccion direccion = new Direccion();
                            direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                            direccion.setCalle(resultSet.getString("Calle"));
                            
                            direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                            direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                            
                            //resto de datos
                            direccion.colonia = new Colonia();
                            direccion.colonia.setIdColonia(resultSet.getInt("IdColonia"));
                            direccion.colonia.setNombreColonia(resultSet.getString("Colonia"));
                            // resto de datos
                            direccion.colonia.Municipio = new Municipio();
                            direccion.colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                            direccion.colonia.Municipio.setNombreMunicipio(resultSet.getString("Municipio"));

                            direccion.colonia.Municipio.Estado = new Estado();
                            direccion.colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                            direccion.colonia.Municipio.Estado.setNombreEstado(resultSet.getString("Estado"));

                            usuario.Direcciones.add(direccion);
                        }
                        result.objects.add(usuario);
                    }
                }
                result.correct = true;
                return 1;
            }
            );
            
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result GetDetail(int idUsuario) {
    Result result = new Result();

        try {

            // clases Wrapper int - INTEGER, double, float, char
            jdbcTemplate.execute("{CALL UsuarioGetById(?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {
               
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR); //Parametro de entrada
                 callableStatement.setInt(2, idUsuario); //Index de usuario

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1); //Obtener del paramtero de entrada, osea el 1

                if (resultSet.next()) {

                    Usuario usuario = new Usuario();

                        usuario.setIdUsuario(idUsuario);
                        usuario.setNombre(resultSet.getString("Nombre"));
                        
                        usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        
                        if(resultSet.getString("ApellidoMaterno") == null){
                        usuario.setApellidoMaterno(" ");}
                        
                        usuario.setUsername(resultSet.getString("Username"));
                        usuario.setEmail(resultSet.getString("Email"));
                        usuario.setTelefono(resultSet.getString("Telefono"));
                        
                        if(resultSet.getString("Celular") == null){
                        usuario.setCelular(" ");}
                        
                      
                        
                        usuario.Rol = new Rol();
                        usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                        usuario.Rol.setNombre(resultSet.getString("Rol"));

                    int idDireccion;
                    if ((idDireccion = resultSet.getInt("IdDireccion")) != 0) {

                        usuario.Direcciones = new ArrayList<>();
                                                                                
                        do {
                            Direccion direccion = new Direccion();
                            
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        //resto de datos
                        direccion.colonia = new Colonia();
                        direccion.colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.colonia.setNombreColonia(resultSet.getString("Colonia"));
                        // resto de datos
                        direccion.colonia.Municipio = new Municipio();
                        direccion.colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.colonia.Municipio.setNombreMunicipio(resultSet.getString("Municipio"));

                        direccion.colonia.Municipio.Estado = new Estado();
                        direccion.colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        direccion.colonia.Municipio.Estado.setNombreEstado(resultSet.getString("Estado"));
                        direccion.colonia.Municipio.Estado.Pais = new Pais();
                        direccion.colonia.Municipio.Estado.Pais.setNombrePais(resultSet.getString("Pais"));

                            usuario.Direcciones.add(direccion);
                        } while (resultSet.next());
                    }
                    result.object = usuario;
                }

                result.correct = true;
                return 1;
            });

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result Add(Usuario usuario) {
        Result result = new Result();

        try {
            result.correct = jdbcTemplate.execute("CALL UsuarioDireccionAdd(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", (CallableStatementCallback<Boolean>) callablestatement -> {

                callablestatement.setString(1, usuario.getNombre());
                callablestatement.setString(2, usuario.getApellidoPaterno());
                callablestatement.setString(3, usuario.getApellidoMaterno());
                callablestatement.setInt(4, usuario.getEdad());
                callablestatement.setString(5, usuario.getSexo());
                callablestatement.setDate(6, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                callablestatement.setString(7, usuario.getUsername());
       
                callablestatement.setString(8, usuario.getEmail());
                callablestatement.setString(9, usuario.getPassword());
                callablestatement.setString(10, usuario.getTelefono());
                callablestatement.setString(11, usuario.getCelular());
                callablestatement.setString(12, usuario.getCurp()); 
                callablestatement.setInt(13, usuario.Rol.getIdRol());
                
                
                
                callablestatement.setString(14, usuario.Direcciones.get(0).getCalle());
                callablestatement.setString(15, usuario.Direcciones.get(0).getNumeroInterior());
                callablestatement.setString(16, usuario.Direcciones.get(0).getNumeroExterior());
                callablestatement.setInt(17, usuario.Direcciones.get(0).colonia.getIdColonia());

                int isCorrect = callablestatement.executeUpdate();

                if (isCorrect == -1) {

                    return true;
                }
                
                return false;
            });

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;

        }

        return result;

    
    }

    @Override
    public Result Update(int idUsuario) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("{CALL UsuarioUpdateAjax(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
            callableStatement.setInt(1, idUsuario);
            callableStatement.setString(2, );
            })
        } catch (Exception e) {
        }
    }

}
    

  