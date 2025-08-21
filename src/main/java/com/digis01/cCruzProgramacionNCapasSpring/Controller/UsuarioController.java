/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.cCruzProgramacionNCapasSpring.Controller;

import com.digis01.cCruzProgramacionNCapasSpring.DAO.ColoniaDAOImplementation;
import com.digis01.cCruzProgramacionNCapasSpring.DAO.EstadoDAOImplementation;
import com.digis01.cCruzProgramacionNCapasSpring.DAO.MunicipioDAOImplementation;
import com.digis01.cCruzProgramacionNCapasSpring.DAO.PaisDAOImplementation;
import com.digis01.cCruzProgramacionNCapasSpring.DAO.RolDAOImplementation;
import com.digis01.cCruzProgramacionNCapasSpring.DAO.UsuarioDAOImplementation;
import com.digis01.cCruzProgramacionNCapasSpring.ML.Direccion;
import com.digis01.cCruzProgramacionNCapasSpring.ML.ErrorCM;
import jakarta.validation.Valid;

import com.digis01.cCruzProgramacionNCapasSpring.ML.Result;
import com.digis01.cCruzProgramacionNCapasSpring.ML.Rol;
import com.digis01.cCruzProgramacionNCapasSpring.ML.Usuario;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Alien 15
 */

@Controller
@RequestMapping("usuario")
public class UsuarioController {
    
    @Autowired //Inyeccion de repositorios y cositas :V
    private UsuarioDAOImplementation usuarioDAOImplementation;
    @Autowired
    private RolDAOImplementation rolDAOImplementation;
    @Autowired
    private PaisDAOImplementation paisDAOImplementation;
    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;
    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;
    @Autowired
    private ColoniaDAOImplementation coloniaDAOImplementation;
    
    
    
    @GetMapping
    public String Index(Model model){
        Result result = usuarioDAOImplementation.GetAll();
        
        if (result.correct) {
            model.addAttribute("usuarios", result.objects);
      
        } else  {
            model.addAttribute("usuarios", null);
        }
        
        return "UsuarioIndex";
    }
    
     @GetMapping("usuarioDetail/{idUsuario}")
    public String UsuarioDetail(@PathVariable int idUsuario, Model model)
    
    {

        Result result = usuarioDAOImplementation.GetDetail(idUsuario); //(●'◡'●)
        
       if(result.correct){
           
        model.addAttribute("datosUsuario", result);
        
       }else{
      
       }

        return "UsuarioDetail";
    }
    
    
        @GetMapping("add") 
    public String add(Model model){
        
         Result result = rolDAOImplementation.GetAll();
         Result result2 = paisDAOImplementation.GetAll();
         
        model.addAttribute("Roles", result.objects);
         model.addAttribute("Paises", result2.objects);
        model.addAttribute("Usuario", new Usuario());
        
        return "UsuarioForm";
    }
    
    
     @GetMapping("getformEditable")
    public String formEditable(@RequestParam int idUsuario,
            @RequestParam(required = false) Integer idDireccion, 
            Model model){
        
        Result resultrol = rolDAOImplementation.GetAll();
        
        model.addAttribute("Roles",resultrol.objects);
        
         if (idDireccion == null) { 
                
            
            Result result = usuarioDAOImplementation.GetDetail(idUsuario); //Este result contendra un idUsuario, y quiza un idDireccion
        
            int DireccionId = -1;
            
            model.addAttribute("Usuario",result.object); //Los ids se mandan a la vista del formulario para cargarlos. 
            model.addAttribute("idUsuario",DireccionId);
            
            
       }
        
           return "UsuarioForm";

        

    }
    
    
   
