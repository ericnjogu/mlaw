/**
 * 
 */
package org.martinlaw.routing;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kew.actiontaken.ActionTakenValue;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.engine.RouteHelper;
import org.kuali.rice.kew.engine.node.SplitNode;
import org.kuali.rice.kew.engine.node.SplitResult;
import org.martinlaw.MartinlawConstants;

/**
 * go on to a branch if the initiator is not the approver
 * <p>useful where we do not want an FYI to be generated where the initiator and approver is the same person</p>
 * @author mugo
 *
 */
public class InitiatorIsNotApproverSplit implements SplitNode {

	/* (non-Javadoc)
	 * @see org.kuali.rice.kew.engine.node.SplitNode#process(org.kuali.rice.kew.engine.RouteContext, org.kuali.rice.kew.engine.RouteHelper)
	 */
	@Override
	public SplitResult process(RouteContext context, RouteHelper helper)
			throws Exception {
		List<String> branchNames = new ArrayList<String>();
		String initiatorPrincipalId = context.getDocument().getInitiatorPrincipalId();
		String approverPrincipalId = getActionPrincipalId(context.getDocument().getActionsTaken(), KewApiConstants.ACTION_TAKEN_APPROVED_CD); 
		if (approverPrincipalId == null || StringUtils.equals(initiatorPrincipalId, approverPrincipalId)) {
			return new SplitResult(branchNames);
		} else {
			branchNames.add(MartinlawConstants.RoutingBranches.FYI_INITIATOR_IF_NOT_APPROVER);
			return new SplitResult(branchNames);
		}
	}
	
	/**
	 * retrieve an action principal id
	 * <p>If there are several actions of the same type, return the last of those actions encountered</p> 
	 * @param docHdr - the document header to retrieve the date from
	 * @param actionCode - the action's code
	 * @return the principal ID {@link org.kuali.rice.kew.api.WorkflowDocument#getActionsTaken()}, null otherwise
	 */
	protected String getActionPrincipalId(List<ActionTakenValue> actionsTaken, String actionCode) {
		String principalId = null;
		for (ActionTakenValue action: actionsTaken) {
			if (StringUtils.equals(action.getActionTaken(), actionCode)) {
				principalId = action.getPrincipalId();
			}
		}
		return principalId;
	}

}
