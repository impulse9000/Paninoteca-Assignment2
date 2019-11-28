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
		return finalPrice;
		
	}
	

}
