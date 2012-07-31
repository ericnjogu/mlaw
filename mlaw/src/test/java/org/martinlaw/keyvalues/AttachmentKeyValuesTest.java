package org.martinlaw.keyvalues;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.bo.Attachment;
import org.kuali.rice.krad.bo.Note;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.web.form.DocumentFormBase;
/**
 * test that {@link AttachmentKeyValues} works as expected
 * @author mugo
 *
 */
public class AttachmentKeyValuesTest {

	private AttachmentKeyValues attKeyValues;
	private List<Note> notes;
	private DocumentFormBase model;
	private Document doc;

	@Before
	public void setUp() throws Exception {
		attKeyValues = new AttachmentKeyValues();
		//create some notes
		notes = new ArrayList<Note>(); 
		//add a note without an attachment and one with
		Note mockNoteWithoutAtt = mock(Note.class);
		when(mockNoteWithoutAtt.getAttachment()).thenReturn(null);
		notes.add(mockNoteWithoutAtt);
		Note mockNoteWithAtt = mock(Note.class);
		when(mockNoteWithAtt.getNoteIdentifier()).thenReturn(1001l);
		Attachment att = new Attachment();
		att.setAttachmentFileName("filename.ext");
		when(mockNoteWithAtt.getAttachment()).thenReturn(att);
		notes.add(mockNoteWithAtt);
		// mock the model
		model = mock(DocumentFormBase.class);
		doc = mock(Document.class);
	}

	@Test
	/**
	 * test for null document and notes
	 */
	public void testGetKeyValuesWithNullValues() {
		// null model expect no key values
		assertEquals(0, attKeyValues.getKeyValues(null).size());
		// null document, expect no key values
		when(model.getDocument()).thenReturn(null);
		assertEquals(0, attKeyValues.getKeyValues(model).size());
		// null notes, expect no key values
		when(model.getDocument()).thenReturn(doc);
		when(doc.getNotes()).thenReturn(null);
		assertEquals(0, attKeyValues.getKeyValues(model).size());
	}
	
	/**
	 * test that key values are generated ok when the document has notes
	 */
	@Test
	public void testGetKeyValues() {
		when(doc.getNotes()).thenReturn(notes);
		when(model.getDocument()).thenReturn(doc);
		//expect one where the attachment is present
		List<KeyValue> keyValues = attKeyValues.getKeyValues(model);
		assertEquals(1, keyValues.size());
		assertEquals("1001", keyValues.get(0).getKey());
		assertEquals("filename.ext", keyValues.get(0).getValue());
	}

}
