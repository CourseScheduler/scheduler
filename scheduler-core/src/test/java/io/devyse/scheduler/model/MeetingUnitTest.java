/**
 * @(#) MeetingUnitTest.java
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
import java.time.ZoneOffset;
import java.util.Objects;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 * Unit tests for the AbstractMeeting base class to confirm the default
 * functionality has appropriate semantics.
 *
 * @author Mike Reinhold
 *
 */
@Test(	groups = {"unit","interface","Meeting.basic"}, 
		dependsOnGroups = {"DateTimeBlock.basic"}
)
public class MeetingUnitTest {
	
	/**
	 * Sections which can be used by the Meeting tests
	 * 
	 * 		s1 != s2, s1 !equals s2
	 * 		s1 != s3, s1 !equals s3
	 * 		s1 < s2 < s3
	 */
	Section s1, s2, s3;
	
	/**
	 * Date time blocks which can be used by the Meeting tests
	 * 
	 * 		t1 < t2 < t3
	 */
	DateTimeBlock t1, t2, t3, t4;
	
	/**
	 * Meetings which can be used by the Meeting tests
	 * 
	 * 		m0 = s1 & t1
	 * 		m0b = m0
	 * 		m1 = s1 & t1
	 * 		m2 = s1 & t2
	 * 		m3 = s1 & t3
	 * 		m4 = s2 & t1
	 * 		m5 = s3 & t1
	 */
	Meeting m0, m0b, m1, m2, m3, m4, m5;
	
	
	/**
	 * Prepare the test instances and necessary stubs for use in the tests
	 *
	 */
	@BeforeClass
	public void setup() {
		setupSections();
		setupDateTimeBlocks();
		setupMeetings();
	}
	
	/**
	 * Set up stubbed sections
	 * 
	 */
	private void setupSections() {
		s1 = new SectionStub(10);
		s2 = new SectionStub(50); 
		s3 = new SectionStub(90);
	}
	
	
	/**
	 * Set up simple date time blocks
	 * 
	 */
	private void setupDateTimeBlocks(){
		t1 = new SimpleDateTimeBlock(DayOfWeek.MONDAY, LocalTime.MIDNIGHT, LocalTime.NOON, ZoneOffset.UTC, LocalDate.now(), LocalDate.now().plusDays(7));
		t2 = new SimpleDateTimeBlock(DayOfWeek.TUESDAY, LocalTime.MIDNIGHT, LocalTime.NOON, ZoneOffset.UTC, LocalDate.now(), LocalDate.now().plusDays(7));
		t3 = new SimpleDateTimeBlock(DayOfWeek.WEDNESDAY, LocalTime.MIDNIGHT, LocalTime.NOON, ZoneOffset.UTC, LocalDate.now(), LocalDate.now().plusDays(7));
	}
	
	/**
	 * Set up the simple meetings
	 *
	 */
	private void setupMeetings(){
		m0 = new SimpleMeeting(s1, t1);
		m0b = m0;
		m1 = new SimpleMeeting(s1, t1);
		m2 = new SimpleMeeting(s1, t2);
		m3 = new SimpleMeeting(s1, t3);
		m4 = new SimpleMeeting(s2, t1);
		m5 = new SimpleMeeting(s3, t1);	
	}
	
