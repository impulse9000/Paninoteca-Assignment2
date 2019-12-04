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
	
	MenuItem m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11;
	List<MenuItem> lMista, lPanini, lPaniniFritti;
	TakeAwayBillImpl bill; 
	
	@Before
	public void before() {
		lMista = new ArrayList<MenuItem>(); //lista con AL PIU' 5 panini, totale(misto)>50 euro, totale(fritti+panini)<=50 euro
		lPanini = new ArrayList<MenuItem>(); //lista con ALMENO 5 panini, totale(fritti+panini)<=50 euro
		lPaniniFritti = new ArrayList<MenuItem>(); // lista con AL PIU' 5 panini, totale(fritti+panini)>50 euro
		
		bill = new TakeAwayBillImpl();
		m1 = new MenuItem(itemType.BEVANDA, "coca-cola", 2.0);
		m2 = new MenuItem(itemType.FRITTO, "arancini", 4.5);
		m3 = new MenuItem(itemType.PANINO, "primavera", 4.5);
		m4 = new MenuItem(itemType.PANINO, "salame", 4.0);
		m5 = new MenuItem(itemType.PANINO, "vegetariano", 3.5);
		m6 = new MenuItem(itemType.PANINO, "vegano", 4.5);
		m7 = new MenuItem(itemType.PANINO, "4 formaggi", 3.5);
		m8 = new MenuItem(itemType.PANINO, "salsiccia", 3.5);
		m9 = new MenuItem(itemType.FRITTO, "crocchette patate", 3.5);
		m10 = new MenuItem(itemType.BEVANDA, "fanta", 2.0);
		m11 = new MenuItem(itemType.PANINO, "il costoso", 45.0);
		Collections.addAll(lMista, m1,m2,m11);
		Collections.addAll(lPanini, m3,m4,m5,m6,m7,m8);
		Collections.addAll(lPaniniFritti, m1,m2,m3,m9,m10,m11);
	}
	
	@Test
	public void testOrderPrice() throws TakeAwayBillException {
		double expectedPriceMista = 51.5; //totale con nessuno sconto dato che panini<=5 e totale(fritti+panini)<=50
		double expectedPricePanini = 23.5-3.5*0.50; //totale con sconto 50% sul panino meno caro dato che panini>5
		double expectedPricePaniniFritti = 61.5-61.5*0.10; //totale con sconto 10% sul totale(fritto+panino)>50
		double actualPriceMista = bill.getOrderPrice(lMista);
		double actualPricePanini = bill.getOrderPrice(lPanini);
		double actualPricePaniniFritti = bill.getOrderPrice(lPaniniFritti);
		assertEquals(expectedPriceMista,actualPriceMista, 0.00);
		assertEquals(expectedPricePanini,actualPricePanini, 0.00);
		assertEquals(expectedPricePaniniFritti, actualPricePaniniFritti, 0.00);
	}
	
	@Test
	public void testGet50Discount() {
		double expectedDiscountMista = 0.00; //nessuno sconto dato che panini<=5 
		double expectedDiscountPanini = 3.5*0.50; //sconto 50% sul panino meno caro dato che panini>5
		double expectedDiscountPaniniFritti = 0.00; //nessuno sconto dato che panini<=5 
		double actualDiscountMista = bill.get50Discount(lMista);
		double actualDiscountPanini = bill.get50Discount(lPanini);
		double actualDiscountPaniniFritti = bill.get50Discount(lPaniniFritti);
		assertEquals(expectedDiscountMista, actualDiscountMista, 0.00);
		assertEquals(expectedDiscountPanini, actualDiscountPanini, 0.00);
		assertEquals(expectedDiscountPaniniFritti, actualDiscountPaniniFritti, 0.00); 
		
	}
	
	@Test
	public void testGet10Discount(){
		double expectedDiscountMista = 0.00; //nessuno sconto dato che totale(fritti+panini)<=50
		double expectedDiscountPanini = 0.00; ////nessuno sconto dato che totale(fritti+panini)<=50
		double expectedDiscountPaniniFritti = 61.5*0.10; //sconto 10% sul totale dato che totale(fritti+panini)>50
		double actualDiscountMista = bill.get10Discount(lMista, 56);
		double actualDiscountPanini = bill.get10Discount(lPanini,23.5);
		double actualDiscountPaniniFritti = bill.get10Discount(lPaniniFritti,61.50);
		assertEquals(expectedDiscountMista, actualDiscountMista, 0.00);
		assertEquals(expectedDiscountPanini, actualDiscountPanini, 0.00);
		assertEquals(expectedDiscountPaniniFritti, actualDiscountPaniniFritti, 0.00);
	}
	
	@Test
	public void testTotalDiscounts() {
		double expectedDiscountMista = 0.00; //nessuno sconto dato che panini<=5 e totale(fritti+panini)<=50
		double expectedDiscountPanini = 3.50*0.50; //sconto 50% sul panino meno caro dato che panini>5
		double expectedDiscountPaniniFritti = 61.5*0.10; //sconto 10% sul totale dato che totale(fritti+panini)>50
		double actualDiscountMista = bill.totalDiscounts(lMista, 56);
		double actualDiscountPanini = bill.totalDiscounts(lPanini, 23.5);
		double actualDiscountPaniniFritti = bill.totalDiscounts(lPaniniFritti,61.50);
		assertEquals(expectedDiscountMista, actualDiscountMista, 0.00);
		assertEquals(expectedDiscountPanini, actualDiscountPanini, 0.00);
		assertEquals(expectedDiscountPaniniFritti, actualDiscountPaniniFritti, 0.00);
		
	}
	

}
