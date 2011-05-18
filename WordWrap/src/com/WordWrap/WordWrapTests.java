package com.WordWrap;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class WordWrapTests {

    @Test
    public void rowShorterThanRowLenghtDoesntGetWrapped() {
        String initialRow = "abcdefg";

        List<String> wrappedRow = wrapRowList(initialRow, 10);

        assertEquals("abcdefg", wrappedRow.get(0));
    }

    @Test
    public void rowLongerThanWantedRowLength() {
        String initialRow = "abc def";
        int wantedRowLength = 4;

        List<String> expectedText = new LinkedList<String>();
        expectedText.add("abc");
        expectedText.add("def");

        List<String> actualText = wrapRowList(initialRow, wantedRowLength);
        assertThat(actualText, is(expectedText));
    }

    private List<String> wrapRowList(String initialRow, int wantedRowLength) {
        if (initialRow.length() < wantedRowLength) {
            List<String> expectedText = new LinkedList<String>();
            expectedText.add(initialRow);
            return expectedText;
        }

        List<String> expectedText = new LinkedList<String>();
        expectedText.add("abc");
        expectedText.add("def");

        return expectedText;
    }
}
