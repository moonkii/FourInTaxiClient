package com.noah.taxiclient.utills;



public class CommonParameters {

    public static final String ip = "172.30.1.54";

    //public static final String ip = "192.168.219.134";
    public static final String SERVER_ADDRESS = "http://115.71.237.225/yolo/";

    //유저들의 연결상태 변경 알림
    public static final int RECONNECTION = 101;
    public static final int CREATE_ROOM = 10;
    public static final int REMOVE_ROOM = 11;
    public static final int ENTER_ROOM = 12;
    public static final int RENTER_ROOM = 13;

    //최초 연결 및 방생성
    //최초 연결 및 방입장
    //현재 액티비티가 꺼진상태에서 다시 연결
    //어플이 꺼진 상태에서 다시 연결
    public static final int LEAVE_ROOM = 14;
    //방정보에 대한 key 값은 ROOM
    //client 부분 msg tag
    public static final int NOTIFICATION_SERVER = 15;

    //메세지 수신을 확인을 위한 TAG
    public static final int STATUS_ACTIVATION = 17;
    public static final int STATUS_INACTIVATION = 18;


    //동기화를 위한 어플리케이션의 ON/OFF 상태 체크

    public static final int STATUS_ON_SERVICE=100;
    public static final int STATUS_OFF_SERVICE =101;

    //메세지 전송 TAG
    public static final int SEND_MSG = 20;

    //받은 메세지에 대한 TAG
    public static final int MSG_FROM_ME = 21;
    public static final int MSG_FROM_OTHER = 22;

    //이미지 전송 TAG
    public static final int SEND_IMG = 30;

    //받은 이미지에 대한 TAG
    public static final int IMG_FROM_ME = 31;
    public static final int IMG_FROM_OTHER = 32;

    //메세지 수신을 위한 단말기 상태 확인
    public static final int NOTIFY_RECYCLER_VIEW = 1000;
    public static final int NOTIFY_RECYCLER_VIEW_ME = 2000;

    //Moim Chat, Open talk chat 나누면 하기로 port 분리 가능능
   public static int moim_port = 8000;
    public static int open_port = 8001;

}
