package org.grails.plugins.rest.utils;

import org.junit.Test

public class PassthoughUrlConverterTest {
	@Test
	void 'will return exactly the same text as the input string'() {
		// given
		UrlConverter converter = new PassthoughUrlConverter()
		
		// when
		String actual = converter.convert('/big-brown-fox/jumberOver/a/lazy/dog')
		
		// then
		assert actual == '/big-brown-fox/jumberOver/a/lazy/dog';
	}
}
