package org.example.download.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class YtDlpJsonParser {
    public static void main(String[] args) {
        String videoUrl = "https://www.youtube.com/watch?v=Q3FumV0v6Qk"; // Video URL
        String jsonOutput = runYtDlp(videoUrl);

        if (jsonOutput != null) {
            parseJson(jsonOutput);
        } else {
            System.out.println("Error: yt-dlp dan JSON javob olinmadi!");
        }
    }

    private static String runYtDlp(String videoUrl) {
        try {
            // yt-dlp ni JSON formatda ishga tushiramiz
            ProcessBuilder builder = new ProcessBuilder(
                    "yt-dlp", "-j", videoUrl
            );

            builder.redirectErrorStream(true);
            Process process = builder.start();

            // Natijani o‘qiymiz
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder jsonResult = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonResult.append(line);
            }

            process.waitFor(); // Jarayon tugashini kutamiz
            return jsonResult.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void parseJson(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);

            // Kerakli maydonlarni olish
            String videoId = rootNode.path("id").asText();
            String title = rootNode.path("title").asText();
            JsonNode thumbnails = rootNode.path("thumbnails");

            // Faqat "mqdefault.jpg" ni olish
            String mediumQualityThumbnail = "";
            for (JsonNode thumbnail : thumbnails) {
                String url = thumbnail.path("url").asText();
                if (url.contains("mqdefault")) {
                    mediumQualityThumbnail = url;
                    break;
                }
            }

            // Natijani chiqarish
            System.out.println("Video ID: " + videoId);
            System.out.println("Title: " + title);
            System.out.println("Medium Quality Thumbnail: " + mediumQualityThumbnail);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
