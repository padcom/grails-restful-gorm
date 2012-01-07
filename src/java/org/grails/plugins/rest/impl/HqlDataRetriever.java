package org.grails.plugins.rest.impl;

import java.util.List;

import org.codehaus.groovy.grails.commons.GrailsClass;
import org.grails.plugins.rest.DataRetriever;
import org.grails.plugins.rest.DomainClassResolver;
import org.grails.plugins.rest.url.DomainClassElement;
import org.grails.plugins.rest.url.Element;

public class HqlDataRetriever implements DataRetriever {

	private final DomainClassResolver domainClassResolver;
	private final HqlQueryBuilder hqlBuilder;
	
	public HqlDataRetriever(DomainClassResolver domainClassResolver) {
		this.domainClassResolver = domainClassResolver;
		this.hqlBuilder = new HqlQueryBuilder();
	}
	
	@Override
	public Object retrieve(List<Element> elements) throws Exception {
		DomainClassElement rootElement = (DomainClassElement) elements.get(0);
		GrailsClass root = domainClassResolver.resolve(rootElement.name);
		HqlQuery hql = hqlBuilder.buildFromElements(root, elements);
		return null;
	}

}
