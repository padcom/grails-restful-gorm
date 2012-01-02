import org.codehaus.groovy.grails.commons.ConfigurationHolder

class RestfulUrlUrlMappings {
	static mappings = {
		def config = ConfigurationHolder.config.grails.'restful-gorm'
		def root = config.root ?: '/api'

		"${root}/**"(controller: 'restfulGorm', action: 'index')
	}
}
