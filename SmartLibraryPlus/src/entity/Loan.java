package entity;
import javax.persistence.*;

@Entity
@Table(name="loans")
public class Loan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String borrowDate;
    private String returnDate;

    // Book -> Loan : OneToOne (Zorunlu)
    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    // Student -> Loan bağlantısı (ManyToOne)
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public Loan() {}

    // Getter ve Setter'lar
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getBorrowDate() { return borrowDate; }
    public void setBorrowDate(String borrowDate) { this.borrowDate = borrowDate; }
    public String getReturnDate() { return returnDate; }
    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
}