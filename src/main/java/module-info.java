module brailsoft.base {
	exports com.brailsoft.base;

	requires transitive java.logging;
	requires java.prefs;
	requires transitive javafx.base;

	opens com.brailsoft.base;
}