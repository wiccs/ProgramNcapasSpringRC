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
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.digis01.cCruzProgramacionNCapasSpring.ML.Result;
import com.digis01.cCruzProgramacionNCapasSpring.ML.Rol;
import com.digis01.cCruzProgramacionNCapasSpring.ML.Usuario;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;

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

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Alien 15
 */
@Controller
@RequestMapping("usuario")
public class UsuarioController {

    Result result = new Result();

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
    public String Index(Model model) {
        Result result = usuarioDAOImplementation.GetAll();

        if (result.correct) {
            model.addAttribute("usuarios", result.objects);

        } else {
            model.addAttribute("usuarios", null);
        }

        return "UsuarioIndex";
    }

    @GetMapping("usuarioDetail/{idUsuario}")
    public String UsuarioDetail(@PathVariable int idUsuario, Model model) {

        Result result = usuarioDAOImplementation.GetDetail(idUsuario); //(●'◡'●)

        if (result.correct) {

            model.addAttribute("datosUsuario", result);

        } else {

        }

        return "UsuarioDetail";
    }

    @GetMapping("add")
    public String add(Model model) {

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
            Model model) {

        Result resultrol = rolDAOImplementation.GetAll();

        model.addAttribute("Roles", resultrol.objects);

        if (idDireccion == null) {

            Result result = usuarioDAOImplementation.GetDetail(idUsuario); //Este result contendra un idUsuario, y quiza un idDireccion

            int DireccionId = -1;

            model.addAttribute("Usuario", result.object); //Los ids se mandan a la vista del formulario para cargarlos. 
            model.addAttribute("idUsuario", DireccionId);

        }

        return "UsuarioForm";

    }

    @PostMapping("add") // localhost:8081/alumno/add
    public String Add(@Valid @ModelAttribute("Usuario") Usuario usuario,
            BindingResult bindingResult, Model model, @RequestParam("imagenFile") MultipartFile imagen) {

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
    public Result EstadoByPais(@PathVariable("IdPais") int IdPais) {

        Result result = estadoDAOImplementation.GetAll(IdPais);

        return result;
    }

    @GetMapping("getMunicipioByEstado/{IdEstado}")
    @ResponseBody // retorne un dato estructurado - JSON
    public Result MunicipioByEstado(@PathVariable("IdEstado") int IdEstado) {

        Result result = municipioDAOImplementation.GetAll(IdEstado);

        return result;
    }

    @GetMapping("getColoniaByMunicipio/{IdMunicipio}")
    @ResponseBody // retorne un dato estructurado - JSON
    public Result ColoniaByMunicipio(@PathVariable("IdMunicipio") int IdEstado) {

        Result result = coloniaDAOImplementation.GetAll(IdEstado);

        return result;
    }

    //-----------------------------------------------------------------------------
//Carga Masiva:
    @GetMapping("cargamasiva")
    public String CargaMasiva() {

        return "CargaMasiva";
    }

    @PostMapping("cargamasiva")
    public String CargaMasiva(@RequestParam("archivo") MultipartFile file, Model model, HttpSession session) {
        
        
       
        String root = System.getProperty("user.dir"); //Obtiene la ruta raiz del proyecto.
        String rutaArchivo = "/src/main/resources/archivos/"; //Establecemos la ruta del archivo.
        String fechaSubida = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));//Obtenemos la fecha actual
        //Creamos una ruta unica concatenando la fecha de subida mas el nombre original del archivo. 
        String rutaFinal = root + rutaArchivo +  fechaSubida + file.getOriginalFilename();
        
        
        try {
            file.transferTo(new File(rutaFinal)); //Se transfiere la informacion del archivo subido al nuevo. 
        } catch (Exception ex) {
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        } 
        

