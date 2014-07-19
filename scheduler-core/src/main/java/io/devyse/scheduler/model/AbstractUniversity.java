package io.devyse.scheduler.model;

import java.util.UUID;

/**
 * Provide basic functionality and implementation for the University interface
 *
 * @author Mike Reinhold
 *
 */
public abstract class AbstractUniversity implements University {
	
	/**
	 * Unique identifier for the university based on common name
	 */
	private UUID id;
	
	/**
	 * Common name of the university
	 */
	private String name;
	
	/* (non-Javadoc)
	 * @see io.devyse.scheduler.model.University#getId()
	 */
	@Override
	public UUID getId(){
		return this.id;
	}
	
	/* (non-Javadoc)
	 * @see io.devyse.scheduler.model.University#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}
	
	/**
	 * @param id the id to set
	 */
	protected void setId(UUID id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	protected void setName(String name) {
		this.name = name;
	}

	/**
	 * Create a new AbstractUniversity with the specified name
	 *
	 */
	public AbstractUniversity(String name) {
		super();
		
		this.setName(name);
		this.setId(UUID.nameUUIDFromBytes(name.getBytes()));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof University){ return this.equals((University)obj);}
		else return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getName();
	}
}
