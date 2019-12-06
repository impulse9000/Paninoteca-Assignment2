////////////////////////////////////////////////////////////////////
// [Andrea] [Polo-Perucchin] [1169765]
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import it.unipd.tos.business.exception.TakeAwayBillException;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.MenuItem.itemType;

public class TakeAwayBillImpl implements TakeAwayBill {

    @Override
    public double getOrderPrice(List<MenuItem> itemsOrdered) throws TakeAwayBillException {
        if (isValidOrder(itemsOrdered) == true) {
            double finalPrice = 0;
            ListIterator<MenuItem> items = itemsOrdered.listIterator();
            while (items.hasNext()) {
                finalPrice += items.next().getPrice();
            }
            // la commissione di 0.50 viene applicata se l'ordine è inferiore ai 10 euro,
            // prima di calcolare eventuali sconti
            finalPrice += checkForCommission(finalPrice);
            return finalPrice - totalDiscounts(itemsOrdered, finalPrice);
        } else {
            throw new TakeAwayBillException("ATTENZIONE! L'ordine non può essere composto da più di 30 articoli.");
        }
    }

    // 50% di sconto sul panino meno caro se acquistati più di 5
    // ritorna il risparmio in euro oppure 0 se non applicabile
    public double get50Discount(List<MenuItem> itemsOrdered) {
        if (itemsOrdered.size() > 5) {
            int count = 0;
            double minPrice = 100000;
            ListIterator<MenuItem> items = itemsOrdered.listIterator();
            while (items.hasNext()) {
                MenuItem menuItem = items.next();
                if (menuItem.getItemType() == itemType.PANINO) {
                    count++;
                    if (minPrice > menuItem.getPrice()) {
                        minPrice = menuItem.getPrice();
                    }
                }
            }
            if (count > 5) {
                return 0.50 * minPrice;
            }
        }
        return 0;
    }

    // 10% di sconto se il totale dell'ordine supera i 50 euro (contando solo panini
    // e fritti)
    // ritorna risparmio in euro oppure 0 se non applicabile

    public double get10Discount(List<MenuItem> itemsOrdered, double totalOrderPrice) {
        if (totalOrderPrice > 50) {
            double totalFilteredPrice = 0;
            List<MenuItem> filteredList = new ArrayList<MenuItem>();
            ListIterator<MenuItem> items = itemsOrdered.listIterator();
            while (items.hasNext()) {
                MenuItem menuItem = items.next();
                if (menuItem.getItemType() == itemType.PANINO || menuItem.getItemType() == itemType.FRITTO) {
                    filteredList.add(menuItem);
                    totalFilteredPrice += menuItem.getPrice();
                }
            }
            if (totalFilteredPrice > 50) {
                return totalOrderPrice * 0.10;
            }
        }
        return 0;
    }

    // ritorna lo sconto totale dopo aver analizzato tutti i possibili sconti
    public double totalDiscounts(List<MenuItem> itemsOrdered, double totalOrderPrice) {
        double totDisc = 0;
        totDisc += get50Discount(itemsOrdered);
        totDisc += get10Discount(itemsOrdered, totalOrderPrice);
        return totDisc;

    }

    // true se ordine si componde di AL PIU' 30 articoli, false altrimenti
    public boolean isValidOrder(List<MenuItem> itemsOrdered) {
        if (itemsOrdered.size() > 30) {
            return false;
        } else {
            return true;
        }
    }

    public double checkForCommission(double totalOrderPrice) {
        if (totalOrderPrice < 10) {
            return 0.50;
        } else {
            return 0.00;
        }

    }

}
