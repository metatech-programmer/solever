package co.com.mt.dao;

import co.com.mt.domain.Tema;
import co.com.mt.domain.Usuario;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface TemaDao extends CrudRepository<Tema, Long> {

    public Tema findByidUsuario(Usuario usuario);
    
    public Tema findBynombreTema(String nombreTema);

    public List<Tema> findAllByidUsuario(Usuario usuario);

    public boolean existsBynombreTema(String nombreTema);

    public boolean existsBylinkMeetTema(String linkMeetTema);
}
