
package co.com.mt.servicio;

import co.com.mt.domain.CategoriaTema;
import java.util.List;

public interface CategoriaTemaService {
    
    public List<CategoriaTema> listarCategoriasTemas();

    public void guardar(CategoriaTema categoriaTema);

    public void eliminar(CategoriaTema categoriaTema);

    public CategoriaTema encontrarTema(CategoriaTema categoriaTema);
}
