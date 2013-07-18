/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.transformer.beans;

import java.util.List;

public abstract class Item {

	private List<Item> subItems;
	
	
	public List<Item> getSubItems() {
		return subItems;
	}
	public void setSubItems(List<Item> subItems) {
		this.subItems = subItems;
	}
	
	public abstract List<Object> getNames();
	public abstract List<Object> getValues();
	public abstract int getItemNumber();
}
