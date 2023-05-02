package ro.axonsoft.accsecond.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.axonsoft.accsecond.constants.ResponseMessageConstants;
import ro.axonsoft.accsecond.dto.PersonDTO;
import ro.axonsoft.accsecond.dto.RoomDTO;
import ro.axonsoft.accsecond.entities.PersonEntity;
import ro.axonsoft.accsecond.entities.RoomEntity;
import ro.axonsoft.accsecond.exceptions.BadRequestException;
import ro.axonsoft.accsecond.helpers.CustomModelMapper;
import ro.axonsoft.accsecond.helpers.ResponseMessage;
import ro.axonsoft.accsecond.repositories.PersonEntityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {

    static Logger log = Logger.getLogger(PersonService.class.getName());

    private final PersonEntityRepository personEntityRepository;

    private final CustomModelMapper customModelMapper;

    private final List<PersonDTO> personDTOList = new ArrayList<>();

    @Autowired
    public PersonService(PersonEntityRepository personEntityRepository, CustomModelMapper customModelMapper) {
        this.personEntityRepository = personEntityRepository;
        this.customModelMapper = customModelMapper;
    }

    public void deletePersons() {
        personEntityRepository.deleteAll();
    }

    public void savePerson(PersonDTO personDTO, RoomDTO roomDTO) {
        PersonEntity person = customModelMapper.mapPersonDtoToPersonEntity(personDTO);
        RoomEntity roomEntity = customModelMapper.mapRoomDtoToRoomEntity(roomDTO);
        person.setRoom(roomEntity);
        personEntityRepository.save(person);
    }

    public void createAndSavePerson(String firstName, String lastName, String title, String nameAddition, String ldapUser, RoomDTO roomDTO) {
        PersonEntity checkPerson = personEntityRepository.findPersonByLdapUser(ldapUser);
        if (checkPerson != null) {
            throw new BadRequestException(new ResponseMessage(ResponseMessageConstants.PERSON_ALREADY_DEFINED, ResponseMessageConstants.PERSON_ALREADY_DEFINED_ERROR_CODE));
        }

        PersonDTO personDTO = setPersonDTO(firstName, lastName, title, nameAddition, ldapUser);
        personDTOList.add(personDTO);
        savePerson(personDTO,roomDTO);
    }

    public PersonDTO setPersonDTO(String firstName, String lastName, String title, String nameAddition, String ldapUser) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setFirstName(firstName);
        personDTO.setLastName(lastName);
        personDTO.setTitle(title);
        personDTO.setNameAddition(nameAddition);
        personDTO.setLdapUser(ldapUser);
        return personDTO;
    }

    public PersonDTO findPersonByLdapUser(String ldapUser) {
        return personEntityRepository.findPersonByLdapUser(ldapUser) != null? customModelMapper.mapPersonEntityToPersonDTO(personEntityRepository.findPersonByLdapUser(ldapUser)) : null;
    }

}
