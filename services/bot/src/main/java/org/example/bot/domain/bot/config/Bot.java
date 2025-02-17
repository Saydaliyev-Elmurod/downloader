package org.example.bot.domain.bot.config;

import lombok.extern.log4j.Log4j2;
import org.example.bot.domain.bot.controller.DestinationController;
import org.example.bot.domain.bot.domain.UserEntity;
import org.example.bot.domain.bot.repository.UserRepository;
import org.example.bot.domain.bot.util.Sender;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class Bot extends TelegramLongPollingBot {
    @Lazy
    private final BotConfig config;
    @Lazy
    private final DestinationController destinationController;
    private final UserRepository userRepository;

    public Bot(BotConfig config,@Lazy DestinationController destinationController, final UserRepository userRepository) {
        this.config = config;
        this.destinationController = destinationController;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "get a welcome message"));
        listOfCommands.add(new BotCommand("/help", "info how to use this bot"));
        listOfCommands.add(new BotCommand("/settings", "set your preferences"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(String.valueOf(e.getCause()));
        }
        this.userRepository = userRepository;
    }

    @Override
    public String getBotUsername() {
        return config.getBotUserName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Received update from chat [{}]", update.getMessage().getChatId());

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(update.getMessage().getChatId().toString());
        sendPhoto.setPhoto(new InputFile("https://i.ytimg.com/vi/uS-928Jp4Zo/sddefault.jpg"));
        sendPhoto.setCaption("fsd");
        Sender.send(sendPhoto, this);

        SendAudio sendAudio = new SendAudio();
        sendAudio.setChatId(update.getMessage().getChatId().toString());
        sendAudio.setAudio(new InputFile("https://rr3---sn-ht0hn01-u5nl.googlevideo.com/videoplayback?expire=1739746225&ei=UReyZ_bxOp6Bi9oP4ceByAg&ip=185.213.230.23&id=o-AIyPRW9Ct9yROvvlImhAjC1ejsL6xCM8milo6Nc7V8WH&itag=251&source=youtube&requiressl=yes&xpc=EgVo2aDSNQ%3D%3D&met=1739724625%2C&mh=Hb&mm=31%2C29&mn=sn-ht0hn01-u5nl%2Csn-4g5e6ns7&ms=au%2Crdu&mv=m&mvi=3&pcm2cms=yes&pl=24&rms=au%2Cau&initcwndbps=268750&bui=AUWDL3zUjiXWa9y08b2C6bFvn0reT4sXp14OyOs8Ufc6Qxiur7Y6cidXcht_AYAodau3Jex3ka84DLPr&spc=RjZbSTM-T9CdRuNQedM0cklsWHwOUY5wbEXStLY_nsqPwqHlAfojaPa4Ury9ZXs&vprv=1&svpuc=1&mime=audio%2Fwebm&ns=lKEDjsFbyM47Ukha4CvFgX8Q&rqh=1&gir=yes&clen=33396603&dur=2152.921&lmt=1739591352176487&mt=1739724288&fvip=5&keepalive=yes&lmw=1&fexp=51326932&c=TVHTML5&sefc=1&txp=5532534&n=cNQ3FTAS9zxLBQ&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cxpc%2Cbui%2Cspc%2Cvprv%2Csvpuc%2Cmime%2Cns%2Crqh%2Cgir%2Cclen%2Cdur%2Clmt&lsparams=met%2Cmh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpcm2cms%2Cpl%2Crms%2Cinitcwndbps&lsig=AGluJ3MwRQIhAN-vKfbRzR4XOBsSKFHfR0Oo7i7VfEQXNb8uQfZM-HNFAiBE-bTMs8-hhBqX-bdp4ETqjbBVGP3OqdPh8DQLfrY3Fw%3D%3D&sig=AJfQdSswRgIhAPzXbDfIKJNvCceV2CUwQfyzlIXkyzncanc02vcoPHxUAiEA8LMUqH56CHgQR8HFK6huHFSMELTLn7HjR_Z7qjU1EnE%3D"));
        Sender.send(sendAudio, this);





        final UserEntity user = userRepository.findByTelegramId(update.getMessage().getChatId());
        if (user == null) {
            destinationController.createNewUser(update, this);
        } else {
            destinationController.handle(update);
        }
    }


}
