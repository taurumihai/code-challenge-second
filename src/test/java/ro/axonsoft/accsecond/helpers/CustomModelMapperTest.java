package ro.axonsoft.accsecond.helpers;

import org.junit.Test;
import org.springframework.stereotype.Component;
import ro.axonsoft.accsecond.dto.PersonDTO;
import ro.axonsoft.accsecond.dto.RoomDTO;
import ro.axonsoft.accsecond.entities.PersonEntity;
import ro.axonsoft.accsecond.entities.RoomEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Component
public class CustomModelMapperTest {

    private final CustomModelMapper mapper = new CustomModelMapper();

    private static final List<PersonEntity> personEntityList = new ArrayList<>();

    private static final List<PersonDTO> personDTOList = new ArrayList<>();

    @Test
    public void testRoomEntityToRoomDTO() {
        RoomDTO roomDTO = mapper.mapRoomEntityToRoomDTO(buildStaticRoomEntity());
        assertEquals(roomDTO.getRoomNumber(), "1111");
        assertEquals(roomDTO.getPeople().get(0).getFirstName(), "Frank");
        assertEquals(roomDTO.getPeople().get(0).getLastName(), "Supper");
        assertEquals(roomDTO.getPeople().get(0).getTitle(), "Dr.");
        assertEquals(roomDTO.getPeople().get(0).getNameAddition(), "von");
        assertEquals(roomDTO.getPeople().get(0).getLdapUser(), "fsupper");
    }

    @Test
    public void testPersonEntityToPersonDTO() {
        PersonDTO personDTO = mapper.mapPersonEntityToPersonDTO(buildStaticPersonEntity());
        assertEquals(personDTO.getFirstName(), "Frank");
        assertEquals(personDTO.getLastName(), "Supper");
        assertEquals(personDTO.getTitle(), "Dr.");
        assertEquals(personDTO.getNameAddition(), "von");
        assertEquals(personDTO.getLdapUser(), "fsupper");

    }

    @Test
    public void testPersonDTOToPersonEntity() {
        PersonEntity entity = mapper.mapPersonDtoToPersonEntity(buildStaticPersonDTO());
        assertEquals(entity.getFirstName(), "Frank");
        assertEquals(entity.getLastName(), "Supper");
        assertEquals(entity.getTitle(), "Dr.");
        assertEquals(entity.getNameAddition(), "von");
        assertEquals(entity.getLdapUser(), "fsupper");

    }

    @Test
    public void testRoomEntityToRoomDto() {
        RoomEntity entity = mapper.mapRoomDtoToRoomEntity(buildStaticRoomDto());
        assertEquals(entity.getRoomNumber(), "1111");
        assertEquals(entity.getPeople().get(0).getFirstName(), "Frank");
        assertEquals(entity.getPeople().get(0).getLastName(), "Supper");
        assertEquals(entity.getPeople().get(0).getTitle(), "Dr.");
        assertEquals(entity.getPeople().get(0).getNameAddition(), "von");
        assertEquals(entity.getPeople().get(0).getLdapUser(), "fsupper");
    }

    private static RoomEntity buildStaticRoomEntity() {

        personEntityList.add(PersonEntity.builder()
                .firstName("Frank")
                .lastName("Supper")
                .title("Dr.")
                .nameAddition("von")
                .ldapUser("fsupper").build());

        personEntityList.add(PersonEntity.builder()
                .firstName("Dennis")
                .lastName("Fischer")
                .title("")
                .nameAddition("")
                .ldapUser("dfischer").build());

        return RoomEntity.builder().roomNumber("1111").people(personEntityList).build();
    }

    private static PersonEntity buildStaticPersonEntity() {

        return PersonEntity.builder().firstName("Frank")
                .lastName("Supper")
                .title("Dr.")
                .nameAddition("von")
                .ldapUser("fsupper")
                .build();
    }

    private static PersonDTO buildStaticPersonDTO() {

        return PersonDTO.builder().firstName("Frank")
                .lastName("Supper")
                .title("Dr.")
                .nameAddition("von")
                .ldapUser("fsupper")
                .build();
    }

    private static RoomDTO buildStaticRoomDto() {

        personDTOList.add(PersonDTO.builder()
                .firstName("Frank")
                .lastName("Supper")
                .title("Dr.")
                .nameAddition("von")
                .ldapUser("fsupper").build());

        personDTOList.add(PersonDTO.builder()
                .firstName("Dennis")
                .lastName("Fischer")
                .title("")
                .nameAddition("")
                .ldapUser("dfischer").build());

        return RoomDTO.builder().roomNumber("1111").people(personDTOList).build();
    }
}
