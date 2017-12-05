package com.bbtree.browser;

import com.bbtree.browser.handler.DownloadHandler;
import com.bbtree.browser.handler.KeyboardHandler;
import com.bbtree.browser.handler.LifeSpanHandler;
import com.bbtree.browser.swingext.CursorUIResource;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.OS;
import org.cef.browser.CefBrowser;

import org.cef.browser.CefRequestContext;
import org.cef.callback.CefCommandLine;
import org.cef.callback.CefContextMenuParams;
import org.cef.callback.CefJSDialogCallback;
import org.cef.callback.CefMenuModel;
import org.cef.handler.*;
import org.cef.misc.BoolRef;
import org.cef.network.CefCookieManager;
import org.cef.network.CefRequest;

import javax.swing.*;
import javax.swing.plaf.synth.SynthLookAndFeel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

/**
 * @author lt
 */
public class WebClient extends JFrame {

	private static final long serialVersionUID = -8394795316506150454L;
	protected Toolkit toolkit = Toolkit.getDefaultToolkit();
    /** System Config */
	public static final SystemConfig config = SystemConfig.getConfig();
	public static final String cache = System.getProperties().getProperty("user.home")+ File.separator+"AppData"+ File.separator+"Local"+ File.separator+"bbtree";
	public static final String log_file = cache + File.separator + "bbtree_log.log";

	private final CefClient client_;
	private final CefBrowser browser_;
	private final CefCookieManager cookieManager_;

