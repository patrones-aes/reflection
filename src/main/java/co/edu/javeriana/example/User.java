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
@Table(name = "users")
public class User {
    @Column(name = "id", primaryKey = true)
    private String id;

    @Column(name = "first_name")
    private String firstName;
}
