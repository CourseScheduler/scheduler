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
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;

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
	private DayOfWeek dayOfWeek;
	
	/**
	 * Start time (in the timeZone) for the time block
	 */
	private LocalTime startTime;
	
	/**
	 * End time (in the timeZone) for the time block
	 */
	private LocalTime endTime;
	
	//ANALYZE should we store the time zone with times instead of storing the timezone separate. We would still provide the local times + timezone constructor, but maybe it makes more sense to store in a zoned time
	/**
	 * The time zone for the time block, should be the time
	 * zone of the campus in which the course meeting occurs.
	 */
	private ZoneOffset timeZone;
	
	/* (non-Javadoc)
	 * @see io.devyse.scheduler.model.Period#getDayOfWeek()
	 */
	public DayOfWeek getDayOfWeek() {
		return this.dayOfWeek;
	}
	
	/* (non-Javadoc)
	 * @see io.devyse.scheduler.model.Period#getStartTime()
	 */
	public LocalTime getStartTime() {
		return this.startTime;
	}
	
	/* (non-Javadoc)
	 * @see io.devyse.scheduler.model.Period#getEndTime()
	 */
	public LocalTime getEndTime() {
		return this.endTime;
	}
	
	/* (non-Javadoc)
	 * @see io.devyse.scheduler.model.Period#getTimeZone()
	 */
	public ZoneOffset getTimeZone() {
		return this.timeZone;
	}

	/**
	 * @param dayOfWeek the dayOfWeek to set
	 */
	private void setDayOfWeek(DayOfWeek day) {
		this.dayOfWeek = day;
	}

	/**
	 * @param startTime the startTime to set
	 */
	private void setStartTime(LocalTime start) {
		this.startTime = start;
	}

	/**
	 * @param endTime the endTime to set
	 */
	private void setEndTime(LocalTime end) {
		this.endTime = end;
	}

	/**
	 * @param timeZone the timeZone to set
	 */
	private void setTimeZone(ZoneOffset timeZone) {
		this.timeZone = timeZone;
	}

	/**
	 * Create a new AbstractPeriod with the appropriate day of week, start time,
	 * end time, and timezone.
	 *
	 * @param dow the day of the week
	 * @param startTime the start time, using the zone as a reference
	 * @param endTime the end time, using the zone as a reference
	 * @param zone the time zone, likely based on campus location
	 */
	protected AbstractPeriod(DayOfWeek dow, LocalTime start, LocalTime end, ZoneOffset zone) {
		super();
		
		this.setDayOfWeek(dow);
		this.setStartTime(start);
		this.setEndTime(end);
		this.setTimeZone(zone);
	}
	
	//ANALYZE other constructors with different format times and zones
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Period other) {
		int value = this.getDayOfWeek().compareTo(other.getDayOfWeek());
		
		if(value == 0) {
			value = OffsetTime.of(this.getStartTime(), this.getTimeZone()).compareTo(
					OffsetTime.of(other.getStartTime(), other.getTimeZone()));
			if(value == 0) {
				value = OffsetTime.of(this.getEndTime(), this.getTimeZone()).compareTo(
						OffsetTime.of(other.getEndTime(), other.getTimeZone()));
			}
		}
		return value;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if(other instanceof Period) {
			Period comp = (Period)other;
			if(!this.getDayOfWeek().equals(comp.getDayOfWeek())) return false;
			if(!this.getTimeZone().equals(comp.getTimeZone())) return false;
			if(!this.getStartTime().equals(comp.getStartTime())) return false;
			if(!this.getEndTime().equals(comp.getEndTime())) return false;
			return true;
		} else return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(
			this.getDayOfWeek(),
			this.getEndTime(),
			this.getStartTime(),
			this.getTimeZone()
		);
	}
}
