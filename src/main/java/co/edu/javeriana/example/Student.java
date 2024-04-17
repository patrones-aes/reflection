package co.edu.javeriana.example;

import co.edu.javeriana.annotation.Column;
import co.edu.javeriana.annotation.Table;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "students")
public class Student {
    @Column(name = "id", primaryKey = true)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
}
