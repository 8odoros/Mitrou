import java.awt.EventQueue;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.UIUtils;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.Image;

public class WinWin {

	private JFrame frame;
	Matrix beta;
	static List<String> colNames = new ArrayList<>();
	static String popup = "";

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
		System.setOut(new PrintStream(new FileOutputStream("log.txt",true)));
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 1030, 580);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 5, 0, 0));

		JTextArea textArea = new JTextArea();
		textArea.setRows(30);
		JScrollPane scroll = new JScrollPane(textArea);
		frame.getContentPane().add(scroll, BorderLayout.SOUTH);
		
		JPanel panel_1 = new JPanel();
		scroll.setColumnHeaderView(panel_1);

		JButton btnNewButton = new JButton("Εύρεση συνάρτησης παλινδρόμησης");
	    Image img5 =  ImageIO.read(new File("icons/025-folder.png"));
	    img5 = img5.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
	    btnNewButton.setIcon(new ImageIcon(img5));
		panel.add(btnNewButton);

		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					JFileChooser fileChooser = new JFileChooser();
					frame.getContentPane().add(fileChooser, BorderLayout.NORTH);
					createProg(fileChooser, textArea, panel_1);
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
	    Image img4 =  ImageIO.read(new File("icons/019-shuffle.png"));
	    img4 = img4.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
	    btnLastButton.setIcon(new ImageIcon(img4));
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
		
		JButton btnStatsButton = new JButton("Στατιστικά");
	    Image img3 =  ImageIO.read(new File("icons/026-bar-chart.png"));
	    img3 = img3.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
	    btnStatsButton.setIcon(new ImageIcon(img3));
		panel.add(btnStatsButton);
		btnStatsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String pop = "<html><h1>Στατιστικά</h1><b>"+popup+"</b></html>";
				JOptionPane.showMessageDialog(frame, pop, "information", JOptionPane.INFORMATION_MESSAGE);				
			}
		});
		

		JButton btnHelp = new JButton("Βοήθεια");
	    Image img2 =  ImageIO.read(new File("icons/067-chat.png"));
	    img2 = img2.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
	    btnHelp.setIcon(new ImageIcon(img2));
		panel.add(btnHelp);
		btnHelp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(frame, "<html><b><u>HELP</u>me</b><br>please</html>", "help", JOptionPane.INFORMATION_MESSAGE);				
			}
		});
		

		JButton btnExut = new JButton("Έξοδος");
	    Image img =  ImageIO.read(new File("icons/036-flag.png"));
	    img = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
	    btnExut.setIcon(new ImageIcon(img));
	    panel.add(btnExut);	    
		btnExut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
		        System.exit(0);		
			}
		});
		
		JLabel lblNewLabel = new JLabel("Multiple Regression Analysis");
		frame.getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 20));
	}

	private Matrix createProg(JFileChooser chooser, JTextArea textArea, JPanel chartPanel) throws NoSquareException, IOException {
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
					popup ="";					
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
				if(r2>0.80) popup=popup.concat("<tr><td>GOOD DATA</td><tr>");

				// Print tstat
				Double[] tstat = new Double[beta.getNrows()];
				tstat = ml.mitrou(s2, beta.getNrows());
				for (int i = 1; i < tstat.length; i++) {
					System.out.println("|| tstat[" + i + "]=" + new DecimalFormat("##.##").format(tstat[i]));
				}

				// PRINT
				printY(Y, X, beta, true, chartPanel);

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
			outfile.flush();}
		return returnVal;
	}

	private static void printY(final Matrix Y, final Matrix X, final Matrix beta, final boolean bias, JPanel jp)
			throws FileNotFoundException {
		String funcs = "";
		System.out.println("=====================================================");
		System.out.println("|| Συντελεστές:");
		for (int j = 0; j < beta.getNrows(); j++) {
			System.out.println("|| " + new DecimalFormat("##.##").format(beta.getValueAt(j, 0)));
		}

		System.out.println("=====================================================");
		System.out.println("|| Τιμή παρατήρησης -> Τιμή πρόβλεψης");
	    final XYSeries ser1 = new XYSeries("Παρατήρηση");
	    final XYSeries ser2 = new XYSeries("Πρόβλεψη");
	    Double[] predictedYarr = new Double[Y.getNrows()]; 
		if (bias) {
			for (int i = 0; i < Y.getNrows(); i++) {
				double predictedY = beta.getValueAt(0, 0);
				for (int j = 1; j < beta.getNrows(); j++) {
					predictedY += beta.getValueAt(j, 0) * X.getValueAt(i, j - 1);
				}
				predictedYarr[i] = predictedY;
			    ser1.add(i, Y.getValueAt(i, 0));
			    ser2.add(i, predictedY);
				System.out.println("|| " + Y.getValueAt(i, 0) + " -> " + new DecimalFormat("##.##").format(predictedY));
			}
			
			//Mean, Deviation e.t.c.
			double count = predictedYarr.length;   // is 10.0 for your problem
			double sum1 = 0.0;    // sum of the numbers
			double sum2 = 0.0;    // sum of the squares
			int mark0_10=0;
			int mark10_14=0;
			int mark14_17=0;
			int mark17_20=0;
			double smallest = 20;
			int smallestInd = -1;
			double biggest = 0;
			int biggestInd = -1;
			for (int i=0; i < predictedYarr.length; i++) {
				if(predictedYarr[i]<smallest) {
					smallest = predictedYarr[i];
					smallestInd = i;
				}
				if(predictedYarr[i]>biggest) {
					biggest = predictedYarr[i];
					biggestInd=i;
				}
				
				if(predictedYarr[i]<10) mark0_10++;
				else if(predictedYarr[i]<14) mark10_14++;
				else if(predictedYarr[i]<17) mark14_17++;
				else  mark17_20++;
				
				sum1 += predictedYarr[i];
				sum2 += predictedYarr[i] * predictedYarr[i];
			}
			popup=popup.concat("<table style='border: 1px solid black;'>");
			popup=popup.concat("<tr><td style='border: 1px solid black;'>Biggest</td><td>"+biggest+" (Pupil No."+biggestInd+")</td><tr>");
			popup=popup.concat("<tr><td style='border: 1px solid black;'>Smallest</td><td>"+smallest+" (Pupil No."+smallestInd+")</td><tr>");
			popup=popup.concat("<tr><td style='border: 1px solid black;'>0-10</td><td>"+(100*mark0_10/predictedYarr.length)+"%</td><tr>");
			popup=popup.concat("<tr><td style='border: 1px solid black;'>10-14</td><td>"+(100*mark10_14/predictedYarr.length)+"%</td><tr>");
			popup=popup.concat("<tr><td style='border: 1px solid black;'>14-17</td><td>"+(100*mark14_17/predictedYarr.length)+"%</td><tr>");
			popup=popup.concat("<tr><td style='border: 1px solid black;'>17-20</td><td>"+(100*mark17_20/predictedYarr.length)+"%</td><tr>");
			double average = sum1 / count;	
			popup=popup.concat("<tr><td style='border: 1px solid black;'>average</td><td>"+average+"</td><tr>");
			double variance = (count * sum2 - sum1 * sum1) / (count * count);
			popup= popup.concat("<tr><td style='border: 1px solid black;'>variance</td><td>"+variance+"</td><tr>");
			double stdev = Math.sqrt(variance); 
			popup= popup.concat("<tr><td style='border: 1px solid black;'>stdev</td><td>"+stdev+"</td><tr>");
			popup=popup.concat("</table>");
		}
	    
		System.out.println("=====================================================");
		for (int j = 1; j < beta.getNrows(); j++) {
			funcs +=(beta.getValueAt(j, 0)>0)? " + ":" " ;//Only for possitive numbers
			funcs += String.valueOf(new DecimalFormat("##.##").format(beta.getValueAt(j, 0))) + "*"
					+ colNames.get(j - 1);
		}
		System.out.println("Η συνάρτηση είναι: "
				+ String.valueOf(new DecimalFormat("##.##").format(beta.getValueAt(0, 0))) + funcs);
		System.out.println("=====================================================");
		

	    final XYSeriesCollection data = new XYSeriesCollection();
	    data.addSeries(ser1);
	    data.addSeries(ser2);
	    final JFreeChart chart = ChartFactory.createScatterPlot(
	    	"Συνάρτηση: "+String.valueOf(new DecimalFormat("##.##").format(beta.getValueAt(0, 0))) + funcs,
	        "Μαθητές", 
	        "Επιδόσεις", 
	        data,
	        PlotOrientation.VERTICAL,
	        true,
	        true,
	        false
	    );

	    final ChartPanel chartPanel = new ChartPanel(chart);
	    chartPanel.setPreferredSize(new java.awt.Dimension(1000, 270));
	    jp.removeAll();
	    jp.add(chartPanel);
	    /*demo.pack();
	    UIUtils.centerFrameOnScreen(demo);
	    demo.setVisible(true);
	    */
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
