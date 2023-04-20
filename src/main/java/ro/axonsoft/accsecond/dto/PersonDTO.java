package ro.axonsoft.accsecond.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ro.axonsoft.accsecond.entities.PersonEntity;

@Data
@Builder
@NoArgsConstructor
@ToString
public class PersonDTO {

    @JsonIgnore
    private Long id;
    private String firstName;
    private String lastName;
    private String title;
    private String nameAddition;
    private String ldapUser;

    public PersonDTO(Long id, String firstName, String lastName, String title, String nameAddition, String ldapUser) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.nameAddition = nameAddition;
        this.ldapUser = ldapUser;
    }

    public PersonDTO(PersonEntity person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.title = person.getTitle();
        this.nameAddition = person.getNameAddition();
        this.ldapUser = person.getLdapUser();
    }
}
