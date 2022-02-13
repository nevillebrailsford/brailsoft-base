/**
 * 
 */
package com.brailsoft.base;

/**
 * @author nevil
 *
 */
public class ApplicationConfiguration {
	private static Application registeredApplication = null;

	/**
	 * Register the application class for this app.
	 * 
	 * @param application
	 * @throws AssertionError if application is null, or an application has already
	 *                        been registered.
	 */
	public synchronized static void registerApplication(Application application) {
		assert (application != null);
		assert (registeredApplication == null);
		registeredApplication = application;
	}

	/**
	 * Retrieve the application registered for this app
	 * 
	 * @return application
	 * @throws AssertionError if no application has been registered.
	 */
	public synchronized static Application application() {
		assert (registeredApplication != null);
		return registeredApplication;
	}

	/**
	 * Clear the registration
	 */
	public synchronized static void clear() {
		registeredApplication = null;
	}
}
