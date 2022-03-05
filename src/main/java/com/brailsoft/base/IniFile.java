package com.brailsoft.base;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;

public class IniFile {

	private static Properties values = null;

	/**
	 * Retrieve the value of a property stored in an ini file.
	 * 
	 * @param key
	 * @return corresponding value
	 * @throws IllegalArgumentException if key is null or empty
	 */
	public static synchronized String value(String key) {
		if (key == null) {
			throw new IllegalArgumentException("IniFile: key was null");
		}
		if (key.trim().isEmpty()) {
			throw new IllegalArgumentException("IniFile: key was empty");
		}
		loadProperties();
		String result = values.getProperty(key);
		return result == null ? "" : result;
	}

	/**
	 * Store a property in an ini file.
	 * 
	 * @param key
	 * @param value
	 * @throws IllegalArgumentException if either key or value is null, or if either
	 *                                  is empty
	 */
	public static synchronized void store(String key, String value) {
		if (key == null) {
			throw new IllegalArgumentException("IniFile: key was null");
		}
		if (key.trim().isEmpty()) {
			throw new IllegalArgumentException("IniFile: key was empty");
		}
		if (value == null) {
			throw new IllegalArgumentException("IniFile: value was null");
		}
		if (value.trim().isEmpty()) {
			throw new IllegalArgumentException("IniFile: value was empty");
		}
		loadProperties();
		values.put(key, value);
		saveProperties();
	}

	public static synchronized void clear() {
		values = null;
	}

	private static void createProperties() {
		values = new Properties();
	}

	private static void loadProperties() {
		if (values != null) {
			return;
		}
		createProperties();
		ApplicationDecsriptor application = ApplicationConfiguration.applicationDecsriptor();
		File iniFile = new File(application.iniFile());
		if (!iniFile.exists()) {
			File iniFileDirectory = new File(application.iniFileDirectory());
			if (!iniFileDirectory.exists()) {
				iniFileDirectory.mkdirs();
			}
			return;
		}
		try (FileReader file = new FileReader(iniFile)) {
			values.load(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void saveProperties() {
		if (values == null) {
			return;
		}
		ApplicationDecsriptor application = ApplicationConfiguration.applicationDecsriptor();
		File iniFileDirectory = new File(application.iniFileDirectory());
		if (!iniFileDirectory.exists()) {
			iniFileDirectory.mkdirs();
		}
		File iniFile = new File(application.iniFile());
		try (FileWriter file = new FileWriter(iniFile)) {
			values.store(file, "Properties written by " + application.applicationName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
