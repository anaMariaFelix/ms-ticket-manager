package com.anamariafelix.ms_ticket_manager.repository;

import com.anamariafelix.ms_ticket_manager.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String>{

    User findByEmail(String username);

    Optional<User> findByCpf(String cpf);
}
