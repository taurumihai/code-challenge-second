package ro.axonsoft.accsecond.helpers;

import org.springframework.stereotype.Component;
import ro.axonsoft.accsecond.dto.PersonDTO;
import ro.axonsoft.accsecond.dto.RoomDTO;
import ro.axonsoft.accsecond.entities.PersonEntity;
import ro.axonsoft.accsecond.entities.RoomEntity;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomModelMapper {

    public PersonEntity mapPersonDtoToPersonEntity(PersonDTO personDTO) {
        return PersonEntity.builder().id(personDTO.getId()).firstName(personDTO.getFirstName()).lastName(personDTO.getLastName())
                .title(personDTO.getTitle())
                .nameAddition(personDTO.getNameAddition())
                .ldapUser(personDTO.getLdapUser()).build();
    }

    public RoomEntity mapRoomDtoToRoomEntity(RoomDTO roomDTO) {
        List<PersonEntity> personEntityList = new ArrayList<>();
        if (roomDTO.getPeople() != null && !roomDTO.getPeople().isEmpty()) {
            for (PersonDTO person : roomDTO.getPeople()) {
                personEntityList.add(mapPersonDtoToPersonEntity(person));
            }

        }
        return RoomEntity.builder().id(roomDTO.getId()).roomNumber(roomDTO.getRoomNumber()).people(personEntityList).build();
    }

    public RoomDTO mapRoomEntityToRoomDTO(RoomEntity entity){
        List<PersonDTO> people = new ArrayList<>();

        if (entity != null) {
            for (PersonEntity personEntity : entity.getPeople()) {
                people.add(mapPersonEntityToPersonDTO(personEntity));
            }
            return RoomDTO.builder().id(entity.getId()).roomNumber(entity.getRoomNumber())
                    .people(people)
                    .build();
        }
        return null;
    }

    public PersonDTO mapPersonEntityToPersonDTO(PersonEntity entity){
        return PersonDTO.builder().id(entity.getId()).firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .title(entity.getTitle())
                .nameAddition(entity.getNameAddition())
                .ldapUser(entity.getLdapUser())
                .build();
    }
}
