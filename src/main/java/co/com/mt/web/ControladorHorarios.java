package co.com.mt.web;

import co.com.mt.dao.CategoriaTemaDao;
import co.com.mt.dao.TemaDao;
import co.com.mt.dao.UsuarioDao;
import co.com.mt.domain.Categoria;
import co.com.mt.domain.CategoriaTema;
import co.com.mt.domain.Tema;
import co.com.mt.domain.Usuario;
import co.com.mt.servicio.TemaService;
import co.com.mt.servicio.UsuarioService;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ControladorHorarios {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private TemaService temaService;

    @Autowired
    private TemaDao temaDao;

    @Autowired
    private CategoriaTemaDao categoriaTemaDao;

    @GetMapping("/horarios")
    public String inicio(Model model, @AuthenticationPrincipal User user) {

        //Declaraciones y consultas
        //Dia
        String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        Time horaComparada = Time.valueOf(hora);

//      usuarios
        var usuarios = usuarioService.listarUsuarios();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioDao.findBycorreoUsuario(username);
        String nombre = usuario.getNombreUsuario();

        Categoria categoria = usuario.getIdCategoria();

//      Temas
        var temas = temaService.listarTemas();
        List<Tema> temasList = temaDao.findAllByidUsuario(usuario);
          
        if (!"ROLE_ADMIN".equals(categoria.getNombreCategoria()) || !"ROLE_SPECIALIST".equals(categoria.getNombreCategoria())) {

//                Temas por categoria 
            List<CategoriaTema> categoriasTemas = categoriaTemaDao.findAllByCategoria(categoria);

            if (!categoriasTemas.toString().isEmpty()) {
                List<Long> idTemas = categoriasTemas.stream()
                        .map(t -> t.getId().getIdTema())
                        .collect(Collectors.toList());

                Iterable<Tema> temasC = temaDao.findAllById(idTemas);

                model.addAttribute("temasC", temasC);

            }
        }

//      
//agregar al modelo 
//      usuarios
        model.addAttribute(
                "nombreUsu", nombre);
        model.addAttribute(
                "usuarios", usuarios);
        model.addAttribute(
                "totalUsuarios", usuarios.size());

//      Temas
        model.addAttribute(
                "temas", temas);
        model.addAttribute(
                "temasUsu", temasList);
        model.addAttribute(
                "hora", horaComparada);

        return "horarios";
    }

    @GetMapping("/cupos/{id}")
    public String getCupo(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Tema> temaid = temaDao.findById(id);
        BigDecimal cupos = temaid.orElse(null).getCuposTema();
        
        if (cupos == null || !"0".equals(cupos.toString())) {
            cupos = cupos.subtract(BigDecimal.ONE);
            Tema tema = temaid.orElse(null);
            tema.setCuposTema(cupos);

            temaDao.save(tema);
        }
        redirectAttributes.addAttribute("exito", "FELICIDADES! Ya eres parte de nuestra comunidad, disfruta de tu estadia ahora y siempre...");
        return "redirect:/app";
    }

}
