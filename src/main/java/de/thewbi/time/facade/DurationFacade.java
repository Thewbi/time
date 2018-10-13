package de.thewbi.time.facade;

import java.util.List;
import java.util.Optional;

import de.thewbi.time.data.Entry;

public interface DurationFacade {

	List<Entry> getDurations();

	void saveDuration(Entry entry);

	Optional<Entry> getDurationById(Long id);

	void delete(Entry entry);

}
