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

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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
	 * 		s1 == s2, s1 equals s2
	 * 		s1 != s3, s1 !equals s3
	 * 		s1 != s4, s1 !equals s4
	 * 		s3 < s1 < s4
	 */
	Section s1, s2, s3, s4;
	
	/**
	 * Date time blocks which can be used by the Meeting tests
	 * 
	 * 		t1 < t2 < t3
	 */
	DateTimeBlock t1, t2, t3;
	
	/**
	 * Prepare the test instances and necessary stubs for use in the tests
	 *
	 */
	@BeforeClass
	public void setup() {
		setupSections();
		
		//TODO setup DateTimeBlock stubs
		
		//TODO setup Meetings for testing
		
	}
	
	/**
	 * Setup stubbed sections
	 *
	 */
	private void setupSections() {
		s1 = new SectionStub(50);
		s2 = new SectionStub(50);
		s3 = new SectionStub(10);
		s4 = new SectionStub(90);
	}
	
	//TODO confirm equals
	//TODO confirm hashCode
	//TODO confirm compareTo
	
	/**
	 * A stub of the Section interface for use when testing the Meeting interface
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
		 * @see io.devyse.scheduler.model.Section#getCRN()
		 */
		@Override
		public String getCRN() {
			return this.getId().toString();
		}
		
	}
}
