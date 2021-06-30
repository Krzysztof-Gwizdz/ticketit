package pl.krzysztofgwizdz.ticketit.dao;

import pl.krzysztofgwizdz.ticketit.entity.Organization;

import java.util.List;

public interface OrganizationRepository {

    List<Organization> findAllOrganizations();

    Organization findOrganization(long id);

    Organization saveOrganization(Organization organization);

    void deleteOrganization(Organization organization);
}
