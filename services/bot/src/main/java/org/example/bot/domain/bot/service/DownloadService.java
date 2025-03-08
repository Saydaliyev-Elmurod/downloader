package org.example.bot.domain.bot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.bot.domain.bot.config.Bot;
import org.example.bot.domain.bot.model.VideoInfo;
import org.example.bot.domain.bot.util.Sender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@Log4j2
@AllArgsConstructor
public class DownloadService {
    private final ObjectMapper objectMapper;
    private final Bot bot;

//    private void downloadVideo(final VideoDownloadReply reply) {
//        final String hash = getHash(normalizeUrl(reply.link()));
//
//        return videoRepository.findByHash(hash)
//                .switchIfEmpty(Mono.defer(() -> {
//                    final String string = readVideoInfo(reply.link());
//                    VideoInfo videoInfo = parseJson(string);
//
//                    if (videoInfo != null) {
//                        final Optional<RequestFormat> mp4Format = videoInfo.formats().stream()
//                                .filter(format -> format.ext().equals("mp4"))
//                                .findFirst();
//
//                        if (mp4Format.isPresent()) {
//                            return jmsPublisher.publishSynchronized(new SendInstagramVideoReply(
//                                    reply.telegramId(),
//                                    mp4Format.get().url(),
//                                    videoInfo.thumbnail(),
//                                    hash
//                            )).then(Mono.empty());
//                        }
//                    }
//                    return Mono.empty();
//                }))
//                .flatMap(video -> {
//                    jmsPublisher.publish(new SendVideoReply(reply.telegramId(), VideoMapper.INSTANCE.toResponse(video)));
//                    return Mono.empty();
//                })
//                .then();
//    }

    public static String normalizeUrl(String url) {
        try {
            URI uri = new URI(url);
            return new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), null, null).toString();
        } catch (Exception e) {
            return url;
        }
    }

    public void downloadVideo(String videoUrl, String outputPath, final Long chatId) {
        final String string = readVideoInfo(videoUrl);
        VideoInfo videoInfo = parseJson(string);
        File directory = new File(outputPath);


        if (!directory.exists()) {
            directory.mkdirs();
        }
        ProcessBuilder processBuilder = new ProcessBuilder(
                "/usr/local/bin/yt-dlp", "-o", outputPath + "/video", videoUrl
        );

        processBuilder.redirectErrorStream(true); // log errors

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // read
            }

            int exitCode = process.waitFor();
            System.out.println("Process exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            log.error("Error downloading video", e);
        }

        processBuilder = new ProcessBuilder(
                "ffmpeg", "-i", outputPath + "/video.webm", "-c:v", "libx264", "-c:a", "aac", outputPath + "/video.mp4"
        );

        processBuilder.redirectErrorStream(true); // Xatolarni chiqarish uchun

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // FFmpeg chiqishini konsolga chiqarish
            }

            int exitCode = process.waitFor();
            System.out.println("Process finished with exit code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        SendVideo sendVideo = new SendVideo();
        sendVideo.setChatId(chatId);
        sendVideo.setVideo(new InputFile(new File(outputPath + "/video.mp4")));
        sendVideo.setCaption("string");
        sendVideo.setThumbnail(new InputFile(videoInfo.thumbnail()));
        sendVideo.setSupportsStreaming(true);
        Sender.send(sendVideo, bot);
    }


    private static String readVideoInfo(String videoUrl) {
        try {
            ProcessBuilder builder = new ProcessBuilder(
                    "yt-dlp", "-j", videoUrl
            );

            builder.redirectErrorStream(true);
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder jsonResult = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonResult.append(line);
            }

            process.waitFor();
            return jsonResult.toString();

        } catch (Exception e) {
            log.error("Error downloading video", e);
            return null;
        }
    }

    private VideoInfo parseJson(String json) {
        try {
            VideoInfo videoInfo = objectMapper.readValue(json, VideoInfo.class);
            log.info("Video info: {}", videoInfo);
            return videoInfo;
        } catch (Exception e) {
            log.error("Error downloading video", e);
        }
        return null;
    }

    public String getHash(String url) {
        try {
            byte[] hashBytes = MessageDigest.getInstance("MD5").digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return url;
        }
    }
}
