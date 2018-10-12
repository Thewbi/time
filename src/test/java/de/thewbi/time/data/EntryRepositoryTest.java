package de.thewbi.time.data;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

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

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;

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
	@ExpectedDatabase("/entityrepository/testdata_create/create_expected.xml")
	public void testSave() throws AmbiguousTableNameException, DataSetException, FileNotFoundException,
			DatabaseUnitException, SQLException, IOException {

		final Entry entry = new Entry();
		entry.setEntryType(EntryType.PROJECT);
		entry.setName("name");
		entry.setDescription("description");
		entry.setStart(new Timestamp(1539372838));
		entry.setEnd(new Timestamp(1539372838));
		entryRepository.save(entry);

		export();
	}

//	@Ignore
	@Test
	public void testExportData() throws DatabaseUnitException, SQLException, FileNotFoundException, IOException {
		export();
	}

	private void export() throws DatabaseUnitException, SQLException, AmbiguousTableNameException, IOException,
			DataSetException, FileNotFoundException {
		final IDatabaseConnection connection = new DatabaseConnection(dataSource.getConnection());

		final QueryDataSet queryDataSet = new QueryDataSet(connection);
		queryDataSet.addTable("entry");

		FlatXmlDataSet.write(queryDataSet,
				new FileOutputStream("src/test/resources/entityrepository/testdata_create/partial.xml"));
	}

}
