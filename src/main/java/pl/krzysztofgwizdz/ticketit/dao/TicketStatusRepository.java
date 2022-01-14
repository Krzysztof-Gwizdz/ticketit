package pl.krzysztofgwizdz.ticketit.dao;

import pl.krzysztofgwizdz.ticketit.entity.TicketStatus;

import java.util.Set;

public interface TicketStatusRepository {
    Set<TicketStatus> findAll();

    TicketStatus findTicketStatusById(Integer statusId);
}
