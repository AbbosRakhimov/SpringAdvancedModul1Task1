package uz.pdp.payload;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class WorkerDto {
	
	@NotNull(message = "Name can not be empty")
	private String name;
	
	@NotNull(message = "PhonNumber can not be empty")
	private String phonNumber;
	
	@NotNull(message = "HomeNumber can not be empty")
	private Integer homeNumber;
	
	@NotNull(message = "Street can not be empty")
	private String street;
	
	@NotNull(message = "please enter an id for Department")
	private Integer departmentId;
}
