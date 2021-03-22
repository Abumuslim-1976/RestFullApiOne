package uz.pdp.RestFullApiOne.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.RestFullApiOne.Entity.Company;
import uz.pdp.RestFullApiOne.Entity.Department;
import uz.pdp.RestFullApiOne.payload.ApiResponse;
import uz.pdp.RestFullApiOne.payload.DepartmentDto;
import uz.pdp.RestFullApiOne.repository.CompanyRepository;
import uz.pdp.RestFullApiOne.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    CompanyRepository companyRepository;


    public ApiResponse createDepartment(DepartmentDto departmentDto) {
        boolean byNameAndCompanyId = departmentRepository
                .existsByNameAndCompanyId(departmentDto.getName(), departmentDto.getCompanyId());
        if (byNameAndCompanyId)
            return new ApiResponse("This is such department and company", false);

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new ApiResponse("Company not found", false);

        Department department = new Department();
        department.setName(departmentDto.getName());
        department.setCompany(optionalCompany.get());
        departmentRepository.save(department);
        return new ApiResponse("Department created", true);
    }


    public List<Department> getAllDep() {
        return departmentRepository.findAll();
    }


    public Department getDep(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElseGet(Department::new);
    }


    public ApiResponse deleteDepartment(Integer id) {
        try {
            departmentRepository.deleteById(id);
        } catch (Exception e) {
            return new ApiResponse("ERROR !!! , Department not found", false);
        }
        return new ApiResponse("Department deleted", true);
    }


    public ApiResponse editDepartment(Integer id, DepartmentDto departmentDto) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent())
            return new ApiResponse("Department not found", false);

        boolean exists = departmentRepository.
                existsByNameAndCompanyId(departmentDto.getName(), departmentDto.getCompanyId());
        if (exists)
            return new ApiResponse("This is such department name and company", false);

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new ApiResponse("Company not found !!!", false);

        Department department = optionalDepartment.get();
        department.setName(departmentDto.getName());
        department.setCompany(optionalCompany.get());
        departmentRepository.save(department);
        return new ApiResponse("Department edited", true);
    }

}
