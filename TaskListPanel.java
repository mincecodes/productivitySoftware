// general classes used for formatting
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.FlowLayout;

// these two classes are used for the lists of items and then dynamic stack
import java.util.ArrayList;

// main components used and their event listeners after them
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

// these are for the saving of the task list
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;
import java.util.InputMismatchException;

/**
 * This class defines the JPanel which shows the editable taskList. This class is composed of many 
 * Java swing components, as well as a TaskTablePanel object and Tasks objects. It inherits from JPanel.
 * This class adds components to the gridLayout and uses buttons to manipulate that grid.
 * 
 * @author Mince Codes
 * @version 01-18-2024
 */

public class TaskListPanel extends JPanel {
    private ArrayList<Tasks> taskList = new ArrayList<>();
    // dynamic stack for deleted tasks
    private DynamicStack<Tasks> deletedTaskStack = new DynamicStack<>();
    
    // defining the variables for the main components the user can manipulate
    private JButton addTaskButton = new JButton("Add new task");
    private JButton undoButton = new JButton("Undo Delete");
    private JButton clearButton = new JButton("Clear");
    private JButton saveButton = new JButton("Save tasks");
    private JSlider gridSizeSlider = new JSlider(10, 20, 10);
    
    // defining the gridLayout for the table
    private TaskTablePanel taskTable = new TaskTablePanel();
    
    // defining the arraylists which hold each item in the table
    private ArrayList<JTextField> taskNames = new ArrayList<>();
    private ArrayList<JTextField> dueDates = new ArrayList<>();
    private ArrayList<JComboBox> statusSelection = new ArrayList<>();
    private ArrayList<JComboBox> prioritySelection = new ArrayList<>();
    
    // this is the name of the text for the file that the tasks are saved to
    private String textFileName = "taskList.txt";
    
    /**
     * No-argument constructor of this class. This constructor defines the basic functioning/layout
     * of the various objects in this class, as well as load in any tasks from previous saves.
     * 
     */
    public TaskListPanel() {
        // Creating the event listener for the buttons
        ButtonEventListener buttonListener = new ButtonEventListener();
        
        // these two colors will be used in the defining the look for a few objects
        Color backgroundColor = new Color(232, 238, 252);
        Color buttonColor = new Color(101, 129, 156);
        
        // adds the JSlider to its changeListener
        ChangeListener sliderEventListener = new SliderEventListener();
        gridSizeSlider.addChangeListener(sliderEventListener);
        
        // creating the JPanel container which holds the grid and buttons with a flowlayout.
        JPanel flowContainer = new JPanel();
        flowContainer.setLayout(new FlowLayout( ));
        
        // setting the names of each of the columns of table
        JLabel task = new JLabel("Task", JLabel.CENTER);
        JLabel dueDate = new JLabel("Due", JLabel.CENTER);
        JLabel status = new JLabel("Status", JLabel.CENTER);
        JLabel priority = new JLabel("Priority", JLabel.CENTER);
        
        // these lines set up the task JLabel
        task.setFont(new Font("Calibri", Font.PLAIN, 24));
        task.setToolTipText("Press \"Enter\" to save the name of your task.");
        
        /* this sets the preferred size, which impacts all gridlayout items, as each have the same 
         * size. This is so the JLabels are not completely flush against each other. */
        dueDate.setPreferredSize(new Dimension(100, 50));
        dueDate.setFont(new Font("Calibri", Font.PLAIN, 24));
        dueDate.setToolTipText("Press \"Enter\" to save the due date for your task.");
        
        status.setFont(new Font("Calibri", Font.PLAIN, 24));
        status.setToolTipText("Select the most appropriate status from the drop-down menu.");
        
        priority.setFont(new Font("Calibri", Font.PLAIN, 24));
        priority.setToolTipText("Select the most appropriate priority from the drop-down menu.");
        
        // These three lines set the background colors of three JPanelsL this one, the table, and the container
        this.setBackground(backgroundColor);
        taskTable.setBackground(backgroundColor);
        flowContainer.setBackground(backgroundColor);
        
        // adding each button to its ActionEventListener
        addTaskButton.addActionListener(buttonListener);
        undoButton.addActionListener(buttonListener);
        saveButton.addActionListener(buttonListener);
        clearButton.addActionListener(buttonListener);
        
        // this is setting up the look of each button.
        addTaskButton.setBackground(buttonColor);
        addTaskButton.setForeground(Color.WHITE);
        
        undoButton.setBackground(buttonColor);
        undoButton.setForeground(Color.WHITE);
        
        saveButton.setBackground(buttonColor);
        saveButton.setForeground(Color.WHITE);
        
        clearButton.setBackground(buttonColor);
        clearButton.setForeground(Color.WHITE);
        
        // this is the instruction for the gridSizeSlider()
        gridSizeSlider.setToolTipText("Adjust the size of the grid.");
        
        // adding these initial labels as the first row of the grid.
        taskTable.add(task);
        taskTable.add(dueDate);
        taskTable.add(status);
        taskTable.add(priority);
        
        // calls the helper method to load the table from the previous save.
        loadPreviousTable();
        
        // this adds the tasktable and all buttons to the flowContainer JPanel
        flowContainer.add(taskTable);
        flowContainer.add(addTaskButton);
        flowContainer.add(undoButton);
        flowContainer.add(saveButton);
        flowContainer.add(clearButton);
        flowContainer.add(gridSizeSlider);
        
        // the container is then added to this panel.
        add(flowContainer);
    }
    
