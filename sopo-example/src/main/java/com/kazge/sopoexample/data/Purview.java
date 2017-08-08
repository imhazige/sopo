package com.kazge.sopoexample.data;

import java.io.Serializable;

import com.kazge.sopo.util.StringUtils;
import com.kazge.sopoexample.common.convert.BooleanConvertor;
import com.kazge.sopoexample.common.convert.IConvertor;
import com.kazge.sopoexample.common.xml.XmlDigester;

/**
 * 
 * 
 *
 */
public class Purview implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4693923749872373143L;
	private String pid = "0";
	private String name;
	private String discription;
	private boolean base = false;
	private String parentid;
	private Purview parent;

	private static final String NAME_PROPERTY = "name";
	private static final String DISCRIPTION_PROPERTY = "discription";
	private static final String PID_PROPERTY = "pid";
	private static final String BASE_PROPERTY = "base";
	private static final String PARENT_PROPERTY = "parentid";

	// Constructors

	/** default constructor */
	public Purview(String xml) {
		loadXml(xml);
	}

	private void loadXml(String xml) {
		XmlDigester digester = new XmlDigester(this, "purview");
		IConvertor boolconvert = new BooleanConvertor();
		digester.addAttrRule(PID_PROPERTY);
		digester.addAttrRule(NAME_PROPERTY);
		digester.addAttrRule(BASE_PROPERTY, boolconvert);
		digester.addTextRule(DISCRIPTION_PROPERTY);
		digester.addAttrRule(PARENT_PROPERTY);
		digester.parse(xml);
		
		if ("0".equalsIgnoreCase(pid)){
			throw new DataException("the pid is wrong:" + pid);
		}
		if (StringUtils.isBlank(parentid)){
			throw new DataException("the parentid can't be null.");
		}
	}

	public String getPid() {
		return this.pid;
	}

	public String getName() {
		return this.name;
	}

	public String getDiscription() {
		return this.discription;
	}

	public boolean isBase() {
		return this.base;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (null == obj || !(obj instanceof Purview)){
			return false;
		}
		Purview p = (Purview)obj;
		return this.pid.equalsIgnoreCase(p.pid);
	}
	
	@Override
	public int hashCode() {
		return this.pid.hashCode();
	}

	public String getParentId() {
		return this.parentid;
	}

	public Purview getParent() {
		return this.parent;
	}
}