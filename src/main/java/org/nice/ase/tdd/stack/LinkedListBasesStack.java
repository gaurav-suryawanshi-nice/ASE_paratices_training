package org.nice.ase.tdd.stack;

public class LinkedListBasesStack {

    private int size = 0;
    private StackNode head;

    public boolean isEmpty() {
        return size == 0;
    }

    public void push(int element) {
        head = new StackNode(element, head);
        size++;
    }


    public int pop() {
        if (size == 0) {
            throw new StackEmptyException("Stack is empty");
        }
        size--;
        int element = head.getValue();
        head = head.getNext();
        return element;
    }
}