    /**
     * This private method loads the table from the previous save of the task list. It uses Scanner
     * to read the file that is used to save the tasks from the tasklist. Depending on the line number,
     * it will be a different section to save to.
     */
    private void loadPreviousTable() {
        int lineNumber = 0;
        Tasks newTask = null;
        
        try {
            Scanner taskInput = new Scanner(new File( textFileName ));
            // iterates through the whole file
            while(taskInput.hasNext()) {
                try {
                    // if the lineNumber is divisible by 4, it's the task name
                    if (lineNumber % 4 == 0) {
                        // creates a new task to add this input to
                        newTask = new Tasks();
                        newTask.setTaskName(taskInput.nextLine());
                    }
                    // if the lineNumber - 1 is divisible by 4, it's the task due date
                    else if ((lineNumber - 1) % 4 == 0) {
                        newTask.setDueDate(taskInput.nextLine());
                    }
                    // if the lineNumber - 2 is divisible by 4, it's the completion status
                    else if ((lineNumber - 2) % 4 == 0) {
                        // parses the string integer on this line
                        newTask.setCompletionStatus(Integer.parseInt(taskInput.nextLine()));
                    }
                    // if the lineNumber - 3 is divisible by 4, it's the priority status
                    else if ((lineNumber - 3) % 4 == 0) {
                        // parses the string integer on this line
                        newTask.setPriorityRank(Integer.parseInt(taskInput.nextLine()));
                        
                        // since it's the end of task info, it is added to the list and a new row is added
                        taskList.add(newTask);
                        addRowToTable();
                    }
                    lineNumber++;
                }
                catch (InputMismatchException inputMismatchException) {
                    // if there are any problems with the line, it'll set it to integer 0.
                    System.err.println("Bad integer on line " + lineNumber + ". Setting to 0 by default");
                    taskInput.nextLine();
                }
            }
            taskInput.close();
        }
        catch (IOException ioException) {
            System.err.println( "Java Exception: " + ioException);
            System.out.println( "Sorry, unable to reload list." );
        }
    }
    
    /**
     * This private method sets the colors of the JComboBox depending on which index the comboBox is at.
     * This method also will give the box different colors per index depending on its type (i.e. whether its a 
     * priority ranking or status selection box)
     */
    private void setComboBoxColour(JComboBox comboBox, int index, int type) {
        Color[] statusColorValues = {new Color(204, 204, 204), new Color(209, 130, 65), 
            new Color(89, 158, 113), new Color(138, 11, 13)};
        Color[] priorityColorValues = {new Color(204, 204, 204),new Color(138, 11, 13),
            new Color(209, 130, 65), new Color(62, 61, 94), new Color(108, 72, 140)};
        
        // not started option has a dark text colour.
        if (index == 0) {
            comboBox.setForeground(new Color(247, 249, 255));
        }
        else {
            comboBox.setForeground(Color.WHITE);
        }
        
        /* this decides which of the color lists is appropriate depending on which type of comboBox got 
         * passed into this method. */
        if (type == 1) {
            comboBox.setBackground(statusColorValues[index]);
        }
        else {
            comboBox.setBackground(priorityColorValues[index]);
        }
    }
    
