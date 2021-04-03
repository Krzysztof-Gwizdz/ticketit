package pl.krzysztofgwizdz.ticketit.service;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krzysztofgwizdz.ticketit.dao.OrganizationRepository;
import pl.krzysztofgwizdz.ticketit.entity.Organization;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationsService {

    OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public List<Organization> findAll() {
        return organizationRepository.findAllOrganizations();
    }

    @Override
    public Organization findById(long id) {
        return organizationRepository.findOrganization(id);
    }

    @Override
    public Organization findByShortname(String shortName) {
        List<Organization> organizations = organizationRepository.findAllOrganizations();
        Organization found = null;
        for(Organization organization : organizations){
            if(organization.getName().equalsIgnoreCase(shortName)){
                found = organization;
                break;
            }
        }
        return found;
    }
}
