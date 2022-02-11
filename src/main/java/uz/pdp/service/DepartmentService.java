package uz.pdp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uz.pdp.entity.Company;
import uz.pdp.entity.Department;
import uz.pdp.payload.DepartmentDto;
import uz.pdp.payload.ResponseClass;
import uz.pdp.repository.CompanyRepository;
import uz.pdp.repository.DepartmentRepository;

@Service
public class DepartmentService {

	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	DepartmentRepository departmentRepository;
	
	public ResponseClass addedDepartment(DepartmentDto departmentDto) {
		if(departmentRepository.existsByNameAndCompanyId(departmentDto.getName(), departmentDto.getCompanyId()))
			return new ResponseClass("Department already exist", false);
		Optional<Company> cOptional = companyRepository.findById(departmentDto.getCompanyId());
		if(!cOptional.isPresent())
			return new ResponseClass("Company not found", false);
		Department department = new Department(departmentDto.getName(), cOptional.get());
		departmentRepository.save(department);
		return new ResponseClass("Department successfuly added", true);
	}
	public List<Department> geDepartments(){
		return departmentRepository.findAll();
	}
	public ResponseClass getDepartment(Integer id) {
		Optional<Department> dOptional = departmentRepository.findById(id);
		if(dOptional.isPresent()) {
			return new ResponseClass("Department exist", true, dOptional.get());
		}
		return new ResponseClass("Department not found", false);
	}
	public List<Department> getAllDepartmentByCompanyId(Integer id){
		return departmentRepository.findAllByCompanyId(id);
	}
	public ResponseClass editDepartment(Integer id, DepartmentDto departmentDto) {
		if(departmentRepository.existsByNameAndCompanyIdAndIdNot(departmentDto.getName(), departmentDto.getCompanyId(), id))
			return new ResponseClass("Department already exist", false);
		Optional<Company> cOptional = companyRepository.findById(departmentDto.getCompanyId());
		if(!cOptional.isPresent())
			return new ResponseClass("Company not found", false);
		Optional<Department> dOptional = departmentRepository.findById(id);
		if(!dOptional.isPresent())
			return new ResponseClass("Department not found", false);
		Department department = dOptional.get();
		department.setCompany(cOptional.get());
		department.setName(departmentDto.getName());
		departmentRepository.save(department);
		return new ResponseClass("Department successfuly edited", true);
	}
	public ResponseClass deletDepartment(Integer id) {
		Optional<Department> dOptional = departmentRepository.findById(id);
		if(dOptional.isPresent()) {
			departmentRepository.deleteById(id);
			return new ResponseClass("Department successfuly deleted", true);
		}
		return new ResponseClass("Department not found", false);
	}
}
