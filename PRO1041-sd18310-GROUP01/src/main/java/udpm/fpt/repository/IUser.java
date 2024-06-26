package udpm.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import udpm.fpt.model.User;

/**
 *
 * @author NONG HOANG VU
 */
public interface IUser extends JpaRepository<User, Integer> {

    User findByUsernameAndPassword(String username, String password);
}
