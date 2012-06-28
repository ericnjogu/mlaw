package org.martinlaw.test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.core.framework.resourceloader.SpringResourceLoader;
import org.kuali.test.KRADTestCase;

public abstract class MartinlawTestsBase extends KRADTestCase {

	public MartinlawTestsBase() {
		super("mlaw");
	}	

	/* (non-Javadoc)
	 * @see org.kuali.test.KRADTestCase#getLoadApplicationLifecycle()
	 */
	@Override
	protected Lifecycle getLoadApplicationLifecycle() {
		//TODO: create a get application context file() method in KRADTestCase so that we only override that
		// and keep the logic there
		SpringResourceLoader springResourceLoader = new SpringResourceLoader(new QName("KRADTestResourceLoader"), "classpath:org/martinlaw/bo/BOTest-context.xml", null);
    	springResourceLoader.setParentSpringResourceLoader(getTestHarnessSpringResourceLoader());
    	return springResourceLoader;
	}

	/* (non-Javadoc)
	 * @see org.kuali.rice.test.RiceTestCase#configureLogging()
	 */
	@Override
	protected void configureLogging() throws IOException {
		//use custom logging configuration - does not appear work
		System.setProperty("alt.log4j.config.location","classpath:log4j.properties" );
		super.configureLogging();
	}
	
	@Override
	/**
	 * make sure the right module name is set so that the relevant config files are loaded
	 */
	protected List<String> getConfigLocations() {
		return Arrays.asList(new String[]{"classpath:META-INF/martinlaw-test-config.xml", "classpath:META-INF/krad-test-config.xml"});
	}

}