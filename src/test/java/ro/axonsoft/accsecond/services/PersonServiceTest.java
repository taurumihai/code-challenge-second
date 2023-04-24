package ro.axonsoft.accsecond.services;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import ro.axonsoft.accsecond.dto.PersonDTO;
import ro.axonsoft.accsecond.dto.RoomDTO;
import ro.axonsoft.accsecond.entities.PersonEntity;
import ro.axonsoft.accsecond.entities.RoomEntity;
import ro.axonsoft.accsecond.exceptions.BadRequestException;
import ro.axonsoft.accsecond.helpers.CustomModelMapper;
import ro.axonsoft.accsecond.repositories.PersonEntityRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonEntityRepository personEntityRepository;

    @Mock
    private CustomModelMapper customModelMapper;

    @BeforeEach
    public void initServices() {
        MockitoAnnotations.initMocks(this);
        personEntityRepository = Mockito.mock(PersonEntityRepository.class);
        customModelMapper = Mockito.mock(CustomModelMapper.class);
        personService = new PersonService(personEntityRepository, customModelMapper);
    }

    @Test
    public void shouldCreateNewPersonDTO() {
        PersonDTO personDTO = personService.setPersonDTO("Frank", "Supper", "Dr.", "von", "fsupper");
        assertEquals("Frank", personDTO.getFirstName());
        assertEquals("Supper", personDTO.getLastName());
        assertEquals("Dr.", personDTO.getTitle());
        assertEquals("von", personDTO.getNameAddition());
        assertEquals("fsupper", personDTO.getLdapUser());
    }

    @Test(expected = BadRequestException.class)
    public void createNewPersonShouldFailWithBadRequest() {
        PersonEntity person = PersonEntity.builder().firstName("Frank").lastName("Supper").title("Dr.").nameAddition("von").ldapUser("fsupper").build();
        RoomDTO roomDTO = RoomDTO.builder().roomNumber("1111").build();
        when(personEntityRepository.findPersonByLdapUser("fsupper")).thenReturn(person);
        personService.createAndSavePerson("Frank", "Supper", "Dr.","von","fsupper", roomDTO);
    }

    @Test
    public void testCreateAndSaveNewPerson() {
        PersonEntity personEntity = Mockito.mock(PersonEntity.class);
        RoomEntity roomEntity = Mockito.mock(RoomEntity.class);
        personEntity.setRoom(roomEntity);

        PersonDTO personDTO = personService.setPersonDTO("Frank", "Supper", "Dr.", "von", "fsupper");
        RoomDTO roomDTO = RoomDTO.builder().build();
        when(customModelMapper.mapPersonDtoToPersonEntity(personDTO)).thenReturn(personEntity);
        when(customModelMapper.mapRoomDtoToRoomEntity(roomDTO)).thenReturn(roomEntity);

        when(personEntityRepository.save(personEntity)).thenReturn(PersonEntity.builder().build());

        personService.savePerson(personDTO,roomDTO);

    }

    @Test
    public void testSavePersonShouldPass() {
        PersonEntity personEntity = Mockito.mock(PersonEntity.class);
        RoomEntity roomEntity = Mockito.mock(RoomEntity.class);
        personEntity.setRoom(roomEntity);
        when(personEntityRepository.save(personEntity)).thenReturn(PersonEntity.builder().build());

        PersonDTO personDTO = Mockito.mock(PersonDTO.class);
        RoomDTO roomDTO = Mockito.mock(RoomDTO.class);

        when(customModelMapper.mapPersonDtoToPersonEntity(personDTO)).thenReturn(personEntity);
        when(customModelMapper.mapRoomDtoToRoomEntity(roomDTO)).thenReturn(roomEntity);

        personService.savePerson(personDTO, roomDTO);
        verify(personEntityRepository, times(1)).save(Mockito.any(PersonEntity.class));
    }

    @Test
    public void testDeleteAllPersons() {
        PersonEntity person = PersonEntity.builder().firstName("Frank").lastName("Supper").title("Dr.").nameAddition("von").ldapUser("fsupper").build();
        PersonEntity person1 = PersonEntity.builder().firstName("Frank").lastName("Supper").title("Dr.").nameAddition("von").ldapUser("fsupper").build();
        List<PersonEntity> personEntities = new ArrayList<>();
        personEntities.add(person1);
        personEntities.add(person);

        when(personEntityRepository.findAll()).thenReturn(personEntities);

        personService.deletePersons();
        verify(personEntityRepository, times(1)).deleteAll();
    }
}
