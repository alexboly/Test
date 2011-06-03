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
    public void getTextWrappedOnTwoRowsWithTwoStartingSpaces() {
        String initialRow = "abc  def";
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

    @Test
    public void textContainsOnlySpacesAfterFirstLine() {
        String initialRow = "abc         ";
        int wantedRowLength = 3;

        List<String> expectedText = new LinkedList<String>();
        expectedText.add("abc");

        List<String> actualText = wrapText(initialRow, wantedRowLength);
        assertThat(actualText, is(expectedText));
    }

    private List<String> wrapText(String text, int wantedRowLength) {
        // return wrapTextIterative(text, wantedRowLength);
        // return wrapTextRecursively(text, wantedRowLength, new LinkedList<String>());

        StringWrapper stringWrapper = new StringWrapper(text);
        return stringWrapper.wrapText(wantedRowLength);
    }


    private List<String> wrapTextIterative(String text, int wantedRowLength) {
        List<String> rows = new LinkedList<String>();
        text = text.trim();
        while (text.length() > 0) {
            text = text.trim();
            String nextRow = getNextRow(text, wantedRowLength);
            rows.add(nextRow);
            text = removeLeadingText(text, nextRow);
        }

        return rows;
    }

    private List<String> wrapTextRecursively(String text, int wantedRowLength, List<String> rows) {
        text = text.trim();
        if (text.length() == 0) {
            return rows;
        }
        String nextRow = getNextRow(text, wantedRowLength);
        rows.add(nextRow);
        text = removeLeadingText(text, nextRow);

        return wrapTextRecursively(text, wantedRowLength, rows);
    }

    private String removeLeadingText(String text, String leadingText) {
        return text.substring(leadingText.length());
    }

    private String getNextRow(String text, int endPosition) {
        if (endPosition > text.length()) {
            return text;
        }
        return text.substring(0, endPosition).trim();
    }

    class StringWrapper {
        private String text;

        public StringWrapper(String text) {
            this.text = text;
        }

        public List<String> wrapText(int wantedRowLength) {
            return this.wrapTextRecursively(wantedRowLength, new LinkedList<String>());
        }

        private List<String> wrapTextRecursively(int wantedRowLength, List<String> rows) {
            text = text.trim();
            if (text.length() == 0) {
                return rows;
            }
            String nextRow = extractNextRow(wantedRowLength);
            rows.add(nextRow);

            return this.wrapTextRecursively(wantedRowLength, rows);
        }

        private String extractNextRow(int endPosition) {
            String leadingText;
            leadingText = (endPosition > text.length()) ? text : text.substring(0, endPosition).trim();
            text = text.substring(leadingText.length());
            return leadingText;
        }
    }
}
