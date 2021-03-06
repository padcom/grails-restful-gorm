package org.grails.plugins.rest.url;

public class PropertyElement extends Element {
	public final String name;
	
	public PropertyElement(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return String.format("PropertyElement(name: %s)", name);
	}
}
