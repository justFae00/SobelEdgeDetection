import java.util.concurrent.CountDownLatch;

public class EdgeDetectionTask implements Runnable {
    private int[][] image;
    private int startRow;
    private int endRow;
    private int[][] result;
    private CountDownLatch latch;

    public EdgeDetectionTask(int[][] image, int startRow, int endRow, int[][] result, CountDownLatch latch) {
        this.image = image;
        this.startRow = startRow;
        this.endRow = endRow;
        this.result = result;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            int[][] partialResult = EdgeDetector.applySobelFilter(image, startRow, endRow);
            for (int i = 0; i < partialResult.length; i++) {
                System.arraycopy(partialResult[i], 0, result[startRow + i], 0, partialResult[i].length);
            }
        } finally {
            latch.countDown();
        }
    }
}
