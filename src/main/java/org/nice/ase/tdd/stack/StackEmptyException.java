package org.nice.ase.tdd.stack;

public class StackEmptyException extends RuntimeException {
    private String msg;

    public StackEmptyException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
