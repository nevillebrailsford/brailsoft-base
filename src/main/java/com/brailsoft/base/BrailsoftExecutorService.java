package com.brailsoft.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Provide a single point for handling of ExecutorService functions
 * 
 * @author nevil
 *
 */
public class BrailsoftExecutorService {
	private static BrailsoftExecutorService instance = null;

	private ExecutorService executor = Executors.newFixedThreadPool(5);

	/**
	 * Create the single instance, passing in application. Application is used on
	 * only the first call to getInstance(). Once created, the instance has been
	 * created, the parameter may be omitted.
	 * 
	 * @param application
	 * @return instance
	 * @trhows AssertionError if application missing on first call.
	 */
	public synchronized static BrailsoftExecutorService getInstance(BrailsoftApplication... application) {
		if (instance == null) {
			assert (application != null);
			assert (application.length == 1);
			instance = new BrailsoftExecutorService();
			instance.executor = Executors.newFixedThreadPool(application[0].executorServiceThreads());
		}
		return instance;
	}

	private BrailsoftExecutorService() {
	}

	public ExecutorService executor() {
		return executor;
	}
}
