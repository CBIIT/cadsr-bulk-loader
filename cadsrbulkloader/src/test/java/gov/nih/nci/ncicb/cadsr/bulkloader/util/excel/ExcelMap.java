package gov.nih.nci.ncicb.cadsr.bulkloader.util.excel;

public class ExcelMap
{
    private String  attribute;
    private String  dataType;
    private int     row;
    private short   column;

    public ExcelMap(String attribute, String dataType, int row, short column)
    {
        this.attribute = attribute;
        this.dataType = dataType;
        this.row = row;
        this.column = column;
    }

    public String getAttribute()
    {
        return attribute;
    }

    public String getDataType()
    {
        return dataType;
    }

    public int getRow()
    {
        return row;
    }

    public short getColumn()
    {
        return column;
    }

    public void setAttribute(String attribute)
    {
        this.attribute = attribute;
    }

    public void setDataType(String dataType)
    {
        this.dataType = dataType;
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public void setColumn(short column)
    {
        this.column = column;
    }

}