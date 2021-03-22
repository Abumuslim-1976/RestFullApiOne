package uz.pdp.RestFullApiOne.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {

    @NotNull(message = "The department name should be not empty")
    private String name;

    @NotNull(message = "The company should be not empty")
    private Integer companyId;
}
