/**
 * 
 */
package org.martinlaw.keyvalues;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.bo.Note;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.DocumentFormBase;

/**
 * generates a list of key values for attachments (notes)
 *  
 *  to be displayed as a drop down box for each conveyance annex type. 
 *  should be setup to be refreshed when an attachment is added to the doc 
 * 
 * @author mugo
 *
 */
public class AttachmentKeyValues extends UifKeyValuesFinderBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9206469740259414962L;

	
	@Override
	public List<KeyValue> getKeyValues(ViewModel model) {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		// blank option available by default
		// keyValues.add(new ConcreteKeyValue("", ""));
		if (model == null) {
			return keyValues;
		}
		Document doc = ((DocumentFormBase)model).getDocument();
		if (doc != null && doc.getNotes() != null) {
			for (Note note: doc.getNotes()) {
				// note has to refer to a file
				if (note.getAttachment() != null) {
					keyValues.add(new ConcreteKeyValue(note.getNotePostedTimestamp().toString(), 
							note.getAttachment().getAttachmentFileName()));
				}
			}
		}
		return keyValues;
	}

}
