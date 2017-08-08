package com.kazge.sopoexample.data;

// default package

/**
 * Role entity. @author MyEclipse Persistence Tools
 */

public class Role implements java.io.Serializable {

	// Fields    

	/**
	 * 
	 */
	private static final long serialVersionUID = -3306188533682510838L;
	private Long id;
	private String rid;
	private String name;
	private String memo;

	// Constructors

	/** default constructor */
	public Role() {
	}

	/** full constructor */
	public Role(String rid, String name) {
		this.rid = rid;
		this.name = name;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRid() {
		return this.rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj || !(obj instanceof Role)){
			return false;
		}
		return this.getRid().equalsIgnoreCase(((Role)obj).getRid());
	}

	@Override
	public int hashCode() {
		return rid.hashCode();
	}
}