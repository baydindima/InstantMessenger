package edu.design.messenger.network;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.*;

public class MessageSenderImplTest {

    @Test
    public void testSend() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        MessageSenderImpl messageSender = new MessageSenderImpl() {
            @Override
            protected OutputStream getOutputStream() throws IOException {
                return byteArrayOutputStream;
            }
        };

        String nick = "Nick";
        String text = "Message text";

        messageSender.sendMessage(nick, text);

        Message.TextMessage textMessage = Message.TextMessage.parseFrom(byteArrayOutputStream.toByteArray());

        assertEquals(nick, textMessage.getNick());
        assertEquals(text, textMessage.getText());
    }

}