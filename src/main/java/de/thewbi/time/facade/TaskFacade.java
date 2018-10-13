package de.thewbi.time.facade;

import java.util.List;
import java.util.Optional;

import de.thewbi.time.data.Entry;

public interface TaskFacade {

	List<Entry> getTasks();

	void saveTask(Entry entry);

	Optional<Entry> getTaskById(Long id);

}
