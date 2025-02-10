package org.example.download.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.common.util.jms.VideoDownloadReply;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Queue;

@Service
@AllArgsConstructor
@Log4j2
public class DownloadService {
    Queue<VideoDownloadReply> downloadQueue;

    public Mono<Void> addToQueue(final VideoDownloadReply reply) {
        downloadQueue.add(reply);
        return Mono.empty();
    }
}
