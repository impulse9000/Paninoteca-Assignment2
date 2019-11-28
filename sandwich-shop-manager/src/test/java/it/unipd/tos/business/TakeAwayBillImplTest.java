package it.unipd.tos.business;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.unipd.tos.business.exception.TakeAwayBillException;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.MenuItem.itemType;


public class TakeAwayBillImplTest {
	
	MenuItem m1;
	MenuItem m2;
	List<MenuItem> l;
	
	@Before
	public void before() {
		l = new ArrayList<MenuItem>();
		m1 = new MenuItem(itemType.BEVANDA, "coca-cola", 2.5);
		m2 = new MenuItem(itemType.FRITTO, "arancini", 4.5);
		l.add(m1);
		l.add(m2);
	}
	
	@Test
	public void testOrderPrice() throws TakeAwayBillException {
		
		double expectedPrice = m1.getPrice() + m2.getPrice();
		TakeAwayBillImpl bill = new TakeAwayBillImpl();
		double sum = bill.getOrderPrice(l);
		assertTrue(sum == expectedPrice);
	}
	

}
