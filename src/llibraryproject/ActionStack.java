package llibraryproject;
class ActionStack {
    String[] stack = new String[50];
    int top = -1;

    public void push(String action) {
        if (top < stack.length - 1)
            stack[++top] = action;
    }

    public void display() {
        if (top == -1) {
            System.out.println("No history.");
            return;
        }
        for (int i = top; i >= 0; i--) {
            System.out.println(stack[i]);
        }
    }
}