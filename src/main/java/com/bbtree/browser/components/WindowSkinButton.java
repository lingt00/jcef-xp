/**
 * 
 */
package com.bbtree.browser.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 窗口皮肤按钮
 * @author lt
 */
public class WindowSkinButton extends JButton implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private SkinDialog dlg;

	public WindowSkinButton(JFrame frame) {
		this.frame = frame;
		setName("WindowSkinButton");
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setPreferredSize(new Dimension(20, 20));
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (dlg == null) {
			dlg = new SkinDialog(frame);
			//WebClient.setWindowShape(dlg);
		}
		dlg.setLocation((int)(frame.getLocation().getX() + frame.getWidth() - 120 - dlg.getWidth()), 
				(int)(frame.getLocation().getY() + this.getLocation().getY() + 10));
		dlg.setVisible(true);
	}
}
