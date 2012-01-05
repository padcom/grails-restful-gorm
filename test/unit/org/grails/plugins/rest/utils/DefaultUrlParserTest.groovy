package org.grails.plugins.rest.utils

import org.grails.plugins.rest.url.DefaultUrlParser
import org.grails.plugins.rest.url.DomainClassElement
import org.grails.plugins.rest.url.IdentifierElement
import org.grails.plugins.rest.url.PropertyElement
import org.grails.plugins.rest.url.UrlParser
import org.grails.plugins.rest.url.UrlParserException
import org.junit.Test

class DefaultUrlParserTest {
	// given
	UrlParser parser = new DefaultUrlParser()
	
	@Test(expected = UrlParserException)
	void 'will throw exception if an improper entity name is found'() {
		// when
		def actual = parser.parse("/1")
		
		// then an exception is thrown
	}
	
	@Test
	void 'will parse first-level url'() {
		// when
		def actual = parser.parse("/person")

		// then
		assert actual.size() == 1
		assert actual[0] instanceof DomainClassElement
		assert actual[0].name == "person"
	}

	@Test
	void 'will parse first-level url with id'() {
		// when
		def actual = parser.parse("/person/1")

		// then
		assert actual.size() == 2
		assert actual[0] instanceof DomainClassElement
		assert actual[0].name == "person"
		assert actual[1] instanceof IdentifierElement
		assert actual[1].id == 1L
	}

	@Test
	void 'will parse first-level url with property'() {
		// when
		def actual = parser.parse("/person/firstName")

		// then
		assert actual.size() == 2
		assert actual[0] instanceof DomainClassElement
		assert actual[0].name == "person"
		assert actual[1] instanceof PropertyElement
		assert actual[1].name == "firstName"
	}

	@Test
	void 'will parse first-level url with index with property'() {
		// when
		def actual = parser.parse("/person/1/firstName")

		// then
		assert actual.size() == 3
		assert actual[0] instanceof DomainClassElement
		assert actual[0].name == "person"
		assert actual[1] instanceof IdentifierElement
		assert actual[1].id == 1L
		assert actual[2] instanceof PropertyElement
		assert actual[2].name == "firstName"
	}

	@Test
	void 'will parse first-level url with property with index'() {
		// when
		def actual = parser.parse("/person/names/1")

		// then
		assert actual.size() == 3
		assert actual[0] instanceof DomainClassElement
		assert actual[0].name == "person"
		assert actual[1] instanceof PropertyElement
		assert actual[1].name == "names"
		assert actual[2] instanceof IdentifierElement
		assert actual[2].id == 1L
	}
}
