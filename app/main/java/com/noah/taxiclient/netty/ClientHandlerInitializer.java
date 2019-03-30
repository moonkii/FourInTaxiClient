package com.noah.taxiclient.netty;

/**
 * Created by bosch on 2017-05-23.
 */

class ClientHandlerInitializer {
//class ClientHandlerInitializer extends ChannelInitializer<SocketChannel> {
//
//    @Override
//    protected void initChannel(SocketChannel socketChannel) throws Exception {
//        ChannelPipeline pipeline = socketChannel.pipeline();
//        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
//        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
//        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
//        pipeline.addLast(new ClientHandler());
//    }
}