package com.java.test.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @ClassName TestHttpServerHandler
 * @Author yzm
 * @Date 2020/8/1 - 10:24
 * @Email yzm@ogawatech.com.cn
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * channelRead0 读取客户端数据
     * 说明
     * 1. SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter
     * 2. HttpObject 客户端和服务器端相互通讯的数据被封装成 HttpObject
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {


        System.out.println("对应的channel=            " + ctx.channel());
        System.out.println("pipeline=                 " + ctx.pipeline());
        System.out.println("当前ctx的handler=         " + ctx.handler());
        System.out.println("通过pipeline获取channel:  " + ctx.pipeline().channel());

        //判断 msg 是不是 http request请求
        if (msg instanceof HttpRequest) {

            System.out.println("ctx 类型:                    " + ctx.getClass());
            System.out.println("pipeline hashcode:           " + ctx.pipeline().hashCode());
            System.out.println("TestHttpServerHandler hash:  " + this.hashCode());
            System.out.println("客户端地址:                   " + ctx.channel().remoteAddress());
            System.out.println("msg 类型:                     " + msg.getClass());

            //获取到
            HttpRequest httpRequest = (HttpRequest) msg;
            //获取uri, 过滤指定的资源
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求了 favicon.ico, 不做响应");
                return;
            }

            //回复信息给浏览器 [http协议]
            ByteBuf content = Unpooled.copiedBuffer("hello, 我是服务器", CharsetUtil.UTF_8);
            //构造一个http的相应，即 http response
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            //将构建好 response 返回
            ctx.writeAndFlush(response);

            System.out.println("-------------------------------------------------------------------------------------------");
        }
    }
}
