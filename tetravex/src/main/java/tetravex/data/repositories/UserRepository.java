package tetravex.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tetravex.data.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
    User findById(long id);

    boolean existsUserById(long id);
    boolean existsUserByUsername(String username);
}
