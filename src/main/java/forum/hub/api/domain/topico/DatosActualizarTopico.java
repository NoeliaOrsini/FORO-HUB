package forum.hub.api.domain.topico;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(

        @NotNull
        Long id,

        //@NotBlank
        String titulo,

       // @NotBlank
        String mensaje,

        //@NotBlank
        String nombreCurso,

       // @NotNull
        Estado status,

        //@NotBlank
        String autor
) {
}
