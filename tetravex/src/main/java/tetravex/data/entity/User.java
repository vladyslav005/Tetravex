package tetravex.data.entity;

import jakarta.persistence.*;
import lombok.Data;

//TODO : validations

@Data
@Entity
@Table(name = "user_account")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
}
