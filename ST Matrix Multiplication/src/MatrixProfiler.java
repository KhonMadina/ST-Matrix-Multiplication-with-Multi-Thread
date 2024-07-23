import java.util.Random;

public class MatrixProfiler {
    public static void main(String[] args) {
        int[] sizes = {3, 7, 15, 50, 100, 200, 500, 700, 1000, 5000};

        for (int size : sizes) {
            Matrix m1 = new Matrix(size, size);
            Matrix m2 = new Matrix(size, size);

            Random random = new Random();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    m1.setValue(i, j, random.nextInt(100));
                    m2.setValue(i, j, random.nextInt(100));
                }
            }

            Runtime runtime = Runtime.getRuntime();

            // Single-threaded addition
            runtime.gc();
            long beforeMemory = runtime.totalMemory() - runtime.freeMemory();
            long startTime = System.nanoTime();
            Matrix.add(m1, m2);
            long endTime = System.nanoTime();
            long afterMemory = runtime.totalMemory() - runtime.freeMemory();
            long memoryUsed = afterMemory - beforeMemory;
            System.out.println("Size: " + size + " - Single-threaded addition time: " + (endTime - startTime) + " ns");
            System.out.println("Memory used: " + bytesToMB(memoryUsed) + " MB (" + bytesToGB(memoryUsed) + " GB)");

            // Multi-threaded addition
            runtime.gc();
            beforeMemory = runtime.totalMemory() - runtime.freeMemory();
            startTime = System.nanoTime();
            Matrix.addMultiThreaded(m1, m2);
            endTime = System.nanoTime();
            afterMemory = runtime.totalMemory() - runtime.freeMemory();
            memoryUsed = afterMemory - beforeMemory;
            System.out.println("Size: " + size + " - Multi-threaded addition time: " + (endTime - startTime) + " ns");
            System.out.println("Memory used: " + bytesToMB(memoryUsed) + " MB (" + bytesToGB(memoryUsed) + " GB)");

            // Single-threaded multiplication
            runtime.gc();
            beforeMemory = runtime.totalMemory() - runtime.freeMemory();
            startTime = System.nanoTime();
            Matrix.multiply(m1, m2);
            endTime = System.nanoTime();
            afterMemory = runtime.totalMemory() - runtime.freeMemory();
            memoryUsed = afterMemory - beforeMemory;
            System.out.println("Size: " + size + " - Single-threaded multiplication time: " + (endTime - startTime) + " ns");
            System.out.println("Memory used: " + bytesToMB(memoryUsed) + " MB (" + bytesToGB(memoryUsed) + " GB)");

            // Multi-threaded multiplication
            runtime.gc();
            beforeMemory = runtime.totalMemory() - runtime.freeMemory();
            startTime = System.nanoTime();
            Matrix.multiplyMultiThreaded(m1, m2);
            endTime = System.nanoTime();
            afterMemory = runtime.totalMemory() - runtime.freeMemory();
            memoryUsed = afterMemory - beforeMemory;
            System.out.println("Size: " + size + " - Multi-threaded multiplication time: " + (endTime - startTime) + " ns");
            System.out.println("Memory used: " + bytesToMB(memoryUsed) + " MB (" + bytesToGB(memoryUsed) + " GB)");
        }
    }

    private static double bytesToMB(long bytes) {
        return bytes / (1024.0 * 1024.0);
    }

    private static double bytesToGB(long bytes) {
        return bytes / (1024.0 * 1024.0 * 1024.0);
    }
}
