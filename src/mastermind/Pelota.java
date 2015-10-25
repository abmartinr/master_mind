package mastermind;


import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Pelota extends JButton{

	Dimension d1 = new Dimension(10,10);
	Dimension d2 = new Dimension(40,40);
	
	private String color;
	
	public Pelota(int dim){
		setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder());
        setPreferredSize(d2);
        setContentAreaFilled(false);
	}
	public Pelota(String color, int dim){
		this.color=color;
		ImageIcon icono = new ImageIcon(getClass().getResource(color+".png"));
		setFocusPainted(false);
		setContentAreaFilled(false);
        setBorder(BorderFactory.createEmptyBorder());
		setIcon(icono);
		switch (dim) {
		case 0:
			setPreferredSize(d1);
			setDisabledIcon(icono);
			setEnabled(false);
			break;
		case 1:
			setPreferredSize(d2);
			break;
		}
		
	}
	
	public String getColor(){
		
		return color;
		
	}
	public void setColor(String color){
		this.color = color;
	}
	
	public void setIcon(String color){
		this.color = color;
		ImageIcon s_icono = new ImageIcon(getClass().getResource(color+".png"));
		this.setIcon(s_icono);
		this.setDisabledIcon(s_icono);
	}
	
	
}

