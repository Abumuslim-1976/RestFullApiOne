package uz.pdp.RestFullApiOne.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "home_number",columnNames = {"street","home_number"}))
public class Address{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false,name = "home_number")
    private Integer homeNumber;
}
