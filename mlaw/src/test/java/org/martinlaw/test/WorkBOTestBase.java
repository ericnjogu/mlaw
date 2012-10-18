package org.martinlaw.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.MatterWork;
import org.martinlaw.bo.MatterWorkRule;

/**
 * base class with BO tests for {@link MatterWork} objects
 * @author mugo
 *
 */
public abstract class WorkBOTestBase extends MartinlawTestsBase {

	private MatterWork work;
	private Class<? extends MatterWork> workClass;
	private String docType;

	public WorkBOTestBase() {
		super();
	}

	/**
	 * test that a {@link MatterWork} object created via sql can be retrieved
	 * @param klass
	 */
	@Test
	public void testWorkRetrieve() {
		MatterWork workTemp = getBoSvc().findBySinglePrimaryKey(getWorkClass(), 1001l);
		assertNotNull("result should not be null", workTemp);
		//assertNotNull("contract should not be null", contractWork.getMatter());
	}

	/**
	 * test that {@link MatterWork} is loaded into the data dictionary
	 */
	@Test
	public void testWorkDD() {
		assertNotNull("document entry should not be null", KRADServiceLocatorWeb.getDataDictionaryService().getDataDictionary().getDocumentEntry(getDocType()));
		assertEquals("document type name does not match", getDocType(), KRADServiceLocatorWeb.getDataDictionaryService().getDocumentTypeNameByClass(workClass));
	}

	/**
	 * tests {@link org.martinlaw.bo.MatterWork#isMatterIdValid()}
	 */
	@Test
	public void testMatterIdValidity() {
		assertFalse("un-initialized matter id should be invalid", getWork().isMatterIdValid());
		getWork().setMatterId(-1l);
		assertFalse("matter id should be invalid", getWork().isMatterIdValid());
		getWork().setMatterId(1001l);
		assertTrue("matter id should be valid", getWork().isMatterIdValid());
	}
	
	/* (non-Javadoc)
	 * @see org.kuali.test.KRADTestCase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		// since not derived from ContractBoTestBase, all dependent data needs to be included here
		new SQLDataLoader("classpath:org/martinlaw/scripts/default-data.sql", ";").runSql();
	}
	
	@Test
	/**
	 * tests {@link org.martinlaw.bo.MatterWorkRule#isPrincipalNameInAssigneeList(MatterWork, String)}
	 */
	public void testIsPrincipalNameInAssigneeList () {
		work.setMatterId(3000l);
		MatterWorkRule rule = new MatterWorkRule();
		String principalNameExists = "pn";
		String principalNameDummy = "en";
		assertFalse("matter id does not exist", rule.isPrincipalNameInAssigneeList(work, principalNameExists));
		assertFalse("matter id does not exist", rule.isPrincipalNameInAssigneeList(work, principalNameDummy));
		work.setMatterId(1001l);
		assertTrue("assignee exists", rule.isPrincipalNameInAssigneeList(work, principalNameExists));
		assertFalse("assignee does not exist", rule.isPrincipalNameInAssigneeList(work, principalNameDummy));
	}

	/**
	 * @return the work
	 */
	public MatterWork getWork() {
		return work;
	}

	/**
	 * @param work the work to set
	 */
	public void setWork(MatterWork work) {
		this.work = work;
	}

	/**
	 * @return the workClass
	 */
	public Class<? extends MatterWork> getWorkClass() {
		return workClass;
	}

	/**
	 * @param workClass the workClass to set
	 */
	public void setWorkClass(Class<? extends MatterWork> workClass) {
		this.workClass = workClass;
	}

	/**
	 * @return the docType
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * @param docType the docType to set
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}
}