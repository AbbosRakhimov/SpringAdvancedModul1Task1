package uz.pdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uz.pdp.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

	boolean existsByNameAndCompanyId(String name, Integer id);
	
	boolean existsByNameAndCompanyIdAndIdNot(String name, Integer companyid, Integer id);
	
	List<Department> findAllByCompanyId(Integer id);
}
