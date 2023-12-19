import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class task2 {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java CirclePointPosition <circle_file> <points_file>");
            return;
        }

        // Чтение координат и радиуса окружности из файла
        try (BufferedReader circleReader = new BufferedReader(new FileReader(args[0]))) {
            String[] circleCoordinates = circleReader.readLine().split(" ");
            float centerX = Float.parseFloat(circleCoordinates[0]);
            float centerY = Float.parseFloat(circleCoordinates[1]);
            float radius = Float.parseFloat(circleReader.readLine());

            // Чтение координат точек из файла
            try (BufferedReader pointsReader = new BufferedReader(new FileReader(args[1]))) {
                String line;
                while ((line = pointsReader.readLine()) != null) {
                    String[] pointCoordinates = line.split(" ");
                    float pointX = Float.parseFloat(pointCoordinates[0]);
                    float pointY = Float.parseFloat(pointCoordinates[1]);

                    // Расчет расстояния от точки до центра окружности
                    float distance = (float) Math.sqrt(Math.pow(pointX - centerX, 2) + Math.pow(pointY - centerY, 2));

                    // Определение положения точки относительно окружности
                    int position = (distance == radius) ? 0 : (distance < radius) ? 1 : 2;
                    System.out.println(position);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
