package forum.hub.api.domain.topico;

import java.time.LocalDateTime;

public record DatosListadoTopico(Long id, String titulo, String mensaje, String nombreCurso, LocalDateTime fechaCreacion, Estado status, String autor) {

    public DatosListadoTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getNombreCurso(),topico.getFechaCreacion(), topico.getStatus(), topico.getAutor());
    }
}

