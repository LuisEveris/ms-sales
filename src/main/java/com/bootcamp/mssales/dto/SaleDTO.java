package com.bootcamp.mssales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTO {
    private Integer id;
    private ClientDTO client;
    private List<ProductDTO> products;
}
