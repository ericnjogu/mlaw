/**
 * 
 */
package org.martinlaw.keyvalues;

import org.martinlaw.bo.MatterEvent;
import org.martinlaw.bo.MatterExtensionHelper;

/**
 * {@link tests ScopedKeyValuesUif} when configured on the {@link MatterEvent} maintenance for {@link EventType}
 * @author mugo
 *
 */
public class EventTypeKeyValuesTest extends ScopedKeyValuesUifTestBase {

	@Override
	protected MatterExtensionHelper getTestDataObject() {
		return new MatterEvent();
	}

}
