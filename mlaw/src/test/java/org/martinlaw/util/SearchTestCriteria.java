/**
 * 
 */
package org.martinlaw.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.directory.shared.ldap.util.ReflectionToStringBuilder;

/**
 * holds the information used to conduct a document search test
 * @author mugo
 *
 */
public class SearchTestCriteria {
	private int expectedDocuments;
	private Map<String, String> fieldNamesToSearchValues;
	
	/**
	 * @param fieldNamesToSearchValues
	 */
	public SearchTestCriteria() {
		this.fieldNamesToSearchValues = new HashMap<String, String>();
	}
	/**
	 * gets the number of expected documents found in the search
	 * @return the expectedDocuments
	 */
	public int getExpectedDocuments() {
		return expectedDocuments;
	}
	/**
	 * @param expectedDocuments the expectedDocuments to set
	 */
	public void setExpectedDocuments(int expectedDocuments) {
		this.expectedDocuments = expectedDocuments;
	}
	/**
	 * gets the map of all field names mapped to the search value for each
	 * @return the fieldNamesToSearchValues
	 */
	public Map<String, String> getFieldNamesToSearchValues() {
		return fieldNamesToSearchValues;
	}
	/**
	 * @param fieldNamesToSearchValues the fieldNamesToSearchValues to set
	 */
	public void setFieldNamesToSearchValues(Map<String, String> fieldNamesToSearchValues) {
		this.fieldNamesToSearchValues = fieldNamesToSearchValues;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
