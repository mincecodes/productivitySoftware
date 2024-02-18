import javax.swing.JFrame;

/**
 * This is the class with the main code to run the productivity app.
 * 
 * @author Mince Codes
 * @version 01-17-2024
 */

public class Main {
    public static void main(String[] args) {
        BaseLayoutFrame appWindow = new BaseLayoutFrame();
        
        appWindow.setSize( 1400, 700 );
        appWindow.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        appWindow.setVisible( true );
    }
}