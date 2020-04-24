package scraper.application;

import scraper.core.*;
import scraper.core.events.Event;
import scraper.core.events.EventListener;

import java.io.IOException;
import java.util.*;
import javax.swing.SwingWorker;

public class Worker extends SwingWorker<Void, ProgressEvent> implements EventListener {

	private final Application application;
	private final Scraper scraper;

	public Worker(Application application, Scraper scraper) {
		this.application = application;
		this.scraper = scraper;
		this.scraper.pushEventListener(this);
	}

	@Override
	protected void process(List<ProgressEvent> progressEvents) {
		boolean isCancelled = isCancelled();

		if (isCancelled)
			return; // Sometimes process gets called after the worker has been cancelled

		for (ProgressEvent progressEvent : progressEvents) {
			application.onWorkerProgress(progressEvent);
		}
	}

	@Override
	protected Void doInBackground() {
		scraper.scrape();
		return null;
	}

	@Override
	protected void done() {
		tryCleanupPropertyScrapers();
		application.onWorkerDone();
	}

	private void tryCleanupPropertyScrapers() {
		try {
			scraper.cleanupPropertyScrapers();
		}
		catch (IOException ignored) {}
	}

	@Override
	public void onEventReceived(Event event) {
		ProgressEvent progressEvent = (ProgressEvent)event;
		publish(progressEvent);
	}

	public void writeScraperSummary(String filePath) {
		scraper.writeSummary(filePath);
	}

}
