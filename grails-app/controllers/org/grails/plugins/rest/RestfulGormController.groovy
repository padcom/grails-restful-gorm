package org.grails.plugins.rest

class RestfulGormController {
	def grailsApplication
	def restfulUrlConverter
	def urlParser

	def index() {
		def root = config.root ?: '/api'
		def path = request.forwardURI - request.contextPath - root

		render text: parsePath(path), contentType: 'text/plain'
	}

	private parsePath(String path) {
		"PARSED: ${urlParser.parse(restfulUrlConverter.convert(path))}"
	}

	private getConfig() {
		grailsApplication.config.grails.'restful-gorm'
	}
}
