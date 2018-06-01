import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class MultiLinear {

	private Matrix X;
	final private Matrix Y;
	final private boolean bias;
	public static RealMatrix inverse_of_XXtr_AC;
	public static RealMatrix m_AC;
	
	public MultiLinear(final Matrix x, final Matrix y) {
		this(x,y,true);
	}
	
	public MultiLinear(final Matrix x, final Matrix y, final boolean bias) {
		super();
		this.X = x;
		this.Y = y;
		this.bias = bias;
	}
	
	Matrix calculate() throws NoSquareException {
		if (bias)
			this.X = X.insertColumnWithValue1();
		checkDiemnsion();
	    RealMatrix X_AC = MatrixUtils.createRealMatrix(X.getValues());
	    RealMatrix Y_AC = MatrixUtils.createRealMatrix(Y.getValues());

	    RealMatrix Xtr_AC = X_AC.transpose(); //X'

	    RealMatrix XXtr_AC = Xtr_AC.multiply(X_AC); //XX'

	    inverse_of_XXtr_AC = new LUDecomposition(XXtr_AC).getSolver().getInverse(); //(XX')^-1

	    RealMatrix XtrY_AC = Xtr_AC.multiply(Y_AC); //X'Y

	    m_AC = inverse_of_XXtr_AC.multiply(XtrY_AC); //(XX')^-1 X'Y

	    return new Matrix(m_AC.getData());
	}
	
	public Double[] mitrou(Double Msres, int arrayLength) throws NoSquareException {
		Double standErr[] = new Double[arrayLength];
		Double tstat[] = new Double[arrayLength];
		for(int i=0;i<arrayLength;i++) {
			//4. ÕÐÏËÏÃÉÆÙ ÁÕÔ¼Í (ÔÏÍ ÂÑÉÓÊÅÉ Ï ÊÙÄÉÊÁÓ) = inverse_of_XXtr
			Double diagonalValue = inverse_of_XXtr_AC.getEntry(i, i);
			
			//5. ÕÐÏËÏÃÉÆÙ ÁÕÔ¼Í ÔÏÍ ÐÉÍÁÊÁ ÙÓ MSRES * (x' * x) -1
			Double assistantvalue = diagonalValue*Msres;
			
			//6. ÕÐÏËÏÃÉÆÙ ÔÁ STANDARD ERRORS  (=ÔÕÐÉÊÏ ÓÖÁËÌÁ ÔÏ ËÅÅÉ ÓÔÏ ÖÕËËÏ1)
			standErr[i] = Math.sqrt(assistantvalue);
			
			//7. ÕÐÏËÏÃÉÆÙ ÔSTAT (t ëÝåé óôï öýëëï1) (äå ÷ñåéÜæåôáé ãéá b0 ìüíï ãéá ôá b1, b2, …)
			tstat[i]=m_AC.getEntry(i, 0)/standErr[i];
		}
		
		return tstat;
	}
	
	/**
	 * Preconditions checks
	 */
	void checkDiemnsion() {
		if (X == null) 
			throw new NullPointerException("X matrix cannot be null.");
		if (Y == null) 
			throw new NullPointerException("Y matrix cannot be null.");
		
		if (X.getNcols() > X.getNrows()) {
			throw new IllegalArgumentException("The number of columns in X matrix (descriptors) cannot be more than the number of rows");
		}
		if (X.getNrows() != Y.getNrows()) {
			throw new IllegalArgumentException("The number of rows in X matrix should be the same as the number of rows in Y matrix. ");
		}
	}
	
}
