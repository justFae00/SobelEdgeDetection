import java.awt.image.BufferedImage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Run {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java Main <input-image> <output-image>");
            return;
        }

        String inputImagePath = args[0];
        String outputImagePath = args[1];


        // Load the image
        BufferedImage image = ImageLoader.loadImage(inputImagePath);
        if (image == null) {
            System.out.println("Failed to load the image.");
            return;
        }

        // Convert the image to grayscale
        int[][] grayscaleImage = ImageLoader.convertToGrayscale(image);

        // Number of chunks (threads) to use
        int numChunks = 9;

        // Create a result matrix to store the filtered values
        int[][] resultMatrix = new int[grayscaleImage.length][grayscaleImage[0].length];

        // Create and execute tasks for each chunk
        ExecutorService executor = Executors.newFixedThreadPool(numChunks);
        CountDownLatch latch = new CountDownLatch(numChunks);

        // Divide the image and assign chunks to threads
        for (int i = 0; i < numChunks; i++) {
            int startRow = i * (grayscaleImage.length / numChunks);
            int endRow = (i == numChunks - 1) ? grayscaleImage.length : (i + 1) * (grayscaleImage.length / numChunks);
            executor.execute(new EdgeDetectionTask(grayscaleImage, startRow, endRow, resultMatrix, latch));
        }

        executor.shutdown();
        try {
            latch.await();  // Wait for all threads to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Apply thresholding to the result matrix
        int[][] thresholdedMatrix = Thresholding.applyThresholding(resultMatrix);

        // Save the resulting image
        BufferedImage outputImage = ImageLoader.convertToBufferedImage(thresholdedMatrix);
        ImageLoader.saveImage(outputImage, outputImagePath);
        System.out.println("Edge detection and thresholding completed and saved to " + outputImagePath);
    }
}
