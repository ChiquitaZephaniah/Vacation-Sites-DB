package Sites;

import java.awt.Image;
import java.io.File;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File         SiteDetail.java
 * Description  A class used to display Site details
 * Date         10/31/2023
 * @author	<i>Chiquita Zephaniah</i>
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class SiteDetails extends javax.swing.JDialog {

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre> 
     * Constructor  JDialog to allow to select modal or modeless form
     * Description  Create the form as designed, center it, set the icon, 
     *              closeJButton as default.
     * Date         10/31/2023
     * @author      <i>Chiquita Zephaniah</i>
     * @param       site site
     *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public SiteDetails(Site site) 
    {
        initComponents();
        setLocationRelativeTo(null);
        this.getRootPane().setDefaultButton(closejButton);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("src/Images/vacationimage.jpg"));
        // Display site's information
        this.setTitle("Details of " + site.getName());

        String infoText = "Site: " + site.getName()
                        + "\nCountry: " + site.getCountry()
                        + "\nPopulation: " + site.getPopulation() + " million "
                        + "\nCapital: " + site.getCapital()
                        + "\nArea: " + site.getArea() + " square miles ";

        infojTextArea.setText(infoText);

        // Set and scale the picture
        String imageFileName = "src/Images/" + site.getName() + ".jpg";
        set_and_scaleImage(imageFileName, imagejLabel, "src/Images/vacationsites.jpg");
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * Method       set_and_scaleImage()
     * Description  Scales the image to fit within a JLabel (without stretching
     *              the JLabeL) and sets it into the JLabel
     * Date         10/10/2023
     * History Log  10/10/2023
     * @param       img_file String
     * @param       label JLabel
     * @see         java.awt.Image
     * @see         javax.swing.JLabel
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void set_and_scaleImage(String img_file, JLabel label, String defaultImageFile) {
        File imageFile = new File(img_file);

        if (imageFile.exists() && !imageFile.isDirectory()) {
            ImageIcon icon = new ImageIcon(img_file);
            Image img = icon.getImage();
            Image imgScale = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(imgScale);
            label.setIcon(scaledIcon);
        } else 
        {
            // The image file doesn't exist, use the default image
            ImageIcon defaultIcon = new ImageIcon(defaultImageFile);
            label.setIcon(defaultIcon);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imagejLabel = new javax.swing.JLabel();
        closejButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        infojTextArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        imagejLabel.setText("jLabel1");
        imagejLabel.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                imagejLabelComponentAdded(evt);
            }
        });

        closejButton.setBackground(new java.awt.Color(204, 255, 204));
        closejButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        closejButton.setMnemonic('C');
        closejButton.setText("Close");
        closejButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closejButtonActionPerformed(evt);
            }
        });

        infojTextArea.setColumns(20);
        infojTextArea.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        infojTextArea.setRows(5);
        jScrollPane1.setViewportView(infojTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(156, 156, 156)
                        .addComponent(closejButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(imagejLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(imagejLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(closejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void imagejLabelComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_imagejLabelComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_imagejLabelComponentAdded

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       closejButtonActionPerformed
     * Description  Closes the SiteDetails form only.
     * Date         10/31/2023
     * @author      <i>Chiquita Zephaniah</i>
     * @param       evt java.awt.event.ActionEvent
     *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void closejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closejButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closejButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closejButton;
    private javax.swing.JLabel imagejLabel;
    private javax.swing.JTextArea infojTextArea;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}