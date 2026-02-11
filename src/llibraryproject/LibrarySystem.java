package llibraryproject;

import java.util.Scanner;


public class LibrarySystem {


    static LibraryDatabase db = new LibraryDatabase();
    static WaitlistQueue waitlist = new WaitlistQueue();
    static ActionStack history = new ActionStack();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== LIBRARY LOGIN SYSTEM ===");
            System.out.println("1. Login as Admin");
            System.out.println("2. Login as Student");
            System.out.println("3. Exit System");
            System.out.print("Enter choice: ");
            int loginChoice = sc.nextInt();
            sc.nextLine(); // consume newline

            if (loginChoice == 1) {
                System.out.print("Enter Admin Password: ");
                String pass = sc.nextLine();
                if (pass.equals("admin123")) { // Simple password check
                    adminMenu();
                } else {
                    System.out.println("Wrong password!");
                }
            } else if (loginChoice == 2) {
                System.out.print("Enter Student Name: ");
                String studentName = sc.nextLine();
                System.out.println("Welcome, " + studentName);
                studentMenu(studentName);
            } else if (loginChoice == 3) {
                System.out.println("Shutting down...");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
        sc.close();
    }


    public static void adminMenu() {
        while (true) {
            System.out.println("\n--- ADMIN DASHBOARD ---");
            System.out.println("1. Add Book");
            System.out.println("2. Delete Book");
            System.out.println("3. Process Waitlist");
            System.out.println("4. View Waitlist");
            System.out.println("5. View Global History");
            System.out.println("6. Display All Books");
            System.out.println("7. Logout");
            System.out.print("Admin Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: // Add Book
                    System.out.print("ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Title: ");
                    String title = sc.nextLine();
                    System.out.print("Author: ");
                    String author = sc.nextLine();
                    System.out.print("Copies: ");
                    int copies = sc.nextInt();
                    db.addBook(id, title, author, copies);
                    history.push("Admin Added Book ID " + id);
                    break;
                case 2:
                    System.out.print("Enter ID to delete: ");
                    int did = sc.nextInt();
                    if (db.deleteBook(did))
                        System.out.println("Deleted successfully.");
                    else
                        System.out.println("Book not found.");
                    history.push("Admin Deleted Book ID " + did);
                    break;
                case 3: // Process Waitlist
                    waitlist.processNextStudent();
                    break;
                case 4: // View Waitlist
                    waitlist.viewWaitlist();
                    break;
                case 5: // History
                    history.display();
                    break;
                case 6: // Display
                    db.displayAll();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // --- STUDENT MENU (Limited Access) ---
    public static void studentMenu(String studentName) {
        while (true) {
            System.out.println("\n--- STUDENT DASHBOARD (" + studentName + ") ---");
            System.out.println("1. Search Book");
            System.out.println("2. Rent (Issue) Book");
            System.out.println("3. Return Book");
            System.out.println("4. View Library History");
            System.out.println("5. Display Available Books");
            System.out.println("6. Logout");
            System.out.print("Student Choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Search ID: ");
                    int sid = sc.nextInt();
                    int idx = db.binarySearch(sid);
                    if (idx != -1) {
                        Book b = db.getBook(idx);
                        System.out.println("Found: " + b.title + " by " + b.author);
                    } else {
                        System.out.println("Not Found.");
                    }
                    break;
                case 2: // Rent Book (Issue)
                    System.out.print("Enter ID to Rent: ");
                    int iid = sc.nextInt();
                    int iidx = db.binarySearch(iid);
                    if (iidx != -1) {
                        Book b = db.getBook(iidx);
                        if (b.copies > 0) {
                            b.copies--;
                            System.out.println("Book Rented Successfully!");
                            history.push("Student " + studentName + " Rented ID " + iid);
                        } else {
                            System.out.print("Out of stock. Join waitlist? (1 for Yes): ");
                            int w = sc.nextInt();
                            if (w == 1) {
                                waitlist.addStudent(studentName);
                            }
                        }
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;
                case 3: // Return Book
                    System.out.print("Enter ID to Return: ");
                    int rid = sc.nextInt();
                    int ridx = db.binarySearch(rid);
                    if (ridx != -1) {
                        db.getBook(ridx).copies++;
                        System.out.println("Book Returned.");
                        history.push("Student " + studentName + " Returned ID " + rid);
                    } else {
                        System.out.println("Book not found in library system.");
                    }
                    break;
                case 4: // View History
                    history.display();
                    break;
                case 5: // Display All
                    db.displayAll();
                    break;
                case 6:
                    return; // Returns to Login Menu
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}



