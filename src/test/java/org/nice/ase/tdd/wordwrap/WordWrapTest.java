package org.nice.ase.tdd.wordwrap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordWrapTest {
    @Test
    public void onEmptyStringReturnEmptyStringTest() {
        //act
        String result = WordWrapper.wrap("", 2);
        //assert
        assertEquals("", result);
    }

    @Test
    public void onStringShorterThenWidthReturnStringTest() {
        //act
        String result = WordWrapper.wrap("abc", 4);
        //assert
        assertEquals("abc", result);
    }

    @Test
    public void singleSplitTest() {
        //act
        String result = WordWrapper.wrap("abcd", 2);
        //assert
        assertEquals("ab\ncd", result);
    }

    @Test
    public void multipleSplitWithoutWhiteSpacesTest() {
        //act
        String result = WordWrapper.wrap("abcdefgh", 2);
        //assert
        assertEquals("ab\ncd\nef\ngh", result);
    }

    @Test
    public void multipleSplitWithWhiteSpacesTest() {
        String result = WordWrapper.wrap("ab cdef gh ij", 3);
        assertEquals("ab\ncde\nf\ngh\nij", result);
    }
}