	public WebClient(boolean osrEnabled, boolean transparentPaintingEnabled, String cookiePath,
					 String[] args) {

		setSize(config.getDefaultWidth(),config.getDefaultHeight());
		setLocationRelativeTo(null);
		setContentPane(new JPanel());
		//去掉窗口的装饰
		setUndecorated(false);
		setResizable(true);
		setTitle(config.getBrowserTitle());

		setIconImage(((ImageIcon) UIManager.get("System.logo")).getImage());
		setLayout(new BorderLayout());

		// 1) CefApp is the entry point for JCEF. You can pass
		//    application arguments to it, if you want to handle any
		//    chromium or CEF related switches/attributes in
		//    the native world.
		CefSettings settings = new CefSettings();
		settings.windowless_rendering_enabled = osrEnabled;
		settings.command_line_args_disabled = true;
		settings.cache_path=cache;
		settings.log_file=log_file;
		settings.log_severity = CefSettings.LogSeverity.LOGSEVERITY_DISABLE;
		settings.locale="zh-CN";
		settings.product_version="5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87.2.00.0000 Bbtree/2.00.0000";
		settings.user_agent="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87.2.00.0000 Bbtree/2.00.0000";
		// try to load URL "about:blank" to see the background color
		//settings.background_color = settings.new ColorType(100, 255, 242, 211);
		CefApp myApp = CefApp.getInstance(args, settings);
		CefApp.CefVersion version = myApp.getVersion();
		System.out.println("Using:\n" + version);

		//    We're registering our own AppHandler because we want to
		//    add an own schemes (search:// and client://) and its corresponding
		//    protocol handlers. So if you enter "search:something on the web", your
		//    search request "something on the web" is forwarded to www.google.com
		CefApp.addAppHandler(new CefAppHandlerAdapter(args) {
			@Override
			public void stateHasChanged(CefApp.CefAppState state) {
				System.out.println("CefApp: " + state);
				if (state == CefApp.CefAppState.TERMINATED){
					System.exit(0);
				}
			}
			@Override
			public void onBeforeCommandLineProcessing(String process_type, CefCommandLine command_line) {
				super.onBeforeCommandLineProcessing(process_type, command_line);
				command_line.appendSwitchWithValue("--disable-web-security","true");
				command_line.appendSwitchWithValue("--enable-system-flash","true");
				command_line.appendSwitchWithValue("--plugin-policy","allow");
				command_line.appendSwitchWithValue("--enable-npapi","true");
				command_line.appendSwitchWithValue("--persist-session-cookies","true");
			}
		});

		//    By calling the method createClient() the native part
		//    of JCEF/CEF will be initialized and an  instance of
		//    CefClient will be created. You can create one to many
		//    instances of CefClient.
		client_ = myApp.createClient();

		// 2) You have the ability to pass different handlers to your
		//    instance of CefClient. Each handler is responsible to
		//    deal with different informations (e.g. keyboard input).
		//
		//    For each handler (with more than one method) adapter
		//    classes exists. So you don't need to override methods
		//    you're not interested in.
		client_.addContextMenuHandler(new CefContextMenuHandlerAdapter() {
			@Override
			public void onBeforeContextMenu(CefBrowser browser, CefContextMenuParams params, CefMenuModel model) {
				super.onBeforeContextMenu(browser, params, model);
			}

			@Override
			public boolean onContextMenuCommand(CefBrowser browser, CefContextMenuParams params, int commandId, int eventFlags) {
				return super.onContextMenuCommand(browser,params, commandId, eventFlags);
			}

			@Override
			public void onContextMenuDismissed(CefBrowser browser) {
				super.onContextMenuDismissed(browser);
			}
		});
		client_.addDownloadHandler(new DownloadHandler());
		client_.addJSDialogHandler(new CefJSDialogHandlerAdapter(){
			@Override
			public boolean onJSDialog(CefBrowser browser, String origin_url, String accept_lang, JSDialogType dialog_type, String message_text, String default_prompt_text, CefJSDialogCallback callback, BoolRef suppress_message) {
				return super.onJSDialog(browser, origin_url, accept_lang, dialog_type, message_text, default_prompt_text, callback, suppress_message);
			}
		});
		client_.addKeyboardHandler(new KeyboardHandler());
		client_.addRequestHandler(new CefRequestHandlerAdapter(){
			@Override
			public boolean onBeforeBrowse(CefBrowser browser,CefRequest request, boolean is_redirect) {
				return super.onBeforeBrowse(browser,request, is_redirect);
			}
		});
		client_.addLifeSpanHandler(new LifeSpanHandler(client_,osrEnabled, transparentPaintingEnabled));

		//    Beside the normal handler instances, we're registering a MessageRouter
		//    as well. That gives us the opportunity to reply to JavaScript method
		//    calls (JavaScript binding). We're using the default configuration, so
		//    that the JavaScript binding methods "cefQuery" and "cefQueryCancel"
		//    are used.

		// 2.1) We're overriding CefDisplayHandler as nested anonymous class
		//      to update our address-field, the title of the panel as well
		//      as for updating the status-bar on the bottom of the browser
		client_.addDisplayHandler(new CefDisplayHandlerAdapter() {
			@Override
			public void onAddressChange(CefBrowser browser, String url) {
				super.onAddressChange(browser, url);
			}

			@Override
			public void onTitleChange(CefBrowser browser, String title) {
				super.onTitleChange(browser, title);
				//setTitle(title);
			}

			@Override
			public boolean onTooltip(CefBrowser browser, String text) {
				return super.onTooltip(browser, text);
			}

			@Override
			public void onStatusMessage(CefBrowser browser, String value) {
				super.onStatusMessage(browser, value);
			}

			@Override
			public boolean onConsoleMessage(CefBrowser browser, String message, String source, int line) {
				return super.onConsoleMessage(browser, message, source, line);
			}
		});

		// 2.2) To disable/enable navigation buttons and to display a prgress bar
		//      which indicates the load state of our website, we're overloading
		//      the CefLoadHandler as nested anonymous class. Beside this, the
		//      load handler is responsible to deal with (load) errors as well.
		//      For example if you navigate to a URL which does not exist, the
		//      browser will show up an error message.
		client_.addLoadHandler(new CefLoadHandlerAdapter() {
			@Override
			public void onLoadingStateChange(CefBrowser browser, boolean isLoading, boolean canGoBack, boolean canGoForward) {
				super.onLoadingStateChange(browser, isLoading, canGoBack, canGoForward);
			}

			@Override
			public void onLoadStart(CefBrowser browser, int frameIdentifer) {
				super.onLoadStart(browser, frameIdentifer);
			}

			@Override
			public void onLoadEnd(CefBrowser browser, int frameIdentifier, int httpStatusCode) {
				super.onLoadEnd(browser, frameIdentifier, httpStatusCode);
			}

			@Override
			public void onLoadError(CefBrowser browser, int frameIdentifer, ErrorCode errorCode, String errorText, String failedUrl) {
				super.onLoadError(browser, frameIdentifer, errorCode, errorText, failedUrl);
			}
		});

		// 3) Before we can display any content, we require an instance of
		//    CefBrowser itself by calling createBrowser() on the CefClient.
		//    You can create one to many browser instances per CefClient.
		//
		//    If the user has specified the application parameter "--cookie-path="
		//    we provide our own cookie manager which persists cookies in a directory.
		CefRequestContext requestContext = null;
		if (cookiePath != null) {
			cookieManager_ = CefCookieManager.createManager(cookiePath, false);
			requestContext = CefRequestContext.createContext(new CefRequestContextHandlerAdapter() {
				@Override
				public CefCookieManager getCookieManager() {
					return cookieManager_;
				}
			});
		} else {
			cookieManager_ = CefCookieManager.getGlobalManager();
		}
		browser_ = client_.createBrowser(
				config.getDefaultUrl(), osrEnabled, transparentPaintingEnabled, requestContext);

		//Last but not least we're setting up the UI for this example implementation.
		getContentPane().add(browser_.getUIComponent(), BorderLayout.CENTER);
	}