    /**
     * This helper method adds one more row to the table. This is called whenever a new
     * task is added. It returns nothing.
     * 
     */
    private void addRowToTable() {
        String[] statusNames = { "Not Started", "In Progress", "Done", "Late", "Delete" };
        String[] priorityNames = { "Unranked", "High", "Medium", "Low", "Best Effort" };
        // This is a reference to the last task in the arraylist (i.e. the new task being added to the grid.)
        Tasks lastTask = taskList.get(taskList.size() - 1);
        ComboBoxStatusListener statusComboBoxListener = new ComboBoxStatusListener();
        ComboBoxPriorityListener priorityComboBoxListener = new ComboBoxPriorityListener();
        NameFieldEventListener nameFieldListener = new NameFieldEventListener();
        DateFieldEventListener dateFieldListener = new DateFieldEventListener();
        
        // creating the new components to be added to the table
        JTextField taskName = new JTextField();
        JTextField date = new JTextField();
        JComboBox<String> statusBox = new JComboBox<String>( statusNames );
        JComboBox<String> priorityBox = new JComboBox<String>( priorityNames );
        
        // this adds a row to the current gridlayout
        taskTable.addRows();
        
        // this code sets up the textName Jtextfield with font, size, the text, adds it to arrayList and table
        taskName.setFont(new Font("Calibri", Font.PLAIN, 16));
        taskName.setColumns(10);
        taskName.setText(lastTask.getTaskName());
        taskNames.add(taskName);
        taskTable.add(taskName);
        taskName.addActionListener(nameFieldListener);
        
        // this code sets up the date Jtextfield with font, size, the text, adds it to arrayList and table
        date.setFont(new Font("Calibri", Font.PLAIN, 20));
        date.setHorizontalAlignment(JTextField.CENTER);
        date.setText(lastTask.getDueDate());
        dueDates.add(date);
        taskTable.add(date);
        date.addActionListener(dateFieldListener);
        
        // this code sets up the status JComboBox with the index, color, adds it to arrayList and table
        statusBox.setSelectedIndex(lastTask.getCompletionStatus());
        setComboBoxColour(statusBox, statusBox.getSelectedIndex(), 1);
        statusSelection.add(statusBox);
        taskTable.add(statusBox);
        statusBox.addItemListener( statusComboBoxListener );
        
        // this code sets up the priority JComboBox with the index, color, adds it to arrayList and table
        priorityBox.setSelectedIndex(lastTask.getPriorityRank());
        setComboBoxColour(priorityBox, priorityBox.getSelectedIndex(), 2);
        prioritySelection.add(priorityBox);
        taskTable.add(priorityBox);
        priorityBox.addItemListener( priorityComboBoxListener );
        
        // revalidates and repaints the grid to include above changes
        taskTable.repaintGrid();
    }
    
    /**
     * This helper method removes a row from the table. It does this by removing the components in the
     * row at the specific index, then removing it on the task table.
     * 
     */
    private void removeRowFromTable(int index) {
        //goes through each arrayList and removes the component.
        taskTable.remove(taskNames.remove(index));
        taskTable.remove(dueDates.remove(index));
        taskTable.remove(statusSelection.remove(index));
        taskTable.remove(prioritySelection.remove(index));
        
        // this removes a row on the actual gridLayout
        taskTable.removeRow();
        
        // revalidates and repaints the grid to include above changes
        taskTable.repaintGrid();
    }
    
    /**
     * This helper method uses PrintWriter to write out on a text file all the information about the
     * current taskList and tasks in it, so it can be reloaded to be used whenever the program is run.
     * 
     */
    private void saveCurrentList() {
        try {
            // rewrites over the last saved list
            PrintWriter savedTaskList = new PrintWriter( textFileName );
            
            // iterates through the current tasks and adds the information of each to the file.
            for ( Tasks task : taskList) {
                savedTaskList.println( task.getTaskName() );
                savedTaskList.println( task.getDueDate() );
                savedTaskList.println( task.getCompletionStatus() );
                savedTaskList.println( task.getPriorityRank() );
            }
            savedTaskList.close();
        }
        catch ( IOException ioException ) {
            System.err.println( "Java Exception: " + ioException );
            System.out.println( "Sorry, error with saving." );
        }
    }
    
    /**
     * This inner class defines the action listener for each of the buttons at the side of the grid.
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
            // if the add button is pressed.
            if ( e.getSource() == addTaskButton ) {
                // a new task is added to the arrayList and a new row is added to the table.
                taskList.add(new Tasks());
                addRowToTable();
            }
            // if the taskStack is not empty and the undo button was pressed.
            else if ( !deletedTaskStack.isEmpty() && e.getSource() == undoButton ) {
                // the deletedTask will be added back to the arrayList and new row added to the table
                taskList.add(deletedTaskStack.pop());
                addRowToTable();
            }
            else if (e.getSource() == saveButton) {
                saveCurrentList();
            }
            // if the clear button is pressed.
            else if (e.getSource() == clearButton) {
                // iterates backwards through the task list, pushing each onto the deleted stack.
                for (int counter = taskList.size() - 1; counter >= 0; counter--) {
                    deletedTaskStack.push(taskList.remove(counter));
                    
                    // removes the rows from the table
                    removeRowFromTable(counter);
                    
                    // sets the slider value back to its lowest value.
                    gridSizeSlider.setValue(10);
                }
            }
        }
    }
    
    /**
     * This inner class defines the action listener for task name JTextFields. This is a seperate 
     * action listener from the buttons and the due date JTextFields so that there doesn't have to be
     * an iteration through each arraylist anytime a new action is performed.
     * 
     * This implements the ActionListener interface.
     */
    class NameFieldEventListener implements ActionListener {
        /**
         * This is the overriden abstract method from the ActionListener iterface. It runs whenever
         * a button is pressed.
         * 
         * @param e ActionEvent object which represents the event that just occurred.
         */
        @Override 
        public void actionPerformed( ActionEvent e ) {
            for (JTextField nameField : taskNames) {
                // if the current textField is the one with enter pressed.
                if (e.getSource() == nameField) {
                    // the name will be saved to the task object.
                    taskList.get(taskNames.indexOf(nameField)).setTaskName(nameField.getText());
                }
            }
        }
    }
    
