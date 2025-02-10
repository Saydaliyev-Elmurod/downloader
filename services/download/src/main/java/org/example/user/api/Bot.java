package org.example.user.api;

import it.tdlight.Init;
import it.tdlight.client.*;
import it.tdlight.jni.TdApi;
import it.tdlight.jni.TdApi.*;
import it.tdlight.util.UnsupportedNativeLibraryException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;


@Service
class TelegramBotService implements CommandLineRunner {
    private static final Properties config = loadConfig();
    private static SimpleTelegramClient client;

    private static Properties loadConfig() {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("config.properties")) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            System.err.println("Config.properties file load error.");
            System.exit(1);
        }
        return properties;
    }

    private static final int API_ID = Integer.parseInt(config.getProperty("api_id"));
    private static final String HASH_CODE = config.getProperty("api_hash");
    private static final String PHONE_NUMBER = config.getProperty("phone_number");
    private static final long ADMIN_ID = Long.parseLong(config.getProperty("admin_id"));

    @Override
    public void run(String... args) throws UnsupportedNativeLibraryException {
        startBot();
    }

    @Async
    public void startBot() throws UnsupportedNativeLibraryException {
        Init.init();
        try (SimpleTelegramClientFactory clientFactory = new SimpleTelegramClientFactory()) {
            APIToken apiToken = new APIToken(API_ID, HASH_CODE);
            TDLibSettings settings = TDLibSettings.create(apiToken);
            Path sessionPath = Paths.get("tdlight-session");
            settings.setDatabaseDirectoryPath(sessionPath.resolve("data"));
            settings.setDownloadedFilesDirectoryPath(sessionPath.resolve("downloads"));

            SimpleTelegramClientBuilder clientBuilder = clientFactory.builder(settings);
            SimpleAuthenticationSupplier<?> authenticationData = AuthenticationSupplier.user(PHONE_NUMBER);

            setupHandlers(clientBuilder);
            client = clientBuilder.build(authenticationData);
            client.waitForExit();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setupHandlers(SimpleTelegramClientBuilder clientBuilder) {
        clientBuilder.addUpdateHandler(UpdateAuthorizationState.class, TelegramBotService::onUpdateAuthorizationState);
        clientBuilder.addUpdateHandler(UpdateNewMessage.class, TelegramBotService::onUpdateNewMessage);
    }

    private static void onUpdateAuthorizationState(UpdateAuthorizationState update) {
        if (update.authorizationState instanceof AuthorizationStateReady) {
            System.out.println("Logged in");
        }
    }

    private static void onUpdateNewMessage(TdApi.UpdateNewMessage update) {
        MessageContent messageContent = update.message.content;
        if (messageContent instanceof TdApi.MessageText messageText) {
            sendVideoUsingTDLight(update.message.chatId, "");
        }
    }

    private static void sendVideoUsingTDLight(long chatId, String videoPath) {
        TdApi.InputMessageVideo video = new TdApi.InputMessageVideo();
        video.video = new TdApi.InputFileLocal("/home/elmurod/IdeaProjects/telegram_user_bot/video.mp4");
        video.caption = new TdApi.FormattedText("Mana video!", null);

        TdApi.SendMessage sendMessage = new TdApi.SendMessage();
        sendMessage.chatId = chatId;
        sendMessage.inputMessageContent = video;

        CompletableFuture<Message> send = client.send(sendMessage);
        send.thenAccept(response -> {
            if (response != null && response.content instanceof TdApi.MessageVideo) {
                System.out.println("Video successfully sent!");
            } else {
                System.err.println("Failed to send video!");
            }
        });
    }
}
