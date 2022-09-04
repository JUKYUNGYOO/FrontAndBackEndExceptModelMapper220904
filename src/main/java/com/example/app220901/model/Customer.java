package com.example.app220901.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;


/*
* @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor을
* 포함하는 어노테이션 @Data
*
* */
@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    private int id;
    private String name;
    private String email;
    @Column(name = "mobile_number")
    private String mobileNumber;
    @JsonIgnore
    private String pwd;
    private String role;
    @Column(name="create_dt")
    private String createDt;

}
