package com.WordWrap;

import static org.junit.Assert.*;

import org.junit.Test;


public class WordWrapTests {

	@Test
	public void rowShorterThanRowLenghtDoesntGetWrapped(){
		String initialRow = "abcdefg";
		
		String wrappedRow = wrapRow(initialRow, 10);
		
		assertEquals("abcdefg", wrappedRow);
	}

	private String wrapRow(String initialRow, int rowLength) {
		return initialRow;
	}
}
