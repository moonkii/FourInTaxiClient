package com.noah.taxiclient.netty;

/**
 * Created by bosch on 2017-05-23.
 */

class ClientHandler {
//class ClientHandler extends SimpleChannelInboundHandler<String> {
//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
//
//    }
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//
//        //값이 들어오면 여기서 입력받은 값을 출력해줌
//        Log.i("[From Server]",msg.toString());
//
//        //수신한 내용을 GSON을 이용해 JSON 형식으로 변환
//        Gson gson = new Gson();
//        ChatNoteItem item=gson.fromJson(msg.toString(), ChatNoteItem.class);
//        //해당 채팅에 참여중인 유저 수
//        int count = item.getCount();
//
//        switch (item.getCode()){
//            //접속중인 채팅에서 변경된 유저의 상태 업데이트 될 경우
//            case CommonParameters.STATUS_ACTIVATION:
//            case CommonParameters.STATUS_INACTIVATION:
////                MoimInfoDataHelper.updateUserStatus(item);
////                break;
////            //해당 채팅에 유저가 추가 될 경우, 상태 업데이트 후,
////            case CommonParameters.ENTER_ROOM:
////                MoimInfoDataHelper.updateUserStatus(item);
//
//                //채팅 내용을 저장하고, 화면 업데이트
//                default:
//                    if(count!=0){
//                        //해당 채팅을 활성화 중인 유저 수
////                        int size =MoimInfoDataHelper.getUserCount(item.getRoomId());
//                        //수신 상태 업데이트
////                        count = count-size;
//                        item.setCount(count);
//                    }
//                    String message = gson.toJson(item);
//                    //채팅 내용  업데이트
////                    ChatMoimTalk.receiveMsg(message);
////                    //채팅 내용 REALM에 저장
////                    MoimInfoDataHelper.addChatItem(item);
//                    break;
//        }
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
//            throws Exception {
//        cause.printStackTrace();
//        ctx.close();
//    }
//
//    //사용자 등록
//    @Override
//    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
//        super.channelRegistered(ctx);
//        Log.i("사용자 등록", ctx.toString());
//    }
//
//    //사용자 해지
//    @Override
//    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
//        super.channelUnregistered(ctx);
//        Log.i("사용자 해제", ctx.toString());
//    }
//
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        super.channelReadComplete(ctx);
//    }
}