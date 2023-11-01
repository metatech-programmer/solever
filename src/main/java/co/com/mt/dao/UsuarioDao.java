package co.com.mt.dao;

import co.com.mt.domain.Categoria;
import co.com.mt.domain.Usuario;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioDao extends CrudRepository<Usuario, Long> {

    public Usuario findBycorreoUsuario(String correoUsuario);

    public List<Usuario> findAllByidCategoria(Categoria categoria);

    public boolean existsBycorreoUsuario(String correoUsuario);

    public boolean existsBynumeroDocumentoUsuario(String documentoUsuario);

    public Usuario findBynombreUsuario(String defaultUser);

}
