package domain;

import lombok.Data;

import java.util.UUID;

@Data
public class User {

    private String name;
    private UUID id;
    private String email;
    private Long phone;

    public User(String name, String email, Long phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.id = UUID.randomUUID();
    }


}
