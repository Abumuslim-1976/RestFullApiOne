package uz.pdp.RestFullApiOne.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {

    @NotNull(message = "The corp name should be not empty")
    private String corpName;

    @NotNull(message = "The director name should be not empty")
    private String directorName;

    @NotNull(message = "The name of the house should not be empty")
    private String street;

    @NotNull(message = "The number of the house should not be empty")
    private Integer homeNumber;
}
