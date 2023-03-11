package com.example.userapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Column(name = "address_first_line")
    private String firstLine;

    @Column(name = "county")
    private String county;

    @Column(name = "postcode")
    private String postcode;
}
