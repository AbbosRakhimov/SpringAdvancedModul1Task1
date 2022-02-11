package uz.pdp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Worker {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, unique =  true)
	private String phonNumber;
	
	@OneToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Address address;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Department department;

	public Worker(String name, String phonNumber, Address address, Department department) {
		super();
		this.name = name;
		this.phonNumber = phonNumber;
		this.address = address;
		this.department = department;
	}
	
}
