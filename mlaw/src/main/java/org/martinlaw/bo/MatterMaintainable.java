/**
 * 
 */
package org.martinlaw.bo;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.document.IdentityManagementPersonDocument;
import org.kuali.rice.kim.impl.identity.affiliation.EntityAffiliationBo;
import org.kuali.rice.kim.impl.identity.entity.EntityBo;
import org.kuali.rice.kim.impl.identity.name.EntityNameBo;
import org.kuali.rice.kim.impl.identity.principal.PrincipalBo;
import org.kuali.rice.kim.impl.identity.type.EntityTypeContactInfoBo;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.SequenceAccessorService;
import org.martinlaw.MartinlawConstants;

/**
 * @author mugo
 *
 */
public class MatterMaintainable extends MaintainableImpl {
	private String whiteSpaceRegex = "\\s+";
	transient Logger log = Logger.getLogger(getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = -4987668014432412141L;

	/**
	 * create a principal from the information given by the user 
	 * e.g. Mathenge wa Gitahi
	 * @param principalName - the principal name to create
	 * @param affiliationCode - the affiliation to assign
	 * @param namesFromUser - names given by user
	 * @see org.kuali.rice.kew.xml.UserXmlParser
	 * @see org.kuali.rice.kim.document.IdentityManagementPersonDocument#initializeDocumentForNewPerson
	 */
	protected void createPrincipal(String principalName, String affiliationCode, String namesFromUser) {
		SequenceAccessorService sas = KRADServiceLocator.getSequenceAccessorService();
		Long entityId = sas.getNextAvailableSequenceNumber(KimConstants.SequenceNames.KRIM_ENTITY_ID_S, 
        		EntityBo.class);
		EntityBo entityBo = new EntityBo();
		entityBo.setActive(true);
		entityBo.setId(String.valueOf(entityId));
		
		Long entityNameId = sas.getNextAvailableSequenceNumber(
				"KRIM_ENTITY_NM_ID_S", EntityNameBo.class);
		EntityNameBo name = new EntityNameBo();
		name.setActive(true);
		name.setId(String.valueOf(entityNameId));
		name.setEntityId(entityBo.getId());
		// must be in krim_ent_nm_typ_t.ent_nm_typ_cd
		name.setNameCode("PRFR");
		name.setFirstName(getFirstName(namesFromUser));
		name.setMiddleName(getMiddleName(namesFromUser));
		name.setLastName(getLastName(namesFromUser));
		name.setDefaultValue(true);

		Long entityAffilId = sas.getNextAvailableSequenceNumber("KRIM_ENTITY_AFLTN_ID_S", EntityAffiliationBo.class);
		EntityAffiliationBo affilBo = new EntityAffiliationBo();
		affilBo.setActive(true);
		affilBo.setDefaultValue(true);
		affilBo.setId(String.valueOf(entityAffilId));
		affilBo.setAffiliationTypeCode(affiliationCode);
		affilBo.setEntityId(entityBo.getId());
		
		List<EntityTypeContactInfoBo> entityTypes = new ArrayList<EntityTypeContactInfoBo>();
		EntityTypeContactInfoBo entityType = new EntityTypeContactInfoBo();
		entityType.setEntityId(entityBo.getId());
		entityType.setEntityTypeCode(KimConstants.EntityTypes.PERSON);
		entityType.setActive(true);
		entityTypes.add(entityType);
		entityBo.setEntityTypeContactInfos(entityTypes);
		
		KimApiServiceLocator.getIdentityService().createEntity(EntityBo.to(entityBo));
		getBusinessObjectService().save(affilBo);
		getBusinessObjectService().save(name);
		
		// create and populate principal
		Long principalId = sas.getNextAvailableSequenceNumber(KimConstants.SequenceNames.KRIM_PRNCPL_ID_S, 
				IdentityManagementPersonDocument.class);
		PrincipalBo principalBo = new PrincipalBo();
		principalBo.setActive(true);
		principalBo.setPrincipalId(String.valueOf(principalId));
		principalBo.setPrincipalName(principalName);
		principalBo.setEntityId(String.valueOf(entityId));
		
		KimApiServiceLocator.getIdentityService().addPrincipalToEntity(PrincipalBo.to(principalBo));
	}
	
	/**
	 * return the last name
	 * @param namesFromUser
	 * @return if more than one name, the last one, else empty string
	 */
	protected String getLastName(String namesFromUser) {
		if (StringUtils.isEmpty(namesFromUser)) return "";
		String[] names = namesFromUser.trim().split(whiteSpaceRegex);
		if (names.length > 1) {
			return names[names.length - 1];
		} else {
			return "";
		}
	}

	/**
	 * return the middle name(s)
	 * @param namesFromUser
	 * @return if three or more names, the middle name(s), else empty string (2 or less)
	 */
	protected String getMiddleName(String namesFromUser) {
		if (StringUtils.isEmpty(namesFromUser)) return "";
		String[] names = namesFromUser.trim().split(whiteSpaceRegex);
		StringBuilder sb = new StringBuilder();
		if (names.length > 2) {
			for (int i=1; i<names.length - 1; i++) {
				sb.append(names[i]);
				sb.append(" ");
			}
		}
		return sb.toString().trim();
	}

	/**
	 * return the first name
	 * @param namesFromUser
	 * @return the first name
	 */
	protected String getFirstName(String namesFromUser) {
		if (StringUtils.isEmpty(namesFromUser)) return "";
		String[] names = namesFromUser.trim().split(whiteSpaceRegex);
		return names[0];
	}

	/**
	 * given a human readable name e.g. Pauline Wangui Njogu create a kuali rice principal name
	 * @param namesFromUser - the names provided
	 * @return - the principal name (max 100 chars)
	 */
	protected String createPrincipalName(String namesFromUser) {
		if (StringUtils.isEmpty(namesFromUser)) {
			return namesFromUser;
		} else {
			return namesFromUser.trim().toLowerCase().replaceAll(whiteSpaceRegex, "_");
		}
	}

	/**
	 * create principals who do not exist - clients, witnesses
	 * @param persons
	 * @param affiliationCode TODO
	 */
	protected void createPrincipals(List<? extends MartinlawPerson> persons, String affiliationCode) {
		if (! persons.isEmpty()) {
			for (Object obj: persons) {
				MartinlawPerson client = (MartinlawPerson)obj;
				// if this is a new record. principal name may be empty for testing purposes only
				String principalName = createPrincipalName(client.getPrincipalName());
				if (KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(principalName) == null) {
					createPrincipal(principalName, affiliationCode, client.getPrincipalName());
				}
				client.setPrincipalName(principalName);
			}
		}
	}
	
	/**
	 * replace user supplied names with principal name
	 * @param person - contains user supplied info
	 * @param principalName - the new principal name
	 *//*
	protected void replacePrincipalName(MartinlawPerson person, String principalName) {
		Map<String, String> criteria = new HashMap<String, String>(); 
		criteria.put("principalName", person.getPrincipalName());
		Collection<? extends MartinlawPerson> persons = getBusinessObjectService().findMatching(person.getClass(), criteria);
		if (! persons.isEmpty()) {
			person = persons.iterator().next();
			person.setPrincipalName(principalName);
			getBusinessObjectService().save(person);
		}
	}*/

	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.maintenance.MaintainableImpl#doRouteStatusChange(org.kuali.rice.krad.bo.DocumentHeader)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void doRouteStatusChange(DocumentHeader documentHeader) {
		super.doRouteStatusChange(documentHeader);
		if (documentHeader.getWorkflowDocument().isProcessed()) {
			createPrincipals(((Matter) getDataObject()).getClients(), MartinlawConstants.AffiliationCodes.CLIENT);
		}
	}

}
