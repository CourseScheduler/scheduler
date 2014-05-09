/**
 * @(#) Meeting.java
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
 * Represent a specific instance of when a Section meets. Contains
 * a Period as well as the location information (campus, building,
 * room), the instructor, and the type of meeting (lab vs lecture vs other).
 * 
 *
 * @author Mike Reinhold
 *
 */
public interface Meeting extends Comparable<Meeting> {
	
	/**
	 * The time Period in which the Meeting occurs. This method
	 * returns null if the Meeting time Period is not yet announced
	 *
	 * @return the time Period for this meeting
	 */
	public Period getPeriod();
	
	/**
	 * The name of the campus at which the Meeting occurs. This 
	 * method returns null if the Meeting location is not yet
	 * announced
	 *
	 * @return the campus portion of the location
	 */
	public String getCampus();
	
	/**
	 * The name or address of the building at which the Meeting
	 * occurs. This method returns null if the Meeting location
	 * is not yet announced
	 *
	 * @return the building portion of the location
	 */
	public String getBuilding();
	
	/**
	 * The name or number of the room in which this Meeting occurs.
	 * This method returns null if the Meeting location is not
	 * yet announced
	 *
	 * @return the building room portion of the location
	 */
	public String getRoom();
	
	//TODO getMeetingType
	//TODO getInstructor
	
	//TODO what about add/drop dates, date range, length (number of weeks)
	
}
