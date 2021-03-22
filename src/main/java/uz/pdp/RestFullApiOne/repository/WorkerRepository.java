package uz.pdp.RestFullApiOne.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.RestFullApiOne.Entity.Worker;

@Repository
public interface WorkerRepository extends JpaRepository<Worker,Integer> {

    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);
    boolean existsByNameAndDepartmentId(String name, Integer department_id);
}
