package scraper.application;

import scraper.core.*;
import scraper.core.events.Event;
import scraper.core.events.EventListener;

import java.util.*;
import javax.swing.SwingWorker;

public class Worker extends SwingWorker<Long, ProgressEvent> implements EventListener {

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
		if (isCancelled) return; // Sometimes process gets called after the worker has been cancelled

		for (ProgressEvent progressEvent : progressEvents) {
			application.onWorkerProgress(progressEvent);
		}
	}

	@Override
	protected Long doInBackground() {
		scraper.scrape();
		return scraper.getNanosecondsElapsed();
	}

	@Override
	protected void done() {
		scraper.cleanupPropertyScrapers();
		application.onWorkerDone();
	}

	@Override
	public void onEventReceived(Event event) {
		ProgressEvent progressEvent = (ProgressEvent)event;
		publish(progressEvent);
	}

}
