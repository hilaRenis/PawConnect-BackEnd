package nl.fontys.pawconnect.persistence.interf;

import nl.fontys.pawconnect.persistence.entity.AnnouncementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(exported = false)
public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, String> {
    @Query("""
        SELECT a
        FROM AnnouncementEntity a
        WHERE (:userRole = 'USER_AGENCY')
           OR (:userRole = 'USER_NORMAL' AND
               (a.announcer.id = :userId OR a.announcer.role = 'USER_AGENCY'))
        """)
    List<AnnouncementEntity> findAnnouncementsByUserRoleAndUserId(
            @Param("userRole") String userRole,
            @Param("userId") String userId);
}
