package de.thewbi.time.data;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.AmbiguousTableNameException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration
@AutoConfigureTestEntityManager
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class EntryRepositoryTest {

	@Autowired
	private EntryRepository entryRepository;

	@Autowired
	private DataSource dataSource;

	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/entityrepository/testdata_create/create_sample.xml")
	@ExpectedDatabase(value = "/entityrepository/testdata_create/create_expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testSave() throws AmbiguousTableNameException, DataSetException, FileNotFoundException,
			DatabaseUnitException, SQLException, IOException {

		final Entry entry = new Entry();
		entry.setEntryType(EntryType.PROJECT);
		entry.setName("name");
		entry.setDescription("description");
		entry.setStart(new Timestamp(1539372838));
		entry.setEnd(new Timestamp(1539372838));

		entryRepository.save(entry);

		export("/testdata_create/partial.xml");
	}

	@Test
	@Transactional(propagation = Propagation.SUPPORTS)
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/entityrepository/testdata_create_recursive/create_recursive_sample.xml")
	@ExpectedDatabase(value = "/entityrepository/testdata_create_recursive/create_recursive_expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testSaveRecursive() throws AmbiguousTableNameException, DataSetException, FileNotFoundException,
			DatabaseUnitException, SQLException, IOException {

		final Entry entryA = new Entry();
		entryA.setEntryType(EntryType.PROJECT);
		entryA.setName("name_A_project");
		entryA.setDescription("description_A_project");
		entryA.setStart(new Timestamp(1539372838));
		entryA.setEnd(new Timestamp(1539372838));

		final Entry entryA1 = new Entry();
		entryA1.setEntryType(EntryType.TASK);
		entryA1.setName("name_A1_task");
		entryA1.setDescription("description_A1_task");
		entryA1.setStart(new Timestamp(1539372838));
		entryA1.setEnd(new Timestamp(1539372838));
		entryA1.setParent(entryA);

		final Entry entryA11 = new Entry();
		entryA11.setEntryType(EntryType.DURATION);
		entryA11.setName("name_A11_duration");
		entryA11.setDescription("description_A11_duration");
		entryA11.setStart(new Timestamp(1539372838));
		entryA11.setEnd(new Timestamp(1539372838));
		entryA11.setParent(entryA1);

		final Entry entryA2 = new Entry();
		entryA2.setEntryType(EntryType.TASK);
		entryA2.setName("name_A2_task");
		entryA2.setDescription("description_A2_task");
		entryA2.setStart(new Timestamp(1539372838));
		entryA2.setEnd(new Timestamp(1539372838));
		entryA2.setParent(entryA);

		final Entry entryA21 = new Entry();
		entryA21.setEntryType(EntryType.DURATION);
		entryA21.setName("name_A21_duration");
		entryA21.setDescription("description_A21_duration");
		entryA21.setStart(new Timestamp(1539372838));
		entryA21.setEnd(new Timestamp(1539372838));
		entryA21.setParent(entryA2);

		entryA1.getChildren().add(entryA11);
		entryA2.getChildren().add(entryA21);

		entryA.getChildren().add(entryA1);
		entryA.getChildren().add(entryA2);

		// save parent first
		entryRepository.save(entryA);

		// then save the children
		entryRepository.save(entryA1);
		entryRepository.save(entryA2);

		entryRepository.save(entryA11);
		entryRepository.save(entryA21);

		export("/testdata_create_recursive/partial.xml");

		final List<Entry> allByType = entryRepository.findAllByType(EntryType.PROJECT);
		for (final Entry entry : allByType) {
			dumpEntry(entry);
		}

	}

	private void dumpEntry(final Entry entry) {

		System.out.println(entry.getEntryType().name() + " " + entry.getName());

		for (final Entry child : entry.getChildren()) {
			dumpEntry(child);
		}
	}

	private void export(final String path) throws DatabaseUnitException, SQLException, AmbiguousTableNameException,
			IOException, DataSetException, FileNotFoundException {
		final IDatabaseConnection connection = new DatabaseConnection(dataSource.getConnection());

		final QueryDataSet queryDataSet = new QueryDataSet(connection);
		queryDataSet.addTable("entry");

		FlatXmlDataSet.write(queryDataSet, new FileOutputStream("src/test/resources/entityrepository" + path));
	}

}
