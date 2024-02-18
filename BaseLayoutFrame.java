import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 * @author Mince Codes
 * @version 01-17-2024
 */

public class BaseLayoutFrame extends JFrame {
    private JPanel centreOption = new JPanel();
    private JScrollPane taskScrollPane = new JScrollPane(new TaskListPanel());
    private JScrollPane noteScrollPane = new JScrollPane(new NoteAppPanel());
    private JButton toDoButton = new JButton("To Do List");
    private JButton noteButton = new JButton("Important Notes");
    
    public BaseLayoutFrame() {
        super( "Task List Page" );
        ButtonEventListener buttonListener = new ButtonEventListener();
        
        // I will add 2 buttons and a label to the WEST region of this JPanel 
        JPanel sideBarPanel = new JPanel();
        
        // needed to create gridlayout variable to set gap
        GridLayout sideBarLayout = new GridLayout(3, 1);
        // this sets the gap between each button and the JLabel
        sideBarLayout.setVgap(5);
        sideBarPanel.setLayout( sideBarLayout );
        
        // this is the flow container so the gridlayout doesn't take up all of the WEST side
        JPanel flowContainer = new JPanel();
        flowContainer.setLayout( new FlowLayout());
        
        // this sets the borderlayout for the whole frame, so I can change the centre one later on.
        centreOption.setLayout(new BorderLayout());
        
        // creates the JLabel with the name of the GUI
        JLabel titleLabel = new JLabel("<html>Welcome To Life <br/>Planner</html>");
        titleLabel.setFont(new Font("Calibri", Font.PLAIN, 26));
        titleLabel.setForeground(Color.WHITE);
        
        /* sets the preferred size not just for the label, but the buttons too, as all items in a gridLayout
         are the same size */
        titleLabel.setPreferredSize(new Dimension(200, 100));
        
        Color buttonColor = new Color(29, 47, 64);
        
        // this code is setting the look and action listener of this button
        toDoButton.setFont(new Font("Calibri", Font.PLAIN, 24));
        toDoButton.setBackground(buttonColor);
        toDoButton.setForeground(Color.WHITE);
        toDoButton.addActionListener(buttonListener);
        
        // this code is setting the look and action listener of this button
        noteButton.setFont(new Font("Calibri", Font.PLAIN, 24));
        noteButton.setBackground(buttonColor);
        noteButton.setForeground(Color.WHITE);
        noteButton.addActionListener(buttonListener);
        
        // this defines the scroll bar for the scroll pane that has the taskPanel
        JScrollBar rightScrollBar = taskScrollPane.getVerticalScrollBar();
        rightScrollBar.setUnitIncrement(10);
        
        JScrollBar bottomScrollBar = taskScrollPane.getHorizontalScrollBar();
        bottomScrollBar.setUnitIncrement(10);
        
        // this defines the scroll bar for the scroll pane that has the notePanel
        JScrollBar noteRightScrollBar = noteScrollPane.getVerticalScrollBar();
        noteRightScrollBar.setUnitIncrement(10);
        
        JScrollBar noteBottomScrollBar = noteScrollPane.getHorizontalScrollBar();
        noteBottomScrollBar.setUnitIncrement(10);
        
        // this sets the background of the sidebar
        Color backgroundColor = new Color(101, 129, 156);
        
        // this is so the entire bar has the same background color
        sideBarPanel.setBackground(backgroundColor);
        flowContainer.setBackground(backgroundColor);
        
        // adding each element to the grid layout
        sideBarPanel.add( titleLabel); 
        sideBarPanel.add( toDoButton ); 
        sideBarPanel.add( noteButton ); 
        
        // this container makes it so the grid doesn't take up the entirety of the west layout
        flowContainer.add(sideBarPanel);
        
        centreOption.add( flowContainer, BorderLayout.WEST ); 
        
        // the scrollpane holds all of the task panel
        centreOption.add(taskScrollPane, BorderLayout.CENTER);
        
        add(centreOption);
    } 
    
    /**
     * 
     * 
     */
    class ButtonEventListener implements ActionListener {
        @Override 
        public void actionPerformed( ActionEvent e ) {
            // changes the centre panel based on the button pressed
            if ( e.getSource() == toDoButton ) {
                // first the current panel must be removed
                centreOption.remove(noteScrollPane);
                
                // then the new panel must be added
                centreOption.add(taskScrollPane, BorderLayout.CENTER);
                
                /* repaints and revalidates both the borderlayout panel and the taskscrollpane
                 this is so there is no weird formatting/will switch panels automatically */
                taskScrollPane.repaint();
                taskScrollPane.validate();
                centreOption.repaint();
                centreOption.validate();
            }
            else if ( e.getSource() == noteButton) {
                centreOption.remove(taskScrollPane);
                
                centreOption.add(noteScrollPane, BorderLayout.CENTER);
                
                /* repaints and revalidates both the borderlayout panel and the noteScrollPane
                 this is so there is no weird formatting/will switch panels automatically */
                noteScrollPane.repaint();
                noteScrollPane.validate();
                centreOption.repaint();
                centreOption.validate();
            }
        }
    }
} 