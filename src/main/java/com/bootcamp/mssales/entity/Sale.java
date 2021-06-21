package com.bootcamp.mssales.entity;

import com.bootcamp.mssales.dto.ClientDTO;
import com.bootcamp.mssales.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("sales")
public class Sale {
    @Id
    private Integer id;
    private ClientDTO client;
    private List<ProductDTO> products;

}
