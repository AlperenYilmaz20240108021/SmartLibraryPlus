package app;

import dao.*;
import entity.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookDao bookDao = new BookDao();
        StudentDao studentDao = new StudentDao();
        LoanDao loanDao = new LoanDao();

        while (true) {
            System.out.println("\n--- SmartLibraryPlus Menü ---");
            System.out.println("1 - Kitap Ekle");
            System.out.println("2 - Kitapları Listele");
            System.out.println("3 - Öğrenci Ekle");
            System.out.println("4 - Öğrencileri Listele");
            System.out.println("5 - Kitap Ödünç Ver");
            System.out.println("6 - Ödünç Listesini Görüntüle");
            System.out.println("7 - Kitap Geri Teslim Al");
            System.out.println("0 - Çıkış");
            System.out.print("Seçiminiz: ");
            
            int secim = scanner.nextInt();
            scanner.nextLine(); // Boşluk temizleme

            switch (secim) {
                case 1:
                    System.out.print("Kitap Adı: "); String title = scanner.nextLine();
                    System.out.print("Yazar: "); String author = scanner.nextLine();
                    System.out.print("Yıl: "); int year = scanner.nextInt();
                    bookDao.save(new Book(title, author, year));
                    System.out.println("Kitap başarıyla eklendi.");
                    break;

                case 2:
                    List<Book> books = bookDao.getAll();
                    System.out.println("\n--- Kitap Listesi ---");
                    for (Book b : books) {
                        System.out.println("ID: " + b.getId() + " | Ad: " + b.getTitle() + " | Durum: " + b.getStatus());
                    }
                    break;

                case 3:
                    System.out.print("Öğrenci Adı: "); String name = scanner.nextLine();
                    System.out.print("Bölüm: "); String dept = scanner.nextLine();
                    studentDao.save(new Student(name, dept));
                    System.out.println("Öğrenci başarıyla eklendi.");
                    break;

                case 4:
                    List<Student> students = studentDao.getAll();
                    System.out.println("\n--- Öğrenci Listesi ---");
                    for (Student s : students) {
                        System.out.println("ID: " + s.getId() + " | Ad: " + s.getName() + " | Bölüm: " + s.getDepartment());
                    }
                    break;

                case 5:
                    System.out.print("Öğrenci ID: "); int sId = scanner.nextInt();
                    System.out.print("Kitap ID: "); int bId = scanner.nextInt();
                    Book bToBorrow = bookDao.getById(bId);
                    Student st = studentDao.getById(sId);

                    if (bToBorrow != null && st != null && bToBorrow.getStatus().equals("AVAILABLE")) {
                        Loan loan = new Loan();
                        loan.setBook(bToBorrow);
                        loan.setStudent(st);
                        loan.setBorrowDate(LocalDate.now().toString());
                        
                        bToBorrow.setStatus("BORROWED");
                        bookDao.update(bToBorrow);
                        loanDao.save(loan);
                        System.out.println("İşlem Başarılı!");
                    } else {
                        System.out.println("Hata: Kitap müsait değil veya ID geçersiz!");
                    }
                    break;

                case 6:
                    List<Loan> loans = loanDao.getAll();
                    System.out.println("\n--- Ödünç Listesi ---");
                    for (Loan l : loans) {
                        System.out.println("Öğrenci: " + l.getStudent().getName() + 
                                           " | Kitap: " + l.getBook().getTitle() + 
                                           " | Alış: " + l.getBorrowDate() + 
                                           " | İade: " + (l.getReturnDate() == null ? "Teslim Edilmedi" : l.getReturnDate()));
                    }
                    break;

                case 7:
                    System.out.print("Geri alınacak Kitap ID: "); int bookIdToReturn = scanner.nextInt();
                    List<Loan> allLoans = loanDao.getAll();
                    boolean found = false;
                    for (Loan l : allLoans) {
                        if (l.getBook().getId() == bookIdToReturn && l.getReturnDate() == null) {
                            l.setReturnDate(LocalDate.now().toString());
                            Book b = l.getBook();
                            b.setStatus("AVAILABLE");
                            
                            bookDao.update(b);
                            loanDao.update(l);
                            System.out.println("Kitap başarıyla geri alındı.");
                            found = true;
                            break;
                        }
                    }
                    if (!found) System.out.println("Bu kitap için aktif bir ödünç kaydı bulunamadı.");
                    break;

                case 0:
                    System.out.println("Çıkış yapılıyor...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Geçersiz seçim!");
            }
        }
    }
}