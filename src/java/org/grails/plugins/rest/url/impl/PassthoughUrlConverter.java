package org.grails.plugins.rest.url.impl;

import org.grails.plugins.rest.url.UrlConverter;

public class PassthoughUrlConverter implements UrlConverter {

	@Override
	public String convert(String input) {
		return input;
	}

}
