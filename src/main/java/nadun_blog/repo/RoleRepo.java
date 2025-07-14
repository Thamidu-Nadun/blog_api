package nadun_blog.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nadun_blog.model.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, UUID> {

}
