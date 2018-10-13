package de.thewbi.time;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStampEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(final String text) throws IllegalArgumentException {

		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss");
		dateFormat.setLenient(false);

		try {
			final Date date = dateFormat.parse(text);

			final Timestamp time = new Timestamp(date.getTime());
			setValue(time);

		} catch (final ParseException e) {
			e.printStackTrace();
		}

	}

}
