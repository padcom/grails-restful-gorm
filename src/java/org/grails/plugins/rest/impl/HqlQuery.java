package org.grails.plugins.rest.impl;

public class HqlQuery {
	private final String query;
	private final String lastIdentifier;
	private final boolean lastIsCollection;

	public HqlQuery(String query, String lastIdentifier, boolean lastIsCollection) {
		this.query = query;
		this.lastIdentifier = lastIdentifier;
		this.lastIsCollection = lastIsCollection;
	}

	public String getQuery() {
		return query;
	}

	public String getLastIdentifier() {
		return lastIdentifier;
	}

	public boolean getLastIsCollection() {
		return lastIsCollection;
	}
}
