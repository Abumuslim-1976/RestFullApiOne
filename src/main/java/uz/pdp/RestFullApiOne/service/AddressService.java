package uz.pdp.RestFullApiOne.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.RestFullApiOne.Entity.Address;
import uz.pdp.RestFullApiOne.payload.AddressDto;
import uz.pdp.RestFullApiOne.payload.ApiResponse;
import uz.pdp.RestFullApiOne.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public ApiResponse createAddress(AddressDto addressDto) {
        boolean exists = addressRepository.existsByStreet(addressDto.getStreet());
        if (exists)
            return new ApiResponse("Bunday uy nomi mavjud", false);

        Address address = new Address();
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("Address saqlandi", true);
    }


    public List<Address> getAddress() {
        return addressRepository.findAll();
    }


    public Address getAdd(Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        return optionalAddress.orElseGet(Address::new);
    }


    public ApiResponse deleteAddress(Integer id) {
        try {
            addressRepository.deleteById(id);
        } catch (Exception e) {
            return new ApiResponse("Bunday address yo`q", false);
        }
        return new ApiResponse("Address o`chirildi", true);
    }


    public ApiResponse editAddress(Integer id, AddressDto addressDto) {
        boolean exists = addressRepository.existsByStreetAndIdNot(addressDto.getStreet(), id);
        if (exists)
            return new ApiResponse("Bunday uy nomi mavjud", false);

        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (!optionalAddress.isPresent())
            return new ApiResponse("Bunday Address mavjud emas , tahrirlay olmaysiz",false);

        Address address = optionalAddress.get();
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("Address tahrirlandi",true);
    }

}
