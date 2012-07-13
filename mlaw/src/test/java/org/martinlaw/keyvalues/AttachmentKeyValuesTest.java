package org.martinlaw.keyvalues;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.maintenance.MaintenanceDocumentBase;
import org.kuali.rice.krad.bo.Attachment;
import org.kuali.rice.krad.bo.Note;
/**
 * test that {@link AttachmentKeyValues} works as expected
 * @author mugo
 *
 */
public class AttachmentKeyValuesTest {

	private AttachmentKeyValues attKeyValues;
	private Document mockDoc;
	private List<Note> notes;

	@Before
	public void setUp() throws Exception {
		attKeyValues = new AttachmentKeyValues();
		mockDoc = mock(MaintenanceDocumentBase.class);
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
	}

	@Test
	/**
	 * test for null document and notes
	 */
	public void testGetKeyValuesWithNullValues() {
		// null document, expect one blank key, value
		assertEquals(1, attKeyValues.getKeyValues().size());
		verifyOneBlankKeyValue();
		// null notes
		attKeyValues.setDocument(mockDoc);
		assertEquals(1, attKeyValues.getKeyValues().size());
		verifyOneBlankKeyValue();
	}

	/**
	 * common method to check for a first blank key value
	 */
	protected void verifyOneBlankKeyValue() {
		assertEquals("", attKeyValues.getKeyValues().get(0).getKey());
		assertEquals("", attKeyValues.getKeyValues().get(0).getValue());
	}
	
	/**
	 * test that key values are generated ok when the document has notes
	 */
	@Test
	public void testGetKeyValues() {
		when(mockDoc.getNotes()).thenReturn(notes);
		attKeyValues.setDocument(mockDoc);
		//expect blank key-value, then one where the attachment is present
		assertEquals(2, attKeyValues.getKeyValues().size());
		verifyOneBlankKeyValue();
		assertEquals("1001", attKeyValues.getKeyValues().get(1).getKey());
		assertEquals("filename.ext", attKeyValues.getKeyValues().get(1).getValue());
	}

}
