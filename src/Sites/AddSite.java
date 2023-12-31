package Sites;

import java.awt.Color;
import javax.swing.JOptionPane;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*<pre>
 * Class        AddSite.java
 * Description  A class used to both add and edit a Site into the DB.
 * Date         10/31/2023
 * @author	<i>Chiquita Zephaniah</i>
 *</pre>
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class AddSite extends javax.swing.JDialog {
    private Site mySite = null;
    private int id;
    private String name;
    private String country;
    private float population;
    private String capital;
    private float area;
    private final Color white = Color.WHITE;    // Default background color for input textfield
    private final Color pink = Color.PINK;      // Background color for bad input textfield
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Constructor  AddSite()--default constructor
     * Description  Create an instance of the GUI form and sets icon image.
     *              Used for Add Site.
     * Date         10/31/2023
     * @author      <i>Chiquita Zephaniah</i>
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    public AddSite()
    {
        initComponents();
        // Center the form
        this.setLocationRelativeTo(null);        
        // Set the default button
        this.getRootPane().setDefaultButton(addJButton);   
        // Center the form
        addJButton.setText("Edit");
        // Set the modal to true
        setModal(true);        
    }
    
        /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Constructor  AddSite()--overloaded constructor
     * Description  Create an instance of the GUI form and sets icon image.
     *              Used for Edit Site.
     * Date         10/31/2023
     * @author      <i>Chiquita Zephaniah</i>
     * @param       anotherSite Site
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public AddSite(Site anotherSite)
    {
        this();     //call default constructor to cuild GUI
        mySite = anotherSite;
        nameJTextField.setText(anotherSite.getName());
        countryJTextField.setText(anotherSite.getCountry());
        populationJTextField.setText(String.valueOf(anotherSite.getPopulation()));
        capitalJTextField.setText(anotherSite.getCapital());
        areaJTextField.setText(String.valueOf(anotherSite.getArea()));
        titleJLabel.setText("Edit Site");        
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Constructor  AddSite()--overloaded constructor
     * Description  Create an instance of the GUI form and sets icon image.
     * Date         10/31/2023
     * @author      <i>Chiquita Zephaniah</i>
     * @param       parent Frame
     * @param       modal  boolean
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public AddSite(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        // Center the form
        this.setLocationRelativeTo(null);        
        // Set the default button
        this.getRootPane().setDefaultButton(addJButton); 
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * <pre>
     * Method:      getSite()
     * Description: Returns the site added or edited.
     * Date:        5/12/16
     * @author      Chiquita Zephaniah
     * @return      Site
     *</pre>
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public Site getSite()
    {
        return mySite;
    }    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameJLabel = new javax.swing.JLabel();
        titleJLabel = new javax.swing.JLabel();
        iconJLabel = new javax.swing.JLabel();
        nameJTextField = new javax.swing.JTextField();
        countryJLabel = new javax.swing.JLabel();
        countryJTextField = new javax.swing.JTextField();
        populationJLabel = new javax.swing.JLabel();
        populationJTextField = new javax.swing.JTextField();
        capitalJTextField = new javax.swing.JTextField();
        capitalJLabel = new javax.swing.JLabel();
        areaJTextField = new javax.swing.JTextField();
        areaJLabel = new javax.swing.JLabel();
        addJButton = new javax.swing.JButton();
        quitJButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        nameJLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        nameJLabel.setText("Name of Site :");

        titleJLabel.setFont(new java.awt.Font("Tempus Sans ITC", 2, 36)); // NOI18N
        titleJLabel.setForeground(new java.awt.Color(0, 102, 51));
        titleJLabel.setText("Add Site");

        iconJLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/vacationsites.jpg"))); // NOI18N

        countryJLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        countryJLabel.setText("Country :");

        populationJLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        populationJLabel.setText("Population :");

        capitalJLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        capitalJLabel.setText("Capital :");

        areaJLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        areaJLabel.setText("Area :");

        addJButton.setBackground(new java.awt.Color(204, 255, 204));
        addJButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        addJButton.setMnemonic('A');
        addJButton.setText("Add");
        addJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addJButtonActionPerformed(evt);
            }
        });

        quitJButton.setBackground(new java.awt.Color(204, 255, 204));
        quitJButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        quitJButton.setMnemonic('Q');
        quitJButton.setText("Quit");
        quitJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitJButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(iconJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(titleJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(addJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(quitJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(63, 63, 63)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(countryJLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(countryJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(nameJLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(nameJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(populationJLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(populationJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(capitalJLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(capitalJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(areaJLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(areaJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(titleJLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(iconJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameJLabel)
                    .addComponent(nameJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(countryJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(countryJLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(populationJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(populationJLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(capitalJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(capitalJLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(areaJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(areaJLabel))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quitJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       addJButtonActionPerformed()
     * Description  Add new Site.
     * Date         10/31/2023
     * @param       evt java.awt.event.ActionEvent
     * @author      <i>Chiquita Zephaniah</i>
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void addJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addJButtonActionPerformed
       String message = "Error!";
       try
       {
           name = nameJTextField.getText();
           if(!Validation.isValidName(name) || !Character.isUpperCase(name.charAt(0)))
           {
               message = "Please enter a valid name for a site.";
               nameJTextField.requestFocus();
               throw new IllegalArgumentException();
           }
           country = countryJTextField.getText();
           if(!Validation.isValidName(country) || !Character.isUpperCase(country.charAt(0)))
           {
               message = "Please enter a valid country";
               countryJTextField.requestFocus();
               throw new IllegalArgumentException();
           }
           population = Float.parseFloat(populationJTextField.getText());
           if(!Validation.isNumeric(populationJTextField.getText()) || Float.parseFloat(populationJTextField.getText()) < 0)
           {
               message = "Please enter a valid population";
               populationJTextField.requestFocus();
               throw new IllegalArgumentException();
           }
           capital = capitalJTextField.getText();
           if(!Validation.isValidName(capital) || !Character.isUpperCase(capital.charAt(0)))
           {
               message = "Please enter a valid capital for the site";
               capitalJTextField.requestFocus();
               throw new IllegalArgumentException();
           }
           area = Float.parseFloat(areaJTextField.getText());
           if(!Validation.isNumeric(areaJTextField.getText()) || Float.parseFloat(areaJTextField.getText()) <= 0)
           {
               message = "Please enter a valid area";
               areaJTextField.requestFocus();
               throw new IllegalArgumentException();
           }
           mySite = new Site(0, name, country, population, capital, area);
           
           this.dispose();  
       }
       catch(IllegalArgumentException exp)
       {
           JOptionPane.showMessageDialog(null, message, "Invalid Input", JOptionPane.ERROR_MESSAGE);
       }
    }//GEN-LAST:event_addJButtonActionPerformed

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       quitJButtonActionPerformed()
     * Description  Dispose the Add form.
     * Date         10/31/2023
     * @param       evt java.awt.event.ActionEvent
     * @author      <i>Chiquita Zephaniah</i>
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void quitJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitJButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_quitJButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addJButton;
    private javax.swing.JLabel areaJLabel;
    private javax.swing.JTextField areaJTextField;
    private javax.swing.JLabel capitalJLabel;
    private javax.swing.JTextField capitalJTextField;
    private javax.swing.JLabel countryJLabel;
    private javax.swing.JTextField countryJTextField;
    private javax.swing.JLabel iconJLabel;
    private javax.swing.JLabel nameJLabel;
    private javax.swing.JTextField nameJTextField;
    private javax.swing.JLabel populationJLabel;
    private javax.swing.JTextField populationJTextField;
    private javax.swing.JButton quitJButton;
    private javax.swing.JLabel titleJLabel;
    // End of variables declaration//GEN-END:variables
}
