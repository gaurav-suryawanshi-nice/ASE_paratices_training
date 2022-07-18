package org.nice.ase.tdd.stack;

public class StackNode {
    private int value;

    private StackNode next;

    public StackNode(int value, StackNode next) {
        this.value = value;
        this.next = next;
    }

    public int getValue() {
        return value;
    }

    public StackNode getNext() {
        return next;
    }
}
