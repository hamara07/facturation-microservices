package sn.esmt.tech.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.esmt.tech.entities.Client;
import sn.esmt.tech.repository.ClientRepository;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;


    public Client getClientByClientId(Long clientId) {
        if (clientId == null) {
            throw new IllegalArgumentException("ID client ne peut pas etre null");
        }

        return clientRepository.findById(clientId)
            .orElseThrow(() -> new RuntimeException("Client non trouvé avec id: " + clientId));
    }

    public Client createClient(Client client) {
        Client valide = clientRepository.save(client);
         System.out.println("Client créé avec succès: " + valide);
        return valide;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    public void deleteClient(Long clientId) {
        clientRepository.deleteById(clientId);
        System.out.println("delete client with id: " + clientId);
    }


}
