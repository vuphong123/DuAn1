package udpm.fpt.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import udpm.fpt.model.UserDetails;

/**
 *
 * @author BinhQuoc
 */
public interface IUserDetails extends JpaRepository<UserDetails, Integer>{
    UserDetails findByUser_Username(String username);
    
    @Query("SELECT ud FROM UserDetails ud JOIN FETCH ud.user u WHERE u.username LIKE %:query% OR ud.fullname LIKE %:query%")
    List<UserDetails> findByQuery(@Param("query") String query);

    @Query("SELECT ud FROM  UserDetails ud WHERE ud.email LIKE :email")
    UserDetails findByEmail(@Param("email") String email);
}
