package com.anamariafelix.ms_ticket_manager.client.mapper;

import com.anamariafelix.ms_ticket_manager.client.dto.EventDTO;
import com.anamariafelix.ms_ticket_manager.client.model.Event;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {

    public static Event toEvent(EventDTO eventCreateDTO){

        return new ModelMapper().map(eventCreateDTO, Event.class);
    }

}
