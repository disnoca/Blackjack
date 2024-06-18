package game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TableBackground extends JComponent {

	private Image image;
	
	
    public TableBackground(Image image) {
        this.image = image;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
	
}
