package com.kodilla.erenovation.authentification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthentification {
    private String name;
    private String surname;
    private long id;
    private long passwordId;
    private long addressId;
}

