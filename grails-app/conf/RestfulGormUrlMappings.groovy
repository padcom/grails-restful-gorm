import org.codehaus.groovy.grails.commons.ConfigurationHolder

class UrlMappings {
	static mappings = {
		def config = ConfigurationHolder.config.grails.'restful-gorm'
		def root = config.root ? config.root : '/api'

		"/${root}/**"(controller: 'restfulGorm', action: 'index')	
	}
}
