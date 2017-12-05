/**
 * 
 */
package com.bbtree.browser.base;

import com.bbtree.browser.components.WindowCloseButton;

import javax.swing.*;
import java.awt.*;

/**
 * 对话框基类
 * @author lt
 */
public class BaseDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;

	public BaseDialog(JFrame frame) {
		super(frame);
		setContentPane(new ContentPanel());
		add(new WindowCloseButton(this), BorderLayout.NORTH);
	}
	
	@SuppressWarnings("serial")
	class ContentPanel extends BaseContentPanel {

	}
}
