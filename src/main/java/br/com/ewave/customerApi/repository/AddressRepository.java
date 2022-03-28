package br.com.ewave.customerApi.repository;

import br.com.ewave.customerApi.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
