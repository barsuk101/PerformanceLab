package org.example;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class task3 {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("нет нужных json файлов");
            return;
        }

        String testsFilePath = args[0];
        String valuesFilePath = args[1];

        try {
            JSONObject testsJson = readJsonFile(testsFilePath);
            JSONObject valuesJson = readJsonFile(valuesFilePath);

            JSONArray valuesArray = valuesJson.getJSONArray("values");
            updateReport(testsJson, valuesArray);

            writeJsonFile("report.json", testsJson);
            System.out.println("report создан");
        } catch (IOException e) {
            System.out.println("ошибка при чтении файлов" + e.getMessage());
        }
    }

    private static JSONObject readJsonFile(String filePath) throws IOException {
        try (FileReader fileReader = new FileReader(filePath)) {
            JSONTokener tokener = new JSONTokener(fileReader);
            return new JSONObject(tokener);
        }
    }

    private static void writeJsonFile(String filePath, JSONObject jsonObject) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonObject.toString(2));
        }
    }

    private static void updateReport(JSONObject testsJson, JSONArray valuesArray) {
        for (int i = 0; i < valuesArray.length(); i++) {
            JSONObject valueObject = valuesArray.getJSONObject(i);
            int valueTestId = valueObject.getInt("id");
            String value = valueObject.getString("value");

            updateTestValue(testsJson, valueTestId, value);
        }
    }

    private static void updateTestValue(JSONObject testsJson, int testId, String value) {
        updateTestValueRecursive(testsJson, testId, value);
    }

    private static void updateTestValueRecursive(JSONObject jsonObject, int testId, String value) {
        if (jsonObject.has("id") && jsonObject.getInt("id") == testId) {
            jsonObject.put("value", value);
            return;
        }

        if (jsonObject.has("values")) {
            JSONArray valuesArray = jsonObject.getJSONArray("values");
            for (int i = 0; i < valuesArray.length(); i++) {
                updateTestValueRecursive(valuesArray.getJSONObject(i), testId, value);
            }
        }

        if (jsonObject.has("tests")) {
            JSONArray testsArray = jsonObject.getJSONArray("tests");
            for (int i = 0; i < testsArray.length(); i++) {
                updateTestValueRecursive(testsArray.getJSONObject(i), testId, value);
            }
        }
    }
}
