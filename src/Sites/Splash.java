package Sites;
import java.awt.*;
import javax.swing.*;
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *<pre>
 * Class        Splash.java
 * Description  A class representing the Splash screen used by the 
 *              SitesDB_GUI.java.
 * Project      Vacation Sites
 * Platform     jdk 21; NetBeans IDE 19
 * Date         10/31/2023     
 * </pre>
 * @author      <i>Chiquita Zephaniah</i>
 * @version 	%1% %5%
 * @see     	java.awt.Color
 * @see     	java.awt.Toolkit
 * @see     	javax.swing.BorderFactory
 *****************************************************************************/
public class Splash extends JWindow
{
    // duration is integer value in milliseconds for how long the Splash screen is visible
    private final int duration;
    JProgressBar loading = new JProgressBar();  //progress bar
    private int progress;
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Constructor  Splash()
     * Description  Sets duration to provided parameter.
     * @param       dur int
     * @author      <i>Chiquita Zephaniah</i>
     * Date         10/31/2023
     </pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/  
    public Splash(int dur)
    {
        duration = dur;
        UIManager.put("ProgressBar.selectionForeground", Color.gray.darker());
        loading = new JProgressBar(0, 100);
        loading.setStringPainted(true);
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       showSplash()
     * Description  A method to show a title screen in the center of the screen
     *              for the amount of time given in the constructor
     * @author      <i>Chiquita Zephaniah</i>
     * Date         10/31/2023
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/  
    public void showSplash()
    {
        JPanel content = (JPanel)getContentPane();
        content.setBackground(Color.lightGray);

        // Set the window's bounds, centering the window
        int width = 800;
        int height = 533;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width)/2;
        int y = (screen.height - height)/2;
        setBounds(x,y,width,height);

        // Build the splash screen
        JLabel label = new JLabel(new ImageIcon
            ("src/Images/vacationsplash.jpg"));
        JLabel copyrt = new JLabel
            ("Copyright Vacation Sites Inc., 2021, Vacation Sites", 
                        JLabel.CENTER);
        copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        content.add(label, BorderLayout.CENTER);
        content.add(loading, BorderLayout.SOUTH);
        Color border = new Color(50, 20, 20, 55);
        content.setBorder(BorderFactory.createLineBorder(border, 10));

        setVisible(true);
        // Wait a little while, maybe while loading resources
        try 
        {
            //Increment the progress bar's value to 100 starting from 0
            incProgress(20);
            Thread.sleep(duration);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        setVisible(false);
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       incProgress()
     * Description  Create inner class ProgressThread object, up, and start the
     *              thread
     * @param       amount int
     * @author      <i>Chiquita Zephaniah</i>
     * Date         10/31/2023
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void incProgress(int amount)
    {
        //Instantiate and start new thread
        ProgressThread up = new ProgressThread(amount);
        up.thread.start();
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Class        ProgressThread
     * Description  Nested class that handles the progress bar
     * @author      <i>Chiquita Zephaniah</i>
     * @param       amount int
     * Date         10/31/2023
     *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    class ProgressThread 
    {
        private int amount;
        /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        *<pre>
        * Constructor  ProgressThread()
        * Description  Sets amount to provided parameter.
        * @param       dur int
        * @author      <i>Chiquita Zephaniah</i>
        * Date         10/31/2023
        *</pre>
       *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
        public ProgressThread(int amount)
        {
            this.amount = amount;
        }

        private Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                {
                    //Increment the progress bar until it's value hits 100
                    while (progress < 100) 
                    {
                        progress++;
                        loading.setString(String.valueOf(progress) + "%");
                        try 
                        {
                            Thread.sleep(30);
                        } 
                        catch (InterruptedException ex) 
                        {

                        }
                        loading.setValue(progress);
                    }
                }
            }
        });
    }
}