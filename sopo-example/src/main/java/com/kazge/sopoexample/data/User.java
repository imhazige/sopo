package com.kazge.sopoexample.data;

import java.util.Date;

// default package

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable
{

	// Fields    

	/**
	 * 
	 */
	private static final long serialVersionUID = 3412200897635852709L;
	private Long id;
	private String uid;
	private String name;
	private String pwd;
	private String code;
	private Date regdate;

	// Constructors

	/** default constructor */
	public User()
	{
	}

	/** full constructor */
	public User(String uid, String name, String pwd, String code)
	{
		this.uid = uid;
		this.name = name;
		this.pwd = pwd;
		this.code = code;
	}

	// Property accessors

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getUid()
	{
		return this.uid;
	}

	public void setUid(String uid)
	{
		this.uid = uid;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPwd()
	{
		return this.pwd;
	}

	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}

	public String getCode()
	{
		return this.code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public Date getRegdate()
	{
		return regdate;
	}

	public void setRegdate(Date regdate)
	{
		this.regdate = regdate;
	}
}