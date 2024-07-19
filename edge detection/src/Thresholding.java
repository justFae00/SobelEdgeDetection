public class Thresholding {
    /**
     * Applies thresholding to the result matrix based on the mean value.
     * @param matrix The result matrix after applying the filter.
     * @return The thresholded matrix.
     */
    public static int[][] applyThresholding(int[][] matrix) {
        int height = matrix.length;
        int width = matrix[0].length;
        int sum = 0;
        int count = height * width;

        // Calculate the sum of all elements
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                sum += matrix[y][x];
            }
        }

        // Calculate the mean value
        int mean = sum / count;

        // Apply thresholding
        int[][] thresholded = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                thresholded[y][x] = (matrix[y][x] > mean) ? 0 : 255;
            }
        }

        return thresholded;
    }
}
