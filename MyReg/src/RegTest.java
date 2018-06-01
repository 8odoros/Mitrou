import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


public class RegTest {

	static List<String>  colNames = new ArrayList<>();
	public static void main(String[] args) throws Exception {

		System.setOut(new PrintStream(new FileOutputStream("log.txt",true)));
		System.out.println("=====================================================");
		Date currentDate = new Date();
		System.out.println("|| Curent Date and Time - " + currentDate.toString());

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV", "csv");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("|| You chose to open this file: " + chooser.getSelectedFile().getName());
			System.out.println("=====================================================");
			String fName = chooser.getSelectedFile().getPath();
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
			int repCounter=0;
			String repCols="";
			do {
				if(repeat) {
					repCounter++;
					System.out.println("******************!!! REPEAT !!!*********************");
					System.out.println("Repeat no. "+repCounter+". Removed columns index: "+repCols);
					System.out.println("=====================================================");
				}
				repeat = false;
				double[][] eX = new double[array.length][array[0].length - 1];
				double[][] eY = new double[array.length][1];
				Double sumY = (double) 0;
				int lastCol = array[0].length - 1;
				
				for (int j = 1; j < array[0].length+1; j++) {
					colNames.add("X"+j);
				}
				
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
				final Matrix beta = ml.calculate();

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
				//System.out.println("|| ei_hatSUM=" + ei_hatSUM);
				//System.out.println("|| ei_hat2SUM=" + ei_hat2SUM);
				double s2 = ei_hat2SUM / (array.length - lastCol - 1);
				//System.out.println("|| s2=Msres=" + s2);// =Msres

				// ymeso
				double ymeso = sumY / array.length;
				//System.out.println("|| ymeso=" + ymeso);

				Double sumR = (double) 0;
				Double[] yi_ymeso = new Double[array.length];
				for (int i = 0; i < array.length; i++) {
					yi_ymeso[i] = Math.pow(eY[i][0] - ymeso, 2);
					// System.out.println("yi_ymeso["+i+"]="+yi_ymeso[i]);
					sumR += yi_ymeso[i];
				}
				// Print r2
				double r2 = 1 - ei_hat2SUM / sumR;
				//System.out.println("=====================================================");
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
				if(percentiles.length>=array.length) {
					myVal = percentiles[array.length - 1];
				}else {
					myVal = percentiles[percentiles.length-1];
				}				
				for (int i = tstat.length-2; i >0;  i--) {
					if (tstat[i] < myVal && tstat[i] > -myVal) {
						repeat = true;
						array = removeCol(array, i-1);
						colNames.remove(i-1);
						repCols+=i+" ";
					}
				}
			} while (repeat);
		}
	}

	private static void printY(final Matrix Y, final Matrix X, final Matrix beta, final boolean bias)
			throws FileNotFoundException {
		String funcs="";
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
			funcs += " + "+String.valueOf(new DecimalFormat("##.##").format(beta.getValueAt(j, 0)))+"*"+colNames.get(j-1);
		}
		System.out.println("Η συνάρτηση είναι: "+ String.valueOf(new DecimalFormat("##.##").format(beta.getValueAt(0, 0)))+funcs);
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
