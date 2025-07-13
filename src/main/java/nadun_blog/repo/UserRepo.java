package nadun_blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nadun_blog.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
}
