package io.devyse.scheduler.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Represent the University for which course data can or has been
 * downloaded and schedules can be generated. 
 *
 * @author Mike Reinhold
 *
 */
public interface University extends Comparable<University> {
	
	/**
	 * A UUID uniquely representing the University. This is a Type 3
	 * UUID generated from the bytes of the university name
	 *
	 * @return the UUID that uniquely identifies the University
	 */
	public UUID getId();
	
	/**
	 * The common name of the university, for instance "Kettering University"
	 *
	 * @return university name
	 */
	public String getName();
	
	//TODO data retrieval configuration?
	
	//TODO general URL
	
	//TODO icon, branding?
	
	//TODO campuses?
	
	//TODO terms?
	
	//TODO instructors?
	
	//TODO majors?
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public default boolean equals(University other) {
		return this.getId().equals(other.getId());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public default int getHashCode() {
		return Objects.hash(
			this.getId()
		);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public default int compareTo(University other) {
		return this.getName().compareTo(other.getName());
	}
}
