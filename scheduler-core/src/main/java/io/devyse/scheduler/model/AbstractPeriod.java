/**
 * @(#) AbstractPeriod.java
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
import java.time.LocalTime;
import java.time.ZoneId;

/**
 * Provide basic functionality for implementations of the Period
 * interface.
 *
 * @author Mike Reinhold
 *
 */
public abstract class AbstractPeriod implements Period{
	
	/**
	 * Day of the week for the time block
	 */
	private DayOfWeek day;
	
	/**
	 * Start time (in the timeZone) for the time block
	 */
	private LocalTime start;
	
	/**
	 * End time (in the timeZone) for the time block
	 */
	private LocalTime end;
	
	/**
	 * The time zone for the time block, should be the time
	 * zone of the campus in which the course meeting occurs.
	 */
	private ZoneId timeZone;
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(AbstractPeriod other) {
		//TODO compare
		return 0;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if(other instanceof AbstractPeriod) {
			//TODO check equality
			return true;
		} else {
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		//TODO compute hash code
		return 0;
	}
}
