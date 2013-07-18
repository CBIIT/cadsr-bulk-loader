/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.dao;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Jan 15, 2009
 * @since 
 */

public class BulkLoaderDAOException extends Exception {


	private static final long serialVersionUID = 1L;

	public BulkLoaderDAOException() {
		super();
	}
	
	public BulkLoaderDAOException(Exception e) {
		super(e);
	}
	
	public BulkLoaderDAOException(String message) {
		super(message);
	}
	
	public BulkLoaderDAOException(String message, Exception e) {
		super(message, e);
	}
}
