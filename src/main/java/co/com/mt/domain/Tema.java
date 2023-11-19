package co.com.mt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Table(name = "temas")
@Data
public class Tema implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tema")
    private Long idTema;

    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario idUsuario;

    @NotEmpty
    @Column(name = "nombre_tema", nullable = false)
    private String nombreTema;
    
    @NotEmpty
    @Column(name = "cupos_tema", nullable = false)
    private BigDecimal cuposTema;
    
    @NotEmpty
    @DateTimeFormat(pattern = "HH:mm:ss") 
    @Column(name = "hora_tema", nullable = false)
    private Time horaTema;
    
    @NotEmpty
    @Column(name = "descripcion_tema", nullable = false)
    private String descripcionTema;
    
    @NotEmpty
    @Column(name = "link_meet_tema", nullable = false)
    private String linkMeetTema;

    @NotEmpty
    @Column(name = "imagen_tema", nullable = false)
    private String imagenTema;

}
