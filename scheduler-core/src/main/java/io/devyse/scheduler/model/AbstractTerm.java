/**
 * @(#) AbstractTerm.java
 *
 * This file is part of the Course Scheduler, an open source, cross platform
 * course scheduling tool, configurable for most universities.
 *
 * Copyright (C) 2010-2014 Devyse.io; All rights reserved.
 *
 * @license GNU General Public License version 3 (GPLv3)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package io.devyse.scheduler.model;

/**
 * Provide basic functionality and implementation for the Term
 * interface
 *
 * @author Mike Reinhold
 *
 */
public abstract class AbstractTerm implements Term {
	
	/**
	 * The university for which this term exists
	 */
	private String university;
	
	/**
	 * The unique term identifier for the university
	 */
	private String id;
	
	/**
	 * The common term name
	 */
	private String name;
	
	/* (non-Javadoc)
	 * @see io.devyse.scheduler.model.Term#getUniversity()
	 */
	@Override
	public String getUniversity() {
		return this.university;
	}
	
	/* (non-Javadoc)
	 * @see io.devyse.scheduler.model.Term#getTermID()
	 */
	@Override
	public String getId() {
		return this.id;
	}
	
	/* (non-Javadoc)
	 * @see io.devyse.scheduler.model.Term#getTermName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * @param university the university to set
	 */
	protected void setUniversity(String university) {
		this.university = university;
	}

	/**
	 * @param id the id to set
	 */
	protected void setId(String id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	protected void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Create a new AbstractTerm for the specified university using the
	 * id provided
	 *
	 * @param university the parent university of the term
	 * @param id the unique identifier of the term
	 */
	public AbstractTerm(String university, String id){
		super();
		
		this.setUniversity(university);
		this.setId(id);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Term) { return this.equals((Term)obj); }
		else return false;
	}
	
}
