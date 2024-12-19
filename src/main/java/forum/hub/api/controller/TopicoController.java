package forum.hub.api.controller;

import forum.hub.api.domain.topico.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;


@RestController
@RequestMapping("/topicos")
@Validated

public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    public ResponseEntity<?> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
                                             UriComponentsBuilder uriComponentsBuilder) {
        // Verificar si ya existe un tópico con el mismo título y mensaje
        Optional<Topico> topicoExistente = topicoRepository.findByTituloAndMensaje(datosRegistroTopico.titulo(), datosRegistroTopico.mensaje());

        if (topicoExistente.isPresent()) {
            // Retornar un mensaje de error con un código 409 Conflict
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Tópico existente con el mismo título y mensaje.");
        }

        Topico topico = topicoRepository.save(new Topico(datosRegistroTopico));

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(),
                topico.getNombreCurso(), topico.getFechaCreacion(), topico.getStatus().toString(), topico.getAutor());

        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }


    //Para el listado de topicos

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopicos(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findByActivoTrue(paginacion).map(DatosListadoTopico::new));
    }

    @PutMapping("/{id}") // se agrego para modificar con id y mensaje si no se encuentra el topico
    @Transactional
    public ResponseEntity<?> actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id); // Usar findById que retorna un Optional
        if (optionalTopico.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tópico inexistente"); // Retornar 404 con mensaje
        }

        Topico topico = optionalTopico.get(); // Obtener el tópico
        topico.actualizarDatos(datosActualizarTopico);

        DatosRespuestaTopico respuesta = new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getNombreCurso(),
                topico.getFechaCreacion(),
                topico.getStatus().toString(),
                topico.getAutor()
        );
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> eliminarTopico(@PathVariable Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id); // Usar findById que retorna un Optional
        if (optionalTopico.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tópico inexistente"); // Retornar 404 con mensaje
        }

        Topico topico = optionalTopico.get(); // Obtener el tópico
        topico.desactivarTopico(); // Desactivar el tópico

        return ResponseEntity.ok("Tópico borrado con éxito");
    }
    //buscar topico por id

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> retornaDatosTopico(@PathVariable Long id) {

        Topico topico = topicoRepository.getReferenceById(id);
        var datosTopico = new DatosRespuestaTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(),
                topico.getNombreCurso(),topico.getFechaCreacion(),
                topico.getStatus().toString(), topico.getAutor());
        return ResponseEntity.ok(datosTopico);
    }

}




