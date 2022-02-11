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

import uz.pdp.entity.Company;
import uz.pdp.payload.CompanyDto;
import uz.pdp.payload.ResponseClass;
import uz.pdp.service.CompanyService;

@RestController
@RequestMapping(value = "/company")
public class CompanyController {

	@Autowired
	CompanyService companyService;
	
	@PostMapping
	public HttpEntity<?> addCompany(@Valid @RequestBody CompanyDto companyDto){
		ResponseClass responseClass = companyService.addCompany(companyDto);
		return ResponseEntity.status(responseClass.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(responseClass);
	}
	@GetMapping
	public HttpEntity<?> getCompanys(){
		List<Company> companies=companyService.getCompanies();
		return ResponseEntity.status(HttpStatus.OK).body(companies);
	}
	@GetMapping(value = "/{id}")
	public HttpEntity<ResponseClass> getCompany(@PathVariable Integer id){
		ResponseClass responseClass = companyService.getCompany(id);
		return ResponseEntity.status(responseClass.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(responseClass);
	}
	@PutMapping(value = "/{id}")
	public HttpEntity<ResponseClass> editCompany(@PathVariable Integer id, @Valid @RequestBody CompanyDto companyDto){
		ResponseClass responseClass = companyService.editCompany(id, companyDto);
		return ResponseEntity.status(responseClass.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(responseClass);
	}
	@DeleteMapping("/{id}")
	public HttpEntity<ResponseClass> deletCompany(@PathVariable Integer id){
		ResponseClass responseClass = companyService.deleteCompany(id);
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
