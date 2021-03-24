package uz.pdp.RestFullApiOne.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.RestFullApiOne.Entity.Address;
import uz.pdp.RestFullApiOne.Entity.Department;
import uz.pdp.RestFullApiOne.Entity.Worker;
import uz.pdp.RestFullApiOne.payload.ApiResponse;
import uz.pdp.RestFullApiOne.payload.WorkerDto;
import uz.pdp.RestFullApiOne.repository.AddressRepository;
import uz.pdp.RestFullApiOne.repository.DepartmentRepository;
import uz.pdp.RestFullApiOne.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    AddressRepository addressRepository;


    public ApiResponse createWorker(WorkerDto workerDto) {
        boolean existsByPhoneNumber = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (existsByPhoneNumber)
            return new ApiResponse("This is such a phone number", false);

        boolean existsByNameAndDepartmentId = workerRepository.
                existsByNameAndDepartmentId(workerDto.getName(), workerDto.getDepartmentId());
        if (existsByNameAndDepartmentId)
            return new ApiResponse("This is such Worker name and department", false);

        boolean existsByStreet = addressRepository.existsByStreetAndHomeNumber(workerDto.getStreet(), workerDto.getHomeNumber());
        if (existsByStreet)
            return new ApiResponse("This is such a street",false);

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new ApiResponse("Department not found", false);

        Worker worker = new Worker();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setDepartment(optionalDepartment.get());

        Address address = new Address();
        address.setStreet(workerDto.getStreet());
        address.setHomeNumber(workerDto.getHomeNumber());
        Address saveAddress = addressRepository.save(address);

        worker.setAddress(saveAddress);
        workerRepository.save(worker);
        return new ApiResponse("Worker created", true);
    }


    public List<Worker> getAllWorker() {
        return workerRepository.findAll();
    }


    public Worker getWorker(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        return optionalWorker.orElse(null);
    }


    public ApiResponse editWorker(Integer id, WorkerDto workerDto) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (!optionalWorker.isPresent())
            return new ApiResponse("Worker not found",false);

        boolean byPhoneNumberAndIdNot = workerRepository
                .existsByPhoneNumberAndIdNot(workerDto.getPhoneNumber(), id);
        if (byPhoneNumberAndIdNot)
            return new ApiResponse("This is such a phone number",false);

        boolean existsByNameAndDepartmentId = workerRepository
                .existsByNameAndDepartmentId(workerDto.getName(), workerDto.getDepartmentId());
        if (existsByNameAndDepartmentId)
            return new ApiResponse("This is such Worker name and department",false);

        boolean existsByStreet = addressRepository.existsByStreetAndHomeNumberAndIdNot(workerDto.getStreet(), workerDto.getHomeNumber(), id);
        if (existsByStreet)
            return new ApiResponse("This is such a street",false);

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new ApiResponse("Department not found",false);

        Worker worker = optionalWorker.get();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setDepartment(optionalDepartment.get());

        Address address = worker.getAddress();
        address.setStreet(workerDto.getStreet());
        address.setHomeNumber(workerDto.getHomeNumber());
        Address saveAddress = addressRepository.save(address);

        worker.setAddress(saveAddress);
        workerRepository.save(worker);
        return new ApiResponse("Worker edited",true);
    }
}
