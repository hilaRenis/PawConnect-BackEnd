package nl.fontys.pawconnect.persistence.interf;

import nl.fontys.pawconnect.persistence.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RepositoryRestResource(exported = false)
public interface ImageRepository extends JpaRepository<ImageEntity, String> {
    Optional<ImageEntity> findByUrl(String url);
}
