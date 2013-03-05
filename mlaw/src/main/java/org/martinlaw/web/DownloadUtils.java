/**
 * 
 */
package org.martinlaw.web;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;

/**
 * contains useful functions for downloading files
 * @author mugo
 *
 */
public class DownloadUtils {
	/**
	 * download a file or text represented by an input stream
	 * 
	 * @param response - where to copy the attachment contents to
	 * @param is - represents the source file/string
	 * @param mimeTypeCode - the mime type
	 * @param fileSize  - the file size
	 * @param fileName - the file name to present
	 * @throws IOException
	 */
	protected void downloadAsStream(HttpServletResponse response,
			InputStream is, String mimeTypeCode, int fileSize, String fileName) throws IOException {

		// Set the response headers
		response.setContentType(mimeTypeCode);
		response.setContentLength(fileSize);
		// TODO replace the header strings with constants
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setHeader("Content-Disposition",
		        "attachment; filename=\"" + fileName + "\"");

		// Copy the input stream to the response
		FileCopyUtils.copy(is, response.getOutputStream());
	}
}
