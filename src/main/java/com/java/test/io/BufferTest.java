package com.java.test.io;



import org.junit.Test;
import java.nio.ByteBuffer;

/**
 * @ClassName TestBuffer
 * @Author yzm
 * @Date 2020/7/29 - 10:30
 * @Email yzm@ogawatech.com.cn
 */
public class BufferTest {

    /**
     * put() 存入数据到缓冲区
     * get() 获取缓冲区中的数据
     * <p>
     * 缓冲区的4个属性
     * capacity  缓冲区容量，缓冲区中最大的数据容量
     * limit     界限，表示缓冲区中可以操作数据的大小，limit后的数据不能读写
     * position  位置，缓冲区中正在操作数据的位置
     * mark      标记，记录当前 position 的位置，可以通过 reset() 恢复到mark位置
     * <p>
     * 0 <= mark <= position <= limit <= capacity
     * <p>
     * 非直接缓冲区：通过 allocate() 分配缓冲区，建立在 JVM 中
     * 直接缓冲区：  通过 allocateDirect() 分配缓冲区，建立在物理磁盘中
     */
    @Test
    public void test3(){
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
    }

    @Test
    public void test2(){
        String str = "string";
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(str.getBytes());
        buffer.flip();
        byte[] dst = new byte[buffer.limit()];
        buffer.get(dst, 0, 2);
        System.out.println(new String(dst, 0, 2));
        buffer.mark();
        System.out.println("buffer.position  " + buffer.position());
        buffer.get(dst, 0, 2);
        System.out.println(new String(dst, 0, 2));
        System.out.println("buffer.position  " + buffer.position());
        buffer.reset();
        System.out.println("buffer.position  " + buffer.position());

    }

    @Test
    public void test1() {
        String str = "string";

        //分配指定大小缓冲区
        System.out.println("--------------------------------------------------------");
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        System.out.println("buffer.position  " + buffer.position());
        System.out.println("buffer.limit     " + buffer.limit());
        System.out.println("buffer.capacity  " + buffer.capacity());

        //存数据
        System.out.println("--------------------------------------------------------");
        buffer.put(str.getBytes());
        System.out.println("buffer.position  " + buffer.position());
        System.out.println("buffer.limit     " + buffer.limit());
        System.out.println("buffer.capacity  " + buffer.capacity());

        //切换成读取模式
        System.out.println("--------------------------------------------------------");
        buffer.flip();
        System.out.println("buffer.position  " + buffer.position());
        System.out.println("buffer.limit     " + buffer.limit());
        System.out.println("buffer.capacity  " + buffer.capacity());

        //读数据
        System.out.println("--------------------------------------------------------");
        byte[] dst = new byte[buffer.limit()];
        buffer.get(dst);
        System.out.println(new String(dst, 0, dst.length));
        System.out.println("buffer.position  " + buffer.position());
        System.out.println("buffer.limit     " + buffer.limit());
        System.out.println("buffer.capacity  " + buffer.capacity());

        //rewind() 可重复读
        System.out.println("--------------------------------------------------------");
        buffer.rewind();
        System.out.println("buffer.position  " + buffer.position());
        System.out.println("buffer.limit     " + buffer.limit());
        System.out.println("buffer.capacity  " + buffer.capacity());

        //clear() 数据依然存在，数据处于被遗忘状态
        buffer.clear();
        System.out.println("--------------------------------------------------------");
        System.out.println("buffer.position  " + buffer.position());
        System.out.println("buffer.limit     " + buffer.limit());
        System.out.println("buffer.capacity  " + buffer.capacity());

    }

}
