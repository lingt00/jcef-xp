/**
 * 
 */
package com.bbtree.browser.components;

import com.bbtree.browser.WebClient;
import com.bbtree.browser.base.BaseDialog;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

import javax.swing.*;
import javax.swing.plaf.synth.SynthLookAndFeel;
import java.awt.*;
import java.util.List;

/**
 * 皮肤选择对话框
 * @author lt
 */
public class SkinDialog extends BaseDialog implements EventHandler<MouseEvent> {


	private static final long serialVersionUID = 9128316582248982851L;
	private JFrame frame;
	public SkinDialog(JFrame frame) {
		super(frame);
		this.setUndecorated(true);
		this.frame = frame;
		List<String> skins = WebClient.config.getSkins();
		setSize(400, ((skins.size() - 1) / 3 + 1) * 110 + 20);
		final JFXPanel panel = new JFXPanel();
		panel.setOpaque(false);
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				Scene scene = SceneBuilder.create().build();
				TilePane flowPane = new TilePane();
				flowPane.setPadding(new Insets(10));
				flowPane.setVgap(10);
				flowPane.setHgap(10);
				scene.setRoot(flowPane);
				scene.fillProperty().set(Color.TRANSPARENT);
				panel.setScene(scene);
				// 构建皮肤预览对象，采用javafx2.0
				for (String skinImage : WebClient.config.getSkins()) {
					
					final ImageView view = new ImageView(new Image(SkinDialog.class.getResourceAsStream(
							"/skin/" + skinImage + "/images/bkg.jpg"), 120, 80, false, true));
					view.setId(skinImage);
					view.setCursor(Cursor.HAND);

					final DropShadow dropShadow = new DropShadow();
					dropShadow.setColor(Color.RED);
					dropShadow.setWidth(5);
					view.setEffect(dropShadow);
					view.setOnMouseExited(SkinDialog.this);
					view.setOnMouseEntered(SkinDialog.this);
					view.setOnMouseClicked(SkinDialog.this);
					flowPane.getChildren().add(view);
				}
			}
		});

		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
		WindowTitleBar titleBar = new WindowTitleBar("我的主题", this);
		add(titleBar, BorderLayout.NORTH);
	}


	@Override
	public void handle(MouseEvent event) {
		ImageView imageView = (ImageView)event.getSource();
		if (event.getEventType() == MouseEvent.MOUSE_CLICKED
				|| event.getEventType() == MouseEvent.MOUSE_ENTERED ) {
			// 动态改变皮肤
			SynthLookAndFeel synthLookAndFeel = new SynthLookAndFeel();
			try {
				synthLookAndFeel.load(WebClient.class.getResourceAsStream("/skin/"
						+ imageView.getId() + "/" + imageView.getId() + ".xml"), WebClient.class);
				UIManager.setLookAndFeel(synthLookAndFeel);
				
				// 重绘窗体
				frame.repaint();
				this.repaint();
			} catch (Exception e) {
				e.printStackTrace();
			}

			final DropShadow dropShadow = new DropShadow();
			dropShadow.setSpread(0.5);
			imageView.setEffect(dropShadow);

			if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
				this.setVisible(false);
				this.dispose();
			}
		} else {
			DropShadow dropShadow = new DropShadow();
			dropShadow.setColor(Color.RED);
			dropShadow.setWidth(10);
			imageView.setEffect(dropShadow);
		}
	}
}
