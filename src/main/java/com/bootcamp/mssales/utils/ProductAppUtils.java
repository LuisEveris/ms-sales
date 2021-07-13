package com.bootcamp.mssales.utils;

import com.bootcamp.mssales.dto.ProductDTO;
import com.bootcamp.mssales.entity.Product;
import org.springframework.beans.BeanUtils;

public class ProductAppUtils {

    private ProductAppUtils() {
    }

    public static ProductDTO entityToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        return productDTO;
    }

    public static Product dtoToEntity(ProductDTO productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        return product;
    }


}
