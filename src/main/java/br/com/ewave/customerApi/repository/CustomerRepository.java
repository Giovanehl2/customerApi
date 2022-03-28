package br.com.ewave.customerApi.repository;

import br.com.ewave.customerApi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "select c from Customer c join c.addresses z where z.zipcode= :zipCode")
    List<Customer> findCustomerZipCod(@Param("zipCode") String zipCode);

    @Modifying
    @Query("DELETE Customer c WHERE c.id = ?1")
    void deleteByIdWithJPQL(Long id);

}
