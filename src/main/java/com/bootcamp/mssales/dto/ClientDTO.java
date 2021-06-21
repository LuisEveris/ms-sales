package com.bootcamp.mssales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    private Integer id;
    private String name;
    private Integer dni;
    private String type;
    private String address;
    private String email;
    private Integer phone;
}
