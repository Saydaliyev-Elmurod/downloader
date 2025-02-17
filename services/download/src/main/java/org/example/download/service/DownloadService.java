package org.example.download.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.example.common.model.jms.SendInstagramVideoReply;
import org.example.common.model.jms.SendVideoReply;
import org.example.common.util.jms.VideoDownloadReply;
import org.example.download.models.RequestFormat;
import org.example.download.models.VideoInfo;
import org.example.download.models.mapper.VideoMapper;
import org.example.download.repository.VideoRepository;
import org.example.jms.JmsPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;

@Service
@Log4j2
public class DownloadService {
    private final JmsPublisher jmsPublisher;
    private final ObjectMapper objectMapper;
    private final String botToken;
    private final static Queue<VideoDownloadReply> downloadQueue = new PriorityQueue<>();
    private final VideoRepository videoRepository;

    public DownloadService(final JmsPublisher jmsPublisher, final ObjectMapper objectMapper, @Value("${bot.token}") final String botToken, final VideoRepository videoRepository) {
        this.jmsPublisher = jmsPublisher;
        this.objectMapper = objectMapper;
        this.botToken = botToken;
        this.videoRepository = videoRepository;
    }

    public Mono<Void> addToQueue(final VideoDownloadReply reply) {
        return Mono.defer(() -> {
            if (downloadQueue.isEmpty()) {
                return downloadVideo(reply);
            } else {
                downloadQueue.add(reply);
                return Mono.empty();
            }
        }).then();
    }

    private Mono<Void> downloadVideo(final VideoDownloadReply reply) {
        final String hash = getHash(normalizeUrl(reply.link()));

        return videoRepository.findByHash(hash)
                .switchIfEmpty(Mono.defer(() -> {
                    final String string = readVideoInfo(reply.link());
                    VideoInfo videoInfo = parseJson(string);

                    if (videoInfo != null) {
                        final Optional<RequestFormat> mp4Format = videoInfo.formats().stream()
                                .filter(format -> format.ext().equals("mp4"))
                                .findFirst();

                        if (mp4Format.isPresent()) {
                            return jmsPublisher.publishSynchronized(new SendInstagramVideoReply(
                                    reply.telegramId(),
                                    mp4Format.get().url(),
                                    videoInfo.thumbnail(),
                                    hash
                            )).then(Mono.empty());
                        }
                    }
                    return Mono.empty();
                }))
                .flatMap(video -> {
                    jmsPublisher.publish(new SendVideoReply(reply.telegramId(), VideoMapper.INSTANCE.toResponse(video)));
                    return Mono.empty();
                })
                .then();
    }

    public static String normalizeUrl(String url) {
        try {
            URI uri = new URI(url);
            return new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), null, null).toString();
        } catch (Exception e) {
            return url;
        }
    }

    public void downloadVideo(String videoUrl, String outputPath) {
        File directory = new File(outputPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        ProcessBuilder processBuilder = new ProcessBuilder(
                "yt-dlp", "-o", outputPath + "/video.mp4", videoUrl
        );

        try {
            Process process = processBuilder.start();
            process.waitFor();
            log.info("Video muvaffaqiyatli yuklab olindi: " + outputPath + "/video.mp4");
        } catch (IOException | InterruptedException e) {
            log.error("Error downloading video", e);
        }
    }


    public void sendVideoToTelegramBot(Long chatId, File file) {
        String telegramApiUrl = "https://api.telegram.org/bot" + botToken + "/sendVideo";

        WebClient webClient = WebClient.create();

        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("chat_id", chatId);
        formData.add("video", new FileSystemResource(file));

        webClient.post()
                .uri(telegramApiUrl)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> System.out.println("Video Telegram botga yuborildi!"))
                .doOnError(Throwable::printStackTrace).block();

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
