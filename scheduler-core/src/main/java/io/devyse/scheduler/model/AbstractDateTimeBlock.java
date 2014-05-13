/**
 * @(#) AbstractDateTimeBlock.java
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Objects;

/**
 * Provide basic functionality for implementations of the DateTimeBlock
 * interface.
 *
 * @author Mike Reinhold
 *
 */
public abstract class AbstractDateTimeBlock implements DateTimeBlock{
	
	/**
	 * Day of the week for the time block
	 */
	private DayOfWeek dayOfWeek;
	
	/**
	 * Start time and timezone for the time block
	 */
	private OffsetTime startTime;
	
	/**
	 * End time and timezone for the time block
	 */
	private OffsetTime endTime;
	
	/**
	 * TODO Describe this field
	 */
	private LocalDate startDate;
	
	/**
	 * TODO Describe this field
	 */
	private LocalDate endDate;
	
	/* (non-Javadoc)
	 * @see io.devyse.scheduler.model.DateTimeBlock#getDayOfWeek()
	 */
	public DayOfWeek getDayOfWeek() {
		return this.dayOfWeek;
	}
	
	/* (non-Javadoc)
	 * @see io.devyse.scheduler.model.DateTimeBlock#getStartTime()
	 */
	public OffsetTime getStartTime() {
		return this.startTime;
	}
	
	/* (non-Javadoc)
	 * @see io.devyse.scheduler.model.DateTimeBlock#getLocalStartTime()
	 */
	public LocalTime getLocalStartTime() {
		return this.getStartTime().toLocalTime();
	}
	
	/* (non-Javadoc)
	 * @see io.devyse.scheduler.model.DateTimeBlock#getEndTime()
	 */
	public OffsetTime getEndTime() {
		return this.endTime;
	}
	
	/* (non-Javadoc)
	 * @see io.devyse.scheduler.model.DateTimeBlock#getLocalEndTime()
	 */
	public LocalTime getLocalEndTime() {
		return this.getEndTime().toLocalTime();
	}
	
	/* (non-Javadoc)
	 * @see io.devyse.scheduler.model.DateTimeBlock#getDuration()
	 */
	public Duration getDuration() {
		return Duration.between(this.getStartTime(), this.getEndTime());
	}
	
	/* (non-Javadoc)
	 * @see io.devyse.scheduler.model.DateTimeBlock#getStartDate()
	 */
	public LocalDate getStartDate() {
		return this.startDate;
	}
	
	/* (non-Javadoc)
	 * @see io.devyse.scheduler.model.DateTimeBlock#getEndDate()
	 */
	public LocalDate getEndDate() {
		return this.endDate;
	}
	
	/* (non-Javadoc)
	 * @see io.devyse.scheduler.model.DateTimeBlock#getPeriod()
	 */
	public Period getPeriod() {
		return Period.between(this.getStartDate(), this.getEndDate());
	}
	
	/* (non-Javadoc)
	 * @see io.devyse.scheduler.model.DateTimeBlock#overlapsWith(io.devyse.scheduler.model.DateTimeBlock)
	 */
	public boolean overlapsWith(DateTimeBlock other) {
		if(this.getDayOfWeek().equals(other.getDayOfWeek())) {	//TODO add date range comparison
			return (this.getStartTime().isAfter(other.getStartTime()) && this.getStartTime().isBefore(other.getEndTime())) ||
					(other.getStartTime().isAfter(this.getStartTime()) && other.getStartTime().isBefore(this.getEndTime()));
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();		//TODO add date range append
		
		sb.append(this.getDayOfWeek());
		sb.append(" ");
		sb.append(this.getStartTime());
		sb.append("-");						//TODO what should this string look like? 
		sb.append(this.getEndTime());
		
		return sb.toString();
	}
	
	/* (non-Javadoc)
	 * @see io.devyse.scheduler.model.DateTimeBlock#toString(java.time.format.TextStyle, java.time.format.DateTimeFormatter)
	 */
	public String toString(TextStyle style, DateTimeFormatter formatter) {
		StringBuilder sb = new StringBuilder();		//TODO add date range append
		
		sb.append(this.getDayOfWeek().getDisplayName(style, formatter.getLocale()));
		sb.append(" ");
		sb.append(this.getStartTime().format(formatter));
		sb.append("-");						//TODO what should this string look like
		sb.append(this.getEndTime().format(formatter));
		
		return sb.toString();
	}

	/**
	 * @param dayOfWeek the dayOfWeek to set
	 */
	private void setDayOfWeek(DayOfWeek day) {
		this.dayOfWeek = day;
	}

	/**
	 * @param start the startTime to set
	 */
	private void setStartTime(OffsetTime start) {
		this.startTime = start;
	}

	/**
	 * @param end the endTime to set
	 */
	private void setEndTime(OffsetTime end) {
		this.endTime = end;
	}
	
	/**
	 * @param start the startDate to set
	 */
	private void setStartDate(LocalDate start) {
		this.startDate = start;
	}
	
	/**
	 * @param end the endDate to set
	 */
	private void setEndDate(LocalDate end) {
		this.endDate = end;
	}

	/**
	 * Create a new AbstractDateTimeBlock with the appropriate day of week, start time,
	 * end time, and timezone.
	 *
	 * @param dow the day of the week
	 * @param startTime the local start time, uses the zone as a reference
	 * @param endTime the local end time, uses the zone as a reference
	 * @param zone the time zone, likely based on campus location
	 * @param startDate the start date 
	 * @param endDate the end date
	 */
	protected AbstractDateTimeBlock(DayOfWeek dow, LocalTime startTime, LocalTime endTime, ZoneOffset zone, LocalDate startDate, LocalDate endDate) {
		super();
		
		this.setDayOfWeek(dow);
		this.setStartTime(OffsetTime.of(startTime, zone));
		this.setEndTime(OffsetTime.of(endTime, zone));
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}
	
	/**
	 * Create a new AbstractDateTimeBlock with the appropriate day of week, start time,
	 * and end time
	 *
	 * @param dow the day of the week
	 * @param startTime the zoned start time
	 * @param endTime the zoned end time
	 * @param startDate the start date 
	 * @param endDate the end date
	 */
	protected AbstractDateTimeBlock(DayOfWeek dow, OffsetTime startTime, OffsetTime endTime, LocalDate startDate, LocalDate endDate) {
		super();
		
		this.setDayOfWeek(dow);
		this.setStartTime(startTime);
		this.setEndTime(endTime);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(DateTimeBlock other) {
		int value = this.getDayOfWeek().compareTo(other.getDayOfWeek());		//TODO add date range comparison
		
		if(value == 0) {
			value = this.getStartTime().compareTo(other.getStartTime());
			if(value == 0) {
				value = this.getEndTime().compareTo(other.getEndTime());
			}
		}
		return value;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {										//TODO add date range comparison
		if(other instanceof DateTimeBlock) {
			DateTimeBlock comp = (DateTimeBlock)other;
			if(!this.getDayOfWeek().equals(comp.getDayOfWeek())) return false;
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
			this.getStartTime(),
			this.getEndTime(),
			this.getStartDate(),
			this.getEndDate()
		);
	}
}
