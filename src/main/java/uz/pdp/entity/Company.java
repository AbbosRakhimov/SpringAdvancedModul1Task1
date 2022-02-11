package uz.pdp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"corpName", "address_id"}))
public class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String corpName;
	
	@Column(nullable = false)
	private String directorName;
	
	@OneToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Address address;

	public Company(String corpName, String directorName, Address address) {
		super();
		this.corpName = corpName;
		this.directorName = directorName;
		this.address = address;
	}
	
}
