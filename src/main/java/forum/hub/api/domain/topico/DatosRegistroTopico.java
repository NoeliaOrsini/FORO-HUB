package forum.hub.api.domain.topico;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistroTopico(

        @NotBlank(message = "El título es obligatorio") //agregue mensajes de alerta
        String titulo,

        @NotBlank(message = "El mensaje es obligatorio")
        String mensaje,

        @NotBlank(message = "El nombre del curso es obligatorio")
        String nombreCurso,

        // Elimino el campo fechaCreacion, ya que se asignará automáticamente
        // @NotNull  // No es necesario
        // @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // No es necesario
        // LocalDateTime fechaCreacion,

       // @NotNull
       // Estado status, // El estado se asigna automáticamente como ABIERTO en el constructor de Topico

        @NotBlank(message = "El autor es obligatorio")
        String autor
) {
}

