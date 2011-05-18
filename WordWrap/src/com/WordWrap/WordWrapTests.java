package com.WordWrap;

import org.junit.Ignore;
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

        List<String> wrappedRow = wrapText(initialRow, 10);

        assertEquals("abcdefg", wrappedRow.get(0));
    }

    @Test
    public void rowLongerThanWantedRowLength() {
        String initialRow = "abc def";
        int wantedRowLength = 4;

        List<String> expectedText = new LinkedList<String>();
        expectedText.add("abc");
        expectedText.add("def");

        List<String> actualText = wrapText(initialRow, wantedRowLength);
        assertThat(actualText, is(expectedText));
    }

    @Test
    public void getTextWrappedOnTwoRows() {
        String initialRow = "abc def";
        int wantedRowLength = 3;

        List<String> expectedText = new LinkedList<String>();
        expectedText.add("abc");
        expectedText.add("def");

        List<String> actualText = wrapText(initialRow, wantedRowLength);
        assertThat(actualText, is(expectedText));
    }

    @Test
    public void getTextWrappedOnThreeRowsWithSpaces() {
        String initialRow = "a b c d e f";
        int wantedRowLength = 3;

        List<String> expectedText = new LinkedList<String>();
        expectedText.add("a b");
        expectedText.add("c d");
        expectedText.add("e f");

        List<String> actualText = wrapText(initialRow, wantedRowLength);
        assertThat(actualText, is(expectedText));
    }

    @Test
    public void textGetsWrappedOnThreeLines() {
        String initialRow = "abcdef abc";
        int wantedRowLength = 3;

        List<String> expectedText = new LinkedList<String>();
        expectedText.add("abc");
        expectedText.add("def");
        expectedText.add("abc");

        List<String> actualText = wrapText(initialRow, wantedRowLength);
        assertThat(actualText, is(expectedText));
    }

    @Test
    public void textGetsWrappedOnThreeLinesWithoutAnySpace() {
        String initialRow = "abcdefabc";
        int wantedRowLength = 3;

        List<String> expectedText = new LinkedList<String>();
        expectedText.add("abc");
        expectedText.add("def");
        expectedText.add("abc");

        List<String> actualText = wrapText(initialRow, wantedRowLength);
        assertThat(actualText, is(expectedText));
    }

    @Test
    public void textGetsWrappedOnFourLinesWithoutAnySpace() {
        String initialRow = "abcdefabcxyz";
        int wantedRowLength = 3;

        List<String> expectedText = new LinkedList<String>();
        expectedText.add("abc");
        expectedText.add("def");
        expectedText.add("abc");
        expectedText.add("xyz");

        List<String> actualText = wrapText(initialRow, wantedRowLength);
        assertThat(actualText, is(expectedText));
    }

    private List<String> wrapText(String text, int wantedRowLength) {
        List<String> rows = new LinkedList<String>();

        if (text.length() < wantedRowLength) {
            rows.add(text);
        } else {
            int startPosition = 0;
            int endPosition = wantedRowLength;
            while (startPosition < text.length()) {
                if (text.substring(startPosition).startsWith(" ")) {
                    startPosition++;
                    endPosition++;
                }

                rows.add(substring(text, startPosition, endPosition));

                startPosition += wantedRowLength;
                endPosition += wantedRowLength;
                if (endPosition > (text.length())) {
                    endPosition = text.length();
                }
            }
        }

        return rows;
    }

    private String substring(String text, int startPosition, int endPosition) {
        return text.substring(startPosition, endPosition).trim();
    }
}
