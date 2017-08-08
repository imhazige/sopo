package com.kazge.sopoexample.data;
// default package



/**
 * RolePurview entity. @author MyEclipse Persistence Tools
 */

public class RolePurview  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = -7265862407447023385L;
	private Long id;
     private String rid;
     private String pid;


    // Constructors

    /** default constructor */
    public RolePurview() {
    }

    
    /** full constructor */
    public RolePurview(String rid, String pid) {
        this.rid = rid;
        this.pid = pid;
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

    public String getPid() {
        return this.pid;
    }
    
    public void setPid(String pid) {
        this.pid = pid;
    }

    @Override
    public boolean equals(Object obj) {
    	if (null == obj || !(obj instanceof RolePurview)){
    		return false;
    	}
    	RolePurview rp = (RolePurview)obj;
    	
    	return rp.pid.equals(pid) && rp.rid.equals(rid);
    }

    @Override
    public int hashCode() {
    	return (pid + "#" + rid).hashCode();
    }






}