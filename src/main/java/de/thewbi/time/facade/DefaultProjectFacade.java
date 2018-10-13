package de.thewbi.time.facade;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.thewbi.time.data.Entry;
import de.thewbi.time.data.EntryRepository;
import de.thewbi.time.data.EntryType;

@Component
public class DefaultProjectFacade implements ProjectFacade {

	@Autowired
	private EntryRepository entryRepository;

	@Override
	public List<Entry> getProjects() {
		return entryRepository.findAllByType(EntryType.PROJECT);
	}

	@Override
	public void saveProject(final Entry entry) {
		entryRepository.save(entry);
	}

	@Override
	public Optional<Entry> getProjectById(final Long id) {
		return entryRepository.findById(id);
	}

}
