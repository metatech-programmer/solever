package co.com.mt.web;

import co.com.mt.dao.CategoriaDao;
import co.com.mt.dao.CategoriaTemaDao;
import co.com.mt.dao.TemaDao;
import co.com.mt.dao.UsuarioDao;
import co.com.mt.domain.Categoria;
import co.com.mt.domain.CategoriaTema;
import co.com.mt.domain.CategoriaTemaId;
import co.com.mt.domain.Tema;
import co.com.mt.domain.Usuario;
import co.com.mt.servicio.TemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import co.com.mt.servicio.UsuarioService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ControladorInicio {

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


    @Autowired
    private CategoriaDao categoriaDao;

    @Value("file:src/main/resources/static/img/")
    private Resource directorioImagenes;


    @GetMapping("/app")
    public String inicio(Model model, @AuthenticationPrincipal User user) {
//Declaraciones y consultas
//      usuarios
        var usuarios = usuarioService.listarUsuarios();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioDao.findBycorreoUsuario(username);

        Categoria especialist = categoriaDao.findBynombreCategoria("ROLE_SPECIALIST");

        List<Usuario> especialistas = usuarioDao.findAllByidCategoria(especialist);
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
                "especialistas", especialistas);
        model.addAttribute(
                "totalUsuarios", usuarios.size());

//      Temas
        model.addAttribute(
                "temas", temas);
        model.addAttribute(
                "temasUsu", temasList);

        return "app";
    }

    @PostMapping("/agregarTema")
    public String guardar(@Validated Tema tema, @RequestParam(value = "imagen", required = false) MultipartFile imagenTema, @RequestParam("usuarioValid") String usuario, @RequestParam("categoria") String categoria, RedirectAttributes redirectAttributes, Errors errores, BindingResult result) {

        if (!"defecto".equals(categoria)) {
            if (errores.hasErrors() || "null".equals(categoria) || "null".equals(usuario) || temaDao.existsBynombreTema(tema.getNombreTema()) || temaDao.existsBylinkMeetTema(tema.getLinkMeetTema())) {
                redirectAttributes.addAttribute("error", "El tema que se intento crear ya existe o falto algun parametro.");
                return "redirect:/app";
            }
        } else {
            if (errores.hasErrors() || "null".equals(usuario)) {
                redirectAttributes.addAttribute("error", "Falto algun parametro en el tema ingresado.");
                redirectAttributes.addAttribute("idTema", tema.getIdTema());
                return "redirect:/editarTema/{idTema}";
            }
        }

        Optional<Usuario> usuarioS = usuarioDao.findById(Long.valueOf(usuario));
        Usuario usuarioF = usuarioS.get();
        tema.setIdUsuario(usuarioF);

        if (!"defecto".equals(categoria)) {
            String nombreImagen = guardarImagen(imagenTema);
            tema.setImagenTema(nombreImagen);
        }

        temaService.guardar(tema);
        if (!"defecto".equals(categoria)) {
            guardarTemaCategoria(categoria, tema);
        }

        if (!categoria.equals("defecto")) {
            redirectAttributes.addAttribute("exito", "El tema se ha creado con exito, felcitaciones.");
            return "redirect:/app";
        } else {
            redirectAttributes.addAttribute("exito", "El tema se ha actualizado con exito, felcitaciones.");
            return "redirect:/editar";
        }
    }

    @GetMapping("/editar")
    public String editar(Model model, @AuthenticationPrincipal User user) {
        var usuarios = usuarioService.listarUsuarios();
        var temas = temaService.listarTemas();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioDao.findBycorreoUsuario(username);
        String nombre = usuario.getNombreUsuario();

        model.addAttribute(
                "nombreUsu", nombre);
        model.addAttribute(
                "usuarios", usuarios);
        model.addAttribute(
                "temas", temas);
        return "editar";
    }

    @GetMapping("/editarUsuario/{idUsuario}")
    public String editarUsuario(Usuario usuario, Model model) {
        usuario = usuarioService.encontrarUsuario(usuario);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuarito = usuarioDao.findBycorreoUsuario(username);
        String nombre = usuarito.getNombreUsuario();

        model.addAttribute(
                "nombreUsu", nombre);

        model.addAttribute("usuario", usuario);
        return "editarUsuario";
    }

    @GetMapping("/editarTema/{idTema}")
    public String editarTema(Tema tema, Model model) {
        tema = temaService.encontrarTema(tema);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuarito = usuarioDao.findBycorreoUsuario(username);
        String nombre = usuarito.getNombreUsuario();
        Categoria especialista = categoriaDao.findBynombreCategoria("ROLE_SPECIALIST");

        List<Usuario> especialistass = usuarioDao.findAllByidCategoria(especialista);
        model.addAttribute(
                "especialistass", especialistass);

        model.addAttribute(
                "nombreUsu", nombre);

        model.addAttribute("tema", tema);
        return "editarTema";
    }

    @GetMapping("/eliminar")
    public String eliminar(Model model, @AuthenticationPrincipal User user) {
        var usuarios = usuarioService.listarUsuarios();
        var temas = temaService.listarTemas();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioDao.findBycorreoUsuario(username);
        String nombre = usuario.getNombreUsuario();

        model.addAttribute(
                "nombreUsu", nombre);
        model.addAttribute(
                "usuarios", usuarios);
        model.addAttribute(
                "temas", temas);

        return "eliminar";
    }

    @GetMapping("/eliminarUsu")
    public String eliminarUsu(Usuario usuario, RedirectAttributes redirectAttributes) {
        usuarioService.eliminar(usuario);
        redirectAttributes.addAttribute("exito", "El usuario se ha eliminado con exito, felcitaciones.");
        return "redirect:/eliminar";
    }

    @GetMapping("/eliminarEspecialist")
    public String eliminarEspecialist(Usuario usuario, RedirectAttributes redirectAttributes) {
        List<Tema> temas = temaDao.findAllByidUsuario(usuario);
        Usuario defaul = usuarioDao.findBynombreUsuario("defaultSpecialist");
        if (!temas.isEmpty()) {
            for (Tema tema : temas) {
                tema.setIdUsuario(defaul);
                temaService.guardar(tema);
            }
            usuarioService.eliminar(usuario);
            redirectAttributes.addAttribute("exito", "El especialista se ha eliminado con exito, felcitaciones.");
            return "redirect:/eliminar";
        } else {
            usuarioService.eliminar(usuario);
            redirectAttributes.addAttribute("exito", "El especialista se ha eliminado con exito, felcitaciones.");
            return "redirect:/eliminar";
        }
    }

    @GetMapping("/eliminarTema")
    public String eliminarTema(Tema tema, RedirectAttributes redirectAttributes) throws IOException {
        List<CategoriaTema> categoriaTemas = categoriaTemaDao.findAllByIdIdTema(tema.getIdTema());

        var temaDefault = temaDao.findById(tema.getIdTema());
        Tema temaEliminar = temaDefault.get();
        if (!categoriaTemas.isEmpty()) {
            for (CategoriaTema categoriaTema : categoriaTemas) {
                categoriaTemaDao.delete(categoriaTema);
            }

            String imagen = temaEliminar.getImagenTema();
            Path pathArchivo = Paths.get(directorioImagenes.getFile().getAbsolutePath() + "/" + imagen);

            try {
                if (Files.exists(pathArchivo)) {
                    Files.deleteIfExists(pathArchivo);
                } else {
                    temaService.eliminar(temaEliminar);
                    redirectAttributes.addAttribute("exito", "El tema se ha eliminado con exito, felcitaciones.");
                    return "redirect:/eliminar";
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());

            }

            temaService.eliminar(tema);
            redirectAttributes.addAttribute("exito", "El tema se ha eliminado con exito junto con su imagen, felcitaciones.");

            return "redirect:/eliminar";
        } else {
            temaService.eliminar(tema);
            redirectAttributes.addAttribute("exito", "El tema se ha eliminado con exito... fue sencillo el trabajo, felcitaciones.");

            return "redirect:/eliminar";
        }

    }

    private String guardarImagen(MultipartFile imagen) {
        try {
            String nombreImagen = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(imagen.getOriginalFilename());

            Path rutaCompleta = Paths.get(directorioImagenes.getFile().getAbsolutePath() + "/" + nombreImagen);
            Files.write(rutaCompleta, imagen.getBytes());
            return nombreImagen;
        } catch (IOException e) {
            return null;
        }
    }

    private void guardarTemaCategoria(String categoria, Tema tema) {
        String[] categorias = categoria.split(",");
        var idTema = temaDao.findBynombreTema(tema.getNombreTema());

        for (String rol : categorias) {
            CategoriaTemaId catgoriaTID = new CategoriaTemaId();
            CategoriaTema categoTema = new CategoriaTema();
            Categoria catego = categoriaDao.findBynombreCategoria(rol);
            catgoriaTID.setIdTema(idTema.getIdTema());
            catgoriaTID.setIdCategoria(catego.getIdCategoria());
            categoTema.setId(catgoriaTID);
            categoTema.setCategoria(catego);
            categoTema.setTema(idTema);
            categoriaTemaDao.save(categoTema);
        }
    }

}
