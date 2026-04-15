package aho.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`users`")
@Setter
@Getter
public class User {
    public static List<User> listUsers = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "`fio`")
    private String fio;

    @Column(name = "`password`")
    private String password;

    @Column(name = "`role`")
    private int role;

    @Column(name = "`otdel`")
    private int otdel;

    public User(){
    }
}
