package udpm.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import udpm.fpt.model.Flavor;

/**
 *
 * @author NONG HOANG VU
 */
@Repository
public interface IFlavor extends JpaRepository<Flavor, Integer>{

}
