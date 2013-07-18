/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.util.excel;

public class ExcelCell
{
    int             row;
    short           column;
    boolean         isBold;
    boolean         isItalic;
    short           color;

    public ExcelCell(int row, int column)
    {
        this.row = row;
        this.column = (short) column;
    }

    public ExcelCell(boolean bold, boolean italic, short color)
    {
        this.isBold = bold;
        this.isItalic = italic;
        this.color = (short) color;
    }

    public int getRow()
    {
        return row;
    }

    public short getColumn()
    {
        return column;
    }

    public boolean isBold()
    {
        return isBold;
    }

    public boolean isItalic()
    {
        return isItalic;
    }

    public short getColor()
    {
        return color;
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public void setColumn(short column)
    {
        this.column = column;
    }

    public void setBold(boolean bold)
    {
        this.isBold = bold;
    }

    public void setItalic(boolean italic)
    {
        isItalic = italic;
    }

    public void setColor(short color)
    {
        this.color = color;
    }
}
