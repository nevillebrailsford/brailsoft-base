module brailsoft.base {
	exports com.brailsoft.base;

	requires transitive java.logging;
	requires java.prefs;

	opens com.brailsoft.base;
}