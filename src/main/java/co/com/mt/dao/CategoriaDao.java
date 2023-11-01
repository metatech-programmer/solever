
package co.com.mt.dao;

import co.com.mt.domain.Categoria;
import org.springframework.data.repository.CrudRepository;

public interface CategoriaDao extends CrudRepository<Categoria, Long> {
    
      public Categoria findBynombreCategoria(String nombreCatego);
}