/**
 * @(#) Section.java
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
 * Represent the registration unit of a given course.
 *
 * @author Mike Reinhold
 *
 */
public interface Section extends Comparable<Section> {
	
	/**
	 * The CRN is the course registration number that is used
	 * by the registrar to identify a specific Section during
	 * registration.
	 *
	 * @return the course registration number
	 */
	public String getCRN();
	
	//seat availability
	
	//TODO course/listing, term
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public default boolean equals(Section other) {
		//TODO implement this
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public default int getHashCode() {
		//TODO implement this
		return 0;
	}
	
	public default int compareTo(Section other) {
		//TODO implement this
		return 0;
	}
}
