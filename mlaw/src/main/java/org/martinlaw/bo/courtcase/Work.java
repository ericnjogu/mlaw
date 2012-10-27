/**
 * 
 */
package org.martinlaw.bo.courtcase;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.martinlaw.bo.Matter;
import org.martinlaw.bo.MatterAssignee;
import org.martinlaw.bo.MatterClient;
import org.martinlaw.bo.MatterClientFee;
import org.martinlaw.bo.MatterFee;
import org.martinlaw.bo.MatterWork;

/**
 * a transactional document whose notes/attachments have work done about a {@link CourtCase}
 * 
 * @author mugo
 *
 */
@Entity(name="court_case_work")
@Table(name="martinlaw_court_case_work_doc_t")
public class Work extends MatterWork {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6173589951133038815L;

	@Override
	public Class<? extends Matter<? extends MatterAssignee, ? extends MatterWork, ? extends MatterClientFee<? extends MatterFee>, ? extends MatterClient>> getMatterClass() {
		return CourtCase.class;
	}

}
