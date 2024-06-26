package udpm.fpt.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Binh quoc
 */
@Entity
@Table(name = "UserDetails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "salary_id")
    private Salary salary;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "gender")
    private String gender;

    @Column(name = "tel")
    private String tel;

    @Column(name = "email")
    private String email;

    @Column(name = "photo")
    private String photo;

    @Column(name = "address")
    private String address;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "citizen_id")
    private String citizenId;

    @Column(name = "job_position")
    private String jobPosition;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private Date createdAt;

    @Override
    public String toString() {
        return "\nUserDetails{" + "id=" + id + ", user=" + user + ", salary=" + salary + ", fullname=" + fullname + ", gender=" + gender + ", tel=" + tel + ", email=" + email + ", photo=" + photo + ", address=" + address + ", birthdate=" + birthdate + ", citizenId=" + citizenId + ", jobPosition=" + jobPosition + ", note=" + note + ", status=" + status + ", createdAt=" + createdAt + '}';
    }
    
    
}
