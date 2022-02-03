package com.brailsoft.base;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * A record that is passed to the audit function to record an activity of some
 * kind.
 * 
 * @author nevil
 *
 */
public class BrailsoftAuditRecord<T extends BrailsoftAuditType, O extends BrailsoftAuditObject>
		implements Comparable<BrailsoftAuditRecord<T, O>> {
	private T auditType;
	private O auditObject;
	private String auditInformation;

	private final DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern(BrailsoftDateFormats.dateFormatForAuditRecord);
	private String user;
	private ZonedDateTime timeStamp;

	BrailsoftAuditRecord(T type, O object, String information) {
		assert (type != null);
		assert (object != null);
		assert (information != null);
		assert (!information.trim().isEmpty());
		auditType = type;
		auditObject = object;
		auditInformation = information;
		user = System.getProperty("user.name");
		timeStamp = ZonedDateTime.now();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(timeStamp.format(formatter)).append(" ");
		builder.append(user).append(" ");
		builder.append(auditType.type()).append(" ");
		builder.append(auditObject.object()).append(" ");
		builder.append(auditInformation);
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(timeStamp);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BrailsoftAuditRecord<?, ?> that = (BrailsoftAuditRecord<?, ?>) obj;
		return Objects.equals(this.timeStamp, that.timeStamp);
	}

	@Override
	public int compareTo(BrailsoftAuditRecord<T, O> that) {
		return this.timeStamp.compareTo(that.timeStamp);
	}

}
