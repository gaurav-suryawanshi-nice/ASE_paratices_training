package org.nice.ase.tdd.stack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListBasesStackTest {

    private LinkedListBasesStack sut;

    @BeforeEach
    void setUp() {
        sut = new LinkedListBasesStack();
    }

    @Test
    public void isStackEmptyTest() {
        //arrange
        LinkedListBasesStack sut = new LinkedListBasesStack();
        //act
        boolean result = sut.isEmpty();
        //assert
        assertTrue(result);
    }

    @Test
    public void afterPushOneStackShouldNotEmptyTest() {
        //arrange
        LinkedListBasesStack sut = new LinkedListBasesStack();
        sut.push(42);
        //act
        boolean result = sut.isEmpty();
        //assert
        assertFalse(result);
    }

    @Test
    public void afterPushOneAndPopOneStackShouldEmptyTest() {
        //arrange
        LinkedListBasesStack sut = new LinkedListBasesStack();
        sut.push(42);
        int popped = sut.pop();
        //act
        boolean result = sut.isEmpty();
        //assert
        assertTrue(result);
    }

    @Test
    public void afterPushTwoAndPopOneStackShouldNotEmptyTest() {
        //arrange
        LinkedListBasesStack sut = new LinkedListBasesStack();
        sut.push(42);
        sut.push(7);
        sut.pop();
        //act
        boolean result = sut.isEmpty();
        //assert
        assertFalse(result);
    }

    @Test
    public void afterPushOneAndPopOneStackShouldBeSameTest() {
        //arrange
        sut.push(42);
        //act
        int popped = sut.pop();
        //assert
        assertEquals(42, popped);
    }

    @Test
    public void afterPushTwoAndPopTwoStackShouldBeInReverseOrderTest() {
        //arrange
        LinkedListBasesStack sut = new LinkedListBasesStack();
        sut.push(42);
        sut.push(7);
        //act
        int poppedFirst = sut.pop();
        int poppedSecond = sut.pop();
        //assert
        assertEquals(7, poppedFirst);
        assertEquals(42, poppedSecond);
    }

    @Test
    public void ShouldThrowErrorWhenPopFromEmptyStackTest() {
        //arrange
        LinkedListBasesStack sut = new LinkedListBasesStack();
        //act
        StackEmptyException exception = Assertions.assertThrows(StackEmptyException.class, () -> {
            sut.pop();

        });
        //assert
        assertEquals("Stack is empty", exception.getMessage());

    }
}
