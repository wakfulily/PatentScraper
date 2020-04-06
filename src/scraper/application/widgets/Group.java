package scraper.application.widgets;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class Group extends JPanel {

	private static final int DEFAULT_PADDING = 5;

	public Group(String name) {
		this(name, DEFAULT_PADDING);
	}

	public Group(String name, int padding) {
		Border titleBorder = BorderFactory.createTitledBorder(name);
		Border paddingBorder = new EmptyBorder(padding, padding, padding, padding);

		Border border = BorderFactory.createCompoundBorder(titleBorder, paddingBorder);
		setBorder(border);
	}

}
