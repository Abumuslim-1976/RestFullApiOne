package uz.pdp.RestFullApiOne.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.RestFullApiOne.Entity.Address;
import uz.pdp.RestFullApiOne.Entity.Company;
import uz.pdp.RestFullApiOne.payload.ApiResponse;
import uz.pdp.RestFullApiOne.payload.CompanyDto;
import uz.pdp.RestFullApiOne.repository.AddressRepository;
import uz.pdp.RestFullApiOne.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    AddressRepository addressRepository;


    public List<Company> getCompany() {
        return companyRepository.findAll();
    }


    public Company getOneCompany(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.orElseGet(Company::new);
    }


    public ApiResponse createCompany(CompanyDto companyDto) {
        boolean existsByCorpName = companyRepository.existsByCorpName(companyDto.getCorpName());
        if (existsByCorpName)
            return new ApiResponse("There is such corp name", false);

        boolean existsByStreet = addressRepository.existsByStreetAndHomeNumber(companyDto.getStreet(), companyDto.getHomeNumber());
        if (existsByStreet)
            return new ApiResponse("There is such a street name", false);

        Address address = new Address();
        address.setStreet(companyDto.getStreet());
        address.setHomeNumber(companyDto.getHomeNumber());
        Address saveAddress = addressRepository.save(address);

        Company company = new Company();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        company.setAddress(saveAddress);
        companyRepository.save(company);
        return new ApiResponse("Company created", true);
    }


    public ApiResponse editCompany(Integer id, CompanyDto companyDto) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent())
            return new ApiResponse("This is no such company", false);

        boolean corpNameAndIdNot = companyRepository.existsByCorpNameAndIdNot(companyDto.getCorpName(), id);
        if (corpNameAndIdNot)
            return new ApiResponse("This is such a corp name", false);

        boolean existsByStreet = addressRepository.existsByStreetAndHomeNumberAndIdNot(companyDto.getStreet(), companyDto.getHomeNumber(), id);
        if (existsByStreet)
            return new ApiResponse("This is such a street name", false);

        Company company = optionalCompany.get();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());

        Address address = company.getAddress();
        address.setStreet(companyDto.getStreet());
        address.setHomeNumber(companyDto.getHomeNumber());
        Address saveAddress = addressRepository.save(address);

        company.setAddress(saveAddress);
        companyRepository.save(company);
        return new ApiResponse("Company edited", true);
    }

}
