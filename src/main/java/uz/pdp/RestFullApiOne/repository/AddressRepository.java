package uz.pdp.RestFullApiOne.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.RestFullApiOne.Entity.Address;

public interface AddressRepository extends JpaRepository<Address,Integer> {

    boolean existsByStreet(String street);

    boolean existsByStreetAndIdNot(String street, Integer id);
}
