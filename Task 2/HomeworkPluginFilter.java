import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class HomeworkPluginFilter implements PlugInFilter {

    ImagePlus imagePlus;

    public int setup(String arg, ImagePlus imagePlus) {
        this.imagePlus = imagePlus;
        return DOES_ALL;
    }

    public void run(ImageProcessor imageProcessor) {
        int numPixels = imageProcessor.getWidth();
        shiftHorizontally(imageProcessor, numPixels / 2);
        shiftVertically(imageProcessor, numPixels / 2);

        IJ.save(imagePlus, "copy.png");
    }

    private void swapInRow(ImageProcessor imageProcessor, int row, int left, int right) {
        int temp = imageProcessor.getPixel(left, row);
        imageProcessor.putPixel(left, row, imageProcessor.getPixel(right, row));
        imageProcessor.putPixel(right, row, temp);
    }

    private void swapInColumn(ImageProcessor imageProcessor, int column, int top, int bottom) {
        int temp = imageProcessor.getPixel(column, top);
        imageProcessor.putPixel(column, top, imageProcessor.getPixel(column, bottom));
        imageProcessor.putPixel(column, bottom, temp);
    }

    private void swapHorizontally(ImageProcessor imageProcessor, int rowOrColumn, int start, int end) {
        for (; start < end; start++, end--) {
            swapInRow(imageProcessor, rowOrColumn, start, end);
        }
    }

    private void swapVertically(ImageProcessor imageProcessor, int columnOrRow, int start, int end) {
        for (; start < end; start++, end--) {
            swapInColumn(imageProcessor, columnOrRow, start, end);
        }
    }

    private void shiftHorizontally(ImageProcessor imageProcessor, int offset) {
        int width = imageProcessor.getWidth();
        int height = imageProcessor.getHeight();

        for (int row = 0; row < height; row++) {
            swapHorizontally(imageProcessor, row, 0, offset - 1);
            swapHorizontally(imageProcessor, row, offset, width - 1);
            swapHorizontally(imageProcessor, row, 0, width - 1);
        }
    }

    private void shiftVertically(ImageProcessor imageProcessor, int offset) {
        int width = imageProcessor.getWidth();
        int height = imageProcessor.getHeight();

        for (int column = 0; column < width; column++) {
            swapVertically(imageProcessor, column, 0, offset - 1);
            swapVertically(imageProcessor, column, offset, height - 1);
            swapVertically(imageProcessor, column, 0, height - 1);
        }
    }
}
