package org.grails.plugins.rest.url.impl;

import org.grails.plugins.rest.url.UrlConverter;

public class HyphenatedUrlConverter implements UrlConverter {
	public String convert(String input) {
		char[] chars = stringToArrayOfChar(input);
		upperCaseCharacters(chars);
		return constructOutputString(chars);
	}

	private static char[] stringToArrayOfChar(String input) {
		char[] result = new char[input.length()];
		input.getChars(0, input.length(), result, 0);
		return result;
	}

	private static void upperCaseCharacters(char[] chars) {
		for (int i = 0; i < chars.length - 1; i++) {
			if (chars[i] == '-') {
				chars[i + 1] = Character.toUpperCase(chars[i + 1]); 
			}
		}
	}

	private static String constructOutputString(char[] chars) {
		int outputSize = countOutputStringLength(chars);
		char[] output = new char[outputSize];
		int outputIndex = 0;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] != '-') {
				output[outputIndex++] = chars[i];
			}
		}
		return new String(output);
	}

	private static int countOutputStringLength(char[] chars) {
		int outputSize = chars.length;
		for (int i = 0; i < chars.length - 1; i++) {
			if (chars[i] == '-') {
				outputSize--;
			}
		}
		return outputSize;
	}
}
