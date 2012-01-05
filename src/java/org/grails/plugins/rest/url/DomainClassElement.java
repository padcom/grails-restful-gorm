package org.grails.plugins.rest.url;

public class DomainClassElement extends Element {
	public final String name;
	
	public DomainClassElement(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return String.format("DomainClassElement(name: %s)", name);
	}
}
