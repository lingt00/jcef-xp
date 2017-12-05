/**
 * 
 */
package com.bbtree.browser.components;

import com.bbtree.browser.SystemConfig;
import com.bbtree.browser.WebClient;
import javafx.geometry.Rectangle2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 窗口最大化按钮
 * @author lt
 */
public class WindowMaxButton extends JButton {
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private boolean maximized = false;

	private Rectangle2D backupWindowBounds = null;
	
	public WindowMaxButton(JFrame frame) {
		this.frame = frame;
		setName("WindowMaxButton");
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setPreferredSize(new Dimension(28, 18));
		setPreferredSize(new Dimension(28, 18));
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toogleMaximized();
			}
		});
	}
	
	/**
	 * 交替最大化与最小化动作
	 */
	public void toogleMaximized() {
		System.out.println("maximized:"+maximized);
		SystemConfig config = WebClient.config;
		if (this.maximized) {
			// 最小化
			this.maximized = false;
			if (this.backupWindowBounds != null) {
				this.frame.setLocation((int)this.backupWindowBounds.getMinX(), (int)this.backupWindowBounds.getMinY());
				this.frame.setSize((int)this.backupWindowBounds.getWidth(), (int)this.backupWindowBounds.getHeight());
			}
		} else {
			// 最大化
			this.maximized = true;
			this.frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		}
	}
	



	public boolean isMaximized() {
		return this.maximized;
	}

}
