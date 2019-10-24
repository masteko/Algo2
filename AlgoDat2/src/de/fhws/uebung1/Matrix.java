package de.fhws.uebung1;

public class Matrix {
	final static int n = 3;
	static double[][] _matrix;
	static double[][] _new;
	static double[] _vector;
	static double[] _result;
	static double[] _x;
	
	public static void main(String[] args) {
//		_matrix = createMatrix();
//		_vector = createVector();
		_new = new double[3][4];
		_matrix = new double[][] {
				{2, 1, 3, 1},
				{4, 4, 7, 2},
				{2, 5, 9, 3}
		};
		cloneMatrix();
//		_x = new double[] {1, 2, 3};

		printMatrix();
		printVector(_x);
		gauss();
		printMatrix(_new);
//		printVector(_result);
//		_result = mul();
		
//		printVector(_result);
//		for (int i = 0; i < 10; i++) {
//			printVector(_x);
//			norm();
//		}
//		printVector(_x);
	}
	
	public static void cloneMatrix() {
		for (int i = 0; i < _matrix.length; i++) {
			for (int j = 0; j < _matrix[0].length; j++) {
				_new[i][j] =_matrix[i][j];
			}
		}
	}
	
	public static double[][] createMatrix() {
		double[][] result = new double[n][n];
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				result[i][j] = Math.random() * 9 + 1;
			}
		}

		return result;
	}

	public static void printMatrix() {
		printMatrix(_matrix);
	}

	public static void printMatrix(double[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println("");	
	}
	
	public static double[] createVector() {
		double[] result = new double[n];
		
		for (int i = 0; i < n; i++) {
			result[i] = (int) (Math.random() * 9 + 1);
		}
		
		return result;
	}
	
	public static void printVector(double[] v) {
		for (int i = 0; i < n; i++) {
			System.out.println(v[i]);
		}
		System.out.println("");
	}
	
	public static double[] mul() {
		double[] result = new double[n];
		int sum = 0;
		int count = 0;
		
		for (int i = 0; i < n; i++) {
			sum = 0;
			for (int j = 0; j < n; j++) {
				sum += _matrix[i][j] * _vector[j];
				count++;
			}
			result[i] = sum;
		}

		System.out.println("Anzahl an Additionen/Multiplikationen: " + count + "\n");
		
		return result;
	}
	
	public static void norm() {
		int sum = 0;
		int[] newX = new int[n];
		double norm = 0;
		
		for (int i = 0; i < n; i++) {
			sum = 0;
			for (int j = 0; j < n; j++) {
				sum += _matrix[i][j] * _x[j];
			}
			newX[i] = sum;
			norm += sum * sum;
		}
		
		norm = Math.sqrt(norm);
		
		for (int i = 0; i < n; i++) {
			_x[i] = newX[i] / norm;
		}
	}
	
	public static void gauss() {
		double tmp = 0;
		for (int col = 1; col < _matrix[0].length; col++) {
			for (int row = col; row < _matrix.length; row++) {
				tmp = _new[row][col - 1] / _new[col - 1][col - 1];
				
				for (int i = col - 1; i < _matrix[0].length; i++) {
					_new[row][i] = _new[row][i] - tmp * _new[col - 1][i];
				}
				System.out.println("---------------------------------");
				printMatrix(_new);
			}
		}
		
		double[] results = new double[_matrix.length];
		
		for (int row = _matrix.length - 1; row >= 0; row--) {
			for (int col = row + 1; col < _matrix[0].length - 1; col++) {
				_new[row][_matrix[0].length - 1] -= _new[row][col] * results[col];
			}
			results[row] = _new[row][_matrix[0].length - 1] / _new[row][row];
		}
		
		System.out.println("Results:");
		for (int i = 0; i < results.length; i++) {
			System.out.println("x" + (i + 1) + " = " + results[i]);
		}
	}
	
}
