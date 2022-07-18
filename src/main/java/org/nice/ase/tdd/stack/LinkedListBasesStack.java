package org.nice.ase.tdd.stack;

public class LinkedListBasesStack {

    private int size = 0;
    private StackNode head;

    public boolean isEmpty() {
        return size == 0;
    }

    public void push(int element) {
        StackNode newElement = new StackNode(element, head);
        head = newElement;
        size++;
        String practice;
    }


    public int pop() {
        if (size > 0) {
            size--;
            int element = head.getValue();
            head = head.getNext();
            return element;
        } else {
            throw new RuntimeException("Stack is empty");
        }
    }
}
