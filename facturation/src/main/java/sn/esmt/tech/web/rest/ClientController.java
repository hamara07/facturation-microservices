package sn.esmt.tech.web.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sn.esmt.tech.entities.Client;
import sn.esmt.tech.service.ClientService;

import java.util.List;

//@Controller
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;


    //  1. Créer un client
//    @PostMapping("/ajouter")
//    public Client createClient(@RequestBody Client client) {
//        Client savedClient = clientService.createClient(client);
//        System.out.println("Client créé avec le nom: " + savedClient.getNom());
//        return savedClient;
//    }

    @PostMapping("/ajouter")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client savedClient = clientService.createClient(client);
        System.out.println("Client créé avec le nom: " + savedClient.getNom());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    // 2. Récupérer un client par ID
    @GetMapping("/{id}")
    public Client getClient(@PathVariable Long id) {

        return clientService.getClientByClientId(id);
    }

    //  3. Liste de tous les clients
    @GetMapping
    public List<Client> getAllClients() {
        System.out.println("Récupération de tous les clients");
        return clientService.getAllClients();
    }

    //  4. Modifier un client
    @PutMapping("/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client client) {
        Client exist = clientService.getClientByClientId(id);

        // Mise à jour des champs
        exist.setNom(client.getNom());
        exist.setEmail(client.getEmail());
        exist.setNom(client.getNom());

        return clientService.updateClient(exist);
    }

    //  5. Supprimer un client
    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }
}
