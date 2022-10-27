package ewmmainservice.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ewmmainservice.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
