package uz.pdp.RestFullApiOne.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDto {

    @NotNull(message = "The Worker name should be not empty")
    private String name;

    @NotNull(message = "The Worker phone number should be not empty")
    private String phoneNumber;

    @NotNull(message = "The name of the house should not be empty")
    private String street;

    @NotNull(message = "The number of the house should not be empty")
    private Integer homeNumber;

    private Integer departmentId;
}
