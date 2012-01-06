package org.grails.plugins.rest.url.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.grails.plugins.rest.url.DomainClassElement;
import org.grails.plugins.rest.url.Element;
import org.grails.plugins.rest.url.IdentifierElement;
import org.grails.plugins.rest.url.PropertyElement;
import org.grails.plugins.rest.url.UrlParser;
import org.grails.plugins.rest.url.UrlParserException;

public class DefaultUrlParser implements UrlParser {
	private enum ParserState {
		ENTITY,
		IDENTIFIER
	}
	
	@Override
	public List<Element> parse(String url) throws UrlParserException {
		List<Element> result = new ArrayList<Element>();
		
		String[] elements = url.split("/");
		int index = 0;
		ParserState state = ParserState.ENTITY;
		while (index < elements.length) {
			if (!StringUtils.isBlank(elements[index])) {
				switch (state) {
				case ENTITY:
					if (isValidIdentifier(elements, index)) {
						result.add(new DomainClassElement(elements[index]));
						state = ParserState.IDENTIFIER;
					} else {
						throw new UrlParserException("Expected entity name");
					}
					break;
				case IDENTIFIER:
					if (StringUtils.isNumeric(elements[index])) {
						result.add(new IdentifierElement(Long.parseLong(elements[index])));
					} else {
						result.add(new PropertyElement(elements[index]));
					}
					break;
				}
			}
			index++;
		}
		return result;
	}

	private boolean isValidIdentifier(String[] elements, int index) {
		return !CharUtils.isAsciiNumeric(elements[index].charAt(0)) && StringUtils.isAlphanumeric(elements[index]);
	}
}
