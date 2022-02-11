package uz.pdp.payload;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@NotNull
@AllArgsConstructor
@Data
public class ResponseClass {

private String message;
	
	private boolean success;
	
	Object object;

	public ResponseClass(String message, boolean success) {
		super();
		this.message = message;
		this.success = success;
	}
}
