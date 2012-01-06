package org.grails.plugins.rest.test

import grails.persistence.Entity

@Entity
class Person {
	String firstName
	String lastName
	
	static expose = 'person'
	
	static hasMany = [ addresses: Address ]}
