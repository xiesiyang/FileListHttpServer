package io.ayang.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;

import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;

public class FileListHttpServerHandler extends ChannelInboundHandlerAdapter {

    private static final AsciiString CONTENT_TYPE = AsciiString.cached("Content-Type");
    private static final AsciiString CONTENT_LENGTH = AsciiString.cached("Content-Length");
    private static final AsciiString CONNECTION = AsciiString.cached("Connection");
    private static final AsciiString KEEP_ALIVE = AsciiString.cached("keep-alive");


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;

            boolean keepAlive = HttpUtil.isKeepAlive(request);

            String uri = request.uri();
            ByteBuf byteBuf = Unpooled.wrappedBuffer(FileListUtil.fileListByPath(uri).getBytes());

            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, byteBuf);
            response.headers().set(CONTENT_TYPE,"text/html;charset=utf-8");
            response.headers().set(CONTENT_LENGTH,byteBuf.readableBytes());

            if(!keepAlive){
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            }else {
                response.headers().set(CONNECTION,KEEP_ALIVE);
                ctx.write(response);
            }

        }
    }
}