package forum.hub.api.domain.topico;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "topicos")
@Entity(name = "topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;

    @Column(name = "nombre_curso")
    private String nombreCurso;

    @Column(name = "fecha_creacion")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // Formato de la fecha
    private LocalDateTime fechaCreacion; // Se asigna automáticamente en el constructor

    private Boolean activo; //  para mostrar en listado solo topicos activos

    @Enumerated(EnumType.STRING)
    private Estado status;

    private String autor;

    // Constructor que recibe un objeto DatosRegistroTopico
    public Topico(DatosRegistroTopico datosRegistroTopico) {
        this.activo = true;
        this.titulo = datosRegistroTopico.titulo();
        this.mensaje = datosRegistroTopico.mensaje();
        this.nombreCurso = datosRegistroTopico.nombreCurso();
       // this.fechaCreacion = LocalDateTime.now(); // Asigna la fecha actual
         // lo agregué porque a veces insomnia me daba los manosegundos
        this.fechaCreacion = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
       // this.status = datosRegistroTopico.status();
        this.status = Estado.ABIERTO; // Asignar el estado por defecto
        this.autor = datosRegistroTopico.autor();
    }

    public void actualizarDatos(DatosActualizarTopico datosActualizarTopico) {
        if (datosActualizarTopico.titulo() != null) {
            this.titulo = datosActualizarTopico.titulo();
        }
        if (datosActualizarTopico.mensaje() != null) {
            this.mensaje = datosActualizarTopico.mensaje();
        }
        if (datosActualizarTopico.nombreCurso() != null) {
            this.nombreCurso = datosActualizarTopico.nombreCurso();
        }
        if (datosActualizarTopico.status() != null) {
            this.status = datosActualizarTopico.status();
        }
        if (datosActualizarTopico.autor() != null) {
            this.autor = datosActualizarTopico.autor();
        }
    }

    public void desactivarTopico() {
        this.activo = false;
    }
}