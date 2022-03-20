package com.project.warehouse.service;

import com.project.warehouse.dto.ApiResponse;
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

  public void add(Client clientDto){
    if(clientDto.getName().equalsIgnoreCase("#NULL"))return ;
    Client client=new Client();
    if(!clientDto.getId().equals("#null")){
      Optional<Client> byId = clientRepository.findById(clientDto.getId());
      if(byId.isEmpty()){
        return;
      }
    }
    client.setName(clientDto.getName());
    clientRepository.save(client);
  }

  public ApiResponse edit(ClientDto clientDto, Long id){
    Optional<Client> byId = clientRepository.findById(id);
    if (byId.isEmpty()) {
        return null;
    }
    Client client = byId.get();
    if (clientDto.getId().equals("#null")) {
      client.setId(null);
    }else {
      Optional<Client> byId1 = clientRepository.findById(clientDto.getId());
      if (byId1.isEmpty()) {
          return null;
      }
      client.setId(byId1.get().getId());
    }
    client.setName(clientDto.getName());
    clientRepository.save(client);
      return null;
  }

  public void delete(Long id) {
    Optional<Client> byId = clientRepository.findById(id);
    if (byId.isEmpty()) {
      return;
    }
    clientRepository.save(byId.get());
  }

}
