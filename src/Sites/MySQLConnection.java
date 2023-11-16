package Sites;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*<pre>
 * Class        MySQLConnection.java
 * Description  An interface representing the connections constants needed for
 *              the Address Book Application
 * Platform     jdk 21; NetBeans IDE 19
 * Course       CS 143
 * Date         10/31/2023
 * @author	<i>Chiquita Zephaniah</i>
 * @version 	%1% %2%
 * @see     	javax.swing.JFrame
 * @see         java.awt.Toolkit 
 *</pre>
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public interface MySQLConnection {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/javabook";
    public static final String USER = "root";
    public static final String PASS = "chika1603";
    public static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
}
