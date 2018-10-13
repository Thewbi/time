package de.thewbi.time.facade;

import java.util.List;

import de.thewbi.time.data.Entry;

public interface ProjectFacade {

	List<Entry> getProjects();

	void saveProject(Entry entry);

}
