package org.example.download.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Delivery;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.common.util.jms.VideoDownloadReply;
import org.example.download.service.DownloadService;
import org.example.jms.BaseConsumer;
import org.example.jms.JmsConsumer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Log4j2
@JmsConsumer(targetType = "VideoDownloadReply")
@Component
@RequiredArgsConstructor
public class VideoDownloadConsumer extends BaseConsumer {

    private final DownloadService downloadService;
    private final ObjectMapper objectMapper;

    @Override
    protected Mono<Void> consume(final Delivery message) throws IOException {
        log.info("Consume  [{}]", message);
        System.out.println("Consume---------------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        VideoDownloadReply reply = objectMapper.readValue(message.getBody(), VideoDownloadReply.class);
        return downloadService.addToQueue(reply);
    }
}
