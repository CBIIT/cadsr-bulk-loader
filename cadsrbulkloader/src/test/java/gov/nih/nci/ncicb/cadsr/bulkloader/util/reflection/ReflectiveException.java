/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.util.reflection;

import java.io.PrintStream;
import java.io.PrintWriter;

public class ReflectiveException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	private Exception e;

    public ReflectiveException(Exception sourceException)
    {
        this.e = sourceException;
    }

    public void printStackTrace()
    {
        e.printStackTrace();
    }

    public void printStackTrace(PrintStream ps)
    {
        e.printStackTrace(ps);
    }

    public void printStackTrace(PrintWriter pw)
    {
        e.printStackTrace(pw);
    }
}
