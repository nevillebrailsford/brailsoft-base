package com.brailsoft.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Provide a single point for handling of ExecutorService functions
 * 
 * @author nevil
 *
 */
public class ThreadServices {
	private static ThreadServices instance = null;

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
	public synchronized static ThreadServices getInstance(Application... application) {
		if (instance == null) {
			assert (application != null);
			assert (application.length == 1);
			instance = new ThreadServices();
			instance.executor = Executors.newFixedThreadPool(application[0].executorServiceThreads());
		}
		return instance;
	}

	private ThreadServices() {
	}

	public ExecutorService executor() {
		return executor;
	}
}
