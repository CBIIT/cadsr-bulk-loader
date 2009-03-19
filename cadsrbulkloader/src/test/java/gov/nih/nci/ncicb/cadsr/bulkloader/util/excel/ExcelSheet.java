package gov.nih.nci.ncicb.cadsr.bulkloader.util.excel;

import java.util.ArrayList;

public class ExcelSheet
{
    private String  name;
    //name of the sheet
    //holds a list of column maps
    private ArrayList map = new ArrayList();

    public ExcelSheet(String name)
    {
        this.name = name;
    }

    public ArrayList getMap()
    {
        return map;
    }

    public void addColumn(ExcelMap col)
    {
        map.add((Object) col);
    }
}
