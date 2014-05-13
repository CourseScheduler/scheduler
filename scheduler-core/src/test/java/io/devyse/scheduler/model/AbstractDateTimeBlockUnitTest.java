/**
 * @(#) AbstractDateTimeBlockUnitTest.java
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


/**
 * Unit tests for the AbstractDateTimeBlock base class to confirm the default
 * functionality has appropriate semantics.
 *
 * @author Mike Reinhold
 *
 */
@Test(groups = {"unit","abstract"})
public class AbstractDateTimeBlockUnitTest {
	
	private static final long TEST_DURATION = 60;
	private static final long TEST_PERIOD = 1;
	
	private static final DayOfWeek TEST_DAY = DayOfWeek.THURSDAY;
	private static final LocalTime TEST_START_TIME = LocalTime.NOON;
	private static final LocalTime TEST_END_TIME = LocalTime.NOON.plusMinutes((long)(1.5*TEST_DURATION));
	private static final LocalDate TEST_START_DATE = LocalDate.of(2014, 04, 30);
	private static final LocalDate TEST_END_DATE = TEST_START_DATE.plusDays(1);
	private static final ZoneOffset TEST_ZONE_1 = ZoneOffset.of("+06:00");	//Base time zone for the tests - eg. America/Chicago
	private static final ZoneOffset TEST_ZONE_2 = ZoneOffset.of("+05:00");	//Must be "earlier" than zone 1 - eg. America/Detroit
	private static final ZoneOffset TEST_ZONE_3 = ZoneOffset.of("+07:00");	//Must be "later" than zone 1 - eg. America/Denver
	
	private static final int ZONE_1_2_OFFSET = TEST_ZONE_1.getTotalSeconds()-TEST_ZONE_2.getTotalSeconds();
	private static final int ZONE_1_3_OFFSET = TEST_ZONE_3.getTotalSeconds()-TEST_ZONE_1.getTotalSeconds();
	
	//TODO update all of the test cases to include the date range information
	
	/**
	 * Semantics of the periods are as follows:
	 * 
	 * 		a1 & a2 are both references to the same instance
	 *  	a1 & a3 have the same field data, but are separate instances
	 * 		a1 & a4 refer to the same time, but a4 is in an earlier time zone (later local time)
	 * 		a1 & a5 refer to the same time, but a5 is in a later time zone (earlier local time)
	 *  	
	 * 		b1 has same field data as a but with an earlier day
	 * 		b2 has same field data as a but with a later day
	 * 
	 * 		c1 has same field data as a but with an earlier start time
	 * 		c2 has same field data as a but with a later start time
	 * 
	 * 		d1 has same field data as a but with an earlier end time
	 * 		d2 has same field data as a but with a later end time
	 * 
	 * 		e1 has same field data as a but with an earlier time zone
	 * 		e2 has same field data as a but with a later time zone
	 * 
	 * 		f1 has same field data as a but with an earlier start date
	 * 		f2 has same field data as a but with a later start date
	 * 
	 * 		g1 has same field data as a but with an earlier end date
	 * 		g2 has same field data as a but with a later end date
	 */
	private DateTimeBlockStub a1, a2, a3, a4, a5, b1, b2, c1, c2, d1, d2, e1, e2, f1, f2, g1, g2;
	
