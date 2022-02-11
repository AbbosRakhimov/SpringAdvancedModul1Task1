package uz.pdp.payload;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CompanyDto {
	
	@NotNull(message = "DirectorName can not be empty")
	private String directorName;
	
	@NotNull(message = "CorpName can not be empty")
	private String corpName;
	
	@NotNull(message = "HomeNumber can not be empty")
	private Integer homeNumber;
	
	@NotNull(message = "Street can not be empty")
	private String street;
}
