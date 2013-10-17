/**
 * 
 */
package org.martinlaw.routing;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.engine.RouteHelper;
import org.kuali.rice.kew.engine.node.SplitNode;
import org.kuali.rice.kew.engine.node.SplitResult;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.bo.MatterWork;

/**
 * determines if a work document will be subject to approval depending on {@link MatterAnnexType#getRequiresApproval()}
 * @author mugo
 *
 */
public class AnnexTypeApprovalSplit implements SplitNode {
	private Logger log = Logger.getLogger(getClass());
	private BusinessObjectService businessObjectService;

	/* (non-Javadoc)
	 * @see org.kuali.rice.kew.engine.node.SplitNode#process(org.kuali.rice.kew.engine.RouteContext, org.kuali.rice.kew.engine.RouteHelper)
	 */
	@Override
	public SplitResult process(RouteContext context, RouteHelper helper)
			throws Exception {
		List<String> branchNames = new ArrayList<String>();
		// If work document has an annex type that requires approval, return the approval branch name, otherwise empty branch list
		if (context != null && context.getDocument() != null && context.getDocument().getDocumentId() != null) {
			MatterWork work = getBusinessObjectService().findBySinglePrimaryKey(
					MatterWork.class, context.getDocument().getDocumentId());
			if (work == null) {
				/**
				 * when work is null, workflow testing mode is assumed (where no BO's are present)
				 * this is not anticipated on the UI since work is auto created by the framework and annex type is
				 * a required field
				 */
				branchNames.add(MartinlawConstants.RoutingBranches.ANNEX_TYPE_APPROVAL);
				log.error("work with doc number '" + context.getDocument().getDocumentId() +  "' is  null. testing?");
			} else {
				work.refreshNonUpdateableReferences();
				if (work.getAnnexType() != null && work.getAnnexType().getRequiresApproval()) {
					branchNames.add(MartinlawConstants.RoutingBranches.ANNEX_TYPE_APPROVAL);
				}
				if (work.getAnnexType() == null) {
					log.error("annexType for work with doc number '" + context.getDocument().getDocumentId() +  "' is null");
				}
			}
		}
		
		return new SplitResult(branchNames);
	}

	/**
	 * retrieve the business object service in mock friendly way
	 * @return
	 */
	private BusinessObjectService getBusinessObjectService() {
		if (businessObjectService == null) {
			businessObjectService = KRADServiceLocator.getBusinessObjectService();
		}
		
		return businessObjectService;
	}

	/**
	 * @param businessObjectService the businessObjectService to set
	 */
	public void setBusinessObjectService(BusinessObjectService businessObjectService) {
		this.businessObjectService = businessObjectService;
	}
}
