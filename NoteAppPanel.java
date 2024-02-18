import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;

/**
 * This class defines the JPanel for the note-taking element of the productivity GUI. In this class,
 * a Borderlayout containing a gridLayout in the South and a JTextPane at the centre. This class 
 * uses JButtons for the user to create a new text file, open a text file, and save a text file.
 * 
 * @author Mince Codes
 * @version 01-18-2024
 */

public class NoteAppPanel extends JPanel {
    private JButton openButton = new JButton("Open");
    private JButton saveButton = new JButton("Save");
    private JButton newButton = new JButton("New");
    // sets the title for this file.
    private JLabel textTitle = new JLabel("Untitled.txt", JLabel.CENTER);
    private JTextPane textEditor = new JTextPane();
    private JFileChooser fileChooser = new JFileChooser();
    // the basic file created is untitled
    private File currentFile = new File("Untitled.txt");
    
    /**
     * This is the no-argument constructor for the NoteAppPanel. It sets up the general layout of the
     * panel and adds all items needed to action listeners
     */
    public NoteAppPanel() {
        ButtonEventListener buttonListener = new ButtonEventListener();
        
        // the buttons and text JLabel are stored in a gridLayout.
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1));
        
        // sets the layout for the button panel (top half of grid) so that they flow next to each other
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        
        // This sets the layout of the current panel to a borderLayout
        this.setLayout(new BorderLayout());
        
        // this makes it so the filechooser can only select files
        fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
        
        // setting the fonts and action listeners of various components
        textTitle.setFont(new Font("Calibri", Font.PLAIN, 22));
        textTitle.setOpaque(true);
        textTitle.setBackground(Color.WHITE);
        
        textEditor.setFont(new Font("Calibri", Font.PLAIN, 18));
        
        openButton.setBackground(Color.WHITE);
        openButton.addActionListener(buttonListener);
        
        saveButton.setBackground(Color.WHITE);
        saveButton.addActionListener(buttonListener);
        
        newButton.setBackground(Color.WHITE);
        newButton.addActionListener(buttonListener);
        
        // this adds the buttons to the flowlayout
        buttonPanel.add(newButton);
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
        
        // the buttonPanel and the JLabel are added to the gridlayout
        topPanel.add(buttonPanel);
        topPanel.add(textTitle);
        
        // gridlayout added to the north and textPane added to the south
        add(topPanel, BorderLayout.NORTH);
        add(textEditor, BorderLayout.CENTER);
    } 
    
    /**
     * This helper method is called when the "new" JButton is pressed. It sets a new location for
     * the text file and resets the text on the textPane.
     */
    private void newTextFile() {
        // starts with a message to the user
        JOptionPane.showMessageDialog( null, "Create your file! (include.txt at the end)" );
        
        // this result is saved so that the do while loop knows whether or not the user exited the chooser
        int result; 
        do {
            result = fileChooser.showSaveDialog( null );
            // If they canceled, the programs asks them to try again to not get an error with the filechooser afterwards
            if ( result == JFileChooser.CANCEL_OPTION ) {
                JOptionPane.showMessageDialog( null, "Please select a place to save.",
                                              "File Not Selected", JOptionPane.ERROR_MESSAGE );
            }
            // this is so the file that the user creates is FOR SURE a text file
            else if ( fileChooser.getSelectedFile().getName().lastIndexOf(".txt") == -1) {
                JOptionPane.showMessageDialog( null, "NOT A TEXT FILE. Must end in .txt");
            }
        } while (result == JFileChooser.CANCEL_OPTION || 
                 fileChooser.getSelectedFile().getName().lastIndexOf(".txt") == -1);
        
        // sets the currentFile to whatever the user selected and changes the text of textpanel and jlabel accordingly
        currentFile = fileChooser.getSelectedFile();
        textTitle.setText(currentFile.getName());
        textEditor.setText("");
    }
    
    /**
     * This helper method is called when the "open" JButton is pressed. It opens a new text file and loads
     * it into the JtextPane
     */
    private void openTextFile() {
        // I used scanner to read the opened file
        Scanner reader;
        JOptionPane.showMessageDialog( null, "Choose your text file." );
        int result; 
        
        // this do while loop does the same thing as the method prior.
        do {
            result = fileChooser.showOpenDialog( null );
            if ( result == JFileChooser.CANCEL_OPTION ) {
                JOptionPane.showMessageDialog( null, "Please select a file to open.",
                                              "File Not Selected", JOptionPane.ERROR_MESSAGE );
            }
            else if ( fileChooser.getSelectedFile().getName().lastIndexOf(".txt") == -1) {
                JOptionPane.showMessageDialog( null, "NOT A TEXT FILE. Select a .txt file");
            }
        } while (result == JFileChooser.CANCEL_OPTION || 
                 fileChooser.getSelectedFile().getName().lastIndexOf(".txt") == -1);
        
        // sets the file and the current text to the opened file
        currentFile = fileChooser.getSelectedFile();
        textTitle.setText(currentFile.getName());
        
        // try except statement to catch any IO Errors.
        try {
            // I create a string which all the text from the current text file will be added to.
            String text = "";
            reader = new Scanner(currentFile);
            
            // scanner reads from the file and adds it to the text variables with line breaks after each line
            while ( reader.hasNext() ) {
                text += reader.nextLine();
                text += "\n";
            }
            
            // the text of the texteditor is set to the new text from the file
            textEditor.setText(text);
            reader.close();
        }
        catch (IOException ioException) {
            JOptionPane.showMessageDialog(null, "Java Exception: " + ioException + ". Unable to open file.");
        }
    }
    
    /**
     * This helper method is called when the "save" JButton is pressed. It uses PrintWriter to
     * print all the text from the textPane onto the currentFile.
     */
    private void saveTextFile() {
        try {
            PrintWriter fileOutput = new PrintWriter(currentFile.getAbsolutePath());
            //adds all the text to the editor
            fileOutput.print(textEditor.getText());
            fileOutput.close();
            JOptionPane.showMessageDialog(null, "Saved!");
        }
        catch (IOException ioException) {
            JOptionPane.showMessageDialog(null, "Java Exception: " + ioException + ". Unable to save file.");
        }
    }
    
    /**
     * This inner class defines the action listener for each of the buttons at the top of the program.
     * This implements the ActionListener interface.
     * 
     */
    class ButtonEventListener implements ActionListener {
        /**
         * This is the overriden abstract method from the ActionListener iterface. It runs whenever
         * a button is pressed.
         * 
         * @param e ActionEvent object which represents the event that just occurred.
         */
        @Override 
        public void actionPerformed( ActionEvent e ) {
            if (e.getSource() == newButton) {
                // runs the helper method to get a new file
                newTextFile();
            }
            else if (e.getSource() == openButton) {
                // runs the helper method to open a file
                openTextFile();
            }
            else if (e.getSource() == saveButton) {
                // runs the helper method to save a file
                saveTextFile();
            }
        }
    }
} 