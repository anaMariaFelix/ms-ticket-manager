package com.anamariafelix.ms_ticket_manager.client;

import com.anamariafelix.ms_ticket_manager.client.dto.EventDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "event-client", url = "${ms.event.url}")
public interface EventClientOpenFeign {

    @GetMapping("/get-event/{id}")
    EventDTO findById(@PathVariable String id);
}
