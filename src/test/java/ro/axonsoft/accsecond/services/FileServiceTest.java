package ro.axonsoft.accsecond.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import ro.axonsoft.accsecond.dto.PersonDTO;
import ro.axonsoft.accsecond.dto.RoomDTO;
import ro.axonsoft.accsecond.entities.PersonEntity;
import ro.axonsoft.accsecond.entities.RoomEntity;
import ro.axonsoft.accsecond.exceptions.BadRequestException;
import ro.axonsoft.accsecond.exceptions.NumberFormatException;
import ro.axonsoft.accsecond.helpers.CustomModelMapper;
import ro.axonsoft.accsecond.helpers.FileHelper;
import ro.axonsoft.accsecond.helpers.PersonHelper;
import ro.axonsoft.accsecond.repositories.PersonEntityRepository;
import ro.axonsoft.accsecond.repositories.RoomEntityRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
public class FileServiceTest {

    @InjectMocks
    private FileService fileService;

    @InjectMocks
    private RoomService roomService;

    @InjectMocks
    private PersonService personService;

    @Mock
    private CustomModelMapper customModelMapper;

    @Mock
    private RoomEntityRepository roomEntityRepository;

    @Mock
    private PersonEntityRepository personEntityRepository;

    @Before
    public void initMocksTests() {

        personEntityRepository = Mockito.mock(PersonEntityRepository.class);
        roomEntityRepository = Mockito.mock(RoomEntityRepository.class);
        customModelMapper = Mockito.mock(CustomModelMapper.class);


        personService = new PersonService(personEntityRepository, customModelMapper);
        roomService = new RoomService(roomEntityRepository, customModelMapper);
        fileService = new FileService(roomService, personService, customModelMapper);
    }

    @Test(expected = BadRequestException.class)
    public void fileImportShouldFailWithEmptyFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "".getBytes());
        boolean checkFile = FileHelper.isNullOrEmptyFile(file);
        assertTrue(checkFile);
        fileService.parseFile(file);
    }

    @Test(expected = NumberFormatException.class)
    public void fileImportShouldFailWithNumberFormatException() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "Wrong Content in file".getBytes());
        boolean checkFile = FileHelper.isNullOrEmptyFile(file);
        assertFalse(checkFile);

        PersonEntity person = PersonEntity.builder().firstName("Frank").lastName("Supper").title("Dr.").nameAddition("von").ldapUser("fsupper").build();
        PersonEntity person1 = PersonEntity.builder().firstName("Frank").lastName("Supper").title("Dr.").nameAddition("von").ldapUser("fsupper").build();
        List<PersonEntity> personEntities = new ArrayList<>();
        personEntities.add(person1);
        personEntities.add(person);

        when(personEntityRepository.findAll()).thenReturn(personEntities);

        personService.deletePersons();
        verify(personEntityRepository, times(1)).deleteAll();

        RoomEntity roomEntity = RoomEntity.builder().roomNumber("1111").people(personEntities).build();
        RoomEntity roomEntity2 = RoomEntity.builder().roomNumber("1101").people(personEntities).build();
        List<RoomEntity> roomEntities = new ArrayList<>();
        roomEntities.add(roomEntity2);
        roomEntities.add(roomEntity);


        when(roomEntityRepository.findAll()).thenReturn(roomEntities);

        roomService.deleteRooms();
        verify(roomEntityRepository, times(1)).deleteAll();

        fileService.parseFile(file);
    }

    @Test
    public void fileImportShouldPass() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "1111,Dr. Frank von Supper (fsupper),,".getBytes());
        boolean checkFile = FileHelper.isNullOrEmptyFile(file);
        assertFalse(checkFile);

        PersonEntity person = PersonEntity.builder().firstName("Frank").lastName("Supper").title("Dr.").nameAddition("von").ldapUser("fsupper").build();
        PersonEntity person1 = PersonEntity.builder().firstName("Frank").lastName("Supper").title("Dr.").nameAddition("von").ldapUser("fsupper").build();
        List<PersonEntity> personEntities = new ArrayList<>();
        personEntities.add(person1);
        personEntities.add(person);

        when(personEntityRepository.findAll()).thenReturn(personEntities);

        personService.deletePersons();
        verify(personEntityRepository, times(1)).deleteAll();

        RoomEntity roomEntity = RoomEntity.builder().roomNumber("1111").people(personEntities).build();
        RoomEntity roomEntity2 = RoomEntity.builder().roomNumber("1101").people(personEntities).build();
        List<RoomEntity> roomEntities = new ArrayList<>();
        roomEntities.add(roomEntity2);
        roomEntities.add(roomEntity);

        when(roomEntityRepository.findAll()).thenReturn(roomEntities);

        roomService.deleteRooms();
        verify(roomEntityRepository, times(1)).deleteAll();

        RoomEntity entityForMapper = Mockito.mock(RoomEntity.class);
        PersonEntity personEntity = Mockito.mock(PersonEntity.class);
        RoomDTO roomDTO1 = Mockito.mock(RoomDTO.class);
        PersonDTO personDTO = Mockito.mock(PersonDTO.class);

        when(customModelMapper.mapPersonDtoToPersonEntity(personDTO)).thenReturn(personEntity);
        when(customModelMapper.mapRoomDtoToRoomEntity(roomDTO1)).thenReturn(entityForMapper);

        HashMap<String, String> inputMap = new HashMap<>();
        PersonHelper.computePerson("Dr. Frank von Supper (fsupper)", inputMap);
        assertEquals("Frank", inputMap.get("firstName"));
        assertEquals("Supper", inputMap.get("lastName"));
        assertEquals("Dr.", inputMap.get("title"));
        assertEquals("von", inputMap.get("nameAddition"));
        assertEquals("fsupper", inputMap.get("ldapUser"));

       // fileService.parseFile(file);
    }
}
