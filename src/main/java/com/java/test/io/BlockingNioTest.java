package com.java.test.io;


import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @ClassName BilockingNioTest
 * @Author yzm
 * @Date 2020/7/29 - 14:03
 * @Email yzm@ogawatech.com.cn
 */
public class BlockingNioTest {
    /**
     * NIO 网络通信的三个核心
     * 1、通道 （Channel） 负责链接
     * java.nio.channels.Channel 接口：
     * |-- SelectableChannel
     * |-- SocketChannel         TCP
     * |-- ServerSocketChannel   TCP
     * |-- DatagramChannel       UDP
     * <p>
     * |-- Pipe.SinkChannel
     * |-- Pipe.SourceChannel
     * 2、缓冲器（Buffer）数据的存取
     * 3、选择器（Selector）是 SelectableChannel 的多路复用，监控 SelectableChannel 的 IO 状况
     */

    @Test
    public void client() throws IOException {
        //获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
        FileChannel fileChannel = FileChannel.open(Paths.get("C:\\Users\\Administrator\\Desktop\\1.png"), StandardOpenOption.READ);
        //分配缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //读取文件发送到服务器
        while (fileChannel.read(buffer) != -1) {
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }
        //关闭
        fileChannel.close();
        socketChannel.close();
    }

    @Test
    public void server() throws IOException {
        //获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        FileChannel outChannel = FileChannel.open(Paths.get("C:\\Users\\Administrator\\Desktop\\2.png"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        //绑定链接
        serverSocketChannel.bind(new InetSocketAddress(9898));
        //获取客户端通道
        SocketChannel socketChannel = serverSocketChannel.accept();
        //分配缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //接收客户端数据保存
        while (socketChannel.read(buffer) != -1) {
            buffer.flip();
            outChannel.write(buffer);
            buffer.clear();
        }
        //关闭
        serverSocketChannel.close();
        socketChannel.close();
        outChannel.close();
    }
}
