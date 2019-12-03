package it.unipd.tos.business;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import org.junit.Before;
import org.junit.Test;

import it.unipd.tos.business.exception.TakeAwayBillException;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.MenuItem.itemType;


public class TestTakeAwayBillImpl {
	
	MenuItem m1,m2,m3,m4,m5,m6,m7,m8,m9,m10;
	List<MenuItem> lMista, lPanini;
	TakeAwayBillImpl bill; 
	
	@Before
	public void before() {
		lMista = new ArrayList<MenuItem>(); //lista con AL PIU' 5 panini
		lPanini = new ArrayList<MenuItem>(); //lista con ALMENO 5 panini
		bill = new TakeAwayBillImpl();
		m1 = new MenuItem(itemType.BEVANDA, "coca-cola", 2.0);
		m2 = new MenuItem(itemType.FRITTO, "arancini", 4.5);
		m3 = new MenuItem(itemType.PANINO, "primavera", 4.5);
		m4 = new MenuItem(itemType.PANINO, "salame", 4.0);
		m5 = new MenuItem(itemType.PANINO, "vegetariano", 3.5);
		m6 = new MenuItem(itemType.PANINO, "vegano", 4.5);
		m7 = new MenuItem(itemType.PANINO, "4 formaggi", 3.5);
		m8 = new MenuItem(itemType.PANINO, "salsiccia", 3.5);
		m9 = new MenuItem(itemType.FRITTO, "crocchette patate", 2.5);
		m10 = new MenuItem(itemType.BEVANDA, "fanta", 2.0);
		Collections.addAll(lMista, m1,m2,m3);
		Collections.addAll(lPanini, m3,m4,m5,m6,m7,m8);
	}
	
	@Test
	public void testOrderPrice() throws TakeAwayBillException {
		double expectedPriceMista = m1.getPrice() + m2.getPrice() + m3.getPrice();
		double expectedPricePanini = m3.getPrice() + m4.getPrice() + m5.getPrice()/2 + m6.getPrice() + m7.getPrice() + m8.getPrice();
		double sumPriceMista = bill.getOrderPrice(lMista);
		double sumPricePanini = bill.getOrderPrice(lPanini);
		assertEquals(expectedPriceMista,sumPriceMista, 0.00);
		assertEquals(expectedPricePanini,sumPricePanini, 0.00);
	}
	
	@Test
	public void testGet50Discount() {
		double expectedDiscountMista = bill.get50Discount(lMista);
		double expectedDiscountPanini = bill.get50Discount(lPanini);
		assertEquals(0.00, expectedDiscountMista, 0.00);
		assertEquals(3.50*0.50, expectedDiscountPanini, 0.00);
		
	}
	

}
