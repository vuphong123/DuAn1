package udpm.fpt.model;

//Author: BinhQuoc

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Salary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "salary_type")
    private String salaryType;
    
    @Column(name = "salary_mount")
    private Integer salaryAmount;
    
    @Column(name = "status")
    private String status;

    @Override
    public String toString() {
        return salaryType + ": " + salaryAmount + "d";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Salary other = (Salary) obj;
        if (!Objects.equals(this.salaryType, other.salaryType)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.salaryAmount, other.salaryAmount);
    }

}
