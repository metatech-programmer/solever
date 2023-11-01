package co.com.mt.servicio;

import co.com.mt.domain.Categoria;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.com.mt.domain.Usuario;
import co.com.mt.dao.UsuarioDao;
import java.util.List;

@Service("userDetailsService")
@Slf4j
public class AccesoService implements UserDetailsService {

    private final UsuarioDao usuarioDao;

    @Autowired
    public AccesoService(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findBycorreoUsuario(username);

        if (usuario == null) {
            throw new UsernameNotFoundException(username);
        }

        Categoria categoria = usuario.getIdCategoria();

        if (categoria == null) {
            throw new UsernameNotFoundException("Usuario no tiene categoría asociada");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(categoria.getNombreCategoria()));

        return new User(usuario.getCorreoUsuario(), usuario.getPasswordUsuario(), authorities);
    }
}
