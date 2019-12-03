////////////////////////////////////////////////////////////////////
// [Andrea] [Polo-Perucchin] [1169765]
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import it.unipd.tos.business.exception.TakeAwayBillException;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.MenuItem.itemType;

public class TakeAwayBillImpl implements TakeAwayBill {
	
	@Override
	public double getOrderPrice(List<MenuItem> itemsOrdered)
			throws TakeAwayBillException{	
		double finalPrice = 0;
		ListIterator<MenuItem> items = itemsOrdered.listIterator();
		while (items.hasNext()) {
			finalPrice += items.next().getPrice();
		}
		return finalPrice-get50Discount(itemsOrdered);
		
	}
	
	// 50% di sconto sul panino meno caro se acquistati più di 5, ritorna il risparmio in euro oppure 0 se non applicabile
	public double get50Discount(List<MenuItem> itemsOrdered) {
		int count = 0;
		double minPrice = 100000;
		ListIterator<MenuItem> items = itemsOrdered.listIterator();
		while(items.hasNext()) {
			MenuItem menuItem = items.next();
			if (menuItem.getItemType() == itemType.PANINO) {
				count++;
				if (minPrice > menuItem.getPrice()) {
					minPrice = menuItem.getPrice();
				}
			}
			
		}
		if (count>5) 
			return 0.50*minPrice;
		else
			return 0;
	}
	

}
