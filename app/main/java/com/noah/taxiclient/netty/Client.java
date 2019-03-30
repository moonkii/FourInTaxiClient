package com.noah.taxiclient.netty;

/**
 * Created by bosch on 2017-05-23.
 */

public class Client {
//    static EventLoopGroup group = new NioEventLoopGroup();
//    static Channel channel;
//    private final String host;
//    private final int port;
//
//    public Client(String host, int port) {
//        this.host = host;
//        this.port = port;
//    }
//
//    //클라이언트 에선 강제로 끊지 않고, 전송된 코드를 확인해서 서버에서 연결 종료
//    public static void disconnect(){
//        group.shutdownGracefully();
//    }
//
//    //서버에 채팅 내용 전달
//    public static void sendMessage(String msg) {
//        channel.writeAndFlush(msg + "\r\n");
//    }
//
//    //연결 요청.
//    public Channel run() throws InterruptedException, IOException {
//        try {
//            Bootstrap bootstrap = new io.netty.bootstrap.Bootstrap()
//                    .group(group)
//                    .channel(NioSocketChannel.class)
//                    .handler(new ClientHandlerInitializer());
//            channel = bootstrap.connect(host, port).sync().channel();
//            return channel;
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
}
