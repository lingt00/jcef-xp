/**
 * 
 */
package com.bbtree.browser.components;

import javax.swing.*;

/**
 * 主面板的Logo
 * @author lt
 */
public class LogoLabel extends JLabel {
	
	private static final long serialVersionUID = 1L;

	public LogoLabel() {
		setIcon(((ImageIcon) UIManager.get("System.logo")));
	}
	
	public LogoLabel(String text) {
		super(text);
		setIcon(((ImageIcon) UIManager.get("System.logo")));
	}
}
