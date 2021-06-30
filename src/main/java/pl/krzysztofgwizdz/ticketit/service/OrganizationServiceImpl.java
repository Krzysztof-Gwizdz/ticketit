package pl.krzysztofgwizdz.ticketit.service;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krzysztofgwizdz.ticketit.dao.OrganizationRepository;
import pl.krzysztofgwizdz.ticketit.dao.UserRepository;
import pl.krzysztofgwizdz.ticketit.dto.OrganizationDTO;
import pl.krzysztofgwizdz.ticketit.entity.Organization;
import pl.krzysztofgwizdz.ticketit.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationsService {

    OrganizationRepository organizationRepository;
    UserRepository userRepository;

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

    @Override
    public Organization saveOrganization(OrganizationDTO organizationDTO, String username) {
        Organization organization = new Organization();
        organization.setFullName(organizationDTO.getFullName());
        organization.setName(organizationDTO.getShortName());
        User orgOwner = userRepository.findUserByUsername(username);
        List<User> orgMembers = organization.getMembers();
        if(orgMembers==null){
            orgMembers = new ArrayList<>();
        }
        orgMembers.add(orgOwner);
        organization.setMembers(orgMembers);
        return organizationRepository.saveOrganization(organization);
    }

    @Autowired
    public void setOrganizationRepository(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
