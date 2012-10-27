/**
 * 
 */
package org.martinlaw;

/**
 * holds constants
 * 
 * @author mugo
 *
 */
public class Constants {
	public class DocTypes {
		public final static String CONTRACT_WORK = "ContractWorkDocument";
		public static final String COURTCASE_WORK = "CourtCaseWorkDocument";
		public static final String OPINION_WORK = "OpinionWorkDocument";
		public static final String CONVEYANCE_WORK = "ConveyanceWorkDocument";
		public static final String CONTRACT_FEE = "ContractFeeDocument";
		public static final String CONVEYANCE_FEE = "ConveyanceFeeDocument";
		public static final String COURTCASE_FEE = "CourtCaseFeeDocument";
		public static final String OPINION_FEE = "OpinionFeeDocument";
	}
	
	public class RequestMappings {
		public final static String TX = "tx";
	}
	
	public class PropertyNames {
		public final static String MATTER_ID = "matterId";
	}
	
	public class MessageKeys {
		public final static String ERROR_NOT_INITIATOR = "error.notInitiator";
	}
	
	public class ViewIds {
		public final static String CONTRACT_FEE = "contract_fee_doc_view";
		public static final String CONTRACT_WORK = "contract_work_doc_view";
		public static final String CONVEYANCE_WORK = "conveyance_work_doc_view";
		public static final String COURTCASE_WORK = "courtcase_work_doc_view";
		public static final String OPINION_WORK = "opinion_work_doc_view";
		public static final String CONVEYANCE_FEE = "conveyance_fee_doc_view";
		public static final String COURTCASE_FEE = "courtcase_fee_doc_view";
		public static final String OPINION_FEE = "opinion_fee_doc_view";
	}
}
