package nadun_blog.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nadun_blog.model.Permissions;

@Repository
public interface PermissionRepo extends JpaRepository<Permissions, Integer> {
    Optional<Permissions> findByName(String name);
}
