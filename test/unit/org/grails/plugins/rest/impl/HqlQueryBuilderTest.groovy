package org.grails.plugins.rest.impl

import grails.test.mixin.Mock;

import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.grails.plugins.rest.test.City
import org.grails.plugins.rest.test.Address
import org.grails.plugins.rest.test.Person
import org.grails.plugins.rest.url.*
import org.grails.plugins.rest.url.impl.DefaultUrlParser;
import org.junit.Before;
import org.junit.Test;

@Mock([ City, Address, Person ])
class HqlQueryBuilderTest {
	
	private buildHqlQuery(url) {
		def elements = new DefaultUrlParser().parse(url)
		def root = new DefaultDomainClassResolver(grailsApplication).resolve(elements[0].name)

		return new HqlQueryBuilder().buildFromElements(root, elements).query	
	}
		
	@Test
	void 'will retrieve data for request when there is only the root given'() {
		// when
		def actual = buildHqlQuery("/person")
		
		// then
		assert actual == 'select a from Person a'
	}
		
	@Test
	void 'will retrieve data for request when there the root with ID given'() {
		// when
		def actual = buildHqlQuery("/person/1")
		
		// then
		assert actual == 'select a from Person a where a.id = 1'
	}
		
	@Test
	void 'will retrieve data for request when there the root with ID given and a property'() {
		// when
		def actual = buildHqlQuery("/person/1/firstName")
		
		// then
		assert actual == 'select a.firstName from Person a where a.id = 1'
	}

	@Test
	void 'will retrieve data for request when there the root with ID given and a relation'() {
		// when
		def actual = buildHqlQuery("/person/1/address")
		
		// then
		assert actual == 'select b from Person a inner join a.address b where a.id = 1'
	}

	@Test
	void 'will retrieve data for request when there the root with ID given and a relation with ID'() {
		// when
		def actual = buildHqlQuery("/person/1/address/1")
		
		// then
		assert actual == 'select b from Person a inner join a.address b where a.id = 1 and b.id = 1'
	}

	@Test
	void 'will retrieve data for request when there the root with ID given and a relation with ID and property'() {
		// when
		def actual = buildHqlQuery("/person/1/address/1/street")
		
		// then
		assert actual == 'select b.street from Person a inner join a.address b where a.id = 1 and b.id = 1'
	}

	@Test
	void 'will retrieve data for request when there the root with ID given and a relation with ID and association property'() {
		// when
		def actual = buildHqlQuery("/person/1/address/1/city")
		
		// then
		assert actual == 'select c from Person a inner join a.address b inner join b.city c where a.id = 1 and b.id = 1'
	}
}
