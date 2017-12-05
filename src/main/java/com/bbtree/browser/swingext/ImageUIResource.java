/**
 * 
 */
package com.bbtree.browser.swingext;

import javax.swing.*;
import javax.swing.plaf.UIResource;
import java.awt.*;

/**
 * 皮肤扩展图片UI
 * @author lt
 *
 */
public class ImageUIResource implements UIResource {
	private Image image;

	public ImageUIResource(String imagePath) {
		image = new ImageIcon(ImageUIResource.class.getResource(imagePath)).getImage();
	}

	public Image getImage() {
		return image;
	}
	
}
