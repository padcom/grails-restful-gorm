package org.grails.plugins.rest.impl;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.groovy.grails.commons.GrailsClass;
import org.codehaus.groovy.grails.commons.GrailsDomainClass;
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty;
import org.grails.plugins.rest.url.Element;
import org.grails.plugins.rest.url.IdentifierElement;
import org.grails.plugins.rest.url.PropertyElement;

public class HqlQueryBuilder {
	private static final String ALIASES = "abcdefghijklmnopqrstuvwxyz";

	private String hql;
	private String select;
	private String join;
	private String where;

	private int lastAliasIndex;
	private boolean lastIsCollection;
	private String lastIdentifier;
	private GrailsDomainClass currentEntity;

	private final Map<Class<?>, ElementProcessor> processors = new HashMap<Class<?>, ElementProcessor>();

	public HqlQueryBuilder() {
		processors.put(PropertyElement.class, new PropertyElementProcessor());
		processors.put(IdentifierElement.class, new IdentifierElementProcessor());
	}

	public HqlQuery buildFromElements(GrailsClass root, List<Element> elements) {
		hql = " from " + root.getNaturalName() + " a ";
		select = "select a";
		join = "";
		where = "";
		lastIsCollection = true;
		lastIdentifier = root.getName();
		lastAliasIndex = 0;
		currentEntity = (GrailsDomainClass) root;
		for (int i = 1; i < elements.size(); i++) {
			processors.get(elements.get(i).getClass()).process(elements.get(i));
		}
		where = isBlank(where) ? "" : "where " + where;
		return new HqlQuery((select + hql + join + where).trim(), lastIdentifier, lastIsCollection);
	}

	private interface ElementProcessor {
		void process(Element item);
	}

	private class PropertyElementProcessor implements ElementProcessor {
		@Override
		public void process(Element item) {
			PropertyElement element = (PropertyElement) item;
			GrailsDomainClassProperty[] properties = currentEntity.getProperties();
			GrailsDomainClassProperty property = findPropertyForElement(properties, element);
			if (isDomainClassField(property)) {
				processDomainClassField(element, property);
			} else {
				processPrimitiveField(element);
			}
			lastIdentifier = element.name;
			currentEntity = property.getReferencedDomainClass();
		}

		private GrailsDomainClassProperty findPropertyForElement(GrailsDomainClassProperty[] properties, PropertyElement element) {
			for (int i = 0; i < properties.length; i++) {
				if (properties[i].getName().equals(element.name)) {
					return properties[i];
				}
			}
			return null;
		}

		private boolean isDomainClassField(GrailsDomainClassProperty f) {
			return f.getReferencedDomainClass() != null;
		}

		@SuppressWarnings("unchecked")
		private void processDomainClassField(PropertyElement element, GrailsDomainClassProperty field) {
			char last = ALIASES.charAt(lastAliasIndex++);
			char current = ALIASES.charAt(lastAliasIndex);
			join += String.format("inner join %s.%s %s ", last, element.name, current);
			select = String.format("select %s", current);
			lastIsCollection = field.getType().isAssignableFrom(Collection.class);
		}

		private void processPrimitiveField(PropertyElement element) {
			select += String.format(".%s", element.name);
			lastIsCollection = false;
		}
	}

	private class IdentifierElementProcessor implements ElementProcessor {
		@Override
		public void process(Element item) {
			IdentifierElement element = (IdentifierElement) item;
			char current = ALIASES.charAt(lastAliasIndex);
			if (where != "") where += " and ";
			where += String.format("%s.id = %d", current, element.id);
			lastIsCollection = false;
		}
	}
}
