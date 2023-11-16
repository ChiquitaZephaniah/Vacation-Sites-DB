package Sites;

import java.awt.Color;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*<pre>
 * Class        SitesDB_GUI.java
 * Description  A class representing the GUI used in the Vacation Sites 
 *              Application. 
 * Platform     jdk 21; NetBeans IDE 19
 * Date         10/31/2023
 * @author	<i>Chiquita Zephaniah</i>
 *</pre>
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class SitesDB_GUI extends javax.swing.JFrame implements MySQLConnection {
    private ArrayList<Site> sites = new ArrayList<Site>();
    private String SITES_TEXT_FILE = "src/Sites/Sites.txt";
    private Site mySite = new Site();
    private int currentID = 1, sizeOfDB;
    private final Color white = Color.WHITE;    // Default background color for input textfield
    private final Color pink = Color.PINK;      // Background color for bad input textfield

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Constructor  SitesDB_GUI()-default constructor
     * Description  Create an instance of the GUI form and sets icon image.
     * Date         10/31/2023
     * @author      <i>Chiquita Zephaniah</i>
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public SitesDB_GUI() {
        try {
            initComponents();
            this.getRootPane().setDefaultButton(addJButton);
            this.setIconImage(Toolkit.getDefaultToolkit().getImage("src/Images/vacationsites.jpg"));
            readFromTextFile(SITES_TEXT_FILE);
            String url = DB_URL;
            String user = USER;
            String password = PASS;
            
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            
            //Retrieving the data to get count on how many records are in the DB
            ResultSet rs = stmt.executeQuery("SELECT count(*) FROM Sites");
            rs.next();
            // Moving the cursor to the last row
            sizeOfDB = rs.getInt("count(*)");
            
            String query = "SELECT * FROM Sites";
            rs = stmt.executeQuery(query);
            rs.next();      //move to first record
            
            int firstIndex = rs.getInt("siteID");
            Site tempSite = searchSite(firstIndex);
            
            display(tempSite);        // show site's data
        }
        catch (SQLException ex)
        {
            // Show error message
            JOptionPane.showMessageDialog(null, "Input error -- SQL error.",
                    "SQL Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *>
     * Method       showSiteData()
     * Description  Display information about selected Site
     * @parem       index int
     * @author      <i>Chiquita Zephaniah</i>
     * Date         10/29/2023
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    private void showSiteData(int index)
    {
        if(index >=0 && index < sites.size())
        {
            siteNumberJLabel.setText(String.valueOf(sites.get(index).getID()));
            nameJTextField.setText(sites.get(index).getName());
            countryJTextField.setText(sites.get(index).getCountry());
            populationJTextField.setText(String.valueOf(sites.get(index).getPopulation()));
            capitalJTextField.setText(sites.get(index).getCapital());
            areaJTextField.setText(String.valueOf(sites.get(index).getArea()));
        }
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       displaySite()
     * Description  Show information about the Site passed as parameter.
     * @param       mySite Site
     * @author      <i>Chiquita Zephaniah</i>
     * Date         10/29/2023
    *</pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void displaySite()
    {
        int location = sitesJList.getSelectedIndex();
        String[] sitesList = new String[sites.size()];
        if (byPopulationJRadioButtonMenuItem.isSelected())
        {
            selectionSort(sites);
            for(int index = 0; index < sites.size(); index++)
            {
                String population_size = String.valueOf(sites.get(index).getPopulation());
                sitesList[index] = sites.get(index).getName()+ ", "
                        + population_size;
            }
        }
        else
        {
            insertionSort(sites);
            for(int index = 0; index < sites.size(); index++)
            {
                sitesList[index] = sites.get(index).getName();
            }
        }
        sitesJList.setListData(sitesList);
        if(location < 0)
            sitesJList.setSelectedIndex(0);
        else
            sitesJList.setSelectedIndex(location);
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       searchSite()
     * Description  Search Site in DB by id.
     * Date:        10/31/2023
     * @author      <i>Chiquita Zephaniah</i>
     * @param       id iny
     * @return      mySite Site
     *</pre>
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    private Site searchSite(int id)
    {
        try
        { 
            String url = DB_URL;
            String user = USER;
            String password = PASS;
            
            //Make connection to MySQL DB
            Connection conn = DriverManager.getConnection(url, user, password);
            mySite = new Site();
            
            // Set prepared statement query to search for site by id
            String query = "SELECT * FROM Sites WHERE siteID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet results = pstmt.executeQuery();
            results.next();         //move to first record
            
            mySite.setID(results.getInt(1));
            mySite.setName(results.getString(2));
            mySite.setCountry(results.getString(3));
            mySite.setPopulation(results.getFloat(4));
            mySite.setCapital(results.getString(5));
            mySite.setArea(results.getFloat(6));

            
            results.close();        //Close the result set
            pstmt.close();          // Close the prepared statement
            conn.close();           // Close the connection
            //return the site
            return mySite;
        }
        catch(SQLException exp)
        {
            //exp.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error searching database for site", 
                    "Search Error", JOptionPane.ERROR_MESSAGE);
            return new Site();
        }        
   }
    
     /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       display()
     * Description  Show information about the Site passed as parameter.
     * @param       mySite Site
     * @author      <i>Chiquita Zephaniah</i>
     * Date         10/31/2023
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void display(Site mySite)
    {
        siteNumberJLabel.setText(String.valueOf(mySite.getID()));
        nameJTextField.setText(mySite.getName());
        countryJTextField.setText(mySite.getCountry());
        populationJTextField.setText(String.valueOf(mySite.getPopulation()));
        capitalJTextField.setText(mySite.getCapital());
        areaJTextField.setText(String.valueOf(mySite.getArea()));
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       readFromTextFile()
     * Description  Reads text file and creates an arraylist with Sites.
     * Date:        10/31/2023
     * @author      <i>Chiquita Zephaniah</i>
     * @param       textFile String
     *</pre>
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void readFromTextFile(String textFile)
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
                    tempSite.setID(Integer.parseInt(token.nextToken()));
                    tempSite.setName(token.nextToken());
                    tempSite.setCountry(token.nextToken());
                    tempSite.setPopulation(Float.parseFloat(token.nextToken()));
                    tempSite.setCapital(token.nextToken());
                    tempSite.setArea(Float.parseFloat(token.nextToken()));
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
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       selectionSort
     * Description  Sorts ArrayList Sites in ascending order by name.Uses 
                    the insertion sort algorithm which select sites at correct 
                    position and shuffles everything else below that position.
     * @author      <i>Chiquita Zephaniah</i>
     * @param       sites
     * Date         10/29/2023
     * </pre>
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void selectionSort(ArrayList <Site> sites)
    {
        int max = 0;
        for(int i = 0; i < sites.size(); i++)
        {
            max = findMaximum(sites, i);
            Site temp = sites.get(i);
            sites.set(i, sites.get(max));
            sites.set(max, temp);
        }
    }

    /** * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       insertionSort
     * Description  Sorts ArrayList Sites in ascending order by name.Uses 
                    the insertion sort algorithm which inserts sites at correct 
                    position and shuffles everything else below that position.
     * @author      <i>Chiquita Zephaniah</i>
     * @param       sites
     * Date         10/29/2023
     * </pre>
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    public static void insertionSort(ArrayList <Site> sites)
    {
        int i, j;
        for(i = 0; i< sites.size(); i++)
        {
            Site temp = sites.get(i);
            j = i - 1;
            while (j >= 0 && sites.get(j).getName().compareToIgnoreCase(temp.getName())> 0)
            {
                sites.set(j + 1, sites.get(j));
                j--;
            }
            sites.set(j + 1, temp);
        }
    }
        /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *>
     * Method       findMaximum
     * Description  Called by selectionSort to find the index of the member 
     *              with the maximum population from a given index to the end 
     *              of the ArrayList
     * @author      <i>Chiquita Zephaniah</i>
     * Date         10/29/2023
     * 
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    public int findMaximum(ArrayList < Site > sites, int i)
    {
        int j, max = i;
        for(j = i + 1; j < sites.size(); j++)
        {
            if (sites.get(j).getPopulation()> sites.get(max).getPopulation())
                max = j;
        }
        return max;
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method            saveSite()
    * Description       write Site to a text file that is "," delimited
    * @author       	<i>Chiquita Zephaniah</i>	
    * @param		evt ActionEvent
    * @see          	javax.swing.event.ActionEvent
    * Date          	10/29/2023
    * History Log   	
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void saveSite(String file)
    {
        try 
        {
            FileWriter filePointer = new FileWriter(file, false);
            PrintWriter output = new PrintWriter (filePointer);
            for(int index = 0; index < sites.size(); index++)
            {
                Site tempSite = sites.get(index);
                    String line = tempSite.getID() + "," +
                    tempSite.getName() + "," + tempSite.getCountry() +
                    "," + tempSite.getPopulation() + "," + tempSite.getCapital() +
                    "," + tempSite.getArea();
            output.println(line); // Use println to write each site on a new line
        }
        output.close();
        
    } 
        catch (IOException ex) 
        {
        JOptionPane.showMessageDialog(null, "Site not saved", "Save Error",
                JOptionPane.WARNING_MESSAGE);
        sitesJList.setVisible(true);
        sitesJList.setSelectedIndex(0);
        }
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method            siteID()
    * Description       Search site
    * @author       	<i>Chiquita Zephaniah</i>	
    * @param		evt ActionEvent
    * @see          	javax.swing.event.ActionEvent
    * Date          	10/29/2023
    * History Log   	
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public int siteID(String name)
     {
        int siteId = -1;

        try {
            String url = DB_URL;
            String user = USER;
            String password = PASS;

        
            Connection con = DriverManager.getConnection(url, user, password);

            PreparedStatement stmt = con.prepareStatement("SELECT siteID FROM sites2 WHERE Name = ?");
            stmt.setString(1, name);

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) 
            {
                siteId = resultSet.getInt("siteID");
            }
        } 
        catch (SQLException exp) 
        {
            JOptionPane.showMessageDialog(null, "Error updating to database", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        return siteId;
     }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        titlejLabel = new javax.swing.JLabel();
        sitesJScrollPane = new javax.swing.JScrollPane();
        sitesJList = new javax.swing.JList<>();
        nameJLabel = new javax.swing.JLabel();
        nameJTextField = new javax.swing.JTextField();
        countryJLabel = new javax.swing.JLabel();
        countryJTextField = new javax.swing.JTextField();
        populationJLabel = new javax.swing.JLabel();
        capitalJTextField = new javax.swing.JTextField();
        capitalJLabel = new javax.swing.JLabel();
        populationJTextField = new javax.swing.JTextField();
        areaJLabel = new javax.swing.JLabel();
        areaJTextField = new javax.swing.JTextField();
        addJButton = new javax.swing.JButton();
        editJButton = new javax.swing.JButton();
        deleteJButton = new javax.swing.JButton();
        exitJButton = new javax.swing.JButton();
        iconJLabel = new javax.swing.JLabel();
        siteNumberJLabel = new javax.swing.JLabel();
        vacationJMenuBar = new javax.swing.JMenuBar();
        fileJMenu = new javax.swing.JMenu();
        newJMenuItem = new javax.swing.JMenuItem();
        printJMenuItem = new javax.swing.JMenuItem();
        printFormJMenuItem = new javax.swing.JMenuItem();
        exitJMenuItem = new javax.swing.JMenuItem();
        sortJMenu = new javax.swing.JMenu();
        byNameJRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();
        byPopulationJRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();
        databaseJMenu = new javax.swing.JMenu();
        addJMenuItem = new javax.swing.JMenuItem();
        deleteJMenuItem = new javax.swing.JMenuItem();
        editJMenuItem = new javax.swing.JMenuItem();
        searchJMenuItem = new javax.swing.JMenuItem();
        detailsJMenuItem = new javax.swing.JMenuItem();
        helpJMenu = new javax.swing.JMenu();
        aboutJMenuItem = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Vacation Sites");
        setResizable(false);

        titlejLabel.setFont(new java.awt.Font("Tempus Sans ITC", 2, 36)); // NOI18N
        titlejLabel.setForeground(new java.awt.Color(0, 102, 102));
        titlejLabel.setText("Vacation Sites");

        sitesJList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Uluwatu", "Eiffel Tower", "Colosseum", "Marina Bay Sands", "Walt Disney World", "Universal Studios", "Labuan Bajo", "Phi Phi Island", "Twin Tower", "Grand Canyon National Park" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        sitesJList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                sitesJListValueChanged(evt);
            }
        });
        sitesJScrollPane.setViewportView(sitesJList);

        nameJLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        nameJLabel.setText("Name of Site :");

        nameJTextField.setEditable(false);

        countryJLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        countryJLabel.setText("Country :");

        countryJTextField.setEditable(false);

        populationJLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        populationJLabel.setText("Population :");

        capitalJTextField.setEditable(false);

        capitalJLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        capitalJLabel.setText("Capital :");

        populationJTextField.setEditable(false);

        areaJLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        areaJLabel.setText("Area :");

        areaJTextField.setEditable(false);

        addJButton.setBackground(new java.awt.Color(204, 255, 204));
        addJButton.setMnemonic('A');
        addJButton.setText("Add");
        addJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addJButtonActionPerformed(evt);
            }
        });

        editJButton.setBackground(new java.awt.Color(204, 255, 204));
        editJButton.setMnemonic('E');
        editJButton.setText("Edit");
        editJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editJButtonActionPerformed(evt);
            }
        });

        deleteJButton.setBackground(new java.awt.Color(204, 255, 204));
        deleteJButton.setMnemonic('D');
        deleteJButton.setText("Delete");
        deleteJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteJButtonActionPerformed(evt);
            }
        });

        exitJButton.setBackground(new java.awt.Color(204, 255, 204));
        exitJButton.setMnemonic('x');
        exitJButton.setText("Exit");
        exitJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitJButtonActionPerformed(evt);
            }
        });

        iconJLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/vacationsites.jpg"))); // NOI18N

        fileJMenu.setMnemonic('F');
        fileJMenu.setText("File");

        newJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        newJMenuItem.setMnemonic('N');
        newJMenuItem.setText("New");
        newJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(newJMenuItem);

        printJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        printJMenuItem.setMnemonic('P');
        printJMenuItem.setText("Print");
        printJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(printJMenuItem);

        printFormJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        printFormJMenuItem.setMnemonic('t');
        printFormJMenuItem.setText("Print Form");
        printFormJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printFormJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(printFormJMenuItem);

        exitJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        exitJMenuItem.setMnemonic('x');
        exitJMenuItem.setText("Exit");
        exitJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(exitJMenuItem);

        vacationJMenuBar.add(fileJMenu);

        sortJMenu.setMnemonic('S');
        sortJMenu.setText("Sort");

        byNameJRadioButtonMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        byNameJRadioButtonMenuItem.setMnemonic('n');
        byNameJRadioButtonMenuItem.setSelected(true);
        byNameJRadioButtonMenuItem.setText("By name");
        byNameJRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                byNameJRadioButtonMenuItemActionPerformed(evt);
            }
        });
        sortJMenu.add(byNameJRadioButtonMenuItem);

        byPopulationJRadioButtonMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        byPopulationJRadioButtonMenuItem.setMnemonic('B');
        byPopulationJRadioButtonMenuItem.setSelected(true);
        byPopulationJRadioButtonMenuItem.setText("By population");
        byPopulationJRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                byPopulationJRadioButtonMenuItemActionPerformed(evt);
            }
        });
        sortJMenu.add(byPopulationJRadioButtonMenuItem);

        vacationJMenuBar.add(sortJMenu);

        databaseJMenu.setMnemonic('t');
        databaseJMenu.setText("Database Management");

        addJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        addJMenuItem.setMnemonic('A');
        addJMenuItem.setText("Add");
        addJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addJMenuItemActionPerformed(evt);
            }
        });
        databaseJMenu.add(addJMenuItem);

        deleteJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        deleteJMenuItem.setMnemonic('D');
        deleteJMenuItem.setText("Delete");
        deleteJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteJMenuItemActionPerformed(evt);
            }
        });
        databaseJMenu.add(deleteJMenuItem);

        editJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        editJMenuItem.setMnemonic('E');
        editJMenuItem.setText("Edit");
        editJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editJMenuItemActionPerformed(evt);
            }
        });
        databaseJMenu.add(editJMenuItem);

        searchJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        searchJMenuItem.setMnemonic('r');
        searchJMenuItem.setText("Search");
        searchJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchJMenuItemActionPerformed(evt);
            }
        });
        databaseJMenu.add(searchJMenuItem);

        detailsJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        detailsJMenuItem.setText("Details");
        detailsJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detailsJMenuItemActionPerformed(evt);
            }
        });
        databaseJMenu.add(detailsJMenuItem);

        vacationJMenuBar.add(databaseJMenu);

        helpJMenu.setMnemonic('H');
        helpJMenu.setText("Help");

        aboutJMenuItem.setText("About");
        aboutJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutJMenuItemActionPerformed(evt);
            }
        });
        helpJMenu.add(aboutJMenuItem);

        vacationJMenuBar.add(helpJMenu);

        setJMenuBar(vacationJMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sitesJScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nameJLabel)
                                .addGap(15, 15, 15))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(86, 86, 86)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(countryJLabel)
                                    .addComponent(populationJLabel)
                                    .addComponent(capitalJLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(countryJTextField)
                            .addComponent(nameJTextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(capitalJTextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(populationJTextField, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(areaJLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(areaJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(iconJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(titlejLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 319, Short.MAX_VALUE)
                                            .addComponent(siteNumberJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(addJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(editJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(deleteJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(exitJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(siteNumberJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(titlejLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(iconJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nameJLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(countryJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(countryJLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(populationJLabel)
                            .addComponent(populationJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(capitalJLabel)
                            .addComponent(capitalJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(areaJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(areaJLabel)))
                    .addComponent(sitesJScrollPane))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exitJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       exitJButtonActionPerformed()
     * Description  Event handler to end the application.
     * @author      <i>Chiquita Zephaniah</i>
     * Date         10/31/2023    
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void exitJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitJButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitJButtonActionPerformed

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       addJButtonActionPerformed()
     * Description  Add a new Site. 
     * @param       evt java.awt.event.ActionEvent
     * @author      <i>Chiquita Zephaniah</i>
     * Date         10/31/2023
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void addJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addJButtonActionPerformed
        String message = "Site not added.";
        try
        {
            AddSite myAddForm = new AddSite(this, true);
            myAddForm.setVisible(true);
            int lastIndex = 0;
            //Get the new site
            Site newSite = myAddForm.getSite();
            
            if(newSite != null && !exists(newSite))
            {
                //add newSite to array list and DB
            String url = DB_URL;    //"jdbc:mysql://localhost/test";
            String user = USER;     //"root";
            String password = PASS; //"admin";
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            String query = "SELECT * FROM Sites";

            ResultSet rs = stmt.executeQuery(query);
            rs.last();      //move to last record
            
            lastIndex = sizeOfDB;   //rs.getInt("siteID");   //sites.size() + 1;
            newSite.setID(lastIndex +1);
            
            stmt.executeUpdate
            ("INSERT INTO Sites VALUES (" + newSite.getID() + ",'" +
                    newSite.getName() + "','" + newSite.getCountry() +
                    "'," + newSite.getPopulation() + ",'" + newSite.getCapital() +
                    "','" + newSite.getArea() + "')"
            );
            sites.add(newSite);
            //currentID = ++sizeOfDB;
            display(newSite);
            sizeOfDB++;
            con.close();
            }
        }
        catch(NullPointerException exp)
        {
            JOptionPane.showMessageDialog(null, message, "Input Error",
                    JOptionPane.WARNING_MESSAGE);            
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating to database", 
                    "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_addJButtonActionPerformed

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       editJButtonActionPerformed()
     * Description  Edit the displayed Site. 
     * @param       evt java.awt.event.ActionEvent
     * @author      <i>Chiquita Zephaniah</i>
     * Date         10/31/2023
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void editJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editJButtonActionPerformed

    String message = "Site not edited.";
    try {
        currentID = Integer.parseInt(siteNumberJLabel.getText());
        mySite = searchSite(currentID);

        // Use the same Add form for editing
        AddSite myEditForm = new AddSite(mySite);
        myEditForm.setVisible(true);

        Site editSite = myEditForm.getSite();
        editSite.setID(currentID);

        if (editSite != null) {
            // Update edited Site to DB
            mySite.setID(currentID);
            mySite.setName(editSite.getName());
            mySite.setCountry(editSite.getCountry());
            mySite.setPopulation(editSite.getPopulation());
            mySite.setCapital(editSite.getCapital());
            mySite.setArea(editSite.getArea());

            // Make connection to Sites DB
            String url = DB_URL;    //"jdbc:mysql://localhost/test";
            String user = USER;     //"root";
            String password = PASS; //"admin";

            Connection conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);

            // Set query to update record
            String query = "UPDATE Sites SET name = '" + mySite.getName() + "', " +
                           "country = '" + mySite.getCountry() + "', " +
                           "population = " + mySite.getPopulation() + ", " +
                           "capital = '" + mySite.getCapital() + "', " +
                           "area = " + mySite.getArea() + " WHERE siteID = " + mySite.getID();

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);

            // Commit changes and close resources
            conn.commit();
            stmt.close();
            conn.setAutoCommit(true);
            conn.close();

            display(mySite);
        } else {
            JOptionPane.showMessageDialog(null, message,
                    "Error!", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException exp) {
        exp.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error: " + exp.getMessage(),
                "Error!", JOptionPane.ERROR_MESSAGE);
    }

    }//GEN-LAST:event_editJButtonActionPerformed

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       deleteJButtonActionPerformed()
     * Description  Delete the displayed Site. 
     * @param       evt java.awt.event.ActionEvent
     * @author      <i>Chiquita Zephaniah</i>
     * Date         10/31/2023
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void deleteJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteJButtonActionPerformed
        //Get the selected site
        int index = Integer.parseInt(siteNumberJLabel.getText());    //currentID;
        Site siteToDelete = searchSite(index);
        //Determine if the user still wants to delete the site
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete "
            + siteToDelete.getName()
            + "?", "Delete site", JOptionPane.YES_NO_OPTION);
        if(result == JOptionPane.YES_OPTION)
        {
            //remove the site from the DB && update DB
            try
            {
            //Make connection to Create Sites  DB
            String url = DB_URL;    //"jdbc:mysql://localhost/test";
            String user = USER;     //"root";
            String password = PASS; //"admin";
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            // Set the Site information
            String query = "DELETE FROM Sites WHERE siteID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, index);
            // execute the preparedstatement
            pstmt.execute();
            query = "SELECT * FROM Sites";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            index = rs.getInt("siteID");
            
            display(searchSite(index));   //display first record
            conn.close();
            }
            catch(SQLException exp)
            {
                exp.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error deleting.",
                        "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_deleteJButtonActionPerformed

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       printFormJMenuItemActionPerformed()
     * Description  Event handler to print the form as a GUI. Calls the
     *              PrintUtilities class printComponent method.
     * @author      <i>Chiquita Zephaniah</i>
     * Date         10/31/2023
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void printFormJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printFormJMenuItemActionPerformed
        PrintUtilities.printComponent(this);
    }//GEN-LAST:event_printFormJMenuItemActionPerformed

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       exitJMenuItemActionPerformed()
     * Description  Event handler to end the application. Calls the quitJButton
     *              doClick event handler,
     * @author      <i>Chiquita Zephaniah</i>
     * Date         10/31/2023    
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void exitJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitJMenuItemActionPerformed
        exitJButton.doClick();
    }//GEN-LAST:event_exitJMenuItemActionPerformed

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       aboutJMenuItemActionPerformed()
     * Description  Create an About form and show it. 
     * @param       evt java.awt.event.KeyEvent
     * @author      <i>Chiquita Zephaniah</i>
     * Date         10/31/2023
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void aboutJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutJMenuItemActionPerformed
        About aboutWindow = new About(this, true);
        aboutWindow.setVisible(true);
    }//GEN-LAST:event_aboutJMenuItemActionPerformed

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       addJMenuItemActionPerformed()
     * Description  Add a new Site. 
     * @param       evt java.awt.event.ActionEvent
     * @author      <i>Chiquita Zephaniah</i>
     * Date         10/31/2023
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void addJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addJMenuItemActionPerformed
        addJButton.doClick();
    }//GEN-LAST:event_addJMenuItemActionPerformed

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       deleteJMenuItemActionPerformed()
     * Description  Delete the displayed Site. 
     * @param       evt java.awt.event.ActionEvent
     * @author      <i>Chiquita Zephaniah</i>
     * Date         10/31/2023
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void deleteJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteJMenuItemActionPerformed
        deleteJButton.doClick();
    }//GEN-LAST:event_deleteJMenuItemActionPerformed

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       editJMenuItemActionPerformed()
     * Description  Edit the displayed Site. 
     * @param       evt java.awt.event.ActionEvent
     * @author      <i>Chiquita Zephaniah</i>
     * Date         10/31/2023
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void editJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editJMenuItemActionPerformed
        editJButton.doClick();
    }//GEN-LAST:event_editJMenuItemActionPerformed
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       newJMenuItemActionPerformed
     * Description  Event handler to chose a separate file for a site. Also
     *              calls readFromFile and createSite methods.
     * @param       evt java.awt.event.KeyEvent
     * @author      <i>Niko Culevski</i>
     *</pre>
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void newJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newJMenuItemActionPerformed
        try
        {
            JFileChooser chooser = new JFileChooser("src/Sites");
            //Filter only txt files
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Txt Files", "txt");
            chooser.setFileFilter(filter);
            int choice = chooser.showOpenDialog(null);
            if (choice == JFileChooser.APPROVE_OPTION)
            {
                sites.clear();
                sitesJList.removeAll();
                File chosenFile = chooser.getSelectedFile();
                String file = "src/Sites/" + chosenFile.getName();
                
                //need to update fileName to save changes in correct file-- cannot be final
                file = file;
                System.out.println("file = " + file);
                readFromTextFile(file);
                displaySite();

            }
            else
            {
               JOptionPane.showMessageDialog(null, "Cannot find file",
                    "File Input Error", JOptionPane.WARNING_MESSAGE); 
            }
        }
        catch(java.lang.IllegalArgumentException exp)
        {
            JOptionPane.showMessageDialog(null, "File is not in correct format.",
                    "File Input Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_newJMenuItemActionPerformed
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       printJMenuItemActionPerformed()
     * Description  Print form as GUI using PrintUtilities helper class
     * @author      <i>Chiquita Zephaniah</i>
     * Date         4/5/2021
     * History Log  7/18/2018, 4/3/2020
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void printJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printJMenuItemActionPerformed
        // get value from selected site from jList
        int index = sitesJList.getSelectedIndex();
        // This component will be used to display the information about the selected site in the printed area (Paper).
        JTextArea printSite = new JTextArea();
        if(index >= 0)
        {
            try 
            {
                Site temp = new Site(sites.get(index));
                String output = "Name: " + temp.getName() + "\n" +
                        "Country: " + temp.getCountry() +
                        "Population: " + temp.getPopulation() + " million \n" +
                        "Capital: " + temp.getCapital()+ "%\n" +
                        "Area: " + temp.getArea()+ " square miles";
                printSite.setText(output);
                printSite.print();
            }
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, "Sites not Printed",
                "Print Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_printJMenuItemActionPerformed
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method        sitesJListValueChanged()
    * Description   Show Sites data based on selected index
    *               name
    * @param        evt java.swing.event.ActionEvent
    * @author       <i>Chiquita Zephaniah</i>
    * Date          10/31/2023  
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void sitesJListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_sitesJListValueChanged
        int index = (sitesJList.getSelectedIndex());
        if (index >= 0)
            showSiteData(index);
    }//GEN-LAST:event_sitesJListValueChanged
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method        byNameJRadioButtonMenuItemActionPerformed()
    * Description   Event handler for sorting Site by name
    *               name
    * @param        evt ActionEvent
    * @author       <i>Chiquita Zephaniah</i>
    * Date          10/29/2023  
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void byNameJRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_byNameJRadioButtonMenuItemActionPerformed
        displaySite();
    }//GEN-LAST:event_byNameJRadioButtonMenuItemActionPerformed
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method        byNameJRadioButtonMenuItemActionPerformed()
    * Description   Event handler for sorting Site by population
    *               name
    * @param        evt ActionEvent
    * @author       <i>Chiquita Zephaniah</i>
    * Date          10/29/2023  
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void byPopulationJRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_byPopulationJRadioButtonMenuItemActionPerformed
        displaySite();
    }//GEN-LAST:event_byPopulationJRadioButtonMenuItemActionPerformed
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method        detailJMenuItemActionPerformed()
    * Description   Event handler for detailJMenuItem
    *               name
    * @param        evt java.awt.event.ActionEvent
    * @see          java.awt.event.ActionEvent
    * @author       <i>Chiquita Zephaniah</i>
    * Date          10/31/2023  
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void detailsJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detailsJMenuItemActionPerformed
        int index = sitesJList.getSelectedIndex();
        if (index >= 0) {
            Site site = sites.get(index);
            SiteDetails siteDetailJDialog = new SiteDetails(site);
            siteDetailJDialog.setVisible(true);
        }
    }//GEN-LAST:event_detailsJMenuItemActionPerformed
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method        searchJMenuItemActionPerformed()
    * Description   Event handler for searchJMenuItem
    *               name
    * @param        evt java.awt.event.ActionEvent
    * @see          java.awt.event.ActionEvent
    * @author       <i>Chiquita Zephaniah</i>
    * Date          10/31/2023  
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void searchJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchJMenuItemActionPerformed
        String siteName = JOptionPane.showInputDialog("Enter name of Site");
        searchSite(siteName);
    }//GEN-LAST:event_searchJMenuItemActionPerformed
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       linearSearch()
     * Description  search for Sites linearly by name and highlight if found
     * @param       siteArray String[]
     * @param       siteName String
     * @author      <i>Niko Culevski</i>
     * Date         4/5/2021
     * History Log  7/18/2018, 5/7/2020
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private int linearSearch(String[] siteArray, String siteName)
    {
        int index = -1;
        boolean found = false;
        for (int i = 0; i < siteArray.length && !found; i++)
        {
            if(siteArray[i].toLowerCase().contains(siteName.toLowerCase()))
            {
                index = i;
                found = true;
            }
        }
        return index;
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       SearchSite()
     * Description  search for Sites by name and highlight if found
     * @param       siteName String
     * @author      <i>Niko Culevski</i>
     * Date         4/5/2021
     * History Log  7/18/2018, 5/7/2020
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void searchSite(String siteName)
    {
        if ((siteName != null) && (siteName.length() > 0))
        {
            byNameJRadioButtonMenuItem.setSelected(true);
            displaySite();
            
            String[] siteArray = new String[sites.size()];
            for (int i = 0; i < siteArray.length; i++)
                siteArray[i] = sites.get(i).getName().toLowerCase();
            
            int index = linearSearch(siteArray, siteName.toLowerCase());
            
            if (index < 0)
            {
                JOptionPane.showMessageDialog(null, "Site " + siteName +
                        "not found", "Search Result", JOptionPane.WARNING_MESSAGE);
                sitesJList.setSelectedIndex(0);
            }
            else
                sitesJList.setSelectedIndex(index);
        }
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       exists()
     * Description  Check if parameter-given person exists in the DB. 
     * @param       mySite Site
     * @author      <i>Niko Culevski</i>
     * Date         4/5/2021
     * History Log  7/18/2018, 5/7/2020
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private boolean exists(Site mySite)
    {
        boolean found = false;
        try
        {
            
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error in exists.", 
                    "Error!", JOptionPane.ERROR_MESSAGE);
        }
        return found;
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *<pre>
     * Method       main()
     * Description  Displays splash screen and the main Sites DB GUI form.
     * @param       args are the command line strings
     * @author      <i>Chiquita Zephaniah</i>
     * Date         10/31/2023
     *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public static void main(String args[]) {
        // Show splash screen
        Splash mySplash = new Splash(4000);     // duration = 5 seconds
        mySplash.showSplash();                  // show splash screen   
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SitesDB_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SitesDB_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SitesDB_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SitesDB_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SitesDB_GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutJMenuItem;
    private javax.swing.JButton addJButton;
    private javax.swing.JMenuItem addJMenuItem;
    private javax.swing.JLabel areaJLabel;
    private javax.swing.JTextField areaJTextField;
    private javax.swing.JRadioButtonMenuItem byNameJRadioButtonMenuItem;
    private javax.swing.JRadioButtonMenuItem byPopulationJRadioButtonMenuItem;
    private javax.swing.JLabel capitalJLabel;
    private javax.swing.JTextField capitalJTextField;
    private javax.swing.JLabel countryJLabel;
    private javax.swing.JTextField countryJTextField;
    private javax.swing.JMenu databaseJMenu;
    private javax.swing.JButton deleteJButton;
    private javax.swing.JMenuItem deleteJMenuItem;
    private javax.swing.JMenuItem detailsJMenuItem;
    private javax.swing.JButton editJButton;
    private javax.swing.JMenuItem editJMenuItem;
    private javax.swing.JButton exitJButton;
    private javax.swing.JMenuItem exitJMenuItem;
    private javax.swing.JMenu fileJMenu;
    private javax.swing.JMenu helpJMenu;
    private javax.swing.JLabel iconJLabel;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JLabel nameJLabel;
    private javax.swing.JTextField nameJTextField;
    private javax.swing.JMenuItem newJMenuItem;
    private javax.swing.JLabel populationJLabel;
    private javax.swing.JTextField populationJTextField;
    private javax.swing.JMenuItem printFormJMenuItem;
    private javax.swing.JMenuItem printJMenuItem;
    private javax.swing.JMenuItem searchJMenuItem;
    private javax.swing.JLabel siteNumberJLabel;
    private javax.swing.JList<String> sitesJList;
    private javax.swing.JScrollPane sitesJScrollPane;
    private javax.swing.JMenu sortJMenu;
    private javax.swing.JLabel titlejLabel;
    private javax.swing.JMenuBar vacationJMenuBar;
    // End of variables declaration//GEN-END:variables
}
