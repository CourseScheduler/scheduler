package io.devyse.scheduler.model;

/**
 * Provide basic functionality and implementation for the Term
 * interface
 *
 * @author Mike Reinhold
 *
 */
public abstract class AbstractTerm implements Term {
	
	/**
	 * The university for which this term exists
	 */
	private String university;
	
	/**
	 * The unique term identifier for the university
	 */
	private String id;
	
	/**
	 * The common term name
	 */
	private String name;
	
	/* (non-Javadoc)
	 * @see io.devyse.scheduler.model.Term#getUniversity()
	 */
	@Override
	public String getUniversity() {
		return this.university;
	}
	
	/* (non-Javadoc)
	 * @see io.devyse.scheduler.model.Term#getTermID()
	 */
	@Override
	public String getId() {
		return this.id;
	}
	
	/* (non-Javadoc)
	 * @see io.devyse.scheduler.model.Term#getTermName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * @param university the university to set
	 */
	protected void setUniversity(String university) {
		this.university = university;
	}

	/**
	 * @param id the id to set
	 */
	protected void setId(String id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	protected void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Create a new AbstractTerm for the specified university using the
	 * id provided
	 *
	 * @param university the parent university of the term
	 * @param id the unique identifier of the term
	 */
	public AbstractTerm(String university, String id){
		super();
		
		this.setUniversity(university);
		this.setId(id);
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
		if(obj instanceof Term) { return this.equals((Term)obj); }
		else return false;
	}
	
}
