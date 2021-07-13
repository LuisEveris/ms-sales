package com.bootcamp.mssales.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("clients")
public class Client {
    @Id
    private Integer id;
    private String name;
    private Integer dni;
    private String type;
    private String address;
    private String email;
    private Integer phone;
}
