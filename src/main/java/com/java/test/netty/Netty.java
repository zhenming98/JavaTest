package com.java.test.netty;

/**
 * @ClassName NettySimple
 * @Author yzm
 * @Date 2020/7/31 - 16:28
 * @Email yzm@ogawatech.com.cn
 * <p>
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
public class Netty {
}
