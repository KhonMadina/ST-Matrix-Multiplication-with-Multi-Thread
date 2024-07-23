public class Matrix {
    private int[][] data;
    private int rows;
    private int cols;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        data = new int[rows][cols];
    }

    public void setValue(int row, int col, int value) {
        data[row][col] = value;
    }

    public int getValue(int row, int col) {
        return data[row][col];
    }

    public static Matrix add(Matrix m1, Matrix m2) {
        if (m1.rows != m2.rows || m1.cols != m2.cols) {
            throw new IllegalArgumentException("Matrices dimensions do not match for addition.");
        }
        Matrix result = new Matrix(m1.rows, m1.cols);
        for (int i = 0; i < m1.rows; i++) {
            for (int j = 0; j < m1.cols; j++) {
                result.setValue(i, j, m1.getValue(i, j) + m2.getValue(i, j));
            }
        }
        return result;
    }

    public static Matrix multiply(Matrix m1, Matrix m2) {
        if (m1.cols != m2.rows) {
            throw new IllegalArgumentException("Matrices dimensions do not match for multiplication.");
        }
        Matrix result = new Matrix(m1.rows, m2.cols);
        for (int i = 0; i < m1.rows; i++) {
            for (int j = 0; j < m2.cols; j++) {
                int sum = 0;
                for (int k = 0; k < m1.cols; k++) {
                    sum += m1.getValue(i, k) * m2.getValue(k, j);
                }
                result.setValue(i, j, sum);
            }
        }
        return result;
    }

    public static Matrix addMultiThreaded(Matrix m1, Matrix m2) {
        if (m1.rows != m2.rows || m1.cols != m2.cols) {
            throw new IllegalArgumentException("Matrices dimensions do not match for addition.");
        }
        Matrix result = new Matrix(m1.rows, m1.cols);
        Thread[] threads = new Thread[m1.rows];
        for (int i = 0; i < m1.rows; i++) {
            final int row = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < m1.cols; j++) {
                    result.setValue(row, j, m1.getValue(row, j) + m2.getValue(row, j));
                }
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static Matrix multiplyMultiThreaded(Matrix m1, Matrix m2) {
        if (m1.cols != m2.rows) {
            throw new IllegalArgumentException("Matrices dimensions do not match for multiplication.");
        }
        Matrix result = new Matrix(m1.rows, m2.cols);
        Thread[] threads = new Thread[m1.rows];
        for (int i = 0; i < m1.rows; i++) {
            final int row = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < m2.cols; j++) {
                    int sum = 0;
                    for (int k = 0; k < m1.cols; k++) {
                        sum += m1.getValue(row, k) * m2.getValue(k, j);
                    }
                    result.setValue(row, j, sum);
                }
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void display() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
    }
}

