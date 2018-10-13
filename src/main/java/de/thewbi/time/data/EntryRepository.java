package de.thewbi.time.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EntryRepository extends CrudRepository<Entry, Long> {

	@Query("SELECT e FROM #{#entityName} e WHERE e.entryType = :entryType")
	List<Entry> findAllByType(EntryType entryType);

}
