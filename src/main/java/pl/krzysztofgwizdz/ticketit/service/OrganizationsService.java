package pl.krzysztofgwizdz.ticketit.service;

import pl.krzysztofgwizdz.ticketit.entity.Organization;

import java.util.List;

public interface OrganizationsService {

    List<Organization> findAll();

    Organization findById(long id);

    Organization findByShortname(String shortName);
}
