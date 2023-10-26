package cp213;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

/**
 * Stores a List of MenuItems and provides a method return these items in a
 * formatted String. May be constructed from an existing List or from a file
 * with lines in the format:
 *
 * <pre>
1.25 hot dog
10.00 pizza
...
 * </pre>
 *
 * @author your name here
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2022-05-20
 */
public class Menu {

    // Attributes.
    private List<MenuItem> items = new ArrayList<MenuItem>();
    private String lineFormat = "%2d) %s\n";

    /**
     * Creates a new Menu from an existing Collection of MenuItems. MenuItems are
     * copied into the Menu List.
     *
     * @param items an existing Collection of MenuItems.
     */
    public Menu(Collection<MenuItem> items) {
	this.items.addAll(items);
    }

    /**
     * Constructor from a Scanner of MenuItem strings. Each line in the Scanner
     * corresponds to a MenuItem. You have to read the Scanner line by line and add
     * each MenuItem to the List of items.
     *
     * @param fileScanner A Scanner accessing MenuItem String data.
     */
    public Menu(Scanner fileScanner) {

	while (fileScanner.hasNext()) {
	    double price = fileScanner.nextDouble();
	    String name = fileScanner.nextLine().trim();
	    items.add(new MenuItem(name, price));
	}
    }

    /**
     * Returns the List's i-th MenuItem.
     *
     * @param i Index of a MenuItem.
     * @return the MenuItem at index i
     */
    public MenuItem getItem(int i) {
	return items.get(i);
    }

    /**
     * Returns the number of MenuItems in the items List.
     *
     * @return Size of the items List.
     */
    public int size() {
	return items.size();
    }

    /**
     * Returns the Menu items as a String in the format:
     *
     * <pre>
    5) poutine      $ 3.75
    6) pizza        $10.00
     * </pre>
     *
     * where n) is the index + 1 of the MenuItems in the List.
     */
    @Override
    public String toString() {
	StringBuilder buffer = new StringBuilder();

	for (int i = 0; i < items.size(); i++) {
	    buffer.append(String.format(lineFormat, i + 1, items.get(i).toString()));
	}
	return buffer.toString();
    }
}