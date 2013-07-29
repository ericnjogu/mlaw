/**
 * 
 */
package org.martinlaw.bo.work;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2013 Eric Njogu (kunadawa@gmail.com)
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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @author mugo
 *
 */
public class NoteHelper {
	/**
	 * convert urls to clickable links. 
	 * <p>Detects http, ftp - terminating punctuation is considered part of the link. 
	 * urls detected when separated from sorrounding text by space(s)</p>
	 * <p> absolute windows (having a drive letter) - should be the last item on the line or separated from other text by
	 * [,<>|]</p>
	 * <p> linux paths not supported :( presently</p>
	 * @param note - the string to replace links into
	 * @return the provided string with the all detected links and paths converted into html links
	 */
	public String addLinks(String note) {	
		if (!StringUtils.isEmpty(note)) {
			Map<String, Pattern> patterns = new HashMap<String, Pattern>();
			patterns.put("", Pattern.compile("((http|ftp|spdy)[^ ]+)", Pattern.CASE_INSENSITIVE));
			final String fileScheme = "file://";
			patterns.put(fileScheme, Pattern.compile("([a-zA-Z]{1}:\\\\[^,<>\\|:\"/\\?\\*]+)"));
			// patterns.put(fileScheme, Pattern.compile("(/[^]+)"));
			for (String scheme: patterns.keySet()) {
				Matcher matcher = patterns.get(scheme).matcher(note);
				// List<String> found = new ArrayList<String>(); 
				int start = 0;
				while (matcher.find(start)) {
					// found.add(matcher.group(1));
					String abbrev = StringUtils.abbreviateMiddle(matcher.group(1), "...", 50);
					final String part1 = note.substring(0, matcher.start());
					final String part3 = note.substring(matcher.end());
					final String part2 = "<a href=\"" + scheme + matcher.group(1) + "\">" + abbrev + "</a>";
					note = part1 + part2 + part3;
					matcher = patterns.get(scheme).matcher(note);
					start = part1.length() + part2.length();
				}
				/*for (String find: found) {
					find = find.replace("\\", "\\\\");
					note = note.replaceAll(find, "<a href=\"" + scheme + find + "\">" + 
							StringUtils.abbreviateMiddle(find, "...", 50) + "</a>");
				}*/
			}
		}
		return note;
	}

}
