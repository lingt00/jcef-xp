/**
 * 
 */
package com.bbtree.browser.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 窗口最小化按钮
 * @author lt
 */
public class WindowMinButton extends JButton {
	private static final long serialVersionUID = 1L;
	public WindowMinButton(final JFrame frame) {
		setName("WindowMinButton");
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setPreferredSize(new Dimension(28, 18));
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setState(JFrame.ICONIFIED);
			}
		});
	}
}
