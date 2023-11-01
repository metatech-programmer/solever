package co.com.mt.dao;

import co.com.mt.domain.Categoria;
import co.com.mt.domain.CategoriaTema;
import co.com.mt.domain.CategoriaTemaId;
import co.com.mt.domain.Tema;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CategoriaTemaDao extends CrudRepository<CategoriaTema, CategoriaTemaId> {

    public List<CategoriaTema> findAllByCategoria(Categoria categoria);

    public List<CategoriaTema> findAllByTema(Tema tema);

    public List<CategoriaTema> findAllByIdIdTema(Long idTema);


}
