package ro.axonsoft.accsecond.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ro.axonsoft.accsecond.entities.PersonEntity;
import ro.axonsoft.accsecond.entities.RoomEntity;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@ToString
public class RoomDTO {

    @JsonIgnore
    private Long id;
    private String roomNumber;
    private List<PersonDTO> people;

    public RoomDTO(Long id, String roomNumber, List<PersonDTO> people) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.people = people;
    }

    public RoomDTO(RoomEntity entity) {
        this.roomNumber = entity.getRoomNumber();
        for (PersonEntity person : entity.getPeople()) {
            PersonDTO personDTO = new PersonDTO(person);
            this.people.add(personDTO);
        }
    }
}
