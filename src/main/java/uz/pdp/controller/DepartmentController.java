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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import uz.pdp.entity.Department;
import uz.pdp.payload.DepartmentDto;
import uz.pdp.payload.ResponseClass;
import uz.pdp.service.DepartmentService;

@RestController
@RequestMapping(value = "/department")
public class DepartmentController {

	@Autowired
	DepartmentService departmentService;
	
	@PostMapping
	public HttpEntity<?> addDepartment(@Valid @RequestBody DepartmentDto departmentDto){
		ResponseClass responseClass = departmentService.addedDepartment(departmentDto);
		return ResponseEntity.status(responseClass.isSuccess()?HttpStatus.OK:HttpStatus.BAD_REQUEST).body(responseClass);
	}
	@GetMapping
	public HttpEntity<?> getDepartments(){
		List<Department> getDepartments = departmentService.geDepartments();
		return ResponseEntity.status(HttpStatus.OK).body(getDepartments);
	}
	@GetMapping(value = "/{id}")
	public HttpEntity<ResponseClass> getDepartment(@PathVariable Integer id){
		ResponseClass responseClass = departmentService.getDepartment(id);
		return ResponseEntity.status(responseClass.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(responseClass);
	}
	@GetMapping(value = "/company/{id}")
	public HttpEntity<?> getAllDepartmentByCompanyId(@PathVariable Integer id){
		List<Department> geDepartments = departmentService.getAllDepartmentByCompanyId(id);
		return ResponseEntity.status(HttpStatus.OK).body(geDepartments);
	}
	@PutMapping(value = "/{id}")
	public HttpEntity<?> editDepartmet(@PathVariable Integer id, @Valid @RequestBody DepartmentDto departmentDto){
		ResponseClass responseClass = departmentService.editDepartment(id, departmentDto);
		return ResponseEntity.status(responseClass.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(responseClass);
	}
	@DeleteMapping(value = "/{id}")
	public HttpEntity<?> deleteDepartment(@PathVariable Integer id){
		ResponseClass responseClass = departmentService.deletDepartment(id);
		return ResponseEntity.status(responseClass.isSuccess()? HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(responseClass);
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
