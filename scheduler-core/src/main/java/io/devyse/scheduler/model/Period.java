/**
 * @(#) Period.java
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

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.OffsetTime;

/**
 * Represent a specific time block for a course meeting. Contains
 * the day of the week, start time, end time, and time zone.
 *
 * @author Mike Reinhold
 *
 */
public interface Period extends Comparable<Period>{

	/**
	 * A Period exists on a specific day of the week. For Meetings that
	 * have meet at the same time on multiple days of the week, multiple
	 * Period instances should exist.
	 * 
	 * @return the day of the week on which the Period is slotted
	 */
	public DayOfWeek getDayOfWeek();
	
	/**
	 * The time at which the Period starts. This time includes the campus
	 * timezone information.
	 *
	 * @return the start time of the Period
	 */
	public OffsetTime getStartTime();
	
	/**
	 * The time at which the Period starts in local time of the campus
	 *
	 * @return the local start time of the period
	 */
	public LocalTime getLocalStartTime();
	
	/**
	 * The time at which the Period ends. This time includes the campus
	 * timezone information.
	 *
	 * @return the end time of the Period
	 */
	public OffsetTime getEndTime();
	
	/**
	 * The time at which the Period ends in local time of the campus
	 *
	 * @return the local end time of the period
	 */
	public LocalTime getLocalEndTime();
	
	/**
	 * The length of the period as defined by the start and end times
	 *
	 * @return the duration of the period
	 */
	public Duration getDuration();
	
}
