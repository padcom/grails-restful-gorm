package org.grails.plugins.rest.impl

import grails.test.mixin.Mock;

import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.grails.plugins.rest.test.Address
import org.grails.plugins.rest.test.Person
import org.grails.plugins.rest.url.*
import org.junit.Before;
import org.junit.Test;

@Mock([ Address, Person ])
class GormDataRetrieverTest {
	
	@Before
	void 'configure database'() {
		def person = new Person(firstName: "John", lastName: "Doe")
		person.addToAddresses(new Address(city: "Neverland", street: "Rosemary st."))
		person.save()
		
		person = new Person(firstName: "Jane", lastName: "Smith")
		person.addToAddresses(new Address(city: "New York", street: "1st st"))
		person.addToAddresses(new Address(city: "Dallas", street: "Some street"))
		person.save()
	}

	private retrieveData(elements) {
		def root = new DefaultDomainClassResolver(grailsApplication).resolve(elements[0].name)?.clazz
		
		def query = root.createCriteria()
		return query.list {
			for (def i = 1; i < elements.size(); i++) {
				if (elements[i] instanceof IdentifierElement) {
					eq "id", elements[i].id
				} else if (elements[i] instanceof PropertyElement) {
					property elements[i].name
				}
			}
		}
	}
		
	@Test
	void 'will retrieve data for request when there is only the root given'() {
		// given
		def elements = [
			new DomainClassElement("person")
		]
		
		def actual = retrieveData(elements)
		
		// then
		assert actual.size() == 2
		assert actual[0].lastName == "Doe" 
	}
		
	@Test
	void 'will retrieve data for request when there the root with ID given'() {
		// given
		def elements = [
			new DomainClassElement("person"),
			new IdentifierElement(1L)
		]
		
		def actual = retrieveData(elements)
		
		// then
		assert actual.size() == 1
		assert actual[0].lastName == "Doe" 
	}
		
	@Test
	void 'will retrieve data for request when there the root with ID given and a property'() {
		// given
		def elements = [
			new DomainClassElement("person"),
			new IdentifierElement(2L),
			new PropertyElement("firstName"),
		]
		
		def actual = retrieveData(elements)
		
		// then
		assert actual.size() == 1
		assert actual == "Jane" 
	}
}
