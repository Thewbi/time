package de.thewbi.time.data;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Entry {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity_sequence")
	@SequenceGenerator(name = "entity_sequence", sequenceName = "ENTITY_SEQ")
	private Long id;

	@Enumerated(EnumType.STRING)
	private EntryType entryType;

	private Timestamp start;

	private Timestamp end;

	private String name;

	private String description;

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

}
