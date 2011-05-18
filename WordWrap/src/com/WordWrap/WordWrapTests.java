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

	private List<String> wrapText(String text, int wantedRowLength) {
		List<String> rows = new LinkedList<String>();

		if (text.length() < wantedRowLength) {
			rows.add(text);
		} else if (text.length() < 2 * wantedRowLength) {
			int startPosition = 0;
			int endPosition = wantedRowLength;
			while (startPosition < text.length()) {
				rows.add(substring(text, startPosition, endPosition));
				startPosition += wantedRowLength;
				if (endPosition + wantedRowLength > text.length()) {
					endPosition = text.length();
				} else {
					endPosition += wantedRowLength;
				}
			}
		} else {
			rows.add(substring(text, 0, wantedRowLength));
			rows.add(substring(text, wantedRowLength, 2 * wantedRowLength));
			rows.add(substring(text, 2 * wantedRowLength, text.length()));
		}

		return rows;
	}

	private String substring(String text, int startPosition, int endPosition) {
		return text.substring(startPosition, endPosition).trim();
	}
}
