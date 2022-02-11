package uz.pdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uz.pdp.entity.Worker;

@Repository
public interface WorkerRepository  extends JpaRepository<Worker, Integer>{

	boolean existsByPhonNumber(String phonNumber);
	
	boolean existsByPhonNumberAndIdNot(String PhonNumber, Integer id);
	
	 List<Worker> findAllByDepartment_CompanyId(Integer id);
	 
	 List<Worker> findAllByDepartmentId(Integer id);
}
