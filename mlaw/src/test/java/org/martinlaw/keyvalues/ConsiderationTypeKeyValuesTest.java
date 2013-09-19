/**
 * 
 */
package org.martinlaw.keyvalues;

import org.martinlaw.bo.ConsiderationType;
import org.martinlaw.bo.MatterConsideration;
import org.martinlaw.bo.MatterExtensionHelper;

/**
 * {@link tests ScopedKeyValuesUif} when configured on the {@link MatterConsideration} maintenance for {@link ConsiderationType}
 * @author mugo
 *
 */
public class ConsiderationTypeKeyValuesTest extends ScopedKeyValuesUifTestBase {

	@Override
	protected MatterExtensionHelper getTestDataObject() {
		return new MatterConsideration();
	}

}
