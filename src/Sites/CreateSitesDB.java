package Sites;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *<pre>
 * Class        CreateSitesDB.java
 * Description  A class used to create the SitesDB
 * Platform     jdk 21; NetBeans 19
 * Course       CS 143
 * Date         10/31/2023
 * @author      <i>Chiquita Zephaniah</i>
 * </pre>
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class CreateSitesDB implements MySQLConnection{
    private static final String SITES_TEXT_FILE = "src/Sites/Sites.txt";
    private static final ArrayList<Site> sites = new ArrayList<Site>();
    private Site mySite = new Site();
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * <pre>
     * Method       main()
     * Description  Reads data from external text file, creates the Sites table.
     * @param       args are the command line strings
     * @author      <i>Chiquita Zephaniah</i>
     * Date         10/31/2023
     * </pre>
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public static void main(String[] args) {
        try
        {
            readFromTextFile(SITES_TEXT_FILE);
            String url = DB_URL;
            String user = USER;
            String password = PASS;

            //Make connection to MySQL DB
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet table;
            
            //Check if the tables already exist
            table = dbm.getTables(null, null, "Sites", null);
            if(table.next())
            {
                //The table exists, so kill it so we can re-make it
                stmt.executeUpdate("DROP TABLE Sites");
            }
            //Create table Sites
            stmt.executeUpdate("CREATE TABLE Sites (siteID"
            + " SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT, name"
            + " VARCHAR(30), country VARCHAR(20), population INTEGER,"
            + " capital VARCHAR(30), area INTEGER,"
            + " PRIMARY KEY (siteID))");
            
            for(int i = 0; i < sites.size(); i++)
            {
                stmt.executeUpdate
                    ("INSERT INTO Sites (siteID, name, country, population, capital, area) VALUES("
                        + sites.get(i).getID() + ","
                        + "'" + sites.get(i).getName() + "',"
                        + "'" + sites.get(i).getCountry() + "',"
                        + sites.get(i).getPopulation() + ","
                        + "'" + sites.get(i).getCapital() + "'" + ","
                        + "'" + sites.get(i).getArea() + "')"
                );
            }
            stmt.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "SQL Error",
                    "SQL Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
     /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       readFromTextFile()
     * Description  Reads text file and creates an arraylist with persons.
     * Date:        10/31/2023
     * @author      <i>Chiquita Zephaniah</i>
     * @param       textFile String
     *</pre>
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private static void readFromTextFile(String textFile)
    {        
        try
        {            
            FileReader freader = new FileReader(textFile);
            BufferedReader input = new BufferedReader(freader);
            String line = input.readLine();     //read the first line
            
            while(line != null)
            {
                Site tempSite = new Site();
                StringTokenizer token = new StringTokenizer(line, ",");
                while(token.hasMoreElements())
                {
                    tempSite.setID(Integer.parseInt(token.nextToken().trim()));
                    tempSite.setName(token.nextToken().trim());
                    tempSite.setCountry(token.nextToken().trim());
                    tempSite.setPopulation(Float.parseFloat(token.nextToken().trim()));
                    tempSite.setCapital(token.nextToken().trim());
                    tempSite.setArea(Float.parseFloat(token.nextToken().trim()));
                }
                sites.add(tempSite);    // Add Site to ArrayList
                line = input.readLine();
            }
            input.close();      //Close the BufferedReader
        }
        catch(FileNotFoundException fnfexp)
        {
            JOptionPane.showMessageDialog(null, "Input error -- File not found.",
                    "File Not Found Error!", JOptionPane.ERROR_MESSAGE);
        }
        catch(IOException | NumberFormatException exp)
        {
            JOptionPane.showMessageDialog(null, "Input error -- File could not be read.",
                    "File Read Error!", JOptionPane.ERROR_MESSAGE);
        }
    }    
}
