package com.java.test.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @ClassName Server
 * @Author yzm
 * @Date 2020/7/30 - 15:48
 * @Email yzm@ogawatech.com.cn
 */
public class Server {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private static final int PORT = 6667;

    public static void main(String[] args) {
        //创建服务器对象
        Server chatServer = new Server();
        chatServer.listen();
    }

    public Server() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            while (true) {
                int count = selector.select(2000);
                if (count > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        //连接事件
                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() + "上线......");
                        }
                        //read 事件
                        if (key.isReadable()) {
                            readData(key);
                        }
                        iterator.remove();
                    }
                } else {
//                    System.out.println("等待.....");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public void readData(SelectionKey selectionKey) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = socketChannel.read(buffer);
            if (count > 0) {
                String msg = new String(buffer.array());
                System.out.println(socketChannel.getRemoteAddress() + " 客户端说：" + msg);
                //转发消息
                sendInfoToOtherClients(msg, socketChannel);
            }
        } catch (Exception e) {
            try {
                System.out.println(socketChannel.getRemoteAddress() + " 离线了..");
                //取消注册
                selectionKey.cancel();
                //关闭通道
                socketChannel.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void sendInfoToOtherClients(String msg, SocketChannel socketChannel) throws IOException {
        System.out.println("服务器转发消息中...");
        System.out.println("服务器转发数据给客户端线程: " + Thread.currentThread().getName());
        //遍历 所有注册到selector 上的 SocketChannel,并排除 socketChannel
        for (SelectionKey key : selector.keys()) {
            //通过 key 取出对应的 SocketChannel
            Channel channel = key.channel();
            //排除自己
            if (channel instanceof SocketChannel && channel != socketChannel) {
                //转型
                SocketChannel dest = (SocketChannel) channel;
                //将msg 存储到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将buffer 的数据写入 通道
                dest.write(buffer);
            }
        }
    }

}
