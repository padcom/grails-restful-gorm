package org.grails.plugins.rest.impl;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.Collection;
import java.util.List;

import org.codehaus.groovy.grails.commons.GrailsClass;
import org.codehaus.groovy.grails.commons.GrailsDomainClass;
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty;
import org.grails.plugins.rest.url.Element;
import org.grails.plugins.rest.url.IdentifierElement;
import org.grails.plugins.rest.url.PropertyElement;

public class HqlQueryBuilder {
	@SuppressWarnings("unchecked")
	public HqlQuery buildFromElements(GrailsClass root, List<Element> elements) {
		String hql = " from " + root.getNaturalName() + " a ";
		String select = "select a";
		String join = "";
		String where = "";
		String aliases = "abcdefghijklmnopqrstuvwxyz";
		int lastAliasIndex = 0;
		
		GrailsDomainClass c = (GrailsDomainClass) root;
		boolean lastIsCollection = true;
		String lastIdentifier = root.getName();

		for (int i = 1; i < elements.size(); i++) {
			if (elements.get(i) instanceof PropertyElement) {
				PropertyElement element = (PropertyElement) elements.get(i);
				GrailsDomainClassProperty f = null;
				GrailsDomainClassProperty[] properties = c.getProperties();
				for (int j = 0; j < properties.length; j++) {
					if (properties[j].getName().equals(element.name)) {
						f = properties[j];
						break;
					}
				}
				if (f.getReferencedDomainClass() != null) {
					char last = aliases.charAt(lastAliasIndex++);
					char current = aliases.charAt(lastAliasIndex);
					join += String.format("inner join %s.%s %s ", last, element.name, current);
					select = String.format("select %s", current);
					lastIsCollection = f.getType().isAssignableFrom(Collection.class);
				} else {
					select += String.format(".%s", element.name);
					lastIsCollection = false;
				}
				lastIdentifier = element.name;
				c = f.getReferencedDomainClass();
			} else if (elements.get(i) instanceof IdentifierElement) {
				char current = aliases.charAt(lastAliasIndex);
				IdentifierElement element = (IdentifierElement) elements.get(i);
				if (where != "") where += " and ";
				where += String.format("%s.id = %d", current, element.id);
				lastIsCollection = false;
			}
		}
		
		return new HqlQuery((select + hql + join + (isBlank(where) ? "" : "where " + where)).trim(), lastIdentifier, lastIsCollection);
	}
}
