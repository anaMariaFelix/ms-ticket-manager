package com.anamariafelix.ms_ticket_manager.repository;

import com.anamariafelix.ms_ticket_manager.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TicketRepository extends MongoRepository<Ticket,String> {

    List<Ticket> findByCpfAndDeletedFalse(String cpf);

    Optional<Ticket> findByTicketIdAndDeletedFalse(String id);

    @Query("{ 'deleted': false, 'event.id': ?0 }")
    List<Ticket> findByEventIdAndDeletedFalse(String eventId);
}
