package com.noah.taxiclient.netty;

/**
 * Created by bosch on 2017-06-28.
 */

public class Protocol {
//    static Channel channel=null;
//    static Gson gson;
//    static ChatNoteItem chatNoteItem;
//
//
//    //수행중인 채팅이 있을 경우,
//    //연결이 끊겼을 경우,
//
//    public Protocol() {
//        gson = new Gson();
//    }
//
//    //채팅 서버와 클라이언트 연결
//    public static void ChatConnection(){
//        Log.i("연결시작", "chatConnection");
//        try {
//            Client client = new Client(CommonParameters.ip, CommonParameters.moim_port);
//            Log.i("연결 ip", CommonParameters.ip);
//            channel= client.run();
//        } catch (InterruptedException e){
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//
//    public static void reConnection(String userId)  {
//        try {
//            Client client = new Client(CommonParameters.ip, CommonParameters.moim_port);
//            client.run();
//
//            ChatNoteItem chatNoteItem = new ChatNoteItem(CommonParameters.RECONNECTION, userId, "DEFAULT", ChatNoteItem.currentTime(ChatMoimTalk.time_diff)) ;
//            String message = gson.toJson(chatNoteItem);
//            Client.sendMessage(message);
//            Log.i("다시연결", "reconnection");
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //방만들기
//    public static void CreateChatRoom(String roomId, String userId){
//        //내부 DB에 저장할 채팅 방 생성
//        MoimInfoDataHelper.createObject(roomId);
//        long time = System.currentTimeMillis();
//        gson = new Gson();
//        time = time /1000;
//        String title = userId+"/"+time;
//
//
//        chatNoteItem = new ChatNoteItem(CommonParameters.CREATE_ROOM, userId,roomId, ChatNoteItem.currentTime(ChatMoimTalk.time_diff));
//        String message = gson.toJson(chatNoteItem);
//        Log.i("방생성 json", String.valueOf(message));
//        Client.sendMessage(message);
//
//    }
//
//    //최초 접속과 나중 접속에 대해 생각해 볼 것.
//    public static void enterRoom(String roomId, String userId){
//        gson = new Gson();
//        MoimInfoDataHelper.createObject(roomId);
//        chatNoteItem = new ChatNoteItem(CommonParameters.ENTER_ROOM,  userId, roomId, ChatNoteItem.currentTime(ChatMoimTalk.time_diff));
//        String message = gson.toJson(chatNoteItem);
//        Client.sendMessage(message);
//    }
//
//    public static void renterRoom(String roomId, String userId){
//        gson = new Gson();
//        chatNoteItem = new ChatNoteItem(CommonParameters.RENTER_ROOM,  userId, roomId, ChatNoteItem.currentTime(ChatMoimTalk.time_diff));
//        String message = gson.toJson(chatNoteItem);
//        Client.sendMessage(message);
//        Log.i("재입장", roomId);
//
//    }
//
//
//    //방나가기
//    public static void leaveRoom(String roomId, String userId){
//
//        ChatNoteItem chatNoteItem = new ChatNoteItem(CommonParameters.LEAVE_ROOM, userId, roomId, ChatNoteItem.currentTime(ChatMoimTalk.time_diff));
//        String message = gson.toJson(chatNoteItem);
//        Client.sendMessage(message);
//
//        //realm에 저장된 room정보를 삭제
//        MoimInfoDataHelper.removeMoim(roomId);
//    }

}
