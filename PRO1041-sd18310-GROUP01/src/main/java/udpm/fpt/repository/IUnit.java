package udpm.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import udpm.fpt.model.Unit;

/**
 *
 * @author NONG HOANG VU
 */
@Repository
public interface IUnit extends JpaRepository<Unit, Integer> {
}
