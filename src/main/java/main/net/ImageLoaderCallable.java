package main.net;

import javafx.scene.image.Image;

import java.util.concurrent.Callable;

/**
 * Created by romster on 21/09/16.
 */
public class ImageLoaderCallable implements Callable<Image> {

	private final String imageUrl;

	public ImageLoaderCallable(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public Image call() throws Exception {
		Image image = new Image(imageUrl);
		return image;
	}
}
