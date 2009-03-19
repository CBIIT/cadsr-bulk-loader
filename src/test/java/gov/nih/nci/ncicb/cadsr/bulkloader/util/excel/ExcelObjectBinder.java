package gov.nih.nci.ncicb.cadsr.bulkloader.util.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public abstract class ExcelObjectBinder {
    
    protected static Log log = LogFactory.getLog(ExcelObjectBinder.class);

    public ExcelObjectBinder() {
        super();
    }
    
    /**
     * Create objects of type (target) populated from the given Excel file 
     * @param excelFile
     * @param target
     * @return Map of (target) objects keyed by worksheet name.  Each map entry value is a List
     *     of objects.
     * @throws IOException
     */
    public Map bind(InputStream excelFile, Class target) throws IOException {

        HSSFWorkbook wb = ExcelUtility.createWorkbook(excelFile);
        int cnt = wb.getNumberOfSheets();
        Map wsMap = new HashMap();
    
        for (int i = 0; i < cnt; i++)
        {
            String name = wb.getSheetName(i);
            if (log.isDebugEnabled()) {
                log.debug("Worksheet binding => "+i+" ("+name+")");
            }
            List objects = bindWorksheet(target,wb.getSheet(name));
            wsMap.put (name,objects);
        }
        return wsMap;
        
    }
    
    /**
     * Bind the given worksheet to the object specified (target)
     * 
     * @param target Object type to bind
     * @param ws Excel worksheet
     * @return List of objects of type (target) populated with data from worksheet ws
     */
    public abstract List bindWorksheet(Class target, HSSFSheet ws);


}
