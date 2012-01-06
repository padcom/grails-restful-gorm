package org.grails.plugins.rest.url

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
	
	@Test(expected = UrlParserException)
	void 'will throw exception if an entity name is found with invalid characters'() {
		// when
		def actual = parser.parse("/a+")

		// then an exception is thrown
	}

	@Test
	void 'will parse first-level url'() {
		// when
		def actual = parser.parse("/person")

		// then
		assert actual.size() == 1
		assert actual[0].toString() == 'DomainClassElement(name: person)'
	}

	@Test
	void 'will parse first-level url with id'() {
		// when
		def actual = parser.parse("/person/1")

		// then
		assert actual.size() == 2
		assert actual[0].toString() == 'DomainClassElement(name: person)'
		assert actual[1].toString() == 'IdentifierElement(id: 1)'
	}

	@Test
	void 'will parse first-level url with property'() {
		// when
		def actual = parser.parse("/person/firstName")

		// then
		assert actual.size() == 2
		assert actual[0].toString() == 'DomainClassElement(name: person)'
		assert actual[1].toString() == 'PropertyElement(name: firstName)'
	}

	@Test
	void 'will parse first-level url with index with property'() {
		// when
		def actual = parser.parse("/person/1/firstName")

		// then
		assert actual.size() == 3
		assert actual[0].toString() == 'DomainClassElement(name: person)'
		assert actual[1].toString() == 'IdentifierElement(id: 1)'
		assert actual[2].toString() == 'PropertyElement(name: firstName)'
	}

	@Test
	void 'will parse first-level url with property with index'() {
		// when
		def actual = parser.parse("/person/names/1")

		// then
		assert actual.size() == 3
		assert actual[0].toString() == 'DomainClassElement(name: person)'
		assert actual[1].toString() == 'PropertyElement(name: names)'
		assert actual[2].toString() == 'IdentifierElement(id: 1)'
	}
}