    @PostMapping("add") // localhost:8081/alumno/add
    public String Add(@Valid @ModelAttribute("Usuario") Usuario usuario,
            BindingResult bindingResult, Model model, @RequestParam("imagenFile") MultipartFile imagen){
        
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("Usuario", usuario);
//            return "AlumnoForm";//Si tiene errores de validacion recarga el formulario sin borrar los datos ingresados
//        } else {


             if (imagen != null && imagen.getOriginalFilename() != "") {
                String nombre = imagen.getOriginalFilename();
                String extension = nombre.split("\\.")[1];
                if (extension.equals("jpg")) {
                    try {
                        byte[] bytes = imagen.getBytes();
                        String base64Image = Base64.getEncoder().encodeToString(bytes);
                        usuario.setFotito(base64Image);
                    } catch (Exception ex) {
                        System.out.println("Error");
                    }

                }
            }

            Result result = usuarioDAOImplementation.UpdateUser(usuario);
            
            return "redirect:/usuario";
        //}

    }
    
    
      //getMunicipioByEstado?IdEstado=7 -- requestParam
    //getMunicipioByestado/7 -- pathVariable
    @GetMapping("getEstadoByPais/{IdPais}")
    @ResponseBody // retorne un dato estructurado - JSON
    public Result EstadoByPais(@PathVariable("IdPais") int IdPais){
      
        Result result = estadoDAOImplementation.GetAll(IdPais);
     
        return result;
    }
    
    
    
    @GetMapping("getMunicipioByEstado/{IdEstado}")
    @ResponseBody // retorne un dato estructurado - JSON
    public Result MunicipioByEstado(@PathVariable("IdEstado") int IdEstado){
      
        Result result =  municipioDAOImplementation.GetAll(IdEstado);
   
        return result;
    }
    
    
    @GetMapping("getColoniaByMunicipio/{IdMunicipio}")
    @ResponseBody // retorne un dato estructurado - JSON
    public Result ColoniaByMunicipio(@PathVariable("IdMunicipio") int IdEstado){
      
        Result result =  coloniaDAOImplementation.GetAll(IdEstado);
   
        return result;
    }
    
 //-----------------------------------------------------------------------------
    
//Carga Masiva:
    
    @GetMapping("cargamasiva")
    public String CargaMasiva() {

        return "CargaMasiva";
    }
    
    
   
     @PostMapping("cargamasiva")
    public String CargaMasiva(@RequestParam("archivo") MultipartFile file) {

        if (file.getOriginalFilename().split("\\.")[1].equals("txt")) {

            List<Usuario> usuarios = ProcesarTXT(file);
            List<ErrorCM> errores = ValidarDatos(usuarios);

            //modelatribute para mandas la lista de errores
            //si lista errores diferente de vacio, intentar desplegar lista de errores en carga masiva
        } else {

            // excel
        }

        return "CargaMasiva";

    }
    
    
    private List<Usuario> ProcesarTXT(MultipartFile file){

        try {

            InputStream inputStream = file.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            
            
            String linea = ""; 
            List<Usuario> usuarios = new ArrayList<>();
            
            while ((linea = bufferedReader.readLine()) != null) {                

                String[] campos = linea.split("\\|");
                Usuario usuario = new Usuario();
                usuario.setNombre(campos[0]);
                usuario.setApellidoPaterno(campos[1]);
                usuario.setApellidoMaterno(campos[2]);
                usuario.setUsername(campos[3]);
                usuario.Rol = new Rol();
                usuario.Rol.setIdRol(Integer.parseInt(campos[4]));
                usuarios.add(usuario);
            }
            return usuarios;
            
        } catch (Exception ex){
            System.out.println("error");
            return null;
        }
    }
    
    private List<ErrorCM> ValidarDatos(List<Usuario> usuarios){
        
        List<ErrorCM> errores = new ArrayList<>();

        int linea = 1; 

        for (Usuario usuario : usuarios) {

            if (usuario.getNombre() == null || usuario.getNombre() == ""){

                ErrorCM errorCM = new ErrorCM(linea, usuario.getNombre(), "Campo obligatorio");

                errores.add(errorCM);

            }

            if(usuario.getApellidoPaterno() == null || usuario.getApellidoPaterno() == ""){
                
                ErrorCM errorCM  = new ErrorCM(linea,usuario.getApellidoPaterno(), "Campo Apellido es obligatorio");
                
                errores.add(errorCM);
            }
            
           
            
            linea ++;
        }

        return errores;


    }


}
