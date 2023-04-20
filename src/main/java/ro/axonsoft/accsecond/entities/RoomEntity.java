package ro.axonsoft.accsecond.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@ToString
public class RoomEntity {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "room_number")
    private String roomNumber;

    @OneToMany(mappedBy="room")
    private List<PersonEntity> people;

    public RoomEntity(Long id,String roomNumber, List<PersonEntity> people) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.people = people;
    }
}
