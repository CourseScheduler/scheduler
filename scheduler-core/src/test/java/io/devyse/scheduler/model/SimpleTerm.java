package io.devyse.scheduler.model;

/**
 * Simple Term implementation for unit testing the base Term
 * methods and semantics
 *
 * @author Mike Reinhold
 *
 */
public class SimpleTerm extends AbstractTerm {

	/**
	 * Create a new SimpleTerm for the specified university using the 
	 * provided identifier
	 *
	 * @param university the parent university
	 * @param id the unique identifier
	 */
	public SimpleTerm(String university, String id) {
		super(university, id);
		
		//TODO ensure that the AbstractTerm is fully initialized?
		/*
		 * this.setName(id);
		 */		
	}

}
