package org.example.bot.domain.bot.watermark;

import org.bytedeco.javacv.*;

public class VideoWatermark {
    public static void main(String[] args) {
        String inputFile = "IMG_5785.MP4";
        String outputFile = "output_video.mp4";

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile);
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, grabber.getImageWidth(), grabber.getImageHeight());

        try {
            grabber.start();
            recorder.start();
            
            Frame frame;
            while ((frame = grabber.grab()) != null) {
                // Video tahrirlash logikasi, masalan watermarkni olib tashlash uchun
                // Filterlar yoki boshqa tahrirlash amallarini qo'llash mumkin
                
                recorder.record(frame);
            }

            grabber.stop();
            recorder.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
