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
import ro.axonsoft.accsecond.repositories.RoomEntityRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class RoomServiceTest {

    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomEntityRepository roomEntityRepository;

    @Mock
    private CustomModelMapper customModelMapper;

    @BeforeEach
    public void initServices() {
        MockitoAnnotations.initMocks(this);
        roomEntityRepository = Mockito.mock(RoomEntityRepository.class);
        customModelMapper = Mockito.mock(CustomModelMapper.class);
        roomService = new RoomService(roomEntityRepository, customModelMapper);
    }

    @Test
    public void testDeleteAllRooms() {
        //Create Persons list
        List<PersonEntity> personEntities = new ArrayList<>();
        PersonEntity person = PersonEntity.builder().firstName("Frank").lastName("Supper").title("Dr.").nameAddition("von").ldapUser("fsupper").build();
        PersonEntity person1 = PersonEntity.builder().firstName("Frank").lastName("Supper").title("Dr.").nameAddition("von").ldapUser("fsupper").build();
        personEntities.add(person);
        personEntities.add(person1);

        RoomEntity roomEntity = RoomEntity.builder().roomNumber("1111").people(personEntities).build();
        RoomEntity roomEntity2 = RoomEntity.builder().roomNumber("1101").people(personEntities).build();
        List<RoomEntity> roomEntities = new ArrayList<>();
        roomEntities.add(roomEntity2);
        roomEntities.add(roomEntity);


        when(roomEntityRepository.findAll()).thenReturn(roomEntities);

        roomService.deleteRooms();
        verify(roomEntityRepository, times(1)).deleteAll();
    }

    @Test(expected = BadRequestException.class)
    public void createNewRoomShouldFailWithBadRequest() {
        List<PersonEntity> personEntities = new ArrayList<>();
        List<PersonDTO> personDTOList = new ArrayList<>();
        PersonEntity person = PersonEntity.builder().firstName("Frank").lastName("Supper").title("Dr.").nameAddition("von").ldapUser("fsupper").build();
        PersonEntity person1 = PersonEntity.builder().firstName("Frank").lastName("Supper").title("Dr.").nameAddition("von").ldapUser("fsupper").build();
        personEntities.add(person);
        personEntities.add(person1);

        for (PersonEntity entity : personEntities) {
            personDTOList.add(customModelMapper.mapPersonEntityToPersonDTO(entity));
        }

        RoomEntity roomEntity = RoomEntity.builder().roomNumber("1111").people(personEntities).build();
        when(roomEntityRepository.findRoomByRoomNumber("1111")).thenReturn(roomEntity);
        roomService.createNewRoom("1111", personDTOList);
    }

    @Test
    public void testSaveRoom() {
        List<PersonDTO> personDTOList = new ArrayList<>();
        List<PersonEntity> personEntities = new ArrayList<>();
        PersonEntity person = PersonEntity.builder().firstName("Frank").lastName("Supper").title("Dr.").nameAddition("von").ldapUser("fsupper").build();
        PersonEntity person1 = PersonEntity.builder().firstName("Frank").lastName("Supper").title("Dr.").nameAddition("von").ldapUser("fsupper").build();
        personEntities.add(person);
        personEntities.add(person1);


        RoomEntity roomEntity = RoomEntity.builder().roomNumber("1111").people(personEntities).build();
        RoomEntity roomEntity2 = RoomEntity.builder().roomNumber("1101").people(personEntities).build();
        List<RoomEntity> roomEntities = new ArrayList<>();
        roomEntities.add(roomEntity2);
        roomEntities.add(roomEntity);

        for (PersonEntity entity : personEntities) {
            personDTOList.add(customModelMapper.mapPersonEntityToPersonDTO(entity));
        }

        when(roomEntityRepository.findAll()).thenReturn(roomEntities);

        RoomDTO dummyDto = RoomDTO.builder().roomNumber("1111").people(personDTOList).build();
        when(customModelMapper.mapRoomDtoToRoomEntity(dummyDto)).thenReturn(roomEntity);

        roomService.saveRoom(dummyDto);
        verify(roomEntityRepository, times(1)).save(Mockito.any(RoomEntity.class));
    }

    @Test
    public void testGetAllRooms() {
        List<PersonDTO> personDTOList = new ArrayList<>();
        List<PersonEntity> personEntities = new ArrayList<>();

        PersonEntity person = PersonEntity.builder().firstName("Frank").lastName("Supper").title("Dr.").nameAddition("von").ldapUser("fsupper").build();
        PersonEntity person1 = PersonEntity.builder().firstName("Frank").lastName("Supper").title("Dr.").nameAddition("von").ldapUser("fsupper").build();
        personEntities.add(person);
        personEntities.add(person1);

        for (PersonEntity entity : personEntities) {
            personDTOList.add(customModelMapper.mapPersonEntityToPersonDTO(entity));
        }


        RoomEntity roomEntity = RoomEntity.builder().roomNumber("1111").people(personEntities).build();
        RoomEntity roomEntity2 = RoomEntity.builder().roomNumber("1101").people(personEntities).build();
        List<RoomEntity> roomEntities = new ArrayList<>();
        roomEntities.add(roomEntity2);
        roomEntities.add(roomEntity);

        RoomDTO roomDTO = RoomDTO.builder().roomNumber("1111").people(personDTOList).build();
        RoomDTO roomDTO2 = RoomDTO.builder().roomNumber("1101").people(personDTOList).build();
        List<RoomDTO> roomDTOS = new ArrayList<>();

        when(roomEntityRepository.findAll()).thenReturn(roomEntities);
        when(customModelMapper.mapRoomEntityToRoomDTO(roomEntity)).thenReturn(roomDTO);
        when(customModelMapper.mapRoomEntityToRoomDTO(roomEntity2)).thenReturn(roomDTO2);

        for (RoomEntity entity : roomEntities) {
            roomDTOS.add(customModelMapper.mapRoomEntityToRoomDTO(entity));
        }

        List<RoomDTO> list = roomService.getAllRooms();
        assertEquals(roomDTOS, list);
    }

    @Test
    public void testGetRoomByRoomNumberShouldPass() {
        List<PersonDTO> personDTOList = new ArrayList<>();
        List<PersonEntity> personEntities = new ArrayList<>();

        PersonEntity person = PersonEntity.builder().firstName("Frank").lastName("Supper").title("Dr.").nameAddition("von").ldapUser("fsupper").build();
        PersonEntity person1 = PersonEntity.builder().firstName("Frank").lastName("Supper").title("Dr.").nameAddition("von").ldapUser("fsupper").build();
        personEntities.add(person);
        personEntities.add(person1);

        for (PersonEntity entity : personEntities) {
            personDTOList.add(customModelMapper.mapPersonEntityToPersonDTO(entity));
        }

        RoomDTO roomDTO = RoomDTO.builder().roomNumber("1111").people(personDTOList).build();

        when(roomService.getRoomByRoomNumber("1111")).thenReturn(roomDTO);
        roomService.getRoomByRoomNumber("1111");
    }

    @Test
    public void testCreateNewRoomShouldPass() {
        List<PersonDTO> personDTOList = new ArrayList<>();
        List<PersonEntity> personEntities = new ArrayList<>();

        PersonEntity person = PersonEntity.builder().firstName("Frank").lastName("Supper").title("Dr.").nameAddition("von").ldapUser("fsupper").build();
        PersonEntity person1 = PersonEntity.builder().firstName("Frank").lastName("Supper").title("Dr.").nameAddition("von").ldapUser("fsupper").build();

        personEntities.add(person);
        personEntities.add(person1);

        for (PersonEntity entity : personEntities) {
            personDTOList.add(customModelMapper.mapPersonEntityToPersonDTO(entity));
        }

        when(roomService.getRoomByRoomNumber("1111")).thenReturn(null);
        roomService.createNewRoom("1111", personDTOList);
    }

    @Test
    public void testGetLatestAddedRoom() {

        List<PersonDTO> personDTOList = new ArrayList<>();

        PersonDTO person = PersonDTO.builder().firstName("Frank").lastName("Supper").title("Dr.").nameAddition("von").ldapUser("fsupper").build();
        PersonDTO person1 = PersonDTO.builder().firstName("Frank").lastName("Supper").title("Dr.").nameAddition("von").ldapUser("fsupper").build();
        personDTOList.add(person);
        personDTOList.add(person1);

        List<PersonEntity> personEntities = new ArrayList<>();

        PersonEntity person3 = PersonEntity.builder().firstName("Frank").lastName("Supper").title("Dr.").nameAddition("von").ldapUser("fsupper").build();
        PersonEntity person4 = PersonEntity.builder().firstName("Frank").lastName("Supper").title("Dr.").nameAddition("von").ldapUser("fsupper").build();
        personEntities.add(person3);
        personEntities.add(person4);

        RoomDTO roomDTO = RoomDTO.builder().roomNumber("1111").people(personDTOList).build();
        RoomDTO roomDTO2 = RoomDTO.builder().roomNumber("1101").people(personDTOList).build();

        RoomEntity roomEntity = RoomEntity.builder().roomNumber("1111").people(personEntities).build();
        RoomEntity roomEntity2 = RoomEntity.builder().roomNumber("1101").people(personEntities).build();

        when(customModelMapper.mapRoomDtoToRoomEntity(roomDTO)).thenReturn(roomEntity);
        when(customModelMapper.mapRoomDtoToRoomEntity(roomDTO2)).thenReturn(roomEntity2);

        roomService.saveRoom(roomDTO);
        roomService.saveRoom(roomDTO2);

        verify(roomEntityRepository, times(2)).save(Mockito.any(RoomEntity.class));

        when(roomEntityRepository.findTopByOrderByIdDesc()).thenReturn(roomEntity2);

        RoomEntity latestAddedEntity = roomService.findLastAddedRoom();
        assertEquals(roomEntity2, latestAddedEntity);

    }
}
