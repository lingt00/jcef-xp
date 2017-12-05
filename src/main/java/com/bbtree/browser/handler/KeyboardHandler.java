package com.bbtree.browser.handler;

import com.bbtree.browser.WebClient;
import com.bbtree.browser.dialog.DevToolsDialog;
import org.cef.browser.CefBrowser;
import org.cef.handler.CefKeyboardHandler;
import org.cef.misc.BoolRef;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 *
 * @author lt
 * @date 2017/11/27
 */
public class KeyboardHandler implements CefKeyboardHandler {

    private DevToolsDialog devToolsDlg;

    @Override
    public boolean onPreKeyEvent(CefBrowser browser, CefKeyEvent event, BoolRef is_keyboard_shortcut) {
        // Forward CEF ctrl-f4 to AWT ctrl-f4 (which is important because it is tied to the close tab command)
        // Note. This is done on onPreKeyEvent because Javascript apps registering hot keys consume the ctrl-down event (jQuery.WhenIType module does that)
        if (event.type == CefKeyEvent.EventType.KEYEVENT_RAWKEYDOWN && event.modifiers == 4 /* ctrl */ && event.windows_key_code == 115 /* f4 */) {
            JFrame frame = (JFrame) SwingUtilities.getRoot(browser.getUIComponent());
            KeyEvent e = new KeyEvent(frame, KeyEvent.KEY_PRESSED,
                    System.currentTimeMillis(),
                    KeyEvent.CTRL_DOWN_MASK, KeyEvent.VK_F4,
                    KeyEvent.CHAR_UNDEFINED,
                    KeyEvent.KEY_LOCATION_STANDARD);
            KeyboardFocusManager.getCurrentKeyboardFocusManager().dispatchKeyEvent(e);
            return true;
        }
        // Forward CEF ctrl-shift-f4 to AWT ctrl-shift-f4 (which is important because it is tied to the close all tabs command)
        // Note. This is done on onPreKeyEvent because Javascript apps registering hot keys consume the ctrl-down event (jQuery.WhenIType module does that)
        if (event.type == CefKeyEvent.EventType.KEYEVENT_RAWKEYDOWN && event.modifiers == 6 /* ctrl shift */ && event.windows_key_code == 115 /* f4 */) {
            JFrame frame = (JFrame) SwingUtilities.getRoot(browser.getUIComponent());
            KeyEvent e = new KeyEvent(frame, KeyEvent.KEY_PRESSED,
                    System.currentTimeMillis(), KeyEvent.CTRL_DOWN_MASK
                    | KeyEvent.SHIFT_DOWN_MASK, KeyEvent.VK_F4,
                    KeyEvent.CHAR_UNDEFINED,
                    KeyEvent.KEY_LOCATION_STANDARD);
            KeyboardFocusManager.getCurrentKeyboardFocusManager().dispatchKeyEvent(e);
            return true;
        }
        // Forward CEF ctrl-s to AWT ctrl-s (which is important because it is tied to the save tab command)
        // Note. This is done on onPreKeyEvent because Javascript apps registering hot keys consume the ctrl-down event (jQuery.WhenIType module does that)
        if (event.type == CefKeyEvent.EventType.KEYEVENT_RAWKEYDOWN && event.modifiers == 4 /* ctrl */ && event.character == 'S') {
            JFrame frame = (JFrame) SwingUtilities.getRoot(browser.getUIComponent());
            KeyEvent e = new KeyEvent(frame, KeyEvent.KEY_PRESSED,
                    System.currentTimeMillis(),
                    KeyEvent.CTRL_DOWN_MASK, KeyEvent.VK_S,
                    KeyEvent.CHAR_UNDEFINED,
                    KeyEvent.KEY_LOCATION_STANDARD);
            KeyboardFocusManager.getCurrentKeyboardFocusManager().dispatchKeyEvent(e);
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyEvent(CefBrowser browser, CefKeyEvent event) {
        System.out.println("==============");
        System.out.println("onKeyEvent"+event.toString());
        // Forward CEF alt-down to AWT alt-down (which is important to bring up menu mnemonics on Swing menu bar)
        // Note. This is done on onKeyEvent to allow Java script apps to register hotkeys such as "alt-enter".
        if (event.type == CefKeyEvent.EventType.KEYEVENT_RAWKEYDOWN && event.is_system_key && event.modifiers == 8 /* ALT */) {
            JFrame frame = (JFrame) SwingUtilities.getRoot(browser.getUIComponent());
            KeyEvent e = new KeyEvent(frame, KeyEvent.KEY_PRESSED,
                    System.currentTimeMillis(), KeyEvent.ALT_MASK,
                    KeyEvent.VK_ALT, KeyEvent.CHAR_UNDEFINED,
                    KeyEvent.KEY_LOCATION_STANDARD);
            KeyboardFocusManager.getCurrentKeyboardFocusManager().dispatchKeyEvent(e);
            return true;
        }
        // Forward CEF alt-up to AWT alt-up (which is important to let user navigate through Swing menu bar or return to previously focused element)
        // Note. This is done on onKeyEvent to allow Javascript apps to register hotkeys such as "alt-enter".
        if (event.type == CefKeyEvent.EventType.KEYEVENT_KEYUP && event.is_system_key && event.windows_key_code == 18 /* ALT */) {
            JFrame frame = (JFrame) SwingUtilities.getRoot(browser.getUIComponent());
            KeyEvent e = new KeyEvent(frame, KeyEvent.KEY_RELEASED,
                    System.currentTimeMillis(), 0, KeyEvent.VK_ALT,
                    KeyEvent.CHAR_UNDEFINED,
                    KeyEvent.KEY_LOCATION_STANDARD);
            KeyboardFocusManager.getCurrentKeyboardFocusManager().dispatchKeyEvent(e);
            return true;
        }

        boolean keyFlag = false;
        //event.windows_key_code>=112 &&
        if (event.windows_key_code==123){
            keyFlag = true;
        }
        if (event.type == CefKeyEvent.EventType.KEYEVENT_KEYUP && !event.is_system_key && keyFlag) {
            switch (event.windows_key_code){
                case  123:{
                    Component component = SwingUtilities.getRoot(browser.getUIComponent());
                    if (component instanceof WebClient){
                        JFrame frame = (JFrame) component;
                        if (devToolsDlg==null || !devToolsDlg.isVisible()){
                            devToolsDlg = new DevToolsDialog(frame, "developer tools", browser);
                            devToolsDlg.setVisible(true);
                        }else {
                            devToolsDlg.dispose();
                            devToolsDlg = null ;
                        }
                    }else if (component instanceof DevToolsDialog){
                        if (devToolsDlg!=null){
                            devToolsDlg.setVisible(!devToolsDlg.isVisible());
                        }
                    }
                } break;
                default: break;
            }
            return true;
        }
        return false;
    }
}
