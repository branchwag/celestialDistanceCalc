package com.celestialdistance.view;

import com.celestialdistance.model.CelestialObject;
import com.celestialdistance.model.Star;
import com.celestialdistance.model.Galaxy;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//class for panel displaying info about selected celestial object
public class ObjectInfoPanel extends JPanel {
	//instance vars
	private JLabel titleLabel;
	private JTextArea infoTextArea;
	private JTextArea calcTextArea;
	private JComboBox<String> methodComboBox;
	private CelestialObject currentObject;

	//CONSTRUCTOR to set up the info panel
	public ObjectInfoPanel() {
		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		titleLabel = new JLabel("Select an object in the sky");
		titleLabel.setFont(new Font("Sans-Serif", Font.BOLD, 16));

		infoTextArea = new JTextArea(10, 30);
		infoTextArea.setEditable(false);
		infoTextArea.setLineWrap(true);
		infoTextArea.setWrapStyleWord(true);

		calcTextArea = new JTextArea(5, 30);
		calcTextArea.setEditable(false);
		calcTextArea.setLineWrap(true);
		calcTextArea.setWrapStyleWord(true);

		//dropdown list
		methodComboBox = new JComboBox<>();
		methodComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateCalcDisplay();
			}
		});

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		topPanel.add(titleLabel);
		topPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		JPanel methodPanel = new JPanel();
		methodPanel.add(new JLabel("Calculation Method:"));
		methodPanel.add(methodComboBox);

		add(topPanel, BorderLayout.NORTH);
		add(infoTextArea, BorderLayout.CENTER);
		add(methodPanel, BorderLayout.SOUTH);
		add(calcTextArea, BorderLayout.SOUTH);
	}

	//function to update the panel info for selected object
	public void updateObjectInfo(CelestialObject object) {
		this.currentObject = object;

		if (object == null) {
			titleLabel.setText("Select an object in the sky");
			infoTextArea.setText("");
			calcTextArea.setText("");
			methodComboBox.removeAllItems();
			return;
		}

		titleLabel.setText(object.getName());
		infoTextArea.setText(object.getInfo());

		//update the avaliable calc methods
		methodComboBox.removeAllItems();

		if (object instanceof Star) {
			Star star = (Star) object;

			if (star.getParallax() > 0) {
				methodComboBox.addItem("Parallax Method");
			}

			methodComboBox.addItem("Spectroscopic Parallax Method");
		} else if (object instanceof Galaxy) {
			methodComboBox.addItem("Redshift Method");
		}

		updateCalcDisplay();
	}

	//function to update the calc display based on method chosen
	private void updateCalcDisplay() {
		if (currentObject == null) {
			calcTextArea.setText("");
			return;
		}

		String selectedMethod = (String) methodComboBox.getSelectedItem();
		if (selectedMethod == null) {
			return;
		}

		StringBuilder calc = new StringBuilder();
		calc.append("Calculation Method: ").append(selectedMethod).append("\n\n");

        if (currentObject instanceof Star) {
            Star star = (Star) currentObject;
            
            if (selectedMethod.equals("Parallax Method") && star.getParallax() > 0) {
                calc.append("Formula: Distance (parsecs) = 1 / parallax (arcseconds)\n");
                calc.append("Calculation:\n");
                calc.append("1 / ").append(String.format("%.4f", star.getParallax())).append(" = ");
                calc.append(String.format("%.2f", star.calcSpecDistance())).append(" light years");
            }
        } else if (currentObject instanceof Galaxy) {
            Galaxy galaxy = (Galaxy) currentObject;
            
            calc.append("Formula: Distance = (c × z) / H₀, where c is speed of light, z is redshift, H₀ is Hubble constant\n");
            calc.append("Calculation:\n");
            calc.append("z (redshift) = ").append(String.format("%.6f", galaxy.getRedshift())).append("\n");
            calc.append("c (speed of light) = 299,792.458 km/s\n");
            calc.append("H₀ (Hubble constant) = 70 km/s/Mpc\n\n");
            
            double velocity = 299792.458 * galaxy.getRedshift();
            calc.append("Velocity = c × z = 299,792.458 × ").append(String.format("%.6f", galaxy.getRedshift()));
            calc.append(" = ").append(String.format("%.2f", velocity)).append(" km/s\n");
            
            double distanceMpc = velocity / 70.0;
            calc.append("Distance = v / H₀ = ").append(String.format("%.2f", velocity)).append(" / 70 = ");
            calc.append(String.format("%.2f", distanceMpc)).append(" Mpc\n");
            
            calc.append(String.format("%.2f", distanceMpc)).append(" Mpc × 3.26 = ");
            calc.append(String.format("%.2f", galaxy.calcRedshiftDistance())).append(" million light years");
        }

	calcTextArea.setText(calc.toString());
	}
}



