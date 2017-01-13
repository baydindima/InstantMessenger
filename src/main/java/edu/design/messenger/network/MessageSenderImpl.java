package edu.design.messenger.network;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Class for send text message through blocking socket.
 */
@Slf4j
public abstract class MessageSenderImpl implements MessageSender {

    @Override
    public void sendMessage(@NotNull final String nick, @NotNull final String messageText) throws IOException {
        Message.TextMessage textMessage = Message.TextMessage.newBuilder()
                .setNick(nick)
                .setText(messageText)
                .build();
        try {
            textMessage.writeTo(getOutputStream());
        } catch (IOException e) {
            log.error("Exception during message sending", e);
            throw e;
        }
    }

    protected abstract OutputStream getOutputStream() throws IOException ;
}
