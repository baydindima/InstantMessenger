package edu.design.messenger.network;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;


/**
 * Class for sending message to buddy.
 */
public interface MessageSender {

    /**
     * Send message to buddy.
     * @param nick nick of author
     * @param messageText text of message
     */
    void sendMessage(@NotNull final String nick, @NotNull final  String messageText)  throws IOException;

}
