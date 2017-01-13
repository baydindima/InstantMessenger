package edu.design.messenger.network;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

@Slf4j
public class SocketMessageListener extends AbstractMessageListener implements Closeable {

    @NotNull
    private final AtomicBoolean isClosed = new AtomicBoolean();

    public SocketMessageListener(@NotNull Consumer<Message.TextMessage> messageCallback) {
        super(messageCallback);
    }

    public void listen(final int port) throws IOException {
        try {
            try (
                    ServerSocket serverSocket = new ServerSocket(port);
                    Socket socket = serverSocket.accept();
                    DataInputStream inputStream = new DataInputStream(socket.getInputStream())
            ) {
                while (!isClosed.get()) {
                    processMessage(inputStream);
                }
            }
        } catch (IOException e) {
            log.error("Error while listening", e);
            throw e;
        }
    }

    @Override
    public void close() throws IOException {
        isClosed.set(true);
    }
}
