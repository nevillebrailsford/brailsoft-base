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
	 * Create the single instance, passing in application. ApplicationDecsriptor is used on
	 * only the first call to getInstance(). Once created, the instance has been
	 * created, the parameter may be omitted.
	 * 
	 * @return instance
	 */
	public synchronized static ThreadServices getInstance() {
		if (instance == null) {
			instance = new ThreadServices();
			instance.executor = Executors.newFixedThreadPool(ApplicationConfiguration.executorServiceThreads());
		}
		return instance;
	}

	private ThreadServices() {
	}

	public ExecutorService executor() {
		return executor;
	}
}
