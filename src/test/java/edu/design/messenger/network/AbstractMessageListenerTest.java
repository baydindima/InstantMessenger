package edu.design.messenger.network;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class AbstractMessageListenerTest {

    @Test
    public void test() throws IOException {
        String nick = "Nick";
        String text = "Message text";

        AbstractMessageListener listener = new AbstractMessageListener(textMessage -> {
            assertEquals(nick, textMessage.getNick());
            assertEquals(text, textMessage.getText());
        });

        Message.TextMessage message = Message.TextMessage.newBuilder()
                .setNick(nick)
                .setText(text)
                .build();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(message.toByteArray());

        listener.processMessage(byteArrayInputStream);
    }

}