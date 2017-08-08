package com.kazge.sopoexample.data;
// default package



/**
 * UserRole entity. @author MyEclipse Persistence Tools
 */

public class UserRole  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 7964515680028150460L;
	private Long id;
     private String uid;
     private String rid;


    // Constructors

    /** default constructor */
    public UserRole() {
    }

    
    /** full constructor */
    public UserRole(String uid, String rid) {
        this.uid = uid;
        this.rid = rid;
    }

   
    // Property accessors

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return this.uid;
    }
    
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRid() {
        return this.rid;
    }
    
    public void setRid(String rid) {
        this.rid = rid;
    }
   








}