package com.java.test.io;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @ClassName ChannelTest
 * @Author yzm
 * @Date 2020/7/29 - 11:11
 * @Email yzm@ogawatech.com.cn
 */

public class ChannelTest {
    /**
     * 通道 Channel ,负责缓冲区中数据的传输
     * <p>
     * 主要实现类
     * java.nio.channels.Channel 接口：
     * |-- FileChannel
     * |-- SocketChannel         TCP
     * |-- ServerSocketChannel   TCP
     * |-- DatagramChannel       UDP
     * <p>
     * 获取通道
     * 支持通道的类提供了 getChannel()
     * 本地 IO
     * FileInputStream/FileOutputStream
     * RandomAccessFile
     * 网络 IO
     * Socket
     * ServerSocket
     * DatagramSocket
     * JDK1.7后的NIO2提供了静态方法 open()
     * JDK1.7后的NIO2的 Files 工具类的 newByteChannel()
     * <p>
     * 通道之间的数据传输
     * transferFrom()
     * transferTo()
     * <p>
     * 分散（Scatter）与聚集（Gather）
     * 分散读取（Scattering Reads）将通道中的数据分散到多个缓冲区
     * 聚集写入（Gathering Writes）将多个缓冲区的数据聚集到通道中
     * <p>
     * 字符集 Charset
     * 编码 字符串 -> 字节数组
     * 解码 字符数组 -> 字符串
     */
    @Test
    public void test3() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

        inChannel.transferTo(0, inChannel.size(), outChannel);
        outChannel.transferFrom(inChannel, 0, inChannel.size());

        inChannel.close();
        outChannel.close();
    }

    @Test
    public void test2() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

        //内存映射文件
        MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        byte[] dst = new byte[inMappedBuf.limit()];
        inMappedBuf.get(dst);
        outMappedBuf.put(dst);

        inChannel.close();
        outChannel.close();
    }

    @Test
    public void test1() throws Exception {
        FileInputStream fis = new FileInputStream("1.jpg");
        FileOutputStream fos = new FileOutputStream("2.jpg");

        //获取通道
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();
        //分配缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //存入缓冲区
        while (inChannel.read(buffer) != -1) {
            //切换读模式
            buffer.flip();
            outChannel.write(buffer);
            buffer.clear();
        }

        outChannel.close();
        inChannel.close();
        fos.close();
        fis.close();
    }
}
