package tetravex.server.security.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tetravex.data.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);
    User findById(long id);
    boolean existsUserById(long id);
    boolean existsUserByUsername(String username);
}