package ro.axonsoft.accsecond.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@Table(name = "room_table", schema = "public")
public class RoomEntity {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty("room")
    @Column(name = "room_number")
    private String roomNumber;

    @JsonProperty("people")
    @OneToMany(mappedBy="room")
    @ToString.Exclude
    private List<PersonEntity> people;

    public RoomEntity(Long id,String roomNumber, List<PersonEntity> people) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.people = people;
    }
}
