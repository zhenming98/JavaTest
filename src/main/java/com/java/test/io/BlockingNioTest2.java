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
 * @ClassName BlockingNioTest2
 * @Author yzm
 * @Date 2020/7/29 - 14:22
 * @Email yzm@ogawatech.com.cn
 */
public class BlockingNioTest2 {

    private ByteBuffer buffer;

    @Test
    public void client() throws IOException {
        //获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
        FileChannel fileChannel = FileChannel.open(Paths.get("C:\\Users\\Administrator\\Desktop\\1.png"), StandardOpenOption.READ);
        //分配缓冲区
        buffer = ByteBuffer.allocate(1024);
        //读取文件发送到服务器
        while (fileChannel.read(buffer) != -1) {
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }
        //接收反馈
        int len = 0;
        while ((len = socketChannel.read(buffer)) > 0) {
            buffer.flip();
            System.out.println(new String(buffer.array(), 0, len));
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
        //反馈
        buffer.put("123qewasd".getBytes());
        buffer.flip();
        socketChannel.write(buffer);
        //关闭
        serverSocketChannel.close();
        socketChannel.close();
        outChannel.close();
    }

}
