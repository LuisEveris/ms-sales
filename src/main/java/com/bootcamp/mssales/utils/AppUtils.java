package com.bootcamp.mssales.utils;

import com.bootcamp.mssales.dto.SaleDTO;
import com.bootcamp.mssales.entity.Sale;
import org.springframework.beans.BeanUtils;


public class AppUtils {

    private AppUtils() {
    }

    public static SaleDTO entityToDTO(Sale sale) {
        SaleDTO saleDTO = new SaleDTO();
        BeanUtils.copyProperties(sale, saleDTO);
        return saleDTO;
    }

    public static Sale dtoToEntity(SaleDTO saleDTO) {
        Sale sale = new Sale();
        BeanUtils.copyProperties(saleDTO, sale);
        return sale;
    }


}