	/**
	 * Confirm the semantics of reference and object equality.
	 * 
	 * Note:
	 * 	"Sameness" is via instance equality (via ==)
	 * 	"Equality" is object semantic equality (via equals)
	 */
	@Test
	public void confirmEquals() {
		SoftAssert eq = new SoftAssert();

		//Meeting is equal to another if the sections are equal and the date time blocks are equal
		eq.assertSame(m0, m0b, "References to the same instance should be the same");
		eq.assertEquals(m0, m0b, "References to the same instance should be equal");
		eq.assertEquals(m0, m1, "Instances with same uniqueness fields should be equal");
		
		//Meeting is not equal under any other condition
		eq.assertNotEquals(m1, m2, "Instances with varying date time blocks should not be equal");
		eq.assertNotEquals(m1, m4, "Instances with varying sections should not be equal");
		
		eq.assertAll();
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
	 */
	@Test
	public void confirmHashCode() {
		SoftAssert hc = new SoftAssert();
		
		//hashcode semantics should be consistent with equals()		
		hc.assertEquals(m0.hashCode(), m0b.hashCode(), "");
		hc.assertEquals(m0.hashCode(), m1.hashCode(), "");
		
		//ensure that our sample dataset, which has variety in its field content,
		//has some variation in its hashcode. No variation in hash would be bad
		//this is not sufficient on its own, a good hash should have a uniform, 
		//non-clustering distribution
		boolean variety = 
				m1.hashCode() == m2.hashCode() &&
				m1.hashCode() == m3.hashCode() &&
				m1.hashCode() == m4.hashCode() &&
				m1.hashCode() == m5.hashCode()
		;
		hc.assertFalse(variety, "Hashcode should return a variety of values for instance with varying uniqueness fields");
		
		//TODO alternative mechanisms to identify bad hashes (non-uniformity or clustering behavior)
		
		hc.assertAll();
	}
	
	/**
	 * Confirm the semantics of compareTo is consistent with equals() 
	 * and has appropriate object semantics. 
	 */
	@Test
	public void confirmCompareTo() {
		SoftAssert ct = new SoftAssert();
		
		//compareTo should be consistent with equals
		ct.assertEquals(m0.compareTo(m0b), 0, "References to same instance should be equal");
		ct.assertEquals(m0b.compareTo(m0), 0, "References to same instance should be equal, regardless of comparison direction");
		ct.assertEquals(m0.compareTo(m1), 0, "Instances with same fields should be equal");
		ct.assertEquals(m1.compareTo(m0), 0, "Instances with same fields should be equal, regardless of comparison direction");
		
		//compareTo by Section first and DateTimeBlock second
		ct.assertEquals(Math.signum(m1.compareTo(m2)), -1.0f, "Negative result expected for instance with greater date time block");
		ct.assertEquals(Math.signum(m2.compareTo(m1)), 1.0f, "Positive result expected for instance with lesser date time block");
		ct.assertEquals(Math.signum(m2.compareTo(m3)), -1.0f, "Negative result expected for instance with greater date time block");
		ct.assertEquals(Math.signum(m3.compareTo(m2)), 1.0f, "Positive result expected for instance with lesser date time block");
		
		ct.assertEquals(Math.signum(m1.compareTo(m4)), -1.0f, "Negative result expected for instance with greater section");
		ct.assertEquals(Math.signum(m4.compareTo(m1)), 1.0f, "Positive result expected for instance with lesser section");
		ct.assertEquals(Math.signum(m4.compareTo(m5)), -1.0f, "Negative result expected for instance with greater section");
		ct.assertEquals(Math.signum(m5.compareTo(m4)), 1.0f, "Positive result expected for instance with lesser section");

		ct.assertEquals(Math.signum(m3.compareTo(m2)), Math.signum(-m2.compareTo(m3)), "CompareTo should reverse sign for reversed direction of comparison of non-equal instances (lesser date time block)");
		ct.assertEquals(Math.signum(m1.compareTo(m2)), Math.signum(-m2.compareTo(m1)), "CompareTo should reverse sign for reversed direction of comparison of non-equal instances (greater date time block)");
		ct.assertEquals(Math.signum(m5.compareTo(m4)), Math.signum(-m4.compareTo(m5)), "CompareTo should reverse sign for reversed direction of comparison of non-equal instances (lesser section)");
		ct.assertEquals(Math.signum(m1.compareTo(m4)), Math.signum(-m4.compareTo(m1)), "CompareTo should reverse sign for reversed direction of comparison of non-equal instances (greater section)");
		
		ct.assertEquals(Math.signum(m1.compareTo(m2)), Math.signum(m2.compareTo(m3)), "Transitivity expected for instances varying on date time block");
		ct.assertEquals(Math.signum(m1.compareTo(m4)), Math.signum(m4.compareTo(m5)), "Transitivity expected for instances varying on section");
		
		ct.assertAll();
	}
	
	/**
	 * A stub of the Section interface for use when testing the Meeting interface.
	 * We need a stub as a basic implementation can't be tested unless the Meeting
	 * base functionality works as expected.
	 * 
	 * Just uses a "section id" to differentiate between sections. 
	 *
	 * @author Mike Reinhold
	 *
	 */
	private static class SectionStub implements Section {

		/**
		 * Unique SectionStub identifier
		 */
		private int id;
		
		/**
		 * Create a SectionStub
		 *
		 * @param id unique SectionStub identifier
		 */
		public SectionStub(int id) {
			this.id = id;
		}
		
		/**
		 * A unique identifier for the SectionStub
		 *
		 * @param id unique SectionStub identifier
		 */
		private Integer getId() {
			return this.id;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(Section o) {
			return getId().compareTo(((SectionStub)o).getId());
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object other){
			if(other instanceof SectionStub) {return getId().equals(((SectionStub)other).getId());}
			return false;
		}
		
		/* (non-Javadoc)
		 * @see io.devyse.scheduler.model.Section#equals(io.devyse.scheduler.model.Section)
		 */
		@Override
		public boolean equals(Section other){
			if(other instanceof SectionStub) {return getId().equals(((SectionStub)other).getId());}
			return false;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode(){
			return Objects.hashCode(getId());
		}

		/* (non-Javadoc)
		 * @see io.devyse.scheduler.model.Section#getCRN()
		 */
		@Override
		public String getCRN() {
			return this.getId().toString();
		}

		/* (non-Javadoc)
		 * @see io.devyse.scheduler.model.Section#getCourseID()
		 */
		@Override
		public String getCourseID() {
			return "";
		}

		/* (non-Javadoc)
		 * @see io.devyse.scheduler.model.Section#getDescription()
		 */
		@Override
		public String getDescription() {
			return "";
		}

		/* (non-Javadoc)
		 * @see io.devyse.scheduler.model.Section#getName()
		 */
		@Override
		public String getName() {
			return "";
		}

		/* (non-Javadoc)
		 * @see io.devyse.scheduler.model.Section#getSectionID()
		 */
		@Override
		public String getSectionID() {
			return "";
		}

		/* (non-Javadoc)
		 * @see io.devyse.scheduler.model.Section#getTerm()
		 */
		@Override
		public Term getTerm() {
			return null;
		}
	}
}