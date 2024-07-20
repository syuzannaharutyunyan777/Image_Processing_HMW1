import ij.*;
import ij.process.*;
import ij.plugin.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class HomeworkPlugin implements PlugIn {

    static class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    @Override
    public void run(String s) {
        String coursesFile = "Dataset/rye-s-93.crs";
        String studentsFile = "Dataset/rye-s-93.stu";

        int courseNumber = getNumberOfCourses(coursesFile);

        ImageProcessor imageProcessor = new BinaryProcessor(new ByteProcessor(courseNumber, courseNumber));

        drawFrameImage(imageProcessor, courseNumber);

        List<Point> suspiciousPoints = getSuspiciousPoint(studentsFile);

        for (Point point : suspiciousPoints) {
            imageProcessor.putPixel(point.x, point.y, 0);
        }

        IJ.save(new ImagePlus("rye-s-93", imageProcessor), "Dataset/rye-s-93.png");
    }

    public void drawFrameImage(ImageProcessor imageProcessor, int courseNumber) {
        int color = 255 * 255 * 255;
        imageProcessor.setColor(color);
        imageProcessor.drawRect(0, 0, courseNumber, courseNumber);
        imageProcessor.fill();
    }

    public int getNumberOfCourses(String fileName) {
        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String current;
            String previous = null;
            while ((current = bufferedReader.readLine()) != null) {
                previous = current.trim();
            }
            return Integer.parseInt(previous.split(" ")[0]);
        } catch (Exception ex) {
            return -1;
        }
    }

    public List<Point> getSuspiciousPoint(String fileName) {
        List<Point> suspiciousPoints = new ArrayList<>();
        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                String[] lineCourses = currentLine.split(" ");
                if (lineCourses.length > 1) {
                    for (int i = 0; i < lineCourses.length - 1; i++) {
                        for (int j = i + 1; j < lineCourses.length; j++) {
                            suspiciousPoints.add(new Point(Integer.parseInt(lineCourses[i]), Integer.parseInt(lineCourses[j])));
                        }
                    }
                }
            }
        } catch (Exception ex) {
            return new ArrayList<>();
        }

        return suspiciousPoints;
    }
}
