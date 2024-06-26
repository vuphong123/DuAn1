package udpm.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import udpm.fpt.model.PackagingSpecification;

/**
 *
 * @author NONG HOANG VU
 */
@Repository
public interface IPackagingSpecification extends JpaRepository<PackagingSpecification, Integer> {
}
