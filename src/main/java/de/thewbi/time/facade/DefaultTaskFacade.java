/**
 * 
 */
package de.thewbi.time.facade;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.thewbi.time.data.Entry;
import de.thewbi.time.data.EntryRepository;
import de.thewbi.time.data.EntryType;

@Component
public class DefaultTaskFacade implements TaskFacade {

	@Autowired
	private EntryRepository entryRepository;

	@Override
	public List<Entry> getTasks() {
		return entryRepository.findAllByType(EntryType.TASK);
	}

	@Override
	public void saveTask(final Entry entry) {
		entryRepository.save(entry);
	}

	@Override
	public Optional<Entry> getTaskById(final Long id) {
		return entryRepository.findById(id);
	}

}
