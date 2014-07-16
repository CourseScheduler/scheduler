/**
 * @(#) DateTimeBlock.java
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
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Objects;

/**
 * Represent a specific time block for a course meeting. Contains
 * the day of the week, start time, end time, time zone, start date,
 * and end date.
 *
 * @author Mike Reinhold
 *
 */
public interface DateTimeBlock extends Comparable<DateTimeBlock>{

	/**
	 * A DateTimeBlock exists on a specific day of the week. For Meetings that
	 * have meet at the same time on multiple days of the week, multiple
	 * DateTimeBlock instances should exist.
	 * 
	 * @return the day of the week on which the DateTimeBlock is slotted
	 */
	public DayOfWeek getDayOfWeek();
	
	/**
	 * The time at which the DateTimeBlock starts. This time includes the campus
	 * timezone information.
	 *
	 * @return the start time of the DateTimeBlock
	 */
	public OffsetTime getStartTime();
	
	/**
	 * The time at which the DateTimeBlock starts in local time of the campus
	 *
	 * @return the local start time of the DateTimeBlock
	 */
	public LocalTime getLocalStartTime();
	
	/**
	 * The time at which the DateTimeBlock ends. This time includes the campus
	 * timezone information.
	 *
	 * @return the end time of the DateTimeBlock
	 */
	public OffsetTime getEndTime();
	
	/**
	 * The time at which the DateTimeBlock ends in local time of the campus
	 *
	 * @return the local end time of the DateTimeBlock
	 */
	public LocalTime getLocalEndTime();
	
	/**
	 * The length of the DateTimeBlock as defined by the start and end times
	 *
	 * @return the duration of the DateTimeBlock
	 */
	public Duration getDuration();
	
	/**
	 * Some terms have courses which start and end at different times
	 * within the term, making the specific dates for the course
	 * important during scheduling (as the same time block may be
	 * legitimately used by separate courses during different sets
	 * of weeks during the term).
	 *
	 * @return the start date of the DateTimeBlock
	 */
	public LocalDate getStartDate();
	
	/**
	 * Some terms have courses which start and end at different times
	 * within the term, making the specific dates for the course
	 * important during scheduling (as the same time block may be
	 * legitimately used by separate courses during different sets
	 * of weeks during the term).
	 *
	 * @return the end date of the DateTimeBlock
	 */
	public LocalDate getEndDate();
	
	/**
	 * The length of the DateTimeBlock as defined by the start and end times
	 *
	 * @return the period length of the DateTimeBlock
	 */
	public Period getPeriod();
	
	/**
	 * Check if two periods overlap. This uses the offset (zoned) start and
	 * end times of the DateTimeBlock as well as the day of week and start and
	 * end dates to determine if the DateTimeBlocks overlap at all.
	 *
	 * @param other the other DateTimeBlock to check for overlap with this DateTimeBlock
	 * 
	 * @return if the other DateTimeBlock overlaps with this DateTimeBlock
	 */
	public default boolean overlapsWith(DateTimeBlock other) {
		return 	this.dayOfWeekOverlapsWith(other) && 
				this.timeOverlapsWith(other) && 
				this.dateOverlapsWith(other);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public default int compareTo(DateTimeBlock other) {
		int value = this.getStartDate().compareTo(other.getStartDate()); 
		
		if(value == 0) {
			value = this.getDayOfWeek().compareTo(other.getDayOfWeek());
			if(value == 0) {
				value = this.getStartTime().compareTo(other.getStartTime());
				if(value == 0) {
					value = this.getEndTime().compareTo(other.getEndTime());
					if(value == 0) {
						value = this.getEndDate().compareTo(other.getEndDate());
					}
				}
			}
		}
		return value;
	}
	
	/**
	 * Gets the textual representation of the DateTimeBlock using the specified
	 * text style for the day of the week. The Locale from the formatter
	 * is also applied to the text style. The formatter is applied to the
	 * start and end times
	 *
	 * @param style
	 * @param formatter
	 * 
	 * @return
	 */
	public String toString(TextStyle style, DateTimeFormatter formatter);
	
	/**
	 * Check if the time ranges for the two time blocks overlap.
	 * This method ignores the date range and day of the week.
	 * This method DOES NOT consider two time blocks that overlap
	 * only on the start/end minute to be "overlapping" - aka
	 * some universities publish course times as 8-10AM, 10-11AM - 
	 * this is not considered overlapping (scheduling algorithm
	 * options can be used to ensure a minimum passing period)
	 *
	 * @param other the DateTimeBlock for the overlap check
	 * @return if the two DateTimeBlocks overlap based on time only
	 */
	public default boolean timeOverlapsWith(DateTimeBlock other) {
		return  (this.getStartTime().isEqual(other.getStartTime())) ||
				(this.getEndTime().isEqual(other.getEndTime())) ||
				(this.getStartTime().isAfter(other.getStartTime()) && this.getStartTime().isBefore(other.getEndTime())) ||
				(other.getStartTime().isAfter(this.getStartTime()) && other.getStartTime().isBefore(this.getEndTime()))
		;
	}
	
	/**
	 * Check if the date ranges for the two time blocks overlap.
	 * This method ignores the times and day of the week.
	 * This method DOES consider two time blocks that overlap
	 * only on the start/end date to be "overlapping" - aka
	 * if the partial term dates align such that the start date of
	 * segment of the term is the end date of a different segment of
	 * the term.
	 *
	 * @param other the DateTimeBlock for the overlap check
	 * @return if the two DateTimeBlocks overlap based on date only
	 */
	public default boolean dateOverlapsWith(DateTimeBlock other) {
		return  (this.getStartDate().isEqual(other.getStartDate())) ||
				(this.getEndDate().isEqual(other.getEndDate())) ||
				(this.getStartDate().isEqual(other.getEndDate())) ||
				(this.getEndDate().isEqual(other.getStartDate())) ||
				(this.getStartDate().isAfter(other.getStartDate()) && this.getStartDate().isBefore(other.getEndDate())) ||
				(other.getStartDate().isAfter(this.getStartDate()) && other.getStartDate().isBefore(this.getEndDate()))
		;
	}
	
	/**
	 * Check if the day of week for the two time blocks overlap
	 *
	 * @param other the DateTimeBlock for the overlap check
	 * @return if the two DateTimeBlocks overlap based on day of week only
	 */
	public default boolean dayOfWeekOverlapsWith(DateTimeBlock other) {
		return this.getDayOfWeek().equals(other.getDayOfWeek());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public default boolean equals(DateTimeBlock other) {
		return 	this.getStartDate().equals(other.getStartDate()) 	&&
				this.getEndDate().equals(other.getEndDate())		&&
				this.getDayOfWeek().equals(other.getDayOfWeek()) 	&&
				this.getStartTime().equals(other.getStartTime())	&&
				this.getEndTime().equals(other.getEndTime())
		;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public default int getHashCode() {
		return Objects.hash(
			this.getDayOfWeek(),
			this.getStartTime(),
			this.getEndTime(),
			this.getStartDate(),
			this.getEndDate()
		);
	}
}
