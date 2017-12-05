/**
 * 
 */
package com.bbtree.browser.base;

import com.bbtree.browser.SystemConfig;
import com.bbtree.browser.WebClient;
import com.bbtree.browser.swingext.ImageUIResource;

import javax.swing.*;
import java.awt.*;

/**
 * 内容面板,用于绘制圆角和背景图片
 * @author lt
 */
public class BaseContentPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

    public BaseContentPanel() {
    }

    @Override
	public void paintComponent(Graphics g) {
		SystemConfig cfg = WebClient.config;
        
        super.paintComponents(g);
        
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);

		// 绘制背景图片
		// 每一副图像的位置坐标
        int x = 0;  
        int y = 0;
        Image image = ((ImageUIResource) UIManager.get("MainPanel.background")).getImage();
        g.drawImage(image, x, y, getWidth(), getHeight(), this);
        
	    if (!(this.getParent().getParent().getParent() instanceof WebClient)) {
	    	// 非主窗体，颜色渐变减淡
	        GradientPaint gp = new GradientPaint(0, 0, new Color(0.8f, 0.7f, 0.7f, 0.7f), 0, 50,  new Color(1f, 1f, 1f, 1f));
	        g2d.setPaint(gp);
	        g2d.fillRect(0, 0, getWidth(), getHeight());
        }

	    // 绘制窗体边框
        g2d.setStroke(new BasicStroke(1));
        g2d.setPaint(UIManager.getColor("MainPanel.borderColor"));
        g2d.drawLine(0, cfg.getArcY() / 2, 0, getHeight() - cfg.getArcY() / 2);
        g2d.drawLine(getWidth() - 1, cfg.getArcY() / 2, getWidth() - 1, getHeight() - cfg.getArcY() / 2);
        g2d.drawLine(cfg.getArcX() / 2, 0, getWidth()  - cfg.getArcX() / 2, 0);
        g2d.drawLine(cfg.getArcX() / 2, getHeight() - 1, getWidth()  - cfg.getArcX() / 2, getHeight() - 1);
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawArc(0, 0, cfg.getArcX(), cfg.getArcY(), 90, 90);
        g2d.drawArc(0, getHeight() -  cfg.getArcY() - 1, cfg.getArcX(), cfg.getArcY(), 180, 90);
        g2d.drawArc(getWidth() - cfg.getArcX() - 1, 0, cfg.getArcX(), cfg.getArcY(), 0, 90);
        g2d.drawArc(getWidth() - cfg.getArcX() - 1, getHeight() - cfg.getArcY() - 1, cfg.getArcX(), cfg.getArcY(), 270, 90);
	}
}
