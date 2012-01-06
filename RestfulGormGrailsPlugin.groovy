import org.grails.plugins.rest.impl.DefaultDomainClassResolver;
import org.grails.plugins.rest.utils.HyphenatedUrlConverter;
import org.grails.plugins.rest.utils.PassthoughUrlConverter;
import org.grails.plugins.rest.url.DefaultUrlParser;

class RestfulGormGrailsPlugin {
    // the plugin version
    def version = "0.0.1-SNAPSHOT"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.0 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Restful Gorm Plugin" // Headline display name of the plugin
    def author = "Matthias Hryniszak"
    def authorEmail = "padcom@gmail.com"
    def description = '''
XML and JSON RESTful access to GORM-managed domain classes
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/restful-gorm"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
    def organization = [ name: "Aplaline", url: "http://www.aplaline.com/" ]

    // Any additional developers beyond the author specified above.
    def developers = [ [ name: "Matthias Hryniszak", email: "padcom@gmail.com" ]]

    // Location of the plugin's issue tracker.
    def issueManagement = [ system: "GitHub", url: "https://github.com/padcom/grails-restful-gorm/issues" ]

    // Online location of the plugin's browseable source code.
    def scm = [ url: "https://github.com/padcom/grails-restful-gorm" ]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
		if (application.config.grails.web.url.converter == 'hyphenated') {
			restfulUrlConverter(HyphenatedUrlConverter)
		} else {
			restfulUrlConverter(PassthoughUrlConverter)
		}
		urlParser(DefaultUrlParser)
		domainClassResolver(DefaultDomainClassResolver, application)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
