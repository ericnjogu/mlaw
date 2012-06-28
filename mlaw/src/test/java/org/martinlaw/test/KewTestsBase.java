package org.martinlaw.test;

import java.util.List;

import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.test.lifecycles.KEWXmlDataLoaderLifecycle;

public abstract class KewTestsBase extends MartinlawTestsBase {

	public KewTestsBase() {
		super();
	}

	@Override
	protected List<Lifecycle> getSuiteLifecycles() {
		List<Lifecycle> suiteLifecycles = super.getSuiteLifecycles();
		/*
		 * needs to be here rather in loadData() since it leads to 'object modified' OJB exceptions
		 */
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/kim/users.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/kim/groups.xml"));
		//TODO:uncomment conveyance and case when ready
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/rules/rule-templates.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/case.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/caseStatus.xml"));
		// suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/conveyance.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/rules/rules.xml"));
		return suiteLifecycles;
	}
	
	/**
	 * convenience method to retrieve a principal id
	 * @param name - the principal name
	 * @return the principal id
	 */
	protected String getPrincipalIdForName(String name) {
		return KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(name).getPrincipalId();
	}
}