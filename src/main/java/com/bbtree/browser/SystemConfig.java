/**
 * 
 */
package com.bbtree.browser;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.util.List;

/**
 * 系统配置
 * @author lt
 */
public class SystemConfig {
	/** 默认网址 */
	private String defaultUrl;
	/** 默认皮肤 */
	private String defaultSkin;
	/** 默认宽度 */
	private int defaultWidth;
	/** 默认高度 */
	private int defaultHeight;
	/** 圆角X轴 */
	private int arcX;
	/** 圆角Y轴 */
	private int arcY;
	
	private String browserInfo;
	/** 默认标题 */
	private String browserTitle;

	private List<String> skins;
	
	private static Object lockObj = new Object();
	private static SystemConfig instance;
	
	public static SystemConfig getConfig() {
		if (instance == null) {
			synchronized (lockObj) {
				XStream xstream = new XStream(new DomDriver("UTF-8"));
				xstream.alias("config", SystemConfig.class);
				xstream.alias("skins", List.class);
				xstream.alias("skin", String.class);
				instance = (SystemConfig) xstream.fromXML(SystemConfig.
						class.getResource("/system-config.xml"));
			}
		}
		return instance;
	}

	public String getDefaultUrl() {
		return defaultUrl;
	}

	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}

	public String getDefaultSkin() {
		return defaultSkin;
	}

	public void setDefaultSkin(String defaultSkin) {
		this.defaultSkin = defaultSkin;
	}

	public int getDefaultWidth() {
		return defaultWidth;
	}

	public void setDefaultWidth(int defaultWidth) {
		this.defaultWidth = defaultWidth;
	}

	public int getDefaultHeight() {
		return defaultHeight;
	}

	public void setDefaultHeight(int defaultHeight) {
		this.defaultHeight = defaultHeight;
	}

	public int getArcX() {
		return arcX;
	}

	public void setArcX(int arcX) {
		this.arcX = arcX;
	}

	public int getArcY() {
		return arcY;
	}

	public void setArcY(int arcY) {
		this.arcY = arcY;
	}

	public List<String> getSkins() {
		return skins;
	}

	public void setSkins(List<String> skins) {
		this.skins = skins;
	}

	public String getBrowserInfo() {
		return browserInfo;
	}

	public void setBrowserInfo(String browserInfo) {
		this.browserInfo = browserInfo;
	}

	public String getBrowserTitle() {
		return browserTitle;
	}

	public void setBrowserTitle(String browserTitle) {
		this.browserTitle = browserTitle;
	}

	@Override
	public String toString() {
		return "SystemConfig{" +
				"defaultUrl='" + defaultUrl + '\'' +
				", defaultSkin='" + defaultSkin + '\'' +
				", defaultWidth=" + defaultWidth +
				", defaultHeight=" + defaultHeight +
				", arcX=" + arcX +
				", arcY=" + arcY +
				", browserInfo='" + browserInfo + '\'' +
				", browserTitle='" + browserTitle + '\'' +
				", skins=" + skins.toArray() +
				'}';
	}
}
