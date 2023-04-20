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
import ro.axonsoft.accsecond.helpers.CustomModelMapper;
import ro.axonsoft.accsecond.repositories.PersonEntityRepository;

import java.util.ArrayList;
import java.util.Collections;
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

    private List<PersonDTO> personDTOList = new ArrayList<>();


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

    @Test
    public void createNewPersonShouldPass() {

        PersonEntity entity = Mockito.mock(PersonEntity.class);
        RoomEntity roomEntity =  Mockito.mock(RoomEntity.class);
        entity.setRoom(roomEntity);

        when(personEntityRepository.save(entity)).thenReturn(entity);
        PersonDTO firstDummyDTO = personService.setPersonDTO("Frank", "Supper", "Dr.", "von", "fsupper");
        personDTOList.add(firstDummyDTO);

        RoomDTO roomDTO = new RoomDTO( 1L,"1111", personDTOList);
        personService.savePerson(firstDummyDTO, roomDTO);



        when(personEntityRepository.save(Mockito.any(PersonEntity.class)))
                .thenReturn(PersonEntity.builder().firstName("Frank").lastName("Supper").title("Dr.").nameAddition("von")
                        .ldapUser("fsupper")
                        .room(RoomEntity.builder()
                                .roomNumber("1111")
                                .people(Collections.singletonList(PersonEntity.builder()
                                        .id(1L)
                                        .firstName("Frank")
                                        .lastName("Supper")
                                        .title("Dr.")
                                        .nameAddition("von")
                                        .ldapUser("fsupper").build())).build())
                        .build());

        verify(personEntityRepository, times(1)).save(Mockito.any(PersonEntity.class));

    }

}
