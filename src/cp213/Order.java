package cp213;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Stores a HashMap of MenuItem objects and the quantity of each MenuItem
 * ordered. Each MenuItem may appear only once in the HashMap.
 *
 * @author your name here
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2022-05-20
 */
public class Order implements Printable {

    private static final String lineFormat = "%-14s%2d @ $%5.2f = $%6.2f\n";
    private static final String totalFormat = "%-9s                   $%6.2f\n";
    /**
     * The current tax rate on menu items.
     */
    public static final BigDecimal TAX_RATE = new BigDecimal(0.13);
    private final Map<MenuItem, Integer> items = new HashMap<>();

    /**
     * Increments the quantity of a particular MenuItem in an Order with a new
     * quantity. If the MenuItem is not in the order, it is added.
     *
     * @param item     The MenuItem to purchase - the HashMap key.
     * @param quantity The number of the MenuItem to purchase - the HashMap value.
     */
    public void add(final MenuItem item, final int quantity) {
	// Uses a lambda to update the quantity of a MenuItem key.
	this.items.merge(item, quantity, (oldValue, newValue) -> oldValue + newValue);
    }

    /**
     * Calculates the total value of all MenuItems and their quantities in the
     * HashMap.
     *
     * @return the total price for the MenuItems ordered.
     */
    public BigDecimal getSubTotal() {
	BigDecimal total = BigDecimal.ZERO;

	for (final Map.Entry<MenuItem, Integer> entry : this.items.entrySet()) {
	    final MenuItem item = entry.getKey();
	    final Integer quantity = entry.getValue();
	    total = total.add(item.getPrice().multiply(new BigDecimal(quantity)));
	}
	return total;
    }

    /**
     * Calculates and returns the total taxes to apply to the subtotal of all
     * MenuItems in the order. Tax rate is TAX_RATE.
     *
     * @return total taxes on all MenuItems
     */
    public BigDecimal getTaxes() {
	return this.getSubTotal().multiply(TAX_RATE);
    }

    /**
     * Calculates and returns the total price of all MenuItems order, including tax.
     *
     * @return total price
     */
    public BigDecimal getTotal() {
	return this.getSubTotal().add(this.getTaxes());
    }

    /*
     * Implements the Printable interface print method. Prints lines to a Graphics2D
     * object using the drawString method. Prints the current contents of the Order.
     */
    @Override
    public int print(final Graphics graphics, final PageFormat pageFormat, final int pageIndex)
	    throws PrinterException {
	int result = PAGE_EXISTS;

	if (pageIndex == 0) {
	    final Graphics2D g2d = (Graphics2D) graphics;
	    g2d.setFont(new Font("MONOSPACED", Font.PLAIN, 12));
	    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
	    // Now we perform our rendering
	    final String[] lines = this.toString().split("\n");
	    int y = 100;
	    final int inc = 12;

	    for (final String line : lines) {
		g2d.drawString(line, 100, y);
		y += inc;
	    }
	} else {
	    result = NO_SUCH_PAGE;
	}
	return result;
    }

    /**
     * Returns a String version of a receipt for all the MenuItems in the order.
     */
    @Override
    public String toString() {
	final StringBuilder buffer = new StringBuilder();
	for (final Map.Entry<MenuItem, Integer> entry : this.items.entrySet()) {
	    final MenuItem item = entry.getKey();
	    final Integer quantity = entry.getValue();
	    final BigDecimal lineTotal = item.getPrice().multiply(new BigDecimal(quantity));
	    final String line = String.format(lineFormat, item.getName(), quantity, item.getPrice(), lineTotal);
	    buffer.append(line);
	}
	buffer.append("\n");
	buffer.append(String.format(totalFormat, "Subtotal:", this.getSubTotal()));
	buffer.append(String.format(totalFormat, "Taxes:   ", this.getTaxes()));
	buffer.append(String.format(totalFormat, "Total:   ", this.getTotal()));
	return buffer.toString();
    }

    /**
     * Replaces the quantity of a particular MenuItem in an Order with a new
     * quantity. If the MenuItem is not in the order, it is added. If quantity is 0
     * or negative, the MenuItem is removed from the Order.
     *
     * @param item     The MenuItem to update
     * @param quantity The quantity to apply to item
     */
    public void update(final MenuItem item, final int quantity) {
	// Uses a lambda to update the quantity of a MenuItem key.
	if (quantity <= 0) {
	    this.items.remove(item);
	} else {
	    this.items.merge(item, quantity, (oldValue, newValue) -> newValue);
	}
    }
}