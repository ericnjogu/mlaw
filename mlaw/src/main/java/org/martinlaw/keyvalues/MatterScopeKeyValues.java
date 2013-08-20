/**
 * 
 */
package org.martinlaw.keyvalues;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2013 Eric Njogu (kunadawa@gmail.com)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.martinlaw.bo.Matter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

/**
 * generates a list of matter key values for scope - to be displayed in a drop down box for status, event type etc
 * 
 * @author mugo
 *
 */
public class MatterScopeKeyValues extends KeyValuesBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9222886917796064323L;
	private transient Logger log = Logger.getLogger(getClass());
	private String basePackage = "org.martinlaw.bo";


	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.keyvalues.KeyValuesFinder#getKeyValues()
	 */
	@Override
	public List<KeyValue> getKeyValues() {
		List<KeyValue> kvs = new ArrayList<KeyValue>(3);
		kvs.add(new ConcreteKeyValue("", ""));
		// from stack overflow answer
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AssignableTypeFilter(Matter.class));
		try {
			Set<BeanDefinition> results = scanner.findCandidateComponents(getBasePackage());
			for (BeanDefinition beanDef: results) {
				ConcreteKeyValue kv = new ConcreteKeyValue(
						beanDef.getBeanClassName(), beanDef.getBeanClassName().substring(beanDef.getBeanClassName().lastIndexOf('.') + 1));
				kvs.add(kv);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return kvs;
	}


	/**
	 * @return the basePackage
	 */
	public String getBasePackage() {
		return basePackage;
	}


	/**
	 * @param basePackage the basePackage to set
	 */
	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

}
