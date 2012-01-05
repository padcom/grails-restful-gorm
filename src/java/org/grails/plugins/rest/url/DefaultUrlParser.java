package org.grails.plugins.rest.url;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;

public class DefaultUrlParser implements UrlParser {
	private enum ParserState {
		ENTITY,
		IDENTIFIER,
		DONE
	}
	
	@Override
	public List<Element> parse(String url) throws UrlParserException {
		List<Element> result = new ArrayList<Element>();
		
		String[] elements = url.split("/");
		int index = 0;
		ParserState state = ParserState.ENTITY;
		while (!(ParserState.DONE.equals(state) || index >= elements.length)) {
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
