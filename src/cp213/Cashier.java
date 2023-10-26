package cp213;

import java.util.Scanner;

/**
 * Wraps around an Order object to ask for MenuItems and quantities.
 *
 * @author your name here
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2022-05-20
 */
public class Cashier {

    private Menu menu = null;

    /**
     * Constructor.
     *
     * @param menu A Menu.
     */
    public Cashier(Menu menu) {
	this.menu = menu;
    }

    /**
     * Reads keyboard input. Returns a positive quantity, or 0 if the input is not a
     * valid positive integer.
     *
     * @param scan A keyboard Scanner.
     * @return
     */
    private int askForQuantity(Scanner scan) {
	int quantity = 0;
	System.out.print("How many do you want? ");

	try {
	    String line = scan.nextLine();
	    quantity = Integer.parseInt(line);
	} catch (NumberFormatException nfex) {
	    System.out.println("Not a valid number");
	}
	return quantity;
    }

    /**
     * Prints the menu.
     */
    private void printCommands() {
	System.out.println("\nMenu:");
	System.out.println(menu.toString());
	System.out.println("Press 0 when done.");
	System.out.println("Press any other key to see the menu again.\n");
    }

    /**
     * Reads keyboard input. Returns an integer corresponding to a menu item, or -1
     * if the input is not a valid menu item.
     *
     * @param scan A keyboard Scanner.
     * @return an integer representing a command.
     */
    private int readCommand(Scanner scan) {
	int command = -1;
	System.out.print("Command: ");
	String line = scan.nextLine();

	try {
	    command = Integer.parseInt(line);
	} catch (NumberFormatException nfex) {
	    System.out.println("Not a valid number");
	} finally {
	    if (command < 0 || command > this.menu.size()) {
		command = -1;
	    }
	}
	return command;
    }

    /**
     * Asks for commands and quantities. Prints a receipt when all orders have been
     * placed.
     *
     * @return the completed Order.
     */
    public Order takeOrder() {
	System.out.println("Welcome to WLU Foodorama!");
	Order order = new Order();
	Scanner scan = new Scanner(System.in);
	int quantity = 0;
	printCommands();
	int command = readCommand(scan);

	while (command != 0) {

	    if (command == -1) {
		printCommands();
	    } else {
		quantity = askForQuantity(scan);

		if (quantity > 0) {
		    order.add(menu.getItem(command - 1), quantity);
		}
	    }
	    command = readCommand(scan);
	}
	scan.close();
	System.out.println("-".repeat(40));
	System.out.println("Receipt");
	System.out.println(order.toString());
	return order;
    }
}