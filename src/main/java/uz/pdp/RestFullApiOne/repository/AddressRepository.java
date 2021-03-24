package uz.pdp.RestFullApiOne.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.RestFullApiOne.Entity.Address;

public interface AddressRepository extends JpaRepository<Address,Integer> {


    boolean existsByStreetAndHomeNumber(String street, Integer homeNumber);
    boolean existsByStreetAndHomeNumberAndIdNot(String street, Integer homeNumber, Integer id);

}
