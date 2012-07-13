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
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

/**
 * generates a list of key values for attachments (notes)
 *  
 *  to be displayed as a drop down box for each conveyance annex type 
 * 
 * @author mugo
 *
 */
public class AttachmentKeyValues extends KeyValuesBase {

	private Document document;
	/**
	 * 
	 */
	private static final long serialVersionUID = 9206469740259414962L;

	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.keyvalues.KeyValuesFinder#getKeyValues()
	 */
	@Override
	public List<KeyValue> getKeyValues() {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		keyValues.add(new ConcreteKeyValue("", ""));
		if (getDocument() != null && getDocument().getNotes() != null) {
			for (Note note: getDocument().getNotes()) {
				// note has to refer to a file
				if (note.getAttachment() != null) {
					keyValues.add(new ConcreteKeyValue(note.getNoteIdentifier().toString(), 
							note.getAttachment().getAttachmentFileName()));
				}
			}
		}
		return keyValues;
	}

	/**
	 * gets the document to display attachments for
	 * 
	 * @return the document
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * @param document the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

}
