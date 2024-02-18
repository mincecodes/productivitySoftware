import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;
import java.awt.GridLayout;

/**
 * This class defines the GridLayout JPanel which all the task's JTextFields and JComboBox's
 * will be added to. This class has methods for changing the length of the table and repainting it.
 * 
 * @author Mince Codes
 * @version 01-17-2024
 */

public class TaskTablePanel extends JPanel {
    private GridLayout gridTable = new GridLayout( 1, 4 );
    
    /**
     * No-argument constructor which sets the layout of this panel to gridLayout
     */
    public TaskTablePanel() {
        // Sets this layout as GridLayout and the space between rows
        this.setLayout(gridTable);
        gridTable.setVgap(3);
    } 
    
    /**
     * This method adds one more row to the table than what it was before. It takes no parameters
     * and returns no values.
     */
    public void addRows() {
        gridTable.setRows(gridTable.getRows() + 1);
    }
    
    /**
     * This method removes one row from the table than what it was before. It takes no parameters
     * and returns no values. There is sanity checking so that not too many rows are removed.
     */
    public void removeRow() {
        if (gridTable.getRows() > 1) {
            gridTable.setRows(gridTable.getRows() - 1);
        }
    }
    
    /**
     * This method is for repainting and revalidating the grid if it has been changed outside of this
     * class. This means that the other classes don't need graphics to call paintComponent and can easily 
     * both revalidate and repaint any changes that they have made to the GridLayout
     */
    public void repaintGrid() {
        revalidate();
        repaint();
    }
} 