package org.grails.plugins.rest.impl;

import java.util.List;

import org.codehaus.groovy.grails.commons.GrailsClass;
import org.codehaus.groovy.grails.orm.hibernate.SessionFactoryHolder;
import org.grails.plugins.rest.DataRetriever;
import org.grails.plugins.rest.DomainClassResolver;
import org.grails.plugins.rest.url.DomainClassElement;
import org.grails.plugins.rest.url.Element;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;

public class HqlDataRetriever implements DataRetriever {

	private final DomainClassResolver domainClassResolver;
	private final SessionFactoryHolder sessionFactoryHolder;
	
	public HqlDataRetriever(DomainClassResolver domainClassResolver, SessionFactoryHolder sessionFactoryHolder) {
		this.domainClassResolver = domainClassResolver;
		this.sessionFactoryHolder = sessionFactoryHolder;
	}
	
	@Override
	public Object retrieve(List<Element> elements) throws Exception {
		DomainClassElement rootElement = (DomainClassElement) elements.get(0);
		GrailsClass root = domainClassResolver.resolve(rootElement.name);
		HqlQuery hql = new HqlQueryBuilder().buildFromElements(root, elements);
		SessionFactory sessionFactory = sessionFactoryHolder.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql.getQuery());
		return query.list();
	}

}
