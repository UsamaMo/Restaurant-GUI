package cp213;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.print.PrinterJob;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * The GUI for the Order class.
 *
 * @author your name here
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2022-05-20
 */
@SuppressWarnings("serial")
public class OrderPanel extends JPanel {

    /**
     * Implements an ActionListener for the 'Print' button. Prints the current
     * contents of the Order to a system printer or PDF.
     */
    private class PrintListener implements ActionListener {

	@Override
	public void actionPerformed(final ActionEvent e) {
	    final PrinterJob printJob = PrinterJob.getPrinterJob();
	    printJob.setPrintable(order);

	    if (printJob.printDialog()) {
		try {
		    System.out.println("Subtotal: " + priceFormat.format(order.getSubTotal()));
		    System.out.println("Taxes: " + priceFormat.format(order.getTaxes()));
		    System.out.println("Total: " + priceFormat.format(order.getTotal()));
		    printJob.print();
		} catch (final Exception printException) {
		    System.err.println(printException);
		}
	    }
	}
    }

    /**
     * Implements a FocusListener on a JTextField. Accepts only positive integers,
     * all other values are reset to 0. Adds a new MenuItem to the Order with the
     * new quantity, updates an existing MenuItem in the Order with the new
     * quantity, or removes the MenuItem from the Order if the resulting quantity is
     * 0.
     */
    private class QuantityListener implements FocusListener {
	private MenuItem item = null;

	QuantityListener(final MenuItem item) {
	    this.item = item;
	}

	@Override
	public void focusGained(final FocusEvent evt) {
	    final JTextField field = (JTextField) evt.getSource();
	    // Automatically highlight the entire contents of the numeric field.
	    field.selectAll();
	}

	@Override
	public void focusLost(final FocusEvent evt) {
	    final JTextField field = (JTextField) evt.getSource();
	    int quantity = 0;

	    try {
		quantity = Integer.parseInt(field.getText());
	    } catch (final java.lang.NumberFormatException formatException) {
		// Do Nothing - finally block clears bad values and negatives
		// This block hides default printing of stack trace
		System.out.println("Bad input: " + field.getText());
	    } finally {
		// Reset quantity to 0 if negative.
		quantity = quantity < 0 ? 0 : quantity;
		field.setText("" + quantity);
		order.update(this.item, quantity);
		// Update the rest of the Order information.
		subtotalLabel.setText(priceFormat.format(order.getSubTotal()));
		taxLabel.setText(priceFormat.format(order.getTaxes()));
		totalLabel.setText(priceFormat.format(order.getTotal()));
		System.out.println("Item: " + this.item + ", Quantity: " + quantity);
	    }
	}
    }

    // Attributes
    private Menu menu = null;
    private final Order order = new Order();
    private final DecimalFormat priceFormat = new DecimalFormat("$##0.00");
    private final JButton printButton = new JButton("Print");
    private final JLabel subtotalLabel = new JLabel("0");
    private final JLabel taxLabel = new JLabel("0");
    private final JLabel totalLabel = new JLabel("0");

    /**
     * Displays the menu in a GUI.
     *
     * @param menu The menu to display.
     */
    public OrderPanel(final Menu menu) {
	this.menu = menu;
	this.layoutView();
    }

    /**
     * Uses the GridLayout to place the labels and buttons.
     */
    private void layoutView() {
	this.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
	this.setLayout(new GridLayout(this.menu.size() + 5, 4));
	this.add(new JLabel("Item"));
	this.add(new JLabel("Price"));
	this.add(new JLabel("Quantity"));

	// Add the menu items.
	for (int i = 0; i < this.menu.size(); i++) {
	    final MenuItem item = this.menu.getItem(i);
	    this.add(new JLabel(item.getName()));
	    final JLabel priceLabel = new JLabel(this.priceFormat.format(item.getPrice()));
	    priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	    this.add(priceLabel);
	    final JTextField qField = new JTextField("0");
	    qField.setHorizontalAlignment(SwingConstants.RIGHT);
	    qField.addFocusListener(new QuantityListener(item));
	    this.add(qField);
	}
	this.add(new JLabel("Subtotal: "));
	this.add(new JLabel());
	this.subtotalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	this.add(this.subtotalLabel);
	this.add(new JLabel("Tax: "));
	this.add(new JLabel());
	this.taxLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	this.add(this.taxLabel);
	this.add(new JLabel("Total: "));
	this.add(new JLabel());
	this.totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	this.add(this.totalLabel);
	this.add(new JLabel());
	// Register the PrinterListener with the print button.
	this.printButton.addActionListener(new PrintListener());
	this.add(this.printButton);
    }
}