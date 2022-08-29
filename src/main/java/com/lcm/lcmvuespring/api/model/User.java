package com.lcm.lcmvuespring.api.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="member")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    //Need to understand why the @Enumerated annotation is used here. What is EnumType.STRING used. what is the difference between STRING and ORDINAL
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @JsonBackReference
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Assign> assignList;
}
