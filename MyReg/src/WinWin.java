import java.awt.EventQueue;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.GridLayout;

public class WinWin {

	private JFrame frame;
	Matrix beta;
	static List<String> colNames = new ArrayList<>();

	/**
	 * @wbp.nonvisual location=117,479
	 */

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinWin window = new WinWin();
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 * @throws NoSquareException
	 */
	public WinWin() throws NoSquareException, IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 * @throws NoSquareException
	 */
	private void initialize() throws NoSquareException, IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 451, 580);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblNewLabel = new JLabel("Title is here");
		lblNewLabel.setFont(new Font("whatever", Font.BOLD, 30));
		panel.add(lblNewLabel);

		JTextArea textArea = new JTextArea();
		textArea.setRows(30);
		JScrollPane scroll = new JScrollPane(textArea);
		frame.getContentPane().add(scroll, BorderLayout.SOUTH);

		JLabel label = new JLabel("");
		panel.add(label);

		JButton btnNewButton = new JButton("Εύρεση συνάρτησης παλινδρόμησης");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel.add(btnNewButton);

		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					JFileChooser fileChooser = new JFileChooser();
					frame.getContentPane().add(fileChooser, BorderLayout.NORTH);
					createProg(fileChooser, textArea);
				} catch (NoSquareException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		JButton btnLastButton = new JButton("Πρόβλεψη");
		panel.add(btnLastButton);

		btnLastButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					JFileChooser fileChooser = new JFileChooser();
					frame.getContentPane().add(fileChooser, BorderLayout.NORTH);
					createProg2(fileChooser, textArea);
				} catch (NoSquareException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	private Matrix createProg(JFileChooser chooser, JTextArea textArea) throws NoSquareException, IOException {
		textArea.setText("");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV", "csv");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String nameChoosed = chooser.getSelectedFile().getName();
			String pathChoosed = chooser.getSelectedFile().getPath();
			chooser.removeAll();
			OutputStream os = new TextAreaOutputStream(textArea);
			PrintStream ps = new PrintStream(os, true, "UTF-8");
			System.setOut(ps);
			System.setErr(ps);
			System.out.println("=====================================================");
			Date currentDate = new Date();
			System.out.println("|| Curent Date and Time - " + currentDate.toString());

			System.out.println("|| You chose to open this file: " + nameChoosed);
			System.out.println("=====================================================");
			String fName = pathChoosed;
			String thisLine;

			FileInputStream fis = new FileInputStream(fName);
			DataInputStream myInput = new DataInputStream(fis);

			List<String[]> lines = new ArrayList<String[]>();
			while ((thisLine = myInput.readLine()) != null) {
				lines.add(thisLine.split(";"));
			}

			// convert our list to a String array.
			String[][] array = new String[lines.size()][0];
			lines.toArray(array);

			Double[] percentiles = new Double[] { 12.706, 4.303, 3.182, 2.776, 2.571, 2.447, 2.365, 2.306, 2.262, 2.228,
					2.201, 2.179, 2.160, 2.145, 2.131, 2.120, 2.110, 2.101, 2.093, 2.086, 2.080, 2.074, 2.069, 2.064,
					2.060, 2.056, 2.052, 2.048, 2.045, 1.960 };

			Boolean repeat = false;
			int repCounter = 0;
			String repCols = "";

			colNames.clear();
			for (int j = 1; j < array[0].length + 1; j++) {
				colNames.add("X" + j);
			}

			do {
				if (repeat) {
					repCounter++;
					System.out.println("******************!!! REPEAT !!!*********************");
					System.out.println("Repeat no. " + repCounter + ". Removed columns index: " + repCols);
					System.out.println("=====================================================");
				}
				repeat = false;
				double[][] eX = new double[array.length][array[0].length - 1];
				double[][] eY = new double[array.length][1];
				Double sumY = (double) 0;
				int lastCol = array[0].length - 1;

				for (int i = 0; i < array.length; i++) {
					Double sumRow = (double) 0;
					for (int j = 0; j < lastCol; j++) {
						eX[i][j] = Double.parseDouble(array[i][j]);
					}
					eY[i][0] = Double.parseDouble(array[i][lastCol]);
					sumY += eY[i][0];
				}

				final Matrix X = new Matrix(eX);
				final Matrix Y = new Matrix(eY);
				// final Matrix X = new Matrix(new
				// double[][]{{1,1},{1,2},{1,3},{1,4},{2,1},{2,2},{2,3},{2,4},{3,1},{3,2},{3,3},{3,4}});
				// final Matrix Y = new Matrix(new
				// double[][]{{1},{1},{1},{-1},{1},{1},{-1},{-1},{1},{-1},{-1},{-1}});
				final MultiLinear ml = new MultiLinear(X, Y);
				beta = ml.calculate();

				// Yi hat
				Double[] ei_hat = new Double[array.length];
				Double[] ei_hat2 = new Double[array.length];
				Double ei_hatSUM = (double) 0;
				Double ei_hat2SUM = (double) 0;
				for (int i = 0; i < array.length; i++) {
					Double sumRow = (double) 0;
					sumRow += beta.getValueAt(0, 0);
					for (int j = 0; j < lastCol; j++) {
						eX[i][j] = Double.parseDouble(array[i][j]);
						sumRow += eX[i][j] * beta.getValueAt(j + 1, 0);
					}
					eY[i][0] = Double.parseDouble(array[i][lastCol]);
					ei_hat[i] = eY[i][0] - sumRow;
					ei_hatSUM += ei_hat[i];
					ei_hat2[i] = Math.pow(ei_hat[i], 2);
					ei_hat2SUM += ei_hat2[i];
					// System.out.println("ei_hat="+ei_hat2[i]);
				}
				// System.out.println("|| ei_hatSUM=" + ei_hatSUM);
				// System.out.println("|| ei_hat2SUM=" + ei_hat2SUM);
				double s2 = ei_hat2SUM / (array.length - lastCol - 1);
				// System.out.println("|| s2=Msres=" + s2);// =Msres

				// ymeso
				double ymeso = sumY / array.length;
				// System.out.println("|| ymeso=" + ymeso);

				Double sumR = (double) 0;
				Double[] yi_ymeso = new Double[array.length];
				for (int i = 0; i < array.length; i++) {
					yi_ymeso[i] = Math.pow(eY[i][0] - ymeso, 2);
					// System.out.println("yi_ymeso["+i+"]="+yi_ymeso[i]);
					sumR += yi_ymeso[i];
				}
				// Print r2
				double r2 = 1 - ei_hat2SUM / sumR;
				// System.out.println("=====================================================");
				System.out.println("|| r2=" + new DecimalFormat("##.##").format(r2));

				// Print tstat
				Double[] tstat = new Double[beta.getNrows()];
				tstat = ml.mitrou(s2, beta.getNrows());
				for (int i = 1; i < tstat.length; i++) {
					System.out.println("|| tstat[" + i + "]=" + new DecimalFormat("##.##").format(tstat[i]));
				}

				// PRINT
				printY(Y, X, beta, true);

				// Check
				Double myVal = null;
				if (percentiles.length >= array.length) {
					myVal = percentiles[array.length - 1];
				} else {
					myVal = percentiles[percentiles.length - 1];
				}
				for (int i = tstat.length - 2; i > 0; i--) {
					if (tstat[i] < myVal && tstat[i] > -myVal) {
						repeat = true;
						array = removeCol(array, i - 1);
						colNames.remove(i - 1);
						repCols += i + " ";
					}
				}
			} while (repeat);
		}
		return beta;
	}

	private int createProg2(JFileChooser chooser, JTextArea textArea) throws NoSquareException, IOException {
		textArea.setText("");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV", "csv");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String nameChoosed = chooser.getSelectedFile().getName();
			String pathChoosed = chooser.getSelectedFile().getPath();
			chooser.removeAll();

			OutputStream os = new TextAreaOutputStream(textArea);
			PrintStream ps = new PrintStream(os, true, "UTF-8");
			System.setOut(ps);
			System.setErr(ps);
			System.out.println("=====================================================");
			Date currentDate = new Date();
			System.out.println("|| Curent Date and Time - " + currentDate.toString());
			System.out.println("|| You chose to open this file: " + nameChoosed);
			System.out.println("=====================================================");
			String fName = pathChoosed;
			String thisLine;

			FileInputStream fis = new FileInputStream(fName);
			DataInputStream myInput = new DataInputStream(fis);

			List<String[]> lines = new ArrayList<String[]>();
			while ((thisLine = myInput.readLine()) != null) {
				lines.add(thisLine.split(";"));
			}

			// convert our list to a String array.
			String[][] array = new String[lines.size()][0];
			lines.toArray(array);

			double[][] eX = new double[array.length][array[0].length];

			for (int i = 0; i < array.length; i++) {
				for (int j = 0; j < array[0].length; j++) {
					eX[i][j] = Double.parseDouble(array[i][j]);
				}
			}
			final Matrix X = new Matrix(eX);

			// Πάρε από το colNames, Αφαίρεσε το Χ
			String[] newCol = new String[array.length];
			for (int i = 0; i < array.length; i++) {
				double predictedY = beta.getValueAt(0, 0);
				for (int j = 1; j < beta.getNrows(); j++) {
					int realJ = Integer.parseInt(colNames.get(j - 1).substring(1)) - 1;// Removes the 'X'
					predictedY += beta.getValueAt(j, 0) * X.getValueAt(i, realJ);
				}
				String newCell = new DecimalFormat("##.##").format(predictedY);
				newCol[i] = newCell;
				System.out.println("Πρόβλεψη " + i + " -> " + newCol[i]);
			}

			// Add new (prediction) column
			String[][] arrayNew = new String[array.length][(array[0].length) + 1];
			for (int r = 0; r < arrayNew.length; r++) {
				for (int c = 0; c < array[0].length; c++) {
					arrayNew[r][c] = array[r][c];
				}
				arrayNew[r][array[0].length] = newCol[r];
			}

			// Write to .csv
			FileWriter outfile = new FileWriter("predict.csv");
			for (int x = 0; x < arrayNew.length; x++) {
				String content = "";
				for (int y = 0; y < arrayNew[0].length; y++) {
					content += arrayNew[x][y] + ";";
				}
				outfile.append(content + "\r\n");
			}
			outfile.flush();

			// Show message
			JOptionPane.showMessageDialog(frame, "<html><b><u>T</u>wo</b><br>lines</html>", "information", JOptionPane.INFORMATION_MESSAGE);
		}
		return returnVal;
	}

	private static void printY(final Matrix Y, final Matrix X, final Matrix beta, final boolean bias)
			throws FileNotFoundException {
		String funcs = "";
		System.out.println("=====================================================");
		System.out.println("|| Συντελεστές:");
		for (int j = 0; j < beta.getNrows(); j++) {
			System.out.println("|| " + new DecimalFormat("##.##").format(beta.getValueAt(j, 0)));
		}

		System.out.println("=====================================================");
		System.out.println("|| Τιμή παρατήρησης -> Τιμή πρόβλεψης");
		if (bias) {
			for (int i = 0; i < Y.getNrows(); i++) {
				double predictedY = beta.getValueAt(0, 0);
				for (int j = 1; j < beta.getNrows(); j++) {
					predictedY += beta.getValueAt(j, 0) * X.getValueAt(i, j - 1);
				}
				System.out.println("|| " + Y.getValueAt(i, 0) + " -> " + new DecimalFormat("##.##").format(predictedY));
			}
		}
		System.out.println("=====================================================");
		for (int j = 1; j < beta.getNrows(); j++) {
			funcs += " + " + String.valueOf(new DecimalFormat("##.##").format(beta.getValueAt(j, 0))) + "*"
					+ colNames.get(j - 1);
		}
		System.out.println("Η συνάρτηση είναι: "
				+ String.valueOf(new DecimalFormat("##.##").format(beta.getValueAt(0, 0))) + funcs);
		System.out.println("=====================================================");
	}

	private static String[][] removeCol(String[][] array, int colRemove) {
		int row = array.length;
		int col = array[0].length;

		String[][] newArray = new String[row][col - 1];

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < colRemove; j++) {
				newArray[i][j] = array[i][j];
			}

			for (int j = colRemove; j < col - 1; j++) {
				newArray[i][j] = array[i][j + 1];
			}

		}

		return newArray;
	}

}
