package com.bbtree.browser.handler;

import com.bbtree.browser.WebClient;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.handler.CefLifeSpanHandlerAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author lt
 * @date 2017/11/28
 */
public class LifeSpanHandler extends CefLifeSpanHandlerAdapter {
    private final CefClient client_;
    private final boolean osrEnabled;
    private final boolean transparentPaintingEnabled;
    public LifeSpanHandler(CefClient client, boolean osrEnabled, boolean transparentPaintingEnabled) {
        this.client_ = client;
        this.osrEnabled = osrEnabled;
        this.transparentPaintingEnabled = transparentPaintingEnabled;
    }

    @Override
    public boolean onBeforePopup(CefBrowser browser,String target_url, String target_frame_name) {
        if (target_frame_name==null || target_frame_name.trim().length()==0){
            target_frame_name = WebClient.config.getBrowserTitle();
        }
        if (target_url!=null && target_url.length()>0){
            PopupWindow popup = new  PopupWindow(target_frame_name,target_url);
            popup.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    System.out.println("windowClosing ");
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    super.windowClosed(e);
                    System.out.println("windowClosed ");
                }

                @Override
                public void windowStateChanged(WindowEvent e) {
                    super.windowStateChanged(e);
                    System.out.println("windowStateChanged ");
                }
            });
            Component webClient = SwingUtilities.getRoot(browser.getUIComponent());
            popup.setLocation(webClient.getX()+20,webClient.getY()+20);
            popup.setFocusable(true);
            popup.setVisible(true);
        }

        return true;
    }

    @Override
    public void onAfterCreated(CefBrowser browser) {
        super.onAfterCreated(browser);
    }

    @Override
    public boolean doClose(CefBrowser browser) {

        return super.doClose(browser);
    }

    @Override
    public void onBeforeClose(CefBrowser browser) {

        super.onBeforeClose(browser);
    }

    class PopupWindow extends JFrame {
        public PopupWindow(String title, String url) throws HeadlessException {
            setSize(WebClient.config.getDefaultWidth(),WebClient.config.getDefaultHeight());
            setLocationRelativeTo(null);
            setContentPane(new JPanel());
            //去掉窗口的装饰
            setUndecorated(false);
            setResizable(true);
            setTitle(title);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setAlwaysOnTop(true);

            setIconImage(((ImageIcon) UIManager.get("System.logo")).getImage());
            setLayout(new BorderLayout());
            CefBrowser browser_ = client_.createBrowser(url, osrEnabled, transparentPaintingEnabled);
            getContentPane().add(browser_.getUIComponent(), BorderLayout.CENTER);
        }

        @Override
        public void setDefaultCloseOperation(int operation) {
            super.setDefaultCloseOperation(operation);
        }

        @Override
        public void dispose() {
            super.dispose();
        }
    }
}
