package org.grails.plugins.rest.url;

import java.util.List;

public interface UrlParser {
	List<Element> parse(String url) throws UrlParserException;
}
