package co.com.mt.servicio;

import co.com.mt.domain.Tema;
import java.util.List;

public interface TemaService {

    public List<Tema> listarTemas();

    public void guardar(Tema tema);

    public void eliminar(Tema tema);

    public Tema encontrarTema(Tema tema);
}
