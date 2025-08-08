package com.anamariafelix.ms_ticket_manager.service;

import com.anamariafelix.ms_ticket_manager.exception.TicketNotFoundException;
import com.anamariafelix.ms_ticket_manager.exception.UniqueViolationException;
import com.anamariafelix.ms_ticket_manager.exception.UserNotFoundException;
import com.anamariafelix.ms_ticket_manager.model.User;
import com.anamariafelix.ms_ticket_manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;

    @Transactional
    public User create(User user){
        try{
            //user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }catch (DuplicateKeyException e){
            throw new UniqueViolationException(String.format("Email or CPF already registered!", user.getEmail()));
        }
    }

    @Transactional(readOnly = true)
    public User findByEmail(String username) {
        return userRepository.findByEmail(username);
    }

    @Transactional(readOnly = true)
    public User findByCpf(String cpf) {
        return userRepository.findByCpf(cpf).orElseThrow(
                () -> new UserNotFoundException(String.format("Client with cpf = %s not found!", cpf)));
    }
}