    /**
     * This inner class defines the action listener for due date JTextFields. This is a seperate 
     * action listener from the buttons and the task name JTextFields so that there doesn't have to be
     * an iteration through each arraylist anytime a new action is performed.
     * 
     * This implements the ActionListener interface.
     */
    class DateFieldEventListener implements ActionListener {
        /**
         * This is the overriden abstract method from the ActionListener iterface. It runs whenever
         * a button is pressed.
         * 
         * @param e ActionEvent object which represents the event that just occurred.
         */
        @Override 
        public void actionPerformed( ActionEvent e ) {
            for (JTextField dateField : dueDates) {
                // if the current dateField is the one with enter pressed.
                if (e.getSource() == dateField) {
                    // the date will be saved to the task object.
                    taskList.get(dueDates.indexOf(dateField)).setDueDate(dateField.getText());
                }
            }
        }
    }
    
    /**
     * This inner class defines the item listener for status JComboBox. This is a seperate 
     * item listener from the other JComboBox arraylist so that there doesn't have to be
     * an iteration through each arraylist anytime a item state is changed.
     * 
     * This implements the ItemListener interface.
     */
    class ComboBoxStatusListener implements ItemListener {
        /**
         * This is the overriden abstract method from the ItemListener iterface. It runs whenever
         * a JComboBox selection is changed
         * 
         * @param e ItemEvent object which represents the event that just occurred.
         */
        @Override 
        public void itemStateChanged( ItemEvent  e ) {
            for (int counter = 0; counter < statusSelection.size(); counter++) {
                // sets reference to the current box
                JComboBox currentBox = statusSelection.get(counter);
                
                // if the currentBox is the source
                if (e.getSource() == currentBox) {
                    //gets the corresponding task
                    Tasks changedTask = taskList.get(counter);
                    
                    // if its the fourth index (delete), the current task and row will be deleted
                    if (currentBox.getSelectedIndex() == 4) {
                        deletedTaskStack.push(taskList.remove(counter));
                        removeRowFromTable(counter);    
                    }
                    else {
                        // saves the change to the task
                        changedTask.setCompletionStatus(currentBox.getSelectedIndex());
                        
                        // sets the right background color
                        setComboBoxColour(currentBox, currentBox.getSelectedIndex(), 1);
                    }
                }
            }
        }
    }
    
    /**
     * This inner class defines the item listener for the priority JComboBoxs. This is a seperate 
     * item listener from the other JComboBox arraylist so that there doesn't have to be
     * an iteration through each arraylist anytime a item state is changed.
     * 
     * This implements the ItemListener interface.
     */
    class ComboBoxPriorityListener implements ItemListener {
        /**
         * This is the overriden abstract method from the ItemListener iterface. It runs whenever
         * a JComboBox selection is changed
         * 
         * @param e ItemEvent object which represents the event that just occurred.
         */
        @Override 
        public void itemStateChanged( ItemEvent e ) {
            for (JComboBox currentBox : prioritySelection) {
                if (e.getSource() == currentBox) {
                    // gets reference to the related task
                    Tasks changedTask = taskList.get(prioritySelection.indexOf(currentBox));
                    
                    // changes the color accordingly
                    setComboBoxColour(currentBox, currentBox.getSelectedIndex(), 2);
                    
                    // saves the change on the task.
                    changedTask.setPriorityRank(currentBox.getSelectedIndex());
                }
            }
        }
    }
    
    /**
     * This inner class defines the change listener for the size JSlider.
     * This implements the ChangeListener interface.
     */
    class SliderEventListener implements ChangeListener {
        /**
         * This is the overriden abstract method from the ChangeListener iterface. It runs whenever
         * a JComboBox selection is changed
         * 
         * @param e ChangeEvent object which represents the event that just occurred.
         */
        @Override 
        public void stateChanged(ChangeEvent e) {
            // if the value is done adjusting and the taskNames arraylist is not empty
            if (!taskNames.isEmpty() && !gridSizeSlider.getValueIsAdjusting()) {
                // sets the columns of taskNames 0, because every gridlayout cell is the same width.
                taskNames.get(0).setColumns(gridSizeSlider.getValue());
                taskTable.repaintGrid();
            }
        }
    }
} 