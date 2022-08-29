package com.lcm.lcmvuespring.api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name="assign")
@NoArgsConstructor
public class Assign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private Instant startOn;
    private Instant dueOn;

    //What is EnumType ORDINAL used for ?
    @Enumerated(EnumType.ORDINAL)
    private AssignStatus assignstatus;

    @ManyToOne
    @JoinColumn(name="securitygroup_id")
    @JsonManagedReference
    private SecurityGroup group;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonManagedReference
    private User user;

}
