package com.java.test.io.nio;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @ClassName Client
 * @Author yzm
 * @Date 2020/7/30 - 16:17
 * @Email yzm@ogawatech.com.cn
 */
public class Client {

    private final String HOST = "127.0.0.1";
    private final int PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public static void main(String[] args) throws Exception {

        //启动我们客户端
        Client chatClient = new Client();

        //启动一个线程, 每个3秒，读取从服务器发送数据
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    chatClient.readInfo();
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        //发送数据给服务器端
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            chatClient.sendInfo(s);
        }

    }

    public Client() throws Exception {
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", PORT));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username + " is ok");
    }

    public void sendInfo(String info) {
        info = username + "说" + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readInfo() {
        try {
            int readChannel = selector.select();
            if (readChannel > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        socketChannel.read(buffer);
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                    iterator.remove();
                }
            } else {
                System.out.println("没有可用通道........");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
