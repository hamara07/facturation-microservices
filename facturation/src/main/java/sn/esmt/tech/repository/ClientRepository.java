package sn.esmt.tech.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.esmt.tech.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
