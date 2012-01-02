package org.grails.plugins.rest

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class RestfulGormController {
	def index() {
		def config = ConfigurationHolder.config.grails.'restful-gorm'
		def root = config.root ?: '/api'
		def path = request.forwardURI - request.contextPath - root

		render text: path, contentType: 'text/plain'
	}
}
