package nl.fontys.pawconnect.persistence.interf;

import nl.fontys.pawconnect.persistence.entity.AnnouncementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false)
public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, String> {

}