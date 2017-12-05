/**
 * 
 */
package com.bbtree.browser.swingext;

import javafx.scene.image.Image;

import javax.swing.plaf.UIResource;

/**
 * 皮肤扩展图片UI
 * @author lt
 *
 */
public class FxImageUIResource implements UIResource {
	private Image image;

	public FxImageUIResource(String imagePath) {
		image = new Image(FxImageUIResource.class.getResource(imagePath).getFile());
	}

	public Image getImage() {
		return image;
	}
	
}
