package llibraryproject;
class StudentNode {
    String name;
    StudentNode next;

    StudentNode(String name) {
        this.name = name;
        this.next = null;
    }
}

class WaitlistQueue {
    StudentNode head = null;
    StudentNode tail = null;

    public void addStudent(String name) {
        StudentNode node = new StudentNode(name);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        System.out.println("Student added to waitlist.");
    }

    public void processNextStudent() {
        if (head == null) {
            System.out.println("Waitlist empty.");
            return;
        }
        System.out.println("Processing: " + head.name);
        head = head.next;
        if (head == null)
            tail = null;
    }

    public void viewWaitlist() {
        if (head == null) {
            System.out.println("Waitlist empty.");
            return;
        }
        StudentNode temp = head;
        while (temp != null) {
            System.out.print(temp.name + " -> ");
            temp = temp.next;
        }
        System.out.println("END");
    }
} 