package co.com.mt.web;

import co.com.mt.dao.CategoriaDao;
import co.com.mt.dao.UsuarioDao;
import co.com.mt.domain.Usuario;
import co.com.mt.servicio.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ControladorUsuarios {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private CategoriaDao categoriaDao;

    @GetMapping("/usuarios")
    public String inicio(Model model, @AuthenticationPrincipal User user) {
        var usuarios = usuarioService.listarUsuarios();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioDao.findBycorreoUsuario(username);
        String nombre = usuario.getNombreUsuario();

        model.addAttribute(
                "nombreUsu", nombre);
        model.addAttribute(
                "usuarios", usuarios);
        model.addAttribute(
                "totalUsuarios", usuarios.size());
        return "usuarios";
    }

    @GetMapping("/registrar")
    public String mostrarFormulario(Model model) {
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        return "registrar";
    }

    @PostMapping("/registro")
    public String guardar(@Validated Usuario usuario, @RequestParam("fotoCedula") MultipartFile fotoCedulaUsuario, @RequestParam("categoria") String opcionSeleccionada, @RequestParam("passwordUsuario") String pass, RedirectAttributes redirectAttributes, Errors errores) {

        if (errores.hasErrors() || "ROLE_ADMIN".equals(opcionSeleccionada) || "defecto".equals(opcionSeleccionada) || usuarioDao.existsBycorreoUsuario(usuario.getCorreoUsuario()) || usuarioDao.existsBynumeroDocumentoUsuario(usuario.getNumeroDocumentoUsuario())) {
            redirectAttributes.addAttribute("error", "No se pudo registrar exitosamente, intentelo nuevamente.");
            return "redirect:/registrar";
        }
        var categoria = categoriaDao.findBynombreCategoria(opcionSeleccionada);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String nombreArchivo = fotoCedulaUsuario.getOriginalFilename();

        usuario.setIdCategoria(categoria);
        usuario.setPasswordUsuario(encoder.encode(pass));
        usuario.setFotoCedulaUsuario(nombreArchivo);
        usuarioService.guardar(usuario);
        return "redirect:/login";

    }

    @PostMapping("/editarU")
    public String editarU(@Validated Usuario usuario, @RequestParam("passwordUsuario") String pass, RedirectAttributes redirectAttributes, Errors errores) {

        if (errores.hasErrors()) {
            redirectAttributes.addAttribute("error", "Falto algun parametro en el usuario ingresado.");
            redirectAttributes.addAttribute("idUsuario", usuario.getIdUsuario());

            return "redirect:/editarUsuario/{idUsuario}";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        usuario.setPasswordUsuario(encoder.encode(pass));
        usuarioService.guardar(usuario);
        redirectAttributes.addAttribute("exito", "El usuario se actualizo exitosamente.");
        return "redirect:/editar";

    }
}