	/**
	 * Prepare the test instances for use in the tests.
	 * 
	 * **IMPORTANT - currently, DateTimeBlock is defined to be immutable
	 * 					as a result, instances are safe to reuse
	 * 					without reinitializing them 
	 * 
	 */
	@BeforeClass
	public void setUp() {
		a1 = new DateTimeBlockStub(TEST_DAY, TEST_START_TIME, TEST_END_TIME, TEST_ZONE_1, TEST_START_DATE, TEST_END_DATE);
		a2 = a1;
		a3 = new DateTimeBlockStub(TEST_DAY, TEST_START_TIME, TEST_END_TIME, TEST_ZONE_1, TEST_START_DATE, TEST_END_DATE);
		a4 = new DateTimeBlockStub(TEST_DAY, TEST_START_TIME.minusSeconds(ZONE_1_2_OFFSET), TEST_END_TIME.plusSeconds(ZONE_1_2_OFFSET), TEST_ZONE_2, TEST_START_DATE, TEST_END_DATE);
		a5 = new DateTimeBlockStub(TEST_DAY, TEST_START_TIME.plusSeconds(ZONE_1_3_OFFSET), TEST_END_TIME.plusSeconds(ZONE_1_3_OFFSET), TEST_ZONE_3, TEST_START_DATE, TEST_END_DATE);
		
		b1 = new DateTimeBlockStub(TEST_DAY.minus(1), TEST_START_TIME, TEST_END_TIME, TEST_ZONE_1, TEST_START_DATE, TEST_END_DATE);
		b2 = new DateTimeBlockStub(TEST_DAY.plus(1), TEST_START_TIME, TEST_END_TIME, TEST_ZONE_1, TEST_START_DATE, TEST_END_DATE);
		
		c1 = new DateTimeBlockStub(TEST_DAY, TEST_START_TIME.minusMinutes(TEST_DURATION), TEST_END_TIME, TEST_ZONE_1, TEST_START_DATE, TEST_END_DATE);
		c2 = new DateTimeBlockStub(TEST_DAY, TEST_START_TIME.plusMinutes(TEST_DURATION), TEST_END_TIME, TEST_ZONE_1, TEST_START_DATE, TEST_END_DATE);
		
		d1 = new DateTimeBlockStub(TEST_DAY, TEST_START_TIME, TEST_END_TIME.minusMinutes(TEST_DURATION), TEST_ZONE_1, TEST_START_DATE, TEST_END_DATE);
		d2 = new DateTimeBlockStub(TEST_DAY, TEST_START_TIME, TEST_END_TIME.plusMinutes(TEST_DURATION), TEST_ZONE_1, TEST_START_DATE, TEST_END_DATE);
		
		e1 = new DateTimeBlockStub(TEST_DAY, TEST_START_TIME, TEST_END_TIME, TEST_ZONE_2, TEST_START_DATE, TEST_END_DATE);
		e2 = new DateTimeBlockStub(TEST_DAY, TEST_START_TIME, TEST_END_TIME, TEST_ZONE_3, TEST_START_DATE, TEST_END_DATE);
		
		f1 = new DateTimeBlockStub(TEST_DAY, TEST_START_TIME, TEST_END_TIME, TEST_ZONE_1, TEST_START_DATE.minusDays(1), TEST_END_DATE);
		f2 = new DateTimeBlockStub(TEST_DAY, TEST_START_TIME, TEST_END_TIME, TEST_ZONE_1, TEST_START_DATE.plusDays(1), TEST_END_DATE);
		
		g1 = new DateTimeBlockStub(TEST_DAY, TEST_START_TIME, TEST_END_TIME, TEST_ZONE_1, TEST_START_DATE, TEST_END_DATE.minusDays(TEST_PERIOD));
		g2 = new DateTimeBlockStub(TEST_DAY, TEST_START_TIME, TEST_END_TIME, TEST_ZONE_1, TEST_START_DATE, TEST_END_DATE.plusDays(TEST_PERIOD));
	}
		
	/**
	 * Confirm the semantics of reference and object equality.
	 * 
	 * Note:
	 * 	"Sameness" is via instance equality (via ==)
	 * 	"Equality" is object semantic equality (via equals)
	 * 	UTC equivalent times in different zones are not equal {@link java.time.chrono.ChronoZonedDateTime#equals(Object)}
	 * 
	 */
	@Test
	public void confirmEquality() {
		SoftAssert eqAssert = new SoftAssert();
		
		eqAssert.assertSame(a1, a2, "References to same instance should be the same");
		eqAssert.assertEquals(a1, a2, "References to same instance should be equal");
		eqAssert.assertEquals(a1, a3, "Instances with same uniqueness fields should be equal");
		eqAssert.assertNotEquals(a1, a4, "Instances with UTC equivalent times in different zones should not be equal");
		eqAssert.assertNotEquals(a1, b1, "Instances with varying days of week should not be equal");
		eqAssert.assertNotEquals(a1, c1, "Instances with varying start times should not be equal");
		eqAssert.assertNotEquals(a1, d1, "Instances with varying end times should not be equal");
		eqAssert.assertNotEquals(a1, e1, "Instances with varying time zones should not be equal");
		eqAssert.assertNotEquals(a1, f1, "Instances with varying start dates should not be equal");
		eqAssert.assertNotEquals(a1, g1, "Instances with varying end dates should not be equal");
						
		eqAssert.assertAll();
	}

