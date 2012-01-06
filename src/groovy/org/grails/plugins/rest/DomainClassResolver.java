package org.grails.plugins.rest;

import org.codehaus.groovy.grails.commons.GrailsClass;

public interface DomainClassResolver {
	public GrailsClass resolve(String name) throws Exception;
}
