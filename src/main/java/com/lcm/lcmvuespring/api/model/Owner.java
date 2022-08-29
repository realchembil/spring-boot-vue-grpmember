package com.lcm.lcmvuespring.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Need to understand how the Lombok dependency works. Do we have to define the constructor separately.
 * What is the @Table and @Entity annotation used for
 */

@Getter
@Setter
@Entity
@Table(name="owner")
public class Owner {

    //Need to understand what the generationType Identity is used for. What is significance of adding the @Id annotation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @JsonBackReference
    @OneToMany(mappedBy = "owner",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<SecurityGroup> securityGroupList;
}
