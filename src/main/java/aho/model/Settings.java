package aho.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`settings`")
@Setter
@Getter
public class Settings {
    public static List<Settings> listSettings = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "`name`")
    private String name;

    @Column(name = "`values`")
    private String values;
}
