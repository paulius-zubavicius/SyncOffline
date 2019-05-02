package com.owr.so.merge.gui.edit.gui;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IconsResource {

	private static Map<String, Image> images = new HashMap<>();

	static {
		loadAndPut("ignore");
		loadAndPut("revert");
		loadAndPut("update");
	}

	public Image getImg(String name) {

		name = name.toLowerCase();
		if (images.containsKey(name)) {
			return images.get(name);
		}
		throw new RuntimeException("No image: " + name);
	}

	public ImageView getImgView(String name) {
		return new ImageView(getImg(name));
	}

	private static void loadAndPut(String name) {
		images.put(name.toLowerCase(), loadImg(name.toLowerCase()));
	}

	private static Image loadImg(String name) {
		return new Image(IconsResource.class.getClassLoader().getResourceAsStream("icons/" + name + ".png"));
	}
}
