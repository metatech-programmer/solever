package co.com.mt.servicio;

import co.com.mt.dao.TemaDao;
import co.com.mt.domain.Tema;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TemaServiceImpl implements TemaService {

    @Autowired
    private TemaDao temaDao;

    @Override
    @Transactional(readOnly = true)
    public List<Tema> listarTemas() {
        return (List<Tema>) temaDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Tema tema) {
        temaDao.save(tema);
    }

    @Override
    @Transactional
    public void eliminar(Tema tema) {
        temaDao.delete(tema);
    }

    @Override
    @Transactional
    public Tema encontrarTema(Tema tema) {
        return temaDao.findById(tema.getIdTema()).orElse(null);
    }

}
