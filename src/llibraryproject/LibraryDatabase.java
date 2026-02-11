package llibraryproject;
class LibraryDatabase {
    Book[] books = new Book[100];
    int count = 0;

    // ADD BOOK (O(1)) 
    public void addBook(int id, String title, String author, int copies) {
        if (count == books.length) {
            System.out.println("Library is full!");
            return;
        }
        books[count++] = new Book(id, title, author, copies);
        sortBooks();
    }

    // BUBBLE SORT (O(n^2)) 
    public void sortBooks() {
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                if (books[j].id > books[j + 1].id) {
                    Book temp = books[j];
                    books[j] = books[j + 1];
                    books[j + 1] = temp;
                }
            }
        }
    }

    // BINARY SEARCH (O(log n)) 
    public int binarySearch(int id) {
        int left = 0;
        int right = count - 1; // [cite: 10] 
        while (left <= right) {
            int mid = (left + right) / 2;
            if (books[mid].id == id)
                return mid;
            else if (books[mid].id < id)
                left = mid + 1; // [cite: 12] 
            else
                right = mid - 1;
        }
        return -1;
    }

    // DELETE BOOK (O(n)) 
    public boolean deleteBook(int id) {
        int index = binarySearch(id);
        if (index == -1)
            return false;
        for (int i = index; i < count - 1; i++) {
            books[i] = books[i + 1]; // [cite: 17] 
        }
        count--;
        return true;
    }

    public Book getBook(int index) {
        if (index >= 0 && index < count)
            return books[index];
        return null;
    }
    public void displayAll() {
        if (count == 0) {
            System.out.println("Library is empty.");
            return;
        }
        System.out.println("\nID\tTitle\t\tCopies");
        for (int i = 0; i < count; i++) {
            System.out.println(books[i].id + "\t" + books[i].title + "\t\t" + books[i].copies);
        }
    }
}
