package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "EMPLOYEES")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CITY")
    private String city;

    @Column(name = "CREATED", nullable = false)
    private LocalDateTime created;

    /*
    @Enumerated(EnumType.STRING)
    private Role role;

    @Lob (large object)
    private byte[] image;

    */

    public Employee(@NonNull String name, String city) {
        this.name = name;
        this.city = city;
        this.created = LocalDateTime.now();
    }

    /*public enum Role {
        USER_ROLE,
        ADMIN_ROLE
    }*/
}
