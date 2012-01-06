package org.grails.plugins.rest.impl;

import java.lang.reflect.Field;

import org.codehaus.groovy.grails.commons.DomainClassArtefactHandler;
import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.codehaus.groovy.grails.commons.GrailsClass;
import org.grails.plugins.rest.DomainClassResolver;

public class DefaultDomainClassResolver implements DomainClassResolver {
	private GrailsApplication grailsApplication;
	private GrailsClass[] artefacts;

	public DefaultDomainClassResolver(GrailsApplication grailsApplication) {
		this.grailsApplication = grailsApplication;
	}

	@Override
	public GrailsClass resolve(String name) throws Exception {
		for (GrailsClass artifact : getArtifacts()) {
			String exposedName = getExposedName(artifact.getClazz());
			if (exposedName != null && exposedName.equals(name)) {
				return artifact;
			}
		}
		return null;
	}

	private GrailsClass[] getArtifacts() {
		if (artefacts == null) {
			artefacts = grailsApplication.getArtefacts(DomainClassArtefactHandler.TYPE);
		}
		return artefacts;
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
