package co.com.mt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.sql.Date;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @NotEmpty
    @Column(name = "nombre_usuario", nullable = false)
    private String nombreUsuario;

    @NotEmpty
    @Column(name = "apellido_usuario", nullable = false)
    private String apellidoUsuario;

    @NotEmpty
    @Column(name = "fecha_nacimiento_usuario", nullable = false)
    private Date fechaNacimientoUsuario;

    @NotEmpty
    @Column(name = "genero_usuario", nullable = false)
    private String generoUsuario;

    @NotEmpty
    @Column(name = "numero_tel_usuario", nullable = false)
    private String numeroTelUsuario;

    @NotEmpty
    @Column(name = "numero_documento_usuario", nullable = false)
    private String numeroDocumentoUsuario;

    @NotEmpty
    @Column(name = "foto_cedula_usuario", nullable = false)
    private String fotoCedulaUsuario;

    @NotEmpty
    @Email
    @Column(name = "correo_usuario", nullable = false)
    private String correoUsuario;

    @NotEmpty
    @Column(name = "password_usuario")
    private String passwordUsuario;

    @OneToOne
    @JoinColumn(name = "id_categoria")
    private Categoria idCategoria;

}
