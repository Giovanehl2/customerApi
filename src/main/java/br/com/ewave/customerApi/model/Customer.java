package br.com.ewave.customerApi.model;

import java.io.Serializable;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "customers")
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
public class Customer implements Serializable {
	
	@Id
	@GeneratedValue
	@Setter(AccessLevel.NONE)
	private Long id;

	@Schema(example = "45353545", required = true)
	private Long documentId;

	@Schema(example = "Nome", required = true)
	@Size(max = 100)
	private String name;

	@NotNull
	@Schema(example = "45", required = true)
	private Integer age; /*geralmente data de nascimento que é pesistida na base de dados*/

	@Schema(example = "yyyy/MM/dd HH:mm:ss", required = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date registrationDate;

	@Schema(example = "yyyy/MM/dd HH:mm:ss", required = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;

@ManyToMany(fetch = FetchType.LAZY ,cascade = {CascadeType.ALL})
@JoinTable(
		name = "customer_address",
		joinColumns = { @JoinColumn(name = "customer_id") },
		inverseJoinColumns = { @JoinColumn(name = "address_id") })
    @Schema(required = true)
	private Set<Address> addresses = new HashSet<>();

}
