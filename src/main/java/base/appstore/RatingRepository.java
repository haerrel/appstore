package base.appstore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT appId, userId FROM Rating WHERE appId = :appId and userId = :userId")
    List<Rating> findByAppAndUserId(@Param("appId") Long appId, @Param("userId") Long userId);

}
