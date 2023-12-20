import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class task4 {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("не хватает инпут файла");
            System.exit(1);
        }

        String inputFile = args[0];

        try {
            List<Integer> nums = readNumbersFromFile(inputFile);
            int minMoves = minMovesToEqualize(nums);
            System.out.println(+ minMoves);
        } catch (IOException e) {
            System.err.println("ошибка при чтении файла " + e.getMessage());
        }
    }

    private static List<Integer> readNumbersFromFile(String inputFile) throws IOException {
        List<Integer> nums = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                nums.add(Integer.parseInt(line.trim()));
            }
        }

        return nums;
    }

    private static int minMovesToEqualize(List<Integer> nums) {
        int n = nums.size();
        Collections.sort(nums);

        int median = nums.get(n / 2);

        int minMoves = 0;
        for (int num : nums) {
            minMoves += Math.abs(num - median);
        }

        return minMoves;
    }
}
