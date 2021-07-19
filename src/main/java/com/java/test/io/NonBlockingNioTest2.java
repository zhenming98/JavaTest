package com.java.test.io;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;

/**
 * @ClassName NonBlockingNioTest2
 * @Author yzm
 * @Date 2020/7/29 - 14:31
 * @Email yzm@ogawatech.com.cn
 */
public class NonBlockingNioTest2 {
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
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
        //切换非阻塞
        sChannel.configureBlocking(false);
        //分配缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //发送数据
        buffer.put(new Date().toString().getBytes());
        buffer.flip();
        sChannel.write(buffer);
        buffer.clear();
        //关闭
        sChannel.close();
    }

    @Test
    public void server() throws IOException {
        //获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //切换非阻塞
        serverSocketChannel.configureBlocking(false);
        //绑定链接
        serverSocketChannel.bind(new InetSocketAddress(9898));
        //获取选择器
        Selector selector = Selector.open();
        //将通道注册到选择器 指定监听事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //轮询获取选择器上已经准备就绪的时间
        while (selector.select() > 0) {
            //获取当前选择器中的所有注册的已就绪的监听事件
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            //迭代
            while (it.hasNext()) {
                //获取准备的事件
                SelectionKey sk = it.next();
                //判断什么事件准备就绪
                if (sk.isAcceptable()) {
                    //获取客户端连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //切换非阻塞
                    socketChannel.configureBlocking(false);
                    //注册到选择器
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (sk.isReadable()) {
                    //获取当前选择器读就绪状态的通道
                    SocketChannel socketChannel = (SocketChannel) sk.channel();
                    //读取数据
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int len = 0;
                    while ((len = socketChannel.read(buffer)) > 0) {
                        buffer.flip();
                        System.out.println(new String(buffer.array(), 0, len));
                        buffer.clear();
                    }
                }
                //取消选择键
                it.remove();
            }
        }
    }
}
