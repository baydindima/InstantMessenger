package edu.design.messenger.network;


import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

@Slf4j
public class SocketMessageSender extends MessageSenderImpl implements Closeable {

    private Socket socket;

    private OutputStream outputStream;

    @NotNull
    private final String address;

    private final int port;

    public SocketMessageSender(@NotNull String address, int port) {
        this.address = address;
        this.port = port;
    }


    @Override
    @NotNull
    protected OutputStream getOutputStream() throws IOException {
        if (socket == null) {
            try {
                InetAddress inetAddress = InetAddress.getByName(address);
                socket = new Socket(inetAddress, port);
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                log.error("Exception during message sender init", e);
                throw e;
            }
        }
        return outputStream;
    }


    public void close() throws IOException {
        if (socket != null) {
            outputStream.close();
            socket.close();
        }
    }
}
