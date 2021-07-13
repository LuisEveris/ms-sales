package com.bootcamp.mssales.utils;

import com.bootcamp.mssales.dto.ClientDTO;
import com.bootcamp.mssales.entity.Client;
import org.springframework.beans.BeanUtils;


public class ClientAppUtils {

    private ClientAppUtils() {
    }

    public static ClientDTO entityToDTO(Client product) {
        ClientDTO clientDTO = new ClientDTO();
        BeanUtils.copyProperties(product, clientDTO);
        return clientDTO;
    }

    public static Client dtoToEntity(ClientDTO productDTO) {
        Client client = new Client();
        BeanUtils.copyProperties(productDTO, client);
        return client;
    }


}
