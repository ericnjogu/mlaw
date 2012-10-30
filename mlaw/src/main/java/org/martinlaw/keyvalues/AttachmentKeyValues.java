/**
 * 
 */
package org.martinlaw.keyvalues;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012 Eric Njogu (kunadawa@gmail.com)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


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
