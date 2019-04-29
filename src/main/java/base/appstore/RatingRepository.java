package base.appstore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT appId, userId FROM Rating u WHERE appId = ?1 and userId = ?2")
    List<Rating> findByAppAndUserId(Long appId, Long userId);

}
