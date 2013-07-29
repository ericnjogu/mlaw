/**
 * 
 */
package org.martinlaw.bo.work;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.krad.bo.Note;

/**
 * checks for url's in the submitted text and converts them to html links
 * @author mugo
 *
 */
public class EnhancedNote extends Note {
	transient Logger log = Logger.getLogger(getClass());
	private NoteHelper noteHelper;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3521425084578938434L;

	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.bo.Note#setNoteText(java.lang.String)
	 */
	@Override
	public void setNoteText(String noteText) {
		if (noteHelper == null) {
			noteHelper = new NoteHelper();
		}
		if (StringUtils.isEmpty(noteText)) {
			super.setNoteText(noteText);
		} else {
			super.setNoteText(noteHelper.addLinks(noteText));
		}
	}
}
