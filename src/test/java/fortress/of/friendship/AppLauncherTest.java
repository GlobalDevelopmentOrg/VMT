package fortress.of.friendship;

import fortress.of.friendship.database.H2DatabaseConnector;
import org.h2.tools.DeleteDbFiles;
import org.junit.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Unit tests ensure we run tests before making a pull request.
 * All tests should run.
 * <p>
 * Write tests for new classes you create,
 * this is ensure other developers don't break your code.
 */
public class AppLauncherTest {

    private static final String TEST_DB_NAME = "test/test_db";
    private static final String TEST_DB_USER = "test_user";
    private static final String TEST_DB_PASS = "test_h2_pass_secure_102395349531";

    private static final String TEST_TABLE = "test";

    private static H2DatabaseConnector DATABASE;

    @Before
    public void testCreateDatabase() {
        AppLauncherTest.DATABASE = H2DatabaseConnector.using(AppLauncherTest.TEST_DB_NAME, AppLauncherTest.TEST_DB_USER, AppLauncherTest.TEST_DB_PASS);
        Assert.assertTrue("Database connection is not open", AppLauncherTest.DATABASE.isOpen());

        int result = AppLauncherTest.DATABASE.executeUpdate("CREATE TABLE IF NOT EXISTS " + AppLauncherTest.TEST_TABLE + " (`id` int(11) NOT NULL AUTO_INCREMENT, `registration` varchar(100) NOT NULL DEFAULT '0', `mileage` int(100) NOT NULL DEFAULT '', PRIMARY KEY (`id`))");

        // make sure table was created
        Assert.assertNotEquals("error when creating table", -1, result);
    }

    @Test
    public void testUpdateDatabase() {
        String setRegistration = "20AVD5PO";
        int setMileage = 2349;

        String update = "INSERT INTO " + AppLauncherTest.TEST_TABLE + " (registration, mileage) VALUES ('" + setRegistration + "', '" + 2349 + "')";
        String query = "SELECT * FROM " + AppLauncherTest.TEST_TABLE;

        try{
            int success = AppLauncherTest.DATABASE.executeUpdate(update);
            Assert.assertNotEquals("unable it update database", -1, success);

            ResultSet rs = AppLauncherTest.DATABASE.executeQuery(query);

            Assert.assertTrue("No row found in result set", rs.next());
            String getRegistration = rs.getString("registration");
            int getMileage = Integer.parseInt(rs.getString("mileage"));

            // close result and database connection
            rs.close();
            AppLauncherTest.DATABASE.close();

            // assure database is not open
            Assert.assertTrue("Connection wasn't closed", !AppLauncherTest.DATABASE.isOpen());

            // check values read from database match values wrote to database
            Assert.assertEquals("Retrieved registration doesn't match value set", setRegistration, getRegistration);
            Assert.assertEquals("Retrieved mileage doesn't match value set", setMileage, getMileage);

        }catch (SQLException e){
            Assert.assertTrue(e.getMessage(), false);
        }

        // delete the test database files in /database/test
        DeleteDbFiles.execute("database/test/", null, true);
    }

}
