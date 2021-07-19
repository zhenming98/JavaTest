package com.java.test.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @ClassName NettyServer
 * @Author yzm
 * @Date 2020/7/31 - 16:47
 * @Email yzm@ogawatech.com.cn
 */
public class NettyServer {
    /**
     * 一、Netty抽象出两组线程池 BossGroup 专门负责接收客户端的连接, WorkerGroup 专门负责网络的读写
     * 二、BossGroup 和 WorkerGroup 类型都是 NioEventLoopGroup
     * 三、NioEventLoopGroup 相当于一个事件循环组, 这个组中含有多个事件循环 ，每一个事件循环是 NioEventLoop
     * 四、NioEventLoop 表示一个不断循环的执行处理任务的线程， 每个NioEventLoop 都有一个selector , 用于监听绑定在其上的socket的网络通讯
     * 五、NioEventLoopGroup 可以有多个线程, 即可以含有多个NioEventLoop
     * 六、每个Boss NioEventLoop 循环执行的步骤有3步
     * 1、轮询accept 事件
     * 2、处理accept 事件 , 与 client 建立连接 , 生成NioSocketChannel , 并将其注册到某个 worker NIOEventLoop 上的 selector
     * 3、处理任务队列的任务 , 即 runAllTasks
     * 七、每个 Worker NIOEventLoop 循环执行的步骤
     * 1、轮询read, write 事件
     * 2、处理i/o事件， 即 read , write 事件，在对应 NioSocketChannel 处理
     * 3、处理任务队列的任务 , 即 runAllTasks
     * 八、每个 Worker NIOEventLoop 处理业务时，会使用 pipeline(管道), pipeline 中包含了 channel , 即通过 pipeline 可以获取到对应通道, 管道中维护了很多的处理器
     */
    public static void main(String[] args) throws Exception {

        /**
         * 创建 BossGroup 和 WorkerGroup
         *  1. 创建两个线程组 bossGroup 和 workerGroup
         *  2. bossGroup 只是处理连接请求, 真正的和客户端业务处理, 会交给 workerGroup完成
         *  3. 两个都是无限循环
         *  4. bossGroup 和 workerGroup 含有的子线程(NioEventLoop)的个数
         *     默认实际 cpu核数 * 2
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建服务器段启动对象 配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            //设置线程组
            bootstrap.group(bossGroup, workerGroup)
                    //使用 NioSocketChannel 作为服务器的通道实现
                    .channel(NioServerSocketChannel.class)
                    // 设置线程队列得到连接个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //设置保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //创建一个通道初始化对象(匿名对象)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //给pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //自定义处理器
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("服务器好啦！！！！！！！！！！！！！！！！");
            //启动服务器并绑定端口并且同步 生成一个 ChannelFuture 对象
            ChannelFuture channelFuture = bootstrap.bind(6668).sync();
            //给cf 注册监听器，监控我们关心的事件
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("监听端口 6668 成功");
                    } else {
                        System.out.println("监听端口 6668 失败");
                    }
                }
            });
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
