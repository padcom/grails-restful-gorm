package org.grails.plugins.rest.utils;

public class PassthoughUrlConverter implements UrlConverter {

	@Override
	public String convert(String input) {
		return input;
	}

}
