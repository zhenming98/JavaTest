package com.java.test.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName NettyServerHandler
 * @Author yzm
 * @Date 2020/7/31 - 17:09
 * @Email yzm@ogawatech.com.cn
 * <p>
 * 自定义 Handler 需要集成 Netty 规定好的 HandlerAdapter
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 用于获取客户端发送的信息
     * ChannelHandlerContext ctx: 上下文对象, 含有管道 pipeline （业务）, 通道 channel（数据）
     * Object msg: 客户端发送的消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx = " + ctx);

//        Thread.sleep(5 * 1000);
//        ctx.writeAndFlush(Unpooled.copiedBuffer("aaaaaaaaaaaaaaaaaaaaaaaaa,客户端", CharsetUtil.UTF_8));

        //异步执行 --> taskQueue
//        ctx.channel().eventLoop().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(5 * 1000);
//                    ctx.writeAndFlush(Unpooled.copiedBuffer("aaaaaaaaaaaaa", CharsetUtil.UTF_8));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        //自定义定时任务 --> scheduleTaskQueue
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("aaaaaaaaaaaaa", CharsetUtil.UTF_8));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 5, TimeUnit.SECONDS);

        //将 msg 转为 ByteBuf (Netty 提供, 不是 NIO 的)
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息: " + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址:     " + ctx.channel().remoteAddress());
    }

    /**
     * 数据读取完毕
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        // writeAndFlush 将数据写入缓存并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("hahahahahhahahah,客户端", CharsetUtil.UTF_8));
    }

    /**
     * 处理异常 关闭通道
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
