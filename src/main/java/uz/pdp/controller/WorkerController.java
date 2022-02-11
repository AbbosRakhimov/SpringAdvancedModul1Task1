package uz.pdp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import uz.pdp.entity.Worker;
import uz.pdp.payload.ResponseClass;
import uz.pdp.payload.WorkerDto;
import uz.pdp.service.WorkerService;

@RestController
@RequestMapping(value = "/worker")
public class WorkerController {

	@Autowired
	WorkerService workerService;
	
	@PostMapping 
	public HttpEntity<?> addWorker(@Valid @RequestBody WorkerDto workerDto) {
		ResponseClass responseClass = workerService.addWorker(workerDto);
		return ResponseEntity.status(responseClass.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(responseClass);
	}
	@GetMapping
	public HttpEntity<?> getWorkers(){
		List<Worker> getList= workerService.getWorkers();
		return ResponseEntity.status(HttpStatus.OK).body(getList);
	}
	@GetMapping(value = "/{id}")
	public HttpEntity<?> gtWorker(@PathVariable Integer id) {
		ResponseClass responseClass = workerService.getWorker(id);
		return ResponseEntity.status(responseClass.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(responseClass);
	}
	@GetMapping(value = "/department/{id}")
	public HttpEntity<?> getWorkerByDepartmentId(@PathVariable Integer id){
		List<Worker> getWorkers = workerService.getAllWorkerByDepartmentId(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(getWorkers);
	}
	@GetMapping(value = "/company/{id}")
	public HttpEntity<?> getWorkerByCompanyId(@PathVariable Integer id){
		List<Worker> getWorkers = workerService.getAllWorkerByCompanyId(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(getWorkers);
	}
	@PutMapping(value = "/{id}")
	public HttpEntity<?> editWorker(@PathVariable Integer id, @Valid @RequestBody WorkerDto workerDto){
		ResponseClass responseClass = workerService.editWorker(id, workerDto);
		return ResponseEntity.status(responseClass.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(responseClass);
	}
	@DeleteMapping(value = "/{id}")
	public HttpEntity<?> deleteWorker(@PathVariable Integer id){
		ResponseClass responseClass = workerService.deleteWorker(id);
		return ResponseEntity.status(responseClass.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(responseClass);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
}