	private static void initialComponents(String[] args) throws InterruptedException,
            InvocationTargetException {
		// OSR mode is enabled by default on Linux.
		// and disabled by default on Windows and Mac OS X.
		boolean osrEnabledArg = OS.isLinux();
		boolean transparentPaintingEnabledArg = false;
		String cookiePath = null;
		for (String arg : args) {
			arg = arg.toLowerCase();
			if (!OS.isLinux() && arg.equals("--off-screen-rendering-enabled")) {
				osrEnabledArg = true;
			} else if (arg.equals("--transparent-painting-enabled")) {
				transparentPaintingEnabledArg = true;
			} else if (arg.startsWith("--cookie-path=")) {
				cookiePath = arg.substring("--cookie-path=".length());
				File testPath = new File(cookiePath);
				if (!testPath.isDirectory() || !testPath.canWrite()) {
					System.out.println("Can't use " + cookiePath + " as cookie directory. Check if it exists and if it is writable");
					cookiePath = null;
				} else {
					System.out.println("Storing cookies in " + cookiePath);
				}
			}
		}

		System.out.println("Offscreen rendering " + (osrEnabledArg ? "enabled" : "disabled"));

		// MainFrame keeps all the knowledge to display the embedded browser
		// frame.
		final WebClient webClient = new WebClient(osrEnabledArg, transparentPaintingEnabledArg, cookiePath, args);

		webClient.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("window closing ");
				CefApp.getInstance().dispose();
				webClient.dispose();
			}
		});
		webClient.setVisible(true);
	}

	private static void initialSkin() throws ParseException,
            UnsupportedLookAndFeelException {
		SynthLookAndFeel synthLookAndFeel = new SynthLookAndFeel();
		synthLookAndFeel.load(WebClient.class.getResourceAsStream(
				"/skin/" + config.getDefaultSkin() + "/default.xml"), WebClient.class);
		UIManager.setLookAndFeel(synthLookAndFeel);
		UIManager.put("Button.font", new CursorUIResource(Cursor.HAND_CURSOR));
	}

	public static void main(String[] args) throws ParseException, UnsupportedLookAndFeelException, InterruptedException, InvocationTargetException {
		// 初始化皮肤
		initialSkin();
		// 初始化应用组件
		initialComponents(args);
	}
}