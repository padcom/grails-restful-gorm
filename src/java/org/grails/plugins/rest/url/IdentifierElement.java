package org.grails.plugins.rest.url;

public class IdentifierElement extends Element {
	public final Long id;
	
	public IdentifierElement(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return String.format("IdentifierElement(id: %s)", id);
	}
}
