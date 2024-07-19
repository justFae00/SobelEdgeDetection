public class EdgeDetector {
    private static final int[][] SOBEL_X = {
            { -1, 0, 1 },
            { -2, 0, 2 },
            { -1, 0, 1 }
    };

    private static final int[][] SOBEL_Y = {
            { -1, -2, -1 },
            { 0, 0, 0 },
            { 1, 2, 1 }
    };

    /**
     * Applies the Sobel filter to the given sub-matrix.
     * @param image The grayscale image matrix.
     * @param startRow The starting row of the sub-matrix.
     * @param endRow The ending row of the sub-matrix.
     * @return The result sub-matrix after applying the Sobel filter.
     */
    public static int[][] applySobelFilter(int[][] image, int startRow, int endRow) {
        int width = image[0].length;
        int height = endRow - startRow;
        int[][] result = new int[height][width];

        for (int y = startRow; y < endRow; y++) {
            for (int x = 1; x < width - 1; x++) {
                int gx = applyKernel(image, SOBEL_X, x, y);
                int gy = applyKernel(image, SOBEL_Y, x, y);
                int g = (int) Math.sqrt(gx * gx + gy * gy);
                result[y - startRow][x] = g;
            }
        }

        return result;
    }

    private static int applyKernel(int[][] image, int[][] kernel, int x, int y) {
        int sum = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int pixel = image[y - 1 + i][x - 1 + j];
                sum += kernel[i][j] * pixel;
            }
        }
        return sum;
    }
}
