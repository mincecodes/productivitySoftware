/**
 * This class defines the Task object that is used in the TaskListPanel to keep track of the 
 * various columns of information about the to do tasks. This class includes various methods
 * to get information about the task and manipulate parts of it.
 * 
 * @author Mince Codes
 * @version 01-17-2024
 */

public class Tasks {
    private String taskName = "";
    private String dueDate = "";
    private int completionStatus = 0;
    private int priorityRank = 0;
     
    /**
     * This no-argument constructor defines basically what the values of a task object is.
     */
    public Tasks() {
        this("Untitled", "  /  /    ", 0, 0);
    }
    
    /**
     * This parameterized constructor takes in 4 parameters for creating a task and uses the mutator 
     * methods to set the variables to these parameter values.
     * 
     * @param taskName A String object which represents the name of the task.
     * @param dueDate A String which has the date the task is due in it.
     * @param completionStatus An integer value representing the index of the JComboBox.
     * @param priorityRank An integer value representing the index of the JComboBox.
     */
    public Tasks(String taskName, String dueDate, int completionStatus, int priorityRank) {
        setTaskName(taskName);
        setDueDate(dueDate);
        setCompletionStatus(completionStatus);
        setPriorityRank(priorityRank);
    }
    
    /**
     * Mutator method for the taskName instance variable. This variable represents the 
     * name of the current task.
     * 
     * @param taskName The String object name of what the current task is.
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    
    /**
     * Mutator method for the dueDate instance variable. This variable represents the 
     * date that the current task is due.
     * 
     * @param dueDate The String object representation of what the due date is.
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    
    /**
     * Mutator method for the completionStatus instance variable. This variable represents the 
     * index of the current completion status. There is also sanity checking in this method.
     * 
     * @param completionStatus The integer index of the current completion status.
     */
    public void setCompletionStatus(int completionStatus) {
        if (completionStatus >= 0 && completionStatus < 4) {
            this.completionStatus = completionStatus;
        }
        else {
            this.completionStatus = 1;
        }
    }
    
    /**
     * Mutator method for the priorityRank instance variable. This variable represents the 
     * index of the current priority ranking. There is also sanity checking in this method.
     * 
     * @param priorityRank The integer index of the current priority rank.
     */
    public void setPriorityRank(int priorityRank) {
        if (priorityRank >= 0 && priorityRank < 5) {
            this.priorityRank = priorityRank;
        }
        else {
            this.priorityRank = 1;
        }
    }
    
    /**
     * Accessor method for the taskName instance variable. This variable represents the name
     * of the current task.
     * 
     * @return String - The String name of the task.
     */
    public String getTaskName() {
        return taskName;
    }
    
    /**
     * Accessor method for the dueDate instance variable. This variable represents the date
     * the current task is due.
     * 
     * @return String - The String version of the due date.
     */
    public String getDueDate() {
        return dueDate;
    }
    
    /**
     * Accessor method for the completionStatus instance variable. This variable represents the 
     * index of the status on the JComboBox.
     * 
     * @return int - The index of what the completion status is.
     */
    public int getCompletionStatus() {
        return completionStatus;
    }
    
    /**
     * Accessor method for the priorityRank instance variable. This variable represents the 
     * index of the priority on the JComboBox.
     * 
     * @return int - The index of what the priority rank is.
     */
    public int getPriorityRank() {
        return priorityRank;
    }
    
    /**
     * This toString() method returns a String representation of the task.
     * 
     * @return String - A string representation of the task.
     */
    public String toString() {
        return "This task's name is " + taskName + ". With a due date of " + dueDate + ".";
    }
}