	/**
	 * Confirm hashCode semantics is consistent with equals()
	 * 
	 * Note: 
	 * 	It is not required that "unequal" objects return different hashcodes,
	 * 	however "equal" and "same" objects must return the same hashcode. That said,
	 * 	including a test for "variety" between hashcodes so that all instances don't 
	 * 	return the same value (would kill performance of data structures that use the
	 * 	hashcode)
	 *
	 */
	@Test
	public void confirmHashCode() {
		SoftAssert hcAssert = new SoftAssert();
		
		hcAssert.assertEquals(a1.hashCode(), a2.hashCode(), "References to same instance should have same hashcode. Hashcode not consistent across calls");
		hcAssert.assertEquals(a1.hashCode(), a3.hashCode(), "Instances with same uniqueness fields should have same hashcode. Hashcode not consistent with equals()");
		
		//ensure that our sample dataset, which has variety in its field content,
		//has some variation in its hashcode. No variation in hash would be bad 
		int a1Code = a1.hashCode();
		boolean variety = a1Code == b1.hashCode() &&
				a1Code == b2.hashCode() &&
				a1Code == c1.hashCode() &&
				a1Code == c2.hashCode() &&
				a1Code == d1.hashCode() &&
				a1Code == d2.hashCode() &&
				a1Code == e1.hashCode() &&
				a1Code == e2.hashCode() &&
				a1Code == f1.hashCode() &&
				a1Code == f2.hashCode() &&
				a1Code == g1.hashCode() &&
				a1Code == g2.hashCode()
		;
		hcAssert.assertFalse(variety, "Hashcode should return a variety of values for instance with varying uniqueness fields");
		
		hcAssert.assertAll();
	}
	
	/**
	 * Confirm the semantics of compareTo is consistent with equals() 
	 * and has appropriate object semantics. Only exception to consistency
	 * with equals() is where object semantics yield equal times but equals()
	 * yields different instances: same UTC time represented in 2 different
	 * zones yield compareTo == 0 but equals == false.
	 * 
	 * Note:
	 * 	Order for AbstractDateTimeBlock implies the following:
	 * 		Start date is the first ordering element
	 * 		Sunday < Monday < Tuesday < Wednesday < Thursday < Friday < Saturday
	 * 		Start and end time are compared first as UTC then with local time to preserve consistency with equals
	 * 		Start time takes priority over end time
	 * 		End time only matters when start time is the same
	 *		End date is the last ordering component
	 */
	@Test
	public void confirmCompareTo() {
		SoftAssert ctAssert = new SoftAssert();
		
		ctAssert.assertEquals(a1.compareTo(a2), 0, "References to same instance should be equal");
		ctAssert.assertEquals(a2.compareTo(a1), 0, "References to same instance should be equal, regardless of comparison direction");
		ctAssert.assertEquals(a1.compareTo(a3), 0, "CompareTo should be consistent with equals() for equal instances");
		ctAssert.assertEquals(a3.compareTo(a1), 0, "CompareTo should be consistent with equals() for equal instance, regardless of comparison direction");
		
		ctAssert.assertNotEquals(a1.compareTo(b1), 0, "CompareTo should be consistent with equals() for non-equal instance");
		ctAssert.assertNotEquals(b1.compareTo(a1), 0, "CompareTo should be consistent with equals() for non-equal instance, regardless of comparison direction");
		ctAssert.assertEquals(Math.signum(a1.compareTo(b1)), Math.signum(-b1.compareTo(a1)), "CompareTo should reverse sign for reversed comparison direction of non-equal instances (lesser day of week)");
		ctAssert.assertEquals(Math.signum(a1.compareTo(b2)), Math.signum(-b2.compareTo(a1)), "CompareTo should reverse sign for reversed comparison direction of non-equal instances (greater day of week)");
		ctAssert.assertEquals(Math.signum(a1.compareTo(c1)), Math.signum(-c1.compareTo(a1)), "CompareTo should reverse sign for reversed comparison direction of non-equal instances (lesser start time)");
		ctAssert.assertEquals(Math.signum(a1.compareTo(c2)), Math.signum(-c2.compareTo(a1)), "CompareTo should reverse sign for reversed comparison direction of non-equal instances (greater start time)");
		ctAssert.assertEquals(Math.signum(a1.compareTo(d1)), Math.signum(-d1.compareTo(a1)), "CompareTo should reverse sign for reversed comparison direction of non-equal instances (lesser end time))");
		ctAssert.assertEquals(Math.signum(a1.compareTo(d2)), Math.signum(-d2.compareTo(a1)), "CompareTo should reverse sign for reversed comparison direction of non-equal instances (greater end time)");
		ctAssert.assertEquals(Math.signum(a1.compareTo(e1)), Math.signum(-e1.compareTo(a1)), "CompareTo should reverse sign for reversed comparison direction of non-equal instances (lesser zone)");
		ctAssert.assertEquals(Math.signum(a1.compareTo(e2)), Math.signum(-e2.compareTo(a1)), "CompareTo should reverse sign for reversed comparison direction of non-equal instances (greater zone)");
		ctAssert.assertEquals(Math.signum(a1.compareTo(f1)), Math.signum(-f1.compareTo(a1)), "CompareTo should reverse sign for reversed comparison direction of non-equal instances (lesser start date)");
		ctAssert.assertEquals(Math.signum(a1.compareTo(f2)), Math.signum(-f2.compareTo(a1)), "CompareTo should reverse sign for reversed comparison direction of non-equal instances (greater start date)");
		ctAssert.assertEquals(Math.signum(a1.compareTo(g1)), Math.signum(-g1.compareTo(a1)), "CompareTo should reverse sign for reversed comparison direction of non-equal instances (lesser end date)");
		ctAssert.assertEquals(Math.signum(a1.compareTo(g2)), Math.signum(-g2.compareTo(a1)), "CompareTo should reverse sign for reversed comparison direction of non-equal instances (greater end date)");
				
		ctAssert.assertEquals(Math.signum(a1.compareTo(b1)), 1.0f, "Positive result expected for instance with lesser day of week");
		ctAssert.assertEquals(Math.signum(a1.compareTo(c1)), 1.0f, "Positive result expected for instance with lesser start time");
		ctAssert.assertEquals(Math.signum(a1.compareTo(d1)), 1.0f, "Positive result expected for instance with lesser end time");
		ctAssert.assertEquals(Math.signum(a1.compareTo(e1)), -1.0f, "Negative result expected for instance with lesser zone");	//timezones are sorted descending
		ctAssert.assertEquals(Math.signum(a1.compareTo(f1)), 1.0f, "Positive result expected for instance with lesser start date");
		ctAssert.assertEquals(Math.signum(a1.compareTo(g1)), 1.0f, "Positive result expected for instance with lesser end date");
		ctAssert.assertEquals(Math.signum(a1.compareTo(a4)), 1.0f, "Positive result expected for instance with same UTC but lesser zone");

		ctAssert.assertEquals(Math.signum(a1.compareTo(b2)), -1.0f, "Negative result expected for instance with greater day of week");
		ctAssert.assertEquals(Math.signum(a1.compareTo(c2)), -1.0f, "Negative result expected for instance with greater start time");
		ctAssert.assertEquals(Math.signum(a1.compareTo(d2)), -1.0f, "Negative result expected for instance with greater end time");
		ctAssert.assertEquals(Math.signum(a1.compareTo(e2)), 1.0f, "Positive result expected for instance with greater zone");	//timezones are sorted descending
		ctAssert.assertEquals(Math.signum(a1.compareTo(f2)), -1.0f, "Negative result expected for instance with greater start date");
		ctAssert.assertEquals(Math.signum(a1.compareTo(g2)), -1.0f, "Negative result expected for instance with greater end date");
		ctAssert.assertEquals(Math.signum(a1.compareTo(a5)), -1.0f, "Negative result expected for instance with same UTC but greater zone");
		
		ctAssert.assertEquals(Math.signum(b1.compareTo(a1)), Math.signum(a1.compareTo(b2)), "Transitivity expected for instances differing on day of week");
		ctAssert.assertEquals(Math.signum(c1.compareTo(a1)), Math.signum(a1.compareTo(c2)), "Transitivity expected for instances differing on start");
		ctAssert.assertEquals(Math.signum(d1.compareTo(a1)), Math.signum(a1.compareTo(d2)), "Transitivity expected for instances differing on end");
		ctAssert.assertEquals(Math.signum(e1.compareTo(a1)), Math.signum(a1.compareTo(e2)), "Transitivity expected for instances differing on zone");
		
		ctAssert.assertAll();
	}
	
	/**
	 * Confirm that the duration between start and end times is calculated properly
	 *
	 */
	@Test
	public void confirmDuration() {
		Assert.assertEquals(a1.getDuration().toMinutes(), (long)(1.5*TEST_DURATION), "Duration between start and end times did not match expected number of minutes");
	}
	
	/**
	 * Confirm that the duration between stand and end dates is calculated properly
	 *
	 */
	@Test
	public void confirmPeriod() {
		Assert.assertEquals(a1.getPeriod().getDays(), TEST_PERIOD, "Period between start and end dates did not match expected number of days");
	}
	
	/**
	 * Confirm that DateTimeBlock overlaps are properly detected.
	 * 
	 * Periods overlap if they occur on the same day and there is at least
	 * one minute that is present in both Periods (timeline - local time + timezone) 
	 *
	 */
	@Test
	public void confirmOverlap() {
		SoftAssert olAssert = new SoftAssert();
		
		olAssert.assertEquals(a1.overlapsWith(a2), true, "Periods with same fields should overlap");
		
		olAssert.assertEquals(a1.overlapsWith(b1), false, "Periods on different days should not overlap");
		olAssert.assertEquals(b1.overlapsWith(a1), false, "Periods on different days should not overlap");
		olAssert.assertEquals(a1.overlapsWith(b2), false, "Periods on different days should not overlap");
		olAssert.assertEquals(b2.overlapsWith(a1), false, "Periods on different days should not overlap");
		
		olAssert.assertEquals(a1.overlapsWith(c1), true, "This DateTimeBlock with start time between other DateTimeBlock start and end times should overlap");
		olAssert.assertEquals(a1.overlapsWith(c2), true, "Other DateTimeBlock with start time between this DateTimeBlock start and end times should overlap");
		
		olAssert.assertEquals(d1.overlapsWith(c2), false, "Neither start nor end time of other DateTimeBlock between this DateTimeBlock start and end times should not overlap");
		olAssert.assertEquals(c2.overlapsWith(d1), false, "Neither start nor end time of other DateTimeBlock between this DateTimeBlock start and end times should not overlap");
		
		//TODO check same end/start time does not overlap
		//TODO check start and end date overlaps
		
		olAssert.assertAll();
	}
	
	/**
	 * Stub implementation of DateTimeBlock via AbstractDateTimeBlock to unit test the base functionality
	 * of the AbstractDateTimeBlock class (constructors, equals, hashCode, compareTo, duration, etc)
	 *
	 * @author Mike Reinhold
	 *
	 */
	public static class DateTimeBlockStub extends AbstractDateTimeBlock{

		/**
		 * Create a new DateTimeBlockStub using local times and a specific time zone
		 * 
		 * @param dow the day of the week
		 * @param startTime the local start time
		 * @param endTime the local end time
		 * @param zone the time zone reference for the local times
		 * @param startDate the start date
		 * @param endDate the end date
		 */
		protected DateTimeBlockStub(DayOfWeek dow, LocalTime startTime, LocalTime endTime,	ZoneOffset zone, LocalDate startDate, LocalDate endDate) {
			super(dow, startTime, endTime, zone, startDate, endDate);
		}
		
		/**
		 * Create a new DateTimeBlockStub using the zoned times
		 *
		 * @param dow the day of the week
		 * @param startTime the zoned start time
		 * @param endTime the zoned end time
		 * @param startDate the start date
		 * @param endDate the end date
		 */
		protected DateTimeBlockStub(DayOfWeek dow, OffsetTime startTime, OffsetTime endTime, LocalDate startDate, LocalDate endDate) {
			super(dow, startTime, endTime, startDate, endDate);
		}
	}
}
