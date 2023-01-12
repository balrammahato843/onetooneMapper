package com.mappingdemo.mapping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonRequest {
    private String firstName;
    private String lastName;
    private AddressRequest address;
}
