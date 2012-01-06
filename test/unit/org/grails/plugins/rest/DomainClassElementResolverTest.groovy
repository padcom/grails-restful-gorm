package org.grails.plugins.rest

import grails.test.mixin.Mock

import org.grails.plugins.rest.test.Address
import org.grails.plugins.rest.test.Person
import org.junit.Test

@Mock([ Address, Person ])
class DomainClassElementResolverTest {
	@Test
	void 'will resolve parsed URL to domain class'() {
		// given
		DomainClassElementResolver resolver = new DomainClassElementResolver(grailsApplication)
	
		// when
		def actual = resolver.resolve("person")

		// then
		assert actual.clazz == Person
	}

		@Test
	void 'will resolve to null if the domain class is not exposed'() {
		// given
		DomainClassElementResolver resolver = new DomainClassElementResolver(grailsApplication)
	
		// when
		def actual = resolver.resolve("address")

		// then
		assert actual == null
	}
}
