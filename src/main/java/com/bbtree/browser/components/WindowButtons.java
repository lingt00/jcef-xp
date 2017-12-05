/**
 * 
 */
package com.bbtree.browser.components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 窗口按钮集合
 * @author lt
 */
public class WindowButtons extends JPanel {

	private static final long serialVersionUID = 2814728135391241142L;
	private List<JButton> buttons = new ArrayList<JButton>();

	public WindowButtons(final Window window) {
		super();
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		WindowCloseButton btnClose = new WindowCloseButton(window);
		if (window instanceof JFrame) {
			WindowMinButton btnMin = new WindowMinButton((JFrame)window);
			WindowMaxButton btnMax = new WindowMaxButton((JFrame)window);
			//WindowSkinButton btnSkin = new WindowSkinButton((JFrame)window);
			//add(btnSkin);
			add(btnMin);
			add(btnMax);

			//buttons.add(btnSkin);
			buttons.add(btnMin);
			buttons.add(btnMax);
		}
		add(btnClose);
		buttons.add(btnClose);
	}

	public List<JButton> getButtons() {
		return buttons;
	}

}
