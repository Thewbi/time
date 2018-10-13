package de.thewbi.time.data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.joda.time.Interval;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
public class Entry {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity_sequence")
	@SequenceGenerator(name = "entity_sequence", sequenceName = "ENTITY_SEQ")
	private Long id;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private Long tempParentId;

	@Enumerated(EnumType.STRING)
	private EntryType entryType;

	private Timestamp start;

	private Timestamp end;

	@Column(name = "NAME", unique = true, nullable = false)
	private String name;

	private String description;

	@ManyToOne
	private Entry parent;

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	private Collection<Entry> children = new ArrayList<>();

	public long getDurationInMillis() {
		return getInterval().toDurationMillis();
	}

	public String getDurationAsString() {
		return DurationFormatUtils.formatDuration(getInterval().toDurationMillis(), "HH:mm:ss");
	}

	private Interval getInterval() {

		if (start == null || end == null) {
			return new Interval(0, 0);
		}

		final long startInMillis = start.getTime();
		final long endInMillis = end.getTime();

		Interval interval = null;
		if (startInMillis < endInMillis) {
			interval = new Interval(startInMillis, endInMillis);
		} else {
			interval = new Interval(endInMillis, startInMillis);
		}
		return interval;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public EntryType getEntryType() {
		return entryType;
	}

	public void setEntryType(final EntryType entryType) {
		this.entryType = entryType;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Timestamp getStart() {
		return start;
	}

	public void setStart(final Timestamp start) {
		this.start = start;
	}

	public Timestamp getEnd() {
		return end;
	}

	public void setEnd(final Timestamp end) {
		this.end = end;
	}

	public Entry getParent() {
		return parent;
	}

	public void setParent(final Entry parent) {
		this.parent = parent;
	}

	public Collection<Entry> getChildren() {
		return children;
	}

	public void setChildren(final Collection<Entry> children) {
		this.children = children;
	}

	public Long getTempParentId() {
		return tempParentId;
	}

	public void setTempParentId(final Long tempParentId) {
		this.tempParentId = tempParentId;
	}

}
