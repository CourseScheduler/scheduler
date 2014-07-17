/**
 * @(#) Term.java
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

import java.util.Objects;

/**
 * Represent the registration term 
 *
 * @author Mike Reinhold
 *
 */
public interface Term extends Comparable<Term> {
	
	//TODO university object instead of simple string? probably should...
	
	/**
	 * The university for which this registration term is valid
	 *
	 * @return the university for this term
	 */
	public String getUniversity();
	
	/**
	 * The unique term identifier for this term as defined by
	 * the university. Often a numeric representation of the
	 * year and semester (201402)
	 *
	 * @return the term identifier
	 */
	public String getId();
	
	/**
	 * The common name of the term as defined by the university.
	 * Often a "plain language" representation of the year and
	 * semester (Spring 2014)
	 *
	 * @return the term name
	 */
	public String getName();
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public default boolean equals(Term other) {
		return	this.getUniversity().equals(other.getUniversity()) &&
				this.getId().equals(other.getId())
		;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public default int getHashCode() {
		return Objects.hash(
				this.getUniversity(),
				this.getId()
		);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public default int compareTo(Term other) {
		int result = this.getUniversity().compareTo(other.getUniversity());
		
		if(result == 0){
			result = this.getId().compareTo(other.getId());
		}
		return result;
	}
}
