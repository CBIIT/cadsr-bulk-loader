package gov.nih.nci.ncicb.cadsr.bulkloader.util.excel;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelSchemaGenerator
{
    public static void generateSchema(String excelFile, Connection conn, String schema)
        throws Exception
    {
        HSSFWorkbook wb = ExcelUtility.createWorkbook();

        try
        {
            DatabaseMetaData metadata = conn.getMetaData();
            ResultSet rs = metadata.getTables(null, schema, null, null);


            Pattern p = Pattern.compile("\\\\|/|\\*|\\?|\\[|\\]|\\s+");
            while (rs.next())
            {
                String name = rs.getString("TABLE_NAME");
                try
                {

                    Matcher m = p.matcher(name);
                    if(name == null || m.find() || name.length() > 31)
                        continue;
                    HSSFSheet sheet = wb.createSheet("tbl_" + name);
                    HSSFCell cell = ExcelUtility.createCell(sheet, 1, (short) 1);

                    createTableHeader(sheet, name);

                    ResultSet cols = metadata.getColumns(null, schema, name, null);
                    int start_row = 7;
                    int index = 1;

                    while (cols.next())
                    {
                        String colname = cols.getString("COLUMN_NAME");
                        String type = cols.getString("TYPE_NAME");

                        ExcelUtility.setCellValue(sheet, start_row, (short) index, colname);
                        ExcelUtility.setCellValue(sheet, start_row + 1, (short) index, type);
                        ++index;
                    }
                }
                catch (Throwable e)
                {
                    continue;
                }
            }
            ExcelUtility.writeExcelFile(excelFile, wb);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public static void createTableHeader(HSSFSheet sheet, String name)
    {
        ExcelUtility.setCellValue(sheet, 1, (short) 0, "Table Meta Data");
        ExcelUtility.setCellValue(sheet, 2, (short) 0, "Table Name");
        ExcelUtility.setCellValue(sheet, 2, (short) 1, name);
        ExcelUtility.setCellValue(sheet, 3, (short) 0, "Reload");
        ExcelUtility.setCellValue(sheet, 5, (short) 0, "Test Dataset");
        ExcelUtility.setCellValue(sheet, 7, (short) 0, "Columns");

    }
}
