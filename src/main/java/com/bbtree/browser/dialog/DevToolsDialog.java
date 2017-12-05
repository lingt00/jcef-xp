package com.bbtree.browser.dialog;

import com.bbtree.browser.WebClient;
import org.cef.browser.CefBrowser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 *
 * @author lt
 * @date 2017/11/27
 */
public class DevToolsDialog extends JDialog {
    private static final long serialVersionUID = -4334352020391972837L;
    private final CefBrowser devTools_;

    public DevToolsDialog(Frame owner, String title, CefBrowser browser) {
        this(owner, title, browser, null);
    }

    public DevToolsDialog(Frame owner, String title, CefBrowser browser, Point inspectAt) {
        super(owner, title, false);

        setLayout(new BorderLayout());
        setSize(WebClient.config.getDefaultWidth(), WebClient.config.getDefaultHeight());
        setLocation(owner.getLocation().x + 20, owner.getLocation().y + 20);

        devTools_ = browser.getDevTools(inspectAt);
        add(devTools_.getUIComponent());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                dispose();
                owner.setVisible(true);
            }
        });
    }

    @Override
    public void dispose() {
        devTools_.close();
        super.dispose();
    }
}