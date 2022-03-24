package com.project.warehouse.service;

import com.project.warehouse.dto.ClientDto;
import com.project.warehouse.entity.Client;
import com.project.warehouse.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    final ClientRepository clientRepository;

    public void add(ClientDto clientDto){
        Client client=new Client();
        client.setName(clientDto.getName());
        client.setPhoneNumber(clientDto.getPhoneNumber());
        clientRepository.save(client);
    }

    public void edit(ClientDto clientDto, Long id){
        Optional<Client> byId = clientRepository.findById(id);
        if (byId.isEmpty()) {
            return;
        }
        Client client = byId.get();
        client.setName(clientDto.getName());
        client.setPhoneNumber(clientDto.getPhoneNumber());
        clientRepository.save(client);
    }

    public void delete(Long id) {
        Optional<Client> byId = clientRepository.findById(id);
        if (byId.isEmpty()) {
            return;
        }
        byId.get().setActive(false);
        clientRepository.save(byId.get());
    }

}
