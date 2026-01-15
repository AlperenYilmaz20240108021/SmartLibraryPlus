package entity;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="students")
public class Student {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String department;

    // Student -> Loan : OneToMany (Zorunlu)
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Loan> loans;

    public Student() {}
    public Student(String name, String department) {
        this.name = name;
        this.department = department;
    }

    // Getter ve Setter'lar
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}