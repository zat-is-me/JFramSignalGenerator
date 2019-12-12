/**
 * 
 */
/**
 * @author Tatek_Ahmed
 *
 */
package functionControlor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import chapman.graphics.JPlot2D;

public class GraphGenerator extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField w, a, p, v;
	private JLabel Lw, LA, Lp, Lv;
	private static double frq = 0;
	private static double amp = 0;
	private static double phs = 0;
	private static double vrt = 0;

	private static JButton Export;
	private JButton Generate;

	private static JCheckBox Sine;
	private JCheckBox Square;
	private JCheckBox Sawtooth;

	private static JPanel myTw;
	static JPlot2D graph;
	static JFrame myF;

	private static double[] x = new double[201];
	private static double[] y = new double[201];
	Font font = new Font("", Font.PLAIN, 18);
	private static int check = 0;
	boolean t;

	// <<<<<<<<<<<<<< Main Menu >>>>>>>>>>>>>>>>>
	public static void main(String s[]) {
		GraphGenerator pr = new GraphGenerator();
		GraphGenerator in = new GraphGenerator();

		myF = new JFrame("EEGR 409 Project Two");
		graph = new JPlot2D();
		
		// This for loop will initialized the value of x and y
		in.intial();
		pr.GuiPage();
		pr.signalPlot(x, y);

		myF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myF.setSize(1500, 1000);
		myF.add(myTw, BorderLayout.SOUTH);
		myF.getContentPane().add(graph, BorderLayout.CENTER);

		graph.setPlotType(JPlot2D.LINEAR);
		// graph.setLineColor(Color.green);
		// graph.setLineColor( Color.blue );
		graph.setLineWidth(5.0f);
		graph.setLineStyle(JPlot2D.LINESTYLE_SOLID);
		graph.setMarkerStyle(JPlot2D.MARKER_DIAMOND);
		// graph.setLineColor( Color.BLUE );
		graph.setMarkerStyle(JPlot2D.MARKER_DIAMOND);
		graph.setTitle("Plot of y(x)");
		graph.setXLabel("x axis");
		graph.setYLabel("y axis");
		graph.setGridState(JPlot2D.GRID_ON);

		if (check == 0)
			Export.setEnabled(false);

		// Visibility of window
		myF.setVisible(true);
	}

//<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
	public void intial() {
		for (int i = 0; i <= 2000; i += 10) {
			x[i / 10] = i;
			y[i / 10] = 0;
		}
	}

	public void GuiPage() {
		setLayout(new BorderLayout());
		myTw = new JPanel();
		myTw.setLayout(new BoxLayout(myTw, BoxLayout.Y_AXIS));
		add(myTw);

		// Creating Different windows
		JPanel wMenu = new JPanel();
		JPanel wGeEx = new JPanel();
		JPanel wArg = new JPanel();

		// Panel Background
		wMenu.setBackground(Color.ORANGE);
		wGeEx.setBackground(Color.RED);
		wArg.setBackground(Color.GREEN);
		// myTw.setBackground(Color.blue);

		// add to the big window
		myTw.add(wArg);
		myTw.add(wMenu);
		myTw.add(wGeEx);

		// Create Label
		Lw = new JLabel("Frequancy(Hz): ");
		LA = new JLabel(" Amplitude: ");
		Lp = new JLabel(" Phase Shift: ");
		Lv = new JLabel(" Vertical Shift: ");

		// Create Text
		w = new JTextField(4);
		a = new JTextField(4);
		p = new JTextField(4);
		v = new JTextField(4);

		w.setMaximumSize(w.getPreferredSize());
		a.setMaximumSize(a.getPreferredSize());
		p.setMaximumSize(p.getPreferredSize());
		v.setMaximumSize(v.getPreferredSize());

		// Create Signal Menu
		Sine = new JCheckBox("Sine");
		Square = new JCheckBox("Square");
		Sawtooth = new JCheckBox("Sawtooth");

		// Create Button
		Export = new JButton("Export     ");
		Generate = new JButton("Generate");

		// Changing Font Size
		Lw.setFont(font);
		LA.setFont(font);
		Lp.setFont(font);
		Lv.setFont(font);

		w.setFont(font);
		a.setFont(font);
		p.setFont(font);
		v.setFont(font);

		Export.setFont(font);
		Sine.setFont(font);
		Square.setFont(font);
		Sawtooth.setFont(font);
		Generate.setFont(font);

		// add to the window
		wMenu.add(Sine);
		wMenu.add(Square);
		wMenu.add(Sawtooth);
		wGeEx.add(Generate);
		wGeEx.add(Export);

		wArg.add(Lw);
		wArg.add(w);
		wArg.add(LA);
		wArg.add(a);
		wArg.add(Lp);
		wArg.add(p);
		wArg.add(Lv);
		wArg.add(v);

		Generate.addActionListener(new GenerateButton());
		Export.addActionListener(new ExportButton());
	}

