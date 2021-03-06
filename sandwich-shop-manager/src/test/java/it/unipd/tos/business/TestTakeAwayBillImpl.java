////////////////////////////////////////////////////////////////////
// [Andrea] [Polo-Perucchin] [1169765]
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import it.unipd.tos.business.exception.TakeAwayBillException;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.MenuItem.itemType;

public class TestTakeAwayBillImpl {

    MenuItem m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11;
    List<MenuItem> lMista, lPanini, lPaniniFritti, l31Articoli, lSotto10Euro;
    TakeAwayBillImpl bill;

    @Before
    public void before() {
        // lista con AL PIU' 5 panini, max 30 elementi, tot(misto)>50€,
        // tot(fritti+panini)<=50€
        lMista = new ArrayList<MenuItem>();
        // lista con ALMENO 5 panini, totale(fritti+panini)<=50€
        lPanini = new ArrayList<MenuItem>();
        // lista con AL PIU' 5 panini, totale(fritti+panini)>50€
        lPaniniFritti = new ArrayList<MenuItem>();
        // lista con più di 30 articoli
        l31Articoli = new ArrayList<MenuItem>();
        // lista con prezzo totale<10€
        lSotto10Euro = new ArrayList<MenuItem>();
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
        Collections.addAll(lMista, m1, m2, m11);
        Collections.addAll(lPanini, m3, m4, m5, m6, m7, m8);
        Collections.addAll(lPaniniFritti, m1, m2, m3, m9, m10, m11);
        Collections.addAll(lSotto10Euro, m1, m2);
        for (int i = 0; i < 31; i++) {
            MenuItem el = new MenuItem(itemType.FRITTO, "cosa buona", 4.0);
            Collections.addAll(l31Articoli, el);
        }
    }

    @Test
    public void testOrderPrice_noDiscountRequirements_noDiscount() throws TakeAwayBillException {
        // totale con nessuno sconto dato che panini<=5 e totale(fritti+panini)<=50€
        double expectedPriceMista = 51.5;
        double actualPriceMista = bill.getOrderPrice(lMista);
        assertEquals(expectedPriceMista, actualPriceMista, 0.00);
    }

    @Test
    public void testOrderPrice_greaterThan5Panini_discount50() throws TakeAwayBillException {
        // totale con sconto 50% sul panino meno caro dato che panini>5
        double expectedPricePanini = 23.5 - 3.5 * 0.50;
        double actualPricePanini = bill.getOrderPrice(lPanini);
        assertEquals(expectedPricePanini, actualPricePanini, 0.00);
    }

    @Test
    public void testOrderPrice_50EuroOfFrittiAndPanini_discount10() throws TakeAwayBillException {
        // totale con sconto 10% sul totale(fritto+panino)>50€
        double expectedPricePaniniFritti = 61.5 - 61.5 * 0.10;
        double actualPricePaniniFritti = bill.getOrderPrice(lPaniniFritti);
        assertEquals(expectedPricePaniniFritti, actualPricePaniniFritti, 0.00);
    }

    @Test
    public void testOrderPrice_totalLessThan10Euro_addCommission() throws TakeAwayBillException {
        // totale con 0.50cent di commissione perchè totale<10€
        double expectedPriceSotto10Euro = 6.50 + 0.50;
        double actualPriceSotto10Euro = bill.getOrderPrice(lSotto10Euro);
        assertEquals(expectedPriceSotto10Euro, actualPriceSotto10Euro, 0.00);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testOrderPrice_itemsMoreThan30_ExceptionThrown() throws TakeAwayBillException {
        // lancia eccezione perchè articoli>30
        thrown.expect(TakeAwayBillException.class);
        thrown.expectMessage("ATTENZIONE! L'ordine non può essere composto da più di 30 articoli.");
        assertEquals(98765, bill.getOrderPrice(l31Articoli), 0.00);
    }

    @Test
    public void testGet50Discount_paniniLessOrEqualTo5_noDiscount() {
        // nessuno sconto dato che panini<=5
        double expectedDiscountMista = 0.00;
        double actualDiscountMista = bill.get50Discount(lMista);
        assertEquals(expectedDiscountMista, actualDiscountMista, 0.00);
    }

    @Test
    public void testGet50Discount_paniniGreaterThanFive_discountOnCheaperPanino() {
        // sconto 50% sul panino meno caro dato che panini>5
        double expectedDiscountPanini = 3.5 * 0.50;
        double actualDiscountPanini = bill.get50Discount(lPanini);
        assertEquals(expectedDiscountPanini, actualDiscountPanini, 0.00);
    }

    @Test
    public void testGet10Discount_totalFrittiPaniniLessOrEqualTo50Euro_noDiscount() {
        // nessuno sconto dato che totale(fritti+panini)<=50€
        double expectedDiscountMista = 0.00;
        double actualDiscountMista = bill.get10Discount(lMista, 56);
        assertEquals(expectedDiscountMista, actualDiscountMista, 0.00);
    }

    @Test
    public void testGet10Discount_totalFrittiPaniniGreaterThan50Euro_discountOnTotalOrder() {
        // sconto 10% sul totale dato che totale(fritti+panini)>50€
        double expectedDiscountPaniniFritti = 61.5 * 0.10;
        double actualDiscountPaniniFritti = bill.get10Discount(lPaniniFritti, 61.50);
        assertEquals(expectedDiscountPaniniFritti, actualDiscountPaniniFritti, 0.00);
    }

    @Test
    public void testTotalDiscounts_noRequirements50DiscountNoRequirements10Discount_noDiscount() {
        // nessuno sconto dato che panini<=5 e totale(fritti+panini)<=50€
        double expectedDiscountMista = 0.00;
        double actualDiscountMista = bill.totalDiscounts(lMista, 56);
        assertEquals(expectedDiscountMista, actualDiscountMista, 0.00);
    }

    @Test
    public void testTotalDiscounts_50DiscountRequirements_50Discount() {
        // sconto 50% sul panino meno caro dato che panini>5
        double expectedDiscountPanini = 3.50 * 0.50;
        double actualDiscountPanini = bill.totalDiscounts(lPanini, 23.5);
        assertEquals(expectedDiscountPanini, actualDiscountPanini, 0.00);
    }

    @Test
    public void testIsValidOrder_itemsLessThan30_returnTrue() {
        assertEquals(true, bill.isValidOrder(lMista));
    }

    @Test
    public void testIsValidOrder_itemsGreaterThan30_returnFalse() {
        assertEquals(false, bill.isValidOrder(l31Articoli));
    }

    @Test
    public void testCheckForCommission_totalGreaterOrEqualTo10Euro_return0Commission() {
        assertEquals(0.00, bill.checkForCommission(10), 0.00);
        assertEquals(0.00, bill.checkForCommission(25), 0.00);
    }

    @Test
    public void testCheckForCommission_totalLessThan10Euro_return50CentCommission() {
        assertEquals(0.50, bill.checkForCommission(9.50), 0.00);
    }

}
