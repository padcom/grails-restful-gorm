package org.grails.plugins.rest;

import java.util.List;

import org.grails.plugins.rest.url.Element;

public interface DataRetriever {
	public Object retrieve(List<Element> elements) throws Exception;
}
