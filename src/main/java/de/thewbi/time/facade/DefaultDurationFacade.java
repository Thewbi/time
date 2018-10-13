package de.thewbi.time.facade;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.thewbi.time.data.Entry;
import de.thewbi.time.data.EntryRepository;
import de.thewbi.time.data.EntryType;

@Component
public class DefaultDurationFacade implements DurationFacade {

	@Autowired
	private EntryRepository entryRepository;

	@Override
	public List<Entry> getDurations() {
		return entryRepository.findAllByType(EntryType.DURATION);
	}

	@Override
	public void saveDuration(final Entry entry) {
		entryRepository.save(entry);
	}

	@Override
	public Optional<Entry> getDurationById(final Long id) {
		return entryRepository.findById(id);
	}

	@Override
	public void delete(final Entry entry) {
		entryRepository.delete(entry);
	}

}
