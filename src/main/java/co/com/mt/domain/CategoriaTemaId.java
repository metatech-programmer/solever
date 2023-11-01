package co.com.mt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import lombok.Data;

@Embeddable
@Data
public class CategoriaTemaId implements Serializable {

    public static final long serialVersionUID = 1L;

    @NotEmpty
    @Column(name = "id_categoria")
    private Long idCategoria;

    @NotEmpty
    @Column(name = "id_tema")
    private Long idTema;

}
