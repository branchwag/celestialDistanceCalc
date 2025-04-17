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
	// instance vars
	private JLabel titleLabel;
	private JTextArea infoTextArea;
	private JTextArea calcTextArea;
	private JComboBox<String> methodComboBox;
	private CelestialObject currentObject;

	// CONSTRUCTOR to set up the info panel
	public ObjectInfoPanel() {
		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		titleLabel = new JLabel("Select an object in the sky");
		titleLabel.setFont(new Font("Sans-Serif", Font.BOLD, 16));

		infoTextArea = new JTextArea(10, 30);
		infoTextArea.setEditable(false);
		infoTextArea.setLineWrap(true);
		infoTextArea.setWrapStyleWord(true);
		// a lil padding for the text
		infoTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		// fix to ensure there is no text cursor when you click into the box
		infoTextArea.setCaret(new javax.swing.text.DefaultCaret() {
			@Override
			public boolean isVisible() {
				return false;
			}
		});

		calcTextArea = new JTextArea(5, 30);
		calcTextArea.setEditable(false);
		calcTextArea.setLineWrap(true);
		calcTextArea.setWrapStyleWord(true);
		// adding some padding here as well for the text
		calcTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		// fix to ensure there is no text cursor when you click into the box
		calcTextArea.setCaret(new javax.swing.text.DefaultCaret() {
			@Override
			public boolean isVisible() {
				return false;
			}
		});

		// dropdown list
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

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(methodPanel, BorderLayout.NORTH);
		bottomPanel.add(calcTextArea, BorderLayout.CENTER);

		add(topPanel, BorderLayout.NORTH);
		add(infoTextArea, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	// function to update the panel info for selected object
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

		// update the avaliable calc methods
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

	// function to update the calc display based on method chosen
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
				calc.append(String.format("%.2f", star.calcParallaxDistance())).append(" light years");
			} else if (selectedMethod.equals("Spectroscopic Parallax Method")) {
				calc.append("Formula: 5 * log10(d) = m - M + 5, where d is distance in parsecs\n");
				calc.append("Calculation:\n");
				calc.append("m (apparent magnitude) = ")
						.append(String.format("%.2f", star.getApparentMagnitude()))
						.append("\n");
				calc.append("M (absolute magnitude) = ")
						.append(String.format("%.2f", star.getAbsoluteMagnitude()))
						.append("\n\n");

				double distanceParsecs = Math.pow(10,
						(star.getApparentMagnitude() - star.getAbsoluteMagnitude() + 5) / 5);
				calc.append("d = 10^((").append(String.format("%.2f", star.getApparentMagnitude()))
						.append(" - ");
				calc.append(String.format("%.2f", star.getAbsoluteMagnitude())).append(" + 5) / 5) = ");
				calc.append(String.format("%.2f", distanceParsecs)).append(" parsecs\n");

				double distanceLightYears = distanceParsecs * 3.26;
				calc.append(String.format("%.2f", distanceParsecs)).append(" parsecs x 3.26 = ");
				calc.append(String.format("%.2f", distanceLightYears)).append(" light years");
			}
		} else if (currentObject instanceof Galaxy) {
			Galaxy galaxy = (Galaxy) currentObject;

			calc.append("Formula: Distance = (c × z) / H₀, where c is speed of light, z is redshift, H₀ is Hubble constant\n");
			calc.append("Calculation:\n");
			calc.append("z (redshift) = ").append(String.format("%.6f", galaxy.getRedshift())).append("\n");
			calc.append("c (speed of light) = 299,792.458 km/s\n");
			calc.append("H₀ (Hubble constant) = 70 km/s/Mpc\n\n");

			double velocity = 299792.458 * galaxy.getRedshift();
			calc.append("Velocity = c × z = 299,792.458 × ")
					.append(String.format("%.6f", galaxy.getRedshift()));
			calc.append(" = ").append(String.format("%.2f", velocity)).append(" km/s\n");

			double distanceMpc = velocity / 70.0;
			calc.append("Distance = v / H₀ = ").append(String.format("%.2f", velocity)).append(" / 70 = ");
			calc.append(String.format("%.2f", distanceMpc)).append(
					" Mpc (Megaparsec - one million parsecs NOTE: one parsec is approx 3.26 million light years. Mpcs are used for expressing distances too large to be expressed in light-years or parsecs.)\n");

			calc.append(String.format("%.2f", distanceMpc)).append(" Mpc × 3.26 = ");
			calc.append(String.format("%.2f", galaxy.calcRedshiftDistance()))
					.append(" million light years");
		}

		calcTextArea.setText(calc.toString());
	}
}
