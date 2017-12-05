/**
 * 
 */
package com.bbtree.browser.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

/**
 * 标准窗口对话框标题栏
 * @author lt
 */
public class WindowTitleBar extends JPanel implements MouseInputListener {

	private static final long serialVersionUID = -8812181287285792111L;
	private int mouseDragOffsetX = 0;
	private int mouseDragOffsetY = 0;
	private Window window;
	private WindowButtons buttons; 
	
	public WindowTitleBar(String title, final Window window) {
		this.window = window;
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(0, 0, 0, 10));
		window.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				mouseDragOffsetX = e.getX();
				mouseDragOffsetY = e.getY();
			}
		});
		window.addMouseListener(this);
		window.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				//当鼠标拖动时获取窗口当前位置
				Point p = window.getLocation();
		        //设置窗口的位置
		        //窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
		        window.setLocation((int)(p.x + e.getX() - mouseDragOffsetX), (int)(p.y + e.getY() - mouseDragOffsetY));
			}
		});
		buttons = new WindowButtons(window);
		buttons.setLocation(0, 0);
		add(buttons, BorderLayout.EAST);

		JLabel logLabel = new JLabel(title);
		Font font = new Font(logLabel.getFont().getFontName(), Font.BOLD, 12);
		logLabel.setFont(font);
		logLabel.setForeground(java.awt.Color.BLACK);
		logLabel.setBorder(new EmptyBorder(new java.awt.Insets(2, 10, 0, 0)));
		ImageIcon icon = (ImageIcon) UIManager.get("System.smallLogo");
		logLabel.setIcon(icon);
		add(logLabel, BorderLayout.WEST);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			List<JButton> btns = buttons.getButtons();
			for (JButton button  : btns) {
				System.out.println("event:"+button.getClass().getName());
				if (button instanceof WindowMaxButton) {
					button.doClick();
				}
			}
			
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {

        
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}
	
}
