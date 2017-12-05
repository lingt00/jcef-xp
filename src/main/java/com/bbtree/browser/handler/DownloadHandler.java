package com.bbtree.browser.handler;

import com.bbtree.browser.WebClient;
import org.cef.browser.CefBrowser;
import org.cef.callback.CefBeforeDownloadCallback;
import org.cef.callback.CefDownloadItem;
import org.cef.callback.CefDownloadItemCallback;
import org.cef.handler.CefDownloadHandlerAdapter;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author lt
 * @date 2017/11/22
 */
public class DownloadHandler extends CefDownloadHandlerAdapter {
    @Override
    public void onBeforeDownload(CefBrowser browser, CefDownloadItem downloadItem, String suggestedName, CefBeforeDownloadCallback callback) {
        //super.onBeforeDownload(browser, downloadItem, suggestedName, callback);.
        System.out.println(String.format("isInProgress %s isComplete %s isCanceled %s onBeforeDownload %s ",
                downloadItem.isInProgress(),
                downloadItem.isComplete(),
                downloadItem.isCanceled(),
                downloadItem.getPercentComplete()
        ));
        callback.Continue(suggestedName, true);
    }

    @Override
    public void onDownloadUpdated(CefBrowser browser, CefDownloadItem downloadItem, CefDownloadItemCallback callback) {
        Component component = SwingUtilities.getRoot(browser.getUIComponent());

        boolean isCanceled = downloadItem.isCanceled();
        boolean isComplete = downloadItem.isComplete();
        boolean isInProgress = downloadItem.isInProgress();
        boolean success = isCanceled || isComplete;
        int percentComplete = downloadItem.getPercentComplete();

        String rcvBytes = humanReadableByteCount(downloadItem.getReceivedBytes());
        String totalBytes = humanReadableByteCount(downloadItem.getTotalBytes());
        String speed = humanReadableByteCount(downloadItem.getCurrentSpeed()) + "it/s";

        System.out.println("download "+ percentComplete +"% is end:"+success);

        if (isInProgress){
            ((JFrame)component).setTitle(String.format("%s -下载中:%s/%s - %s - %s",
                    WebClient.config.getBrowserTitle(),
                    rcvBytes,
                    totalBytes,
                    percentComplete+"%",
                    speed
            ));
        }

        if (success){
            if (!(component instanceof WebClient)){
                JFrame frame = (JFrame)component;
                System.out.println("close download frame:");
                browser.stopLoad();
                browser.close();
                frame.setFocusable(false);
                frame.setVisible(false);
            }else {
                ((JFrame)component).setTitle(WebClient.config.getBrowserTitle());
            }
        }

        if (isCanceled){
            callback.cancel();
        }
        super.onDownloadUpdated(browser, downloadItem, callback);
    }

    String humanReadableByteCount(long bytes) {
        int unit = 1024;
        if (bytes < unit) {
            return bytes + " B";
        }

        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = "" + ("kMGTPE").charAt(exp - 1);
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
}
