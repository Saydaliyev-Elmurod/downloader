package org.example.download.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.queue.PredicatedQueue;
import org.example.common.util.jms.VideoDownloadReply;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.PriorityQueue;
import java.util.Queue;

@Service
@AllArgsConstructor
@Log4j2
public class DownloadService {
    private final static Queue<VideoDownloadReply> downloadQueue = new PriorityQueue<>();

    public Mono<Void> addToQueue(final VideoDownloadReply reply) {
        return Mono.fromRunnable(() -> downloadQueue.add(reply)).then();
    }

}
