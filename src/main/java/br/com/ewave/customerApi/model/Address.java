package br.com.ewave.customerApi.model;



import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "addresses")
public class Address implements Serializable {

	@Id
	@GeneratedValue
	@Setter(AccessLevel.NONE)
	private long id;
	

	@Schema(example = "99999-999", required = true)
	@Pattern(regexp = "[0-9]{5}-[\\d]{3}", message = "zipCod invalid")
	private String zipcode;

	@Schema(example = "1", required = true, type = "Integer")
	@Size(min = 1)
	private Integer number;

	@ManyToMany(fetch = FetchType.LAZY ,cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "addresses")
	@JsonIgnoreProperties
	@JsonIgnore
	private Set<Customer> consumers = new HashSet<>();
}
