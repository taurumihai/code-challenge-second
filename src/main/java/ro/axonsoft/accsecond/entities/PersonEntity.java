package ro.axonsoft.accsecond.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@Builder
@Table(name = "person_table", schema = "public")
@ToString
public class PersonEntity {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "title")
    private String title;

    @Column(name = "name_addition")
    private String nameAddition;

    @Column(name = "ldap_user")
    private String ldapUser;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="room_id", nullable=false)
    private RoomEntity room;

    public PersonEntity(Long id, String firstName, String lastName, String title, String nameAddition, String ldapUser, RoomEntity room) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.nameAddition = nameAddition;
        this.ldapUser = ldapUser;
        this.room = room;
    }
}
