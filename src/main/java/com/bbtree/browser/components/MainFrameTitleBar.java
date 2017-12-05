/**
 * 
 */
package com.bbtree.browser.components;


import com.bbtree.browser.WebClient;

import java.awt.*;

/**
 * 主面板的标题栏
 * @author lt
 */
public class MainFrameTitleBar extends WindowTitleBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MainFrameTitleBar(final Window window) {
		super(WebClient.config.getBrowserInfo(), window);
		// 软件logo
//		LogoLabel logLabel = new LogoLabel(WebClient.config.getBrowserInfo());
//		Font font = new Font(logLabel.getFont().getFontName(), Font.BOLD | Font.ITALIC, 12);
//		logLabel.setFont(font);
//		logLabel.setForeground(Color.black);
//		logLabel.setBorder(new EmptyBorder(new Insets(2, 10, 0, 0)));
//		logLabel.setSize(500, 20);
//		add(logLabel, BorderLayout.WEST);
	}
}
