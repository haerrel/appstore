package base.appstore;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<App, Long> {
}
