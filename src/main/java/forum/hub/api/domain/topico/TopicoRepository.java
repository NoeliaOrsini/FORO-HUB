package forum.hub.api.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// que solo muestre los tópicos activos

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findByActivoTrue(Pageable paginacion);
    Optional<Topico> findByTituloAndMensaje(String titulo, String mensaje);//agregado para verificar que no existan duplicados
}