        if (file.getOriginalFilename().split("\\.")[1].equals("txt")) {

            List<Usuario> usuarios = ProcesarTXT(new File(rutaFinal));
            List<ErrorCM> errores = ValidarDatos(usuarios);

            if (errores.isEmpty()) {

                model.addAttribute("listaErrores", errores);
                model.addAttribute("archivoCorrecto", true);
                session.setAttribute("path", rutaFinal); // La ruta se guarda en la session y no se envia a la vista.

            } else {
                model.addAttribute("listaErrores", errores);
                model.addAttribute("archivoCorrecto", false);
            }

        } else {

            List<Usuario> usuarios = ProcesarExcel(new File(rutaFinal));
 
            List<ErrorCM> errores = ValidarDatos(usuarios);

            if (errores.isEmpty()) {

                model.addAttribute("listaErrores", errores);
                model.addAttribute("archivoCorrecto", true);
                session.setAttribute("path", rutaFinal);
                
            } else {
                
                model.addAttribute("listaErrores", errores);
                model.addAttribute("archivoCorrecto", false);

            }

        }

        return "CargaMasiva";

    }

   
   
    

    private List<ErrorCM> ValidarDatos(List<Usuario> usuarios) {

        List<ErrorCM> errores = new ArrayList<>();

        int linea = 1;

        for (Usuario usuario : usuarios) {

           if (usuario.getNombre() == null || usuario.getNombre().isEmpty()) {
    ErrorCM errorCM = new ErrorCM(linea, usuario.getNombre(), "Campo Nombre es obligatorio");
    errores.add(errorCM);
} else {
    String regex = "^[A-Za-z]+(\\s[A-Za-z]+)?$";
    Boolean esValido = Pattern.matches(regex, usuario.getNombre());
    if (!esValido) {
        ErrorCM errorCM = new ErrorCM(linea, usuario.getNombre(), "El nombre no es valido");
        errores.add(errorCM);
    }
}


            if (usuario.getApellidoPaterno() == null || usuario.getApellidoPaterno() == "") {
                ErrorCM errorCM = new ErrorCM(linea, usuario.getApellidoPaterno(), "Apellido Paterno es obligatorio");
                errores.add(errorCM);
            } else {
                String regex = "[A-Za-z/ñ]+$";
                Boolean esValido = Pattern.matches(regex, usuario.getApellidoPaterno());
                if (!esValido) {
                    ErrorCM errorCM = new ErrorCM(linea, usuario.getApellidoPaterno(), "El Apellido  no es valido");
                    errores.add(errorCM);
                }
            }

            if (usuario.getApellidoMaterno() == null || usuario.getApellidoMaterno() == "") {
                ErrorCM errorCM = new ErrorCM(linea, usuario.getApellidoMaterno(), "Apellido Materno es obligatorio");
                errores.add(errorCM);
            } else {
                String regex = "[A-Za-z/ñ]+$";
                Boolean esValido = Pattern.matches(regex, usuario.getApellidoMaterno());
                if (!esValido) {
                    ErrorCM errorCM = new ErrorCM(linea, usuario.getApellidoMaterno(), "El Apellido  no es valido");
                    errores.add(errorCM);
                }
            }

            Integer edad = (Integer) usuario.getEdad();

            if (edad == null || edad == 0) {
                ErrorCM errorCM = new ErrorCM(linea, String.valueOf(edad), "Edad es obligatoria");
                errores.add(errorCM);
            } else {
                String regex = "[1]?[0-9][0-9]$";
                Boolean esValido = Pattern.matches(regex, edad.toString());
                if (!esValido) {
                    ErrorCM errorCM = new ErrorCM(linea, usuario.getApellidoMaterno(), "La edad  no es valido");
                    errores.add(errorCM);
                }
            }
//
            if (usuario.getSexo() == null || usuario.getSexo() == "") {
                ErrorCM errorCM = new ErrorCM(linea, usuario.getSexo(), "Sexo es obligatorio");
                errores.add(errorCM);
            } else {
                String regex = "^[M|N]";
                Boolean esValido = Pattern.matches(regex, usuario.getSexo());
                if (!esValido) {
                    ErrorCM errorCM = new ErrorCM(linea, usuario.getSexo(), "El Sexo no es valido");
                    errores.add(errorCM);
                }
            }
//
            if (usuario.getFechaNacimiento() == null) {
                ErrorCM errorCM = new ErrorCM(linea, null, "La fecha es obligatoria");
                errores.add(errorCM);
            } else {

                if (result.correct = false) {
                    ErrorCM errorCM = new ErrorCM(linea, null, "La fecha no es valida");
                    errores.add(errorCM);
                }
            }
//
            if (usuario.getUsername() == null || usuario.getUsername() == "") {
                ErrorCM errorCM = new ErrorCM(linea, usuario.getSexo(), "usuario es obligatorio");
                errores.add(errorCM);
            } else {
                String regex = "[A-Za-z0-9_]+$";
                Boolean esValido = Pattern.matches(regex, usuario.getUsername());
                if (!esValido) {
                    ErrorCM errorCM = new ErrorCM(linea, usuario.getUsername(), "El Usuario no es valido");
                    errores.add(errorCM);
                }
            }
//
            if (usuario.getEmail() == null || usuario.getEmail() == "") {
                ErrorCM errorCM = new ErrorCM(linea, usuario.getEmail(), "Email es obligatorio");
                errores.add(errorCM);
            } else {
                String regex = "[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}";
                Boolean esValido = Pattern.matches(regex, usuario.getEmail());
                if (!esValido) {
                    ErrorCM errorCM = new ErrorCM(linea, usuario.getEmail(), "El email no es valido");
                    errores.add(errorCM);
                }
            }
//
            if (usuario.getPassword() == null || usuario.getPassword() == "") {
                ErrorCM errorCM = new ErrorCM(linea, usuario.getPassword(), "Password es obligatorio");
                errores.add(errorCM);
            } else {
                String regex = "[A-Za-z0-9./]+";
                Boolean esValido = Pattern.matches(regex, usuario.getPassword());
                if (!esValido) {
                    ErrorCM errorCM = new ErrorCM(linea, usuario.getPassword(), "El password no es valido");
                    errores.add(errorCM);
                }
            }
//
            if (usuario.getTelefono() == null || usuario.getTelefono() == "") {
                ErrorCM errorCM = new ErrorCM(linea, usuario.getPassword(), "Telefono es obligatorio");
                errores.add(errorCM);
            } else {
                String regex = "(\\+52)?\\d{10}";
                Boolean esValido = Pattern.matches(regex, usuario.getTelefono());
                if (!esValido) {
                    ErrorCM errorCM = new ErrorCM(linea, usuario.getTelefono(), "El Telefono no es valido");
                    errores.add(errorCM);
                }
            }
//
            if (usuario.getCelular() == null || usuario.getCelular() == "") {
                ErrorCM errorCM = new ErrorCM(linea, usuario.getPassword(), "Celular es obligatorio");
                errores.add(errorCM);
            } else {
                String regex = "(\\+52)?\\d{10}";
                Boolean esValido = Pattern.matches(regex, usuario.getCelular());
                if (!esValido) {
                    ErrorCM errorCM = new ErrorCM(linea, usuario.getCelular(), "El Celular no es valido");
                    errores.add(errorCM);
                }
            }
            
            if (usuario.getCurp() == null || usuario.getCurp()== "") {
                ErrorCM errorCM = new ErrorCM(linea, usuario.getCurp(), "Curp es obligatorio");
                errores.add(errorCM);
            } else {
                String regex = "[A-Za-z\\d]{18}";
                Boolean esValido = Pattern.matches(regex, usuario.getCurp());
                if (!esValido) {
                    ErrorCM errorCM = new ErrorCM(linea, usuario.getCurp(), "El curp no es valido");
                    errores.add(errorCM);
                }
            }

        }

        linea++;

        return errores;

    }
    
    private List<Usuario> ProcesarTXT(File file) {
        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            
            String linea = "";
            List<Usuario> usuarios = new ArrayList<>();

            while ((linea = bufferedReader.readLine()) != null) {

                String[] campos = linea.split("\\|");
                Usuario usuario = new Usuario();
                usuario.setNombre(campos[0]);
                usuario.setApellidoPaterno(campos[1]);
                usuario.setApellidoMaterno(campos[2]);
                usuario.setEdad(Integer.parseInt(campos[3]));
                usuario.setSexo(campos[4]);
//                
                try {
                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                    Date fecha = formato.parse(campos[5]);
                    usuario.setFechaNacimiento(fecha);

                } catch (Exception e) {

                    result.correct = false;

                }
                usuario.setUsername(campos[6]);
                usuario.setEmail(campos[7]);
                usuario.setPassword(campos[8]);
                usuario.setTelefono(campos[9]);
                usuario.setCelular(campos[10]);
                usuario.setCurp(campos[11]);
                usuario.setFotito(campos[12]);

                usuario.Rol = new Rol();
                usuario.Rol.setIdRol(Integer.parseInt(campos[13]));
                usuarios.add(usuario);
            }
            return usuarios;

        } catch (Exception ex) {
            System.out.println("error");
            return null;
        }
    }
    private List<Usuario> ProcesarExcel(File file) {

        List<Usuario> usuarios = new ArrayList<>();

        try {
                 XSSFWorkbook workbook = new XSSFWorkbook(file);

                 Sheet sheet = workbook.getSheetAt(0);
            
        

            for (Row row : sheet) { //Cada fila de la hoja se mapea como una entidad Usuario

                Usuario usuario = new Usuario();

                  usuario.setNombre(row.getCell(0) != null ? row.getCell(0).toString() : "");
                  usuario.setApellidoPaterno(row.getCell(1) != null ? row.getCell(1).toString():"");
                  usuario.setApellidoMaterno(row.getCell(2) != null ? row.getCell(2).toString():"");
                
                  DataFormatter formatter = new DataFormatter();
                  String celdaEdad = formatter.formatCellValue(row.getCell(3));
                
                  Cell cellEdad = row.getCell(3);
                  
                  usuario.setEdad((int)cellEdad.getNumericCellValue());
                
                  usuario.setSexo(row.getCell(4) != null ? row.getCell(4).toString() : "");
                
                  SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                  Date fecha = format.parse(row.getCell(5).toString());

                  usuario.setFechaNacimiento(fecha != null ? fecha : null);
                  
                  usuario.setUsername(row.getCell(6) != null ? row.getCell(6).toString() :"");
                  
                  usuario.setEmail(row.getCell(7) != null ? row.getCell(7).toString(): "");
                  
                  usuario.setPassword(row.getCell(8) != null ? row.getCell(8).toString():"");
                  
                  DataFormatter formatNum = new DataFormatter();
                  String numero = formatNum.formatCellValue(row.getCell(9)).trim();
                   usuario.setTelefono(numero!= null ? numero :"");
                  
                  String numeroCelular = formatNum.formatCellValue(row.getCell(10)).trim();
                   usuario.setCelular(numeroCelular!= null ? numeroCelular :"");        
                  
                 usuario.setCurp(row.getCell(11) != null ? row.getCell(11).toString() : "");
                 
                  usuario.Rol = new Rol();
                  int idRolint = (int) Double.parseDouble(row.getCell(12).toString()); 
                  Integer idRolInteger = idRolint;
                  
                  usuario.Rol.setIdRol(idRolInteger != null ? idRolInteger : 0);
                         
                  usuario.setFotito(row.getCell(13).toString() != null ? row.getCell(13).toString() : "");
                  
     
                usuarios.add(usuario);

            }

            return usuarios;

        } catch (Exception ex) {
            Result result =new Result();
            result.ex = ex;
            result.errorMessage = ex.getLocalizedMessage();
            return null;

        }

    }

}
//
