package org.grails.plugins.rest;

import java.lang.reflect.Field;

import org.codehaus.groovy.grails.commons.DomainClassArtefactHandler;
import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.codehaus.groovy.grails.commons.GrailsClass;

public class DomainClassElementResolver {
	private GrailsClass[] artefacts;
	
	public DomainClassElementResolver(GrailsApplication grailsApplication) {
		this.artefacts = grailsApplication.getArtefacts(DomainClassArtefactHandler.TYPE);
	}
	
	public GrailsClass resolve(String name) throws Exception {
		for (GrailsClass artifact : artefacts) {
			String exposedName = getExposedName(artifact.getClazz());
			if (exposedName != null && exposedName.equals(name)) {
				return artifact;
			}
		}
		return null;
	}
	
	private String getExposedName(Class<?> domainClass) throws Exception {
		for (Field field : domainClass.getDeclaredFields()) {
			if (field.getName().equals("expose")) {
				field.setAccessible(true);
				return (String) field.get(null);
			}
		}
		return null;
	}
}
