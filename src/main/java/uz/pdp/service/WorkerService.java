package uz.pdp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uz.pdp.entity.Address;
import uz.pdp.entity.Department;
import uz.pdp.entity.Worker;
import uz.pdp.payload.ResponseClass;
import uz.pdp.payload.WorkerDto;
import uz.pdp.repository.AddressRepository;
import uz.pdp.repository.DepartmentRepository;
import uz.pdp.repository.WorkerRepository;

@Service
public class WorkerService {

	@Autowired
	WorkerRepository workerRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	DepartmentRepository departmentRepository;
	
	public ResponseClass addWorker(WorkerDto workerDto) {
		if(workerRepository.existsByPhonNumber(workerDto.getPhonNumber()))
			return new ResponseClass("Worker already exist", false);
		Optional<Department> dOptional = departmentRepository.findById(workerDto.getDepartmentId());
		if(!dOptional.isPresent())
			return new ResponseClass("Department not found", false);
		Address address = new Address(workerDto.getStreet(), workerDto.getHomeNumber());
		Address address2 = addressRepository.save(address);
		Worker worker = new Worker(workerDto.getName(), workerDto.getPhonNumber(), address2, dOptional.get());
		workerRepository.save(worker);
		return new ResponseClass("Worker successfuly added", true);
	}
	public List<Worker> getWorkers(){
		return workerRepository.findAll();
	}
	public ResponseClass getWorker(Integer id) {
		Optional<Worker> wOptional = workerRepository.findById(id);
		if(wOptional.isPresent()) {
			return new ResponseClass("Worker exist", true, wOptional.get());
		}
		return new ResponseClass("Worker not found", false);
	}
	public List<Worker> getAllWorkerByDepartmentId(Integer id){
		return workerRepository.findAllByDepartmentId(id);
	}
	public List<Worker> getAllWorkerByCompanyId(Integer id){
		return workerRepository.findAllByDepartment_CompanyId(id);
	}
	public ResponseClass editWorker(Integer id, WorkerDto workerDto) {
		if(workerRepository.existsByPhonNumberAndIdNot(workerDto.getPhonNumber(), id))
			return new ResponseClass("Worker already exist", false);
		Optional<Worker> wOptional = workerRepository.findById(id);
		if(!wOptional.isPresent())
			return new ResponseClass("Worker not found", false);
		Optional< Address> aOptional = addressRepository.findById(id);
		if(aOptional.isPresent())
			return new ResponseClass("Address not found", false);
		Optional<Department> dOptional = departmentRepository.findById(workerDto.getDepartmentId());
		if(!dOptional.isPresent())
			return new ResponseClass("Department not found", false);
		Address address = aOptional.get();
		address.setHomeNumber(workerDto.getHomeNumber());
		address.setStreet(workerDto.getStreet());
		Address address2= addressRepository.save(address);
		Worker worker = wOptional.get();
		worker.setAddress(address2);
		worker.setDepartment(dOptional.get());
		worker.setName(workerDto.getName());
		worker.setPhonNumber(workerDto.getPhonNumber());
		workerRepository.save(worker);
		return new ResponseClass("Worker successfuly edited", true);
	}
	public ResponseClass deleteWorker(Integer id) {
		Optional< Worker> wOptional = workerRepository.findById(id);
		if(wOptional.isPresent()) {
			workerRepository.deleteById(id);
			return new ResponseClass("Worker successfuly deleted", true);
		}
		return new ResponseClass("Worker not found", false);
	}
}