// This method will take textFiel value and changed to double and validate the input
	public void inputs() {
		try {
			frq = Double.parseDouble(w.getText());
			try {
				amp = Double.parseDouble(a.getText());
				try {
					phs = Double.parseDouble(p.getText());
					try {
						vrt = Double.parseDouble(v.getText());
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Invalide Vertical Shift value", "Error Occured",
								JOptionPane.ERROR_MESSAGE);
						clearText(v);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Invalide Phase Shift value", "Error Occured",
							JOptionPane.ERROR_MESSAGE);
					clearText(p);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Invalide Amplitude value", "Error Occured",
						JOptionPane.ERROR_MESSAGE);
				clearText(a);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Invalide Frequancy value", "Error Occured", JOptionPane.ERROR_MESSAGE);
			clearText(w);
		}

	}

//<<<<<< This method will take textField argument to clear the invalid textField input <<<<<<<<<<<<<<<<<<		
	public void clearText(JTextField textField) {
		textField.setText(" ");
	}

//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public void signalPlot(double[] x, double[] y) {
		graph.setVisible(false);
		graph.addCurve(x, y);
		graph.setVisible(true);
	}

	/* <<<<<<<<<< sine >>>>>>>>>>>>>>>> */
	public void sine() {
		for (int i = 0; i <= 2000; i += 10) {
			double ph = Math.toRadians(phs);
			double B = ((0.1 * Math.PI) * frq) / 100;
			x[i / 10] = i;
			y[i / 10] = (amp * 0.5) * Math.sin((B * i) + ph) + vrt;
		}
		signalPlot(x, y);
	}

	/* <<<<<<<<<< square >>>>>>>>>>>>>>>> */
	public void square() {
		for (int i = 0; i <= 2000; i += 10) {
			double ph = Math.toRadians(phs);
			double B = ((2 * Math.PI) * frq) / 1000;

			double sp = Math.cos(B * i + ph);
			if (sp > 0) {
				y[i / 10] = amp * (0.5) + vrt;
				x[i / 10] = i;
			}
			double sn = Math.cos(B * i + ph);
			if (sn < 0) {
				y[i / 10] = -amp * (0.5) + vrt;
				x[i / 10] = i;
			}
		}
		signalPlot(x, y);
	}

	/* <<<<<<<<<< square >>>>>>>>>>>>>>>> */
	public void sawtooth() {
		for (int i = 0; i <= 2001; i += 10) {
			x[i / 10] = i;
			double ph = Math.toRadians(phs);
			double B = ((2 * Math.PI) * frq) / 2000;
			double s = Math.sin(B * i + ph);
			if (s >= 0) {
				y[i / 10] = amp * 0.5 + Math.cos(B * i + ph) + vrt;
			} else if (s <= 0) {
				y[i / 10] = amp * 0.5 - Math.cos(B * i + ph) + vrt;
			}
		}
		signalPlot(x, y);
	}

//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<				
	public void itemStateChanged(ActionEvent ex) {
		inputs();
		if (Sine.isSelected()) {
			sine();
		} else if (Square.isSelected()) {
			square();
		} else if (Sawtooth.isSelected()) {
			sawtooth();
		} else {
			intial();
		}
	}

// >>>>>>>>>>>>>>>> GenerateButton class <<<<<<<<<<<<<<<<<<<//
	class GenerateButton implements ActionListener {
		public void actionPerformed(ActionEvent Gene) {
			itemStateChanged(Gene);
			Export.setEnabled(true);
		}
	}

// >>>>>>>>>>>>>>>> File Exporter Method <<<<<<<<<<<<<<<<<<<//
	class ExportButton implements ActionListener {
		public void actionPerformed(ActionEvent exp) {
			try// try to write if it does not has error
			{
				PrintWriter witer = new PrintWriter("Signal.csv");
				for (int i = 0; i < 2000; i += 10) {
					witer.printf(x[i / 10] + ", " + y[i / 10]);
					witer.println();
				}
				witer.close();// close the file after writing
			} catch (Exception e) {// catch error when during writing and display;
				JOptionPane.showMessageDialog(null, "Same Thing Error on Exporting", "Message ",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}// -------> ------> Zmain --------> -------->
