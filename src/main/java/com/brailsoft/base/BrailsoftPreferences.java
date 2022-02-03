package com.brailsoft.base;

import java.util.Optional;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class BrailsoftPreferences {
	private static BrailsoftPreferences instance = null;

	private Preferences applicationPreferences = null;

	public synchronized static BrailsoftPreferences getInstance(String... nodeName) {
		if (instance == null) {
			if (nodeName.length == 0) {
				throw new IllegalArgumentException("BrailsoftPreferences: nodeName was null");
			} else if (nodeName.length > 1) {
				throw new IllegalArgumentException("BrailsoftPreferences: more than 1 nodeName was specified");
			}
			if (nodeName[0] == null || nodeName[0].isBlank() || nodeName[0].isEmpty()) {
				throw new IllegalArgumentException("BrailsoftPreferences: nodeName was null");
			}
			instance = new BrailsoftPreferences(nodeName[0]);
		}
		return instance;
	}

	private BrailsoftPreferences(String nodeName) {
		applicationPreferences = Preferences.userRoot().node(nodeName);
	}

	public void clear() throws BackingStoreException {
		applicationPreferences.clear();
		applicationPreferences.flush();
		instance = null;
	}

	public void setPreference(String preferenceName, String preferenceValue) {
		applicationPreferences.put(preferenceName, preferenceValue);
	}

	public Optional<String> preference(String preferenceName) {

		String preferenceValue = applicationPreferences.get(preferenceName, null);
		return Optional.ofNullable(preferenceValue);
	}
}
