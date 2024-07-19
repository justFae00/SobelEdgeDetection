public class ImageChunker {
    /**
     * Divides the grayscale image matrix into smaller sub-matrices (chunks).
     * @param image The grayscale image matrix.
     * @param numChunks The number of chunks to divide the image into.
     * @return A 2D array where each element is a chunk of the image.
     */
    public static int[][][] divideImage(int[][] image, int numChunks) {
        int height = image.length;
        int width = image[0].length;
        int chunkHeight = height / numChunks;
        int[][][] chunks = new int[numChunks][][];

        for (int i = 0; i < numChunks; i++) {
            int startRow = i * chunkHeight;
            int endRow = (i == numChunks - 1) ? height : startRow + chunkHeight;
            int chunkSize = endRow - startRow;
            chunks[i] = new int[chunkSize][width];

            for (int y = 0; y < chunkSize; y++) {
                System.arraycopy(image[startRow + y], 0, chunks[i][y], 0, width);
            }
        }

        return chunks;
    }
}
