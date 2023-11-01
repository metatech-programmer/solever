package co.com.mt.web;

import co.com.mt.dao.UsuarioDao;
import co.com.mt.domain.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ControladorAjustes {

    @Autowired
    private UsuarioDao usuarioDao;

    @GetMapping("/ajustes")
    public String inicio(Model model, @AuthenticationPrincipal User user) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioDao.findBycorreoUsuario(username);
        String nombre = usuario.getNombreUsuario();

        model.addAttribute("nombreUsu", nombre);
        model.addAttribute("usuariosD", usuario);

        String languageSelection = (String) model.getAttribute("languageSelection");
        if (languageSelection == null) {
            languageSelection = "es"; // Establece un valor predeterminado si no hay selección guardada
        }
        model.addAttribute("languageSelection", languageSelection);

        return "ajustes";
    }

}
