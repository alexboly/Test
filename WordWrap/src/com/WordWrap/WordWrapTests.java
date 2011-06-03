package com.WordWrap;

import org.junit.Ignore;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class WordWrapTests {

    private int startPosition;
	private int endPosition;
	private String nextRow;

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
    public void textContainsOnlySpacesAfterFirstLine(){
        String initialRow = "abc         ";
        int wantedRowLength = 3;

        List<String> expectedText = new LinkedList<String>();
        expectedText.add("abc");

        List<String> actualText = wrapText(initialRow, wantedRowLength);
        assertThat(actualText, is(expectedText));    	
    }

    private List<String> wrapText(String text, int wantedRowLength) {
        List<String> rows = new LinkedList<String>();

        //return wrapText(text, wantedRowLength, rows);
        return wrapTextRecursively(text, wantedRowLength, rows);
    }

	private List<String> wrapText(String text, int wantedRowLength,	List<String> rows) {
		if (text.length() < wantedRowLength) {
            rows.add(text);
        } else {
            startPosition = 0;
            endPosition = wantedRowLength;

            while (startPosition < text.length()) {
            	int numberOfSpacesAtBeginning = countLeadingSpaces(text, startPosition);
                startPosition += numberOfSpacesAtBeginning;
                endPosition += numberOfSpacesAtBeginning;

                nextRow = getNextRow(text, startPosition, endPosition);
                if(!nextRow.isEmpty()){
                	rows.add(nextRow);
                }

                startPosition += wantedRowLength;
                endPosition += wantedRowLength;
                if (endPosition > (text.length())) {
                    endPosition = text.length();
                }
            }
        }

        return rows;
	}
	
	private List<String> wrapTextRecursively(String text, int wantedRowLength, List<String> rows){
		text = text.trim();
		
		if(text.length() == 0){
			return rows;
		}

		if(text.length() < wantedRowLength){
			rows.add(text);
			return rows;
		}
		
        nextRow = getNextRow(text, 0, wantedRowLength);
        if(!nextRow.isEmpty()){
        	rows.add(nextRow);
        }
        
        text = text.substring(nextRow.length());
        return wrapTextRecursively(text, wantedRowLength, rows);
	}

	private int countLeadingSpaces(String text, int initialPosition) {
		int numberOfSpacesAtBeginning = 0;
		while (text.substring(initialPosition).startsWith(" ")) {
			++numberOfSpacesAtBeginning;
		    initialPosition++;
		}
		return numberOfSpacesAtBeginning;
	}

    private String getNextRow(String text, int startPosition, int endPosition) {
    	if(endPosition > text.length()){
    		return "";
    	}
        return text.substring(startPosition, endPosition).trim();
    }
}
