package io.devyse.scheduler.model;

/**
 * Provide a basic implementation of the Section interface
 * for testing the base method funtion and semantics
 *
 * @author Mike Reinhold
 *
 */
public class SimpleSection extends AbstractSection {
	
	/**
	 * Create a SimpleSection for the specified term using the
	 * provided registration number
	 *
	 * @param term the term in which the section is available
	 * @param crn the registration number for the section
	 * @param courseId the course identifier
	 * @param sectionId the section identifier
	 */
	public SimpleSection(Term term, String crn, String courseId, String sectionId) {
		super(term, crn, courseId, sectionId);
		
		//TODO ensure that the AbstractSection is fully initialized?
		/*
		 * this.setDescription("description");
		 * this.setName("name");
		 */	
	}	
}
