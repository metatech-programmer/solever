package co.com.mt.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Table(name = "categorias_temas")
@Data
public class CategoriaTema {

    @EmbeddedId
    private CategoriaTemaId id;

    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "id_categoria", insertable = false, updatable = false)
    private Categoria categoria;

    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "id_categoria", insertable = false, updatable = false)
    private Tema tema;
}
