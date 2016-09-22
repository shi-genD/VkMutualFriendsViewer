package main.net;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;

import static java.lang.Thread.interrupted;

/**
 * Created by romster on 21/09/16.
 */
public class ImageViewAsyncApplier {

	private final long timeoutMs;
	private transient boolean wasStarted;
	private long startTimeMs;

	public ImageViewAsyncApplier(int timeoutMs) {
		this.timeoutMs = timeoutMs;
	}

	public ImageViewAsyncApplier() {
		this(Integer.MAX_VALUE);
	}

	private List<Future<Image>> images = new ArrayList<>();
	private List<ImageView> imageViews = new ArrayList<>();


	public void add(Future<Image> imageFuture, ImageView forImageView) {
		if (wasStarted) {
			throw new IllegalStateException("Can not add new items after was started");
		}
		images.add(imageFuture);
		imageViews.add(forImageView);
	}

	public void startApplying() {
		wasStarted = true;
		startTimeMs = System.currentTimeMillis();


		Thread task = new Thread(() -> {
			try {
				while (!images.isEmpty() && !isTimeoutExpired() && !interrupted()) {

					Iterator<Future<Image>> imageIterator = images.iterator();
					Iterator<ImageView> imageViewIterator = imageViews.iterator();

					while (imageIterator.hasNext()) {
						Future<Image> imageFuture = imageIterator.next();
						ImageView imageView = imageViewIterator.next();

						if (imageFuture.isDone()) {
							imageView.setImage(imageFuture.get());

							imageIterator.remove();
							imageViewIterator.remove();
						}
					}
				}
			} catch (Exception ex) {
				//TODO: обработка ошибок
				ex.printStackTrace();
			}
		});
		task.start();
	}

	private boolean isTimeoutExpired() {
		long currentTimeMillis = System.currentTimeMillis();
		return currentTimeMillis > startTimeMs + timeoutMs;
	}


}
