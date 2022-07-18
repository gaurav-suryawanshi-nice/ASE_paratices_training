package org.nice.ase.tdd.stack;

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
        //Given
        LinkedListBasesStack sut = new LinkedListBasesStack();
        //When
        boolean result = sut.isEmpty();
        //Then
        assertTrue(result);
    }

    @Test
    public void afterPushOneStackShouldNotEmptyTest() {
        //Given
        LinkedListBasesStack sut = new LinkedListBasesStack();
        sut.push(42);
        //When
        boolean result = sut.isEmpty();
        //Then
        assertFalse(result);
    }

    @Test
    public void afterPushOneAndPopOneStackShouldEmptyTest() {
        //Given
        LinkedListBasesStack sut = new LinkedListBasesStack();
        sut.push(42);
        int popped = sut.pop();
        //When
        boolean result = sut.isEmpty();
        //Then
        assertTrue(result);
    }

    @Test
    public void afterPushTwoAndPopOneStackShouldNotEmptyTest() {
        //Given
        LinkedListBasesStack sut = new LinkedListBasesStack();
        sut.push(42);
        sut.push(7);
        int popped = sut.pop();
        //When
        boolean result = sut.isEmpty();
        //Then
        assertFalse(result);
    }

    @Test
    public void afterPushOneAndPopOneStackShouldBeSameTest() {
        //Given
        sut.push(42);
        //When
        int popped = sut.pop();
        //Then
        assertEquals(42, popped);
    }

    @Test
    public void afterPushTwoAndPopTwoStackShouldBeInReverseOrderTest() {
        //Given
        LinkedListBasesStack sut = new LinkedListBasesStack();
        sut.push(42);
        sut.push(7);
        //When
        int poppedFirst = sut.pop();
        int poppedSecond = sut.pop();
        //Then
        assertEquals(7, poppedFirst);
        assertEquals(42, poppedSecond);
    }

    @Test
    public void ShouldThrowErrorWhenPopFromEmptyStackTest() {
        //Given
        boolean isExceptionThrown = false;
        LinkedListBasesStack sut = new LinkedListBasesStack();
        //When
        try {
            int poppedFirst = sut.pop();
        } catch (Exception e) {
            isExceptionThrown = true;
        }
        //Then
        assertEquals(true, isExceptionThrown);

    }
}
