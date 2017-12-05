/**
 * 
 */
package com.bbtree.browser.components;

import com.bbtree.browser.WebClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * 窗口关闭按钮
 * @author lt
 */
public class WindowCloseButton extends JButton {
	private static final long serialVersionUID = -2940618794977444801L;

	public WindowCloseButton(final Window window) {
		setName("WindowCloseButton");
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setPreferredSize(new Dimension(38, 18));
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (window instanceof WebClient) {
					window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING) );
				} else if(window instanceof JDialog){
					((JDialog) window).setVisible(false);
				}
			}


		});
	}
}
