package uz.pdp.payload;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DepartmentDto {
	
	@NotNull(message = "Name can not be empty")
	private String name;
	
	@NotNull(message = "CompanyId can not be empty")
	private Integer companyId;
}
