package com.anamariafelix.ms_ticket_manager.repository;

import com.anamariafelix.ms_ticket_manager.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TicketRepository extends MongoRepository<Ticket,String> {
}
