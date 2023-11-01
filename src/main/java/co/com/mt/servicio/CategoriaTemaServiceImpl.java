package co.com.mt.servicio;

import co.com.mt.dao.CategoriaTemaDao;
import co.com.mt.domain.CategoriaTema;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaTemaServiceImpl implements CategoriaTemaService {

    @Autowired
    private CategoriaTemaDao categoriaTemaDao;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaTema> listarCategoriasTemas() {
        return (List<CategoriaTema>) categoriaTemaDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(CategoriaTema categoriaTema) {
        categoriaTemaDao.save(categoriaTema);
    }

    @Override
    @Transactional
    public void eliminar(CategoriaTema categoriaTema) {
        categoriaTemaDao.delete(categoriaTema);
    }

    @Override
    @Transactional
    public CategoriaTema encontrarTema(CategoriaTema categoriaTema) {
        return categoriaTemaDao.findById(categoriaTema.getId()).orElse(null);
    }
}
