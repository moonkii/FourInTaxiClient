package com.noah.taxiclient.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.noah.taxiclient.Public_Data.Realm_Data_Model;
import com.noah.taxiclient.R;
import com.noah.taxiclient.utills.Network;
import com.noah.taxiclient.utills.item_matching;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Act_set_destination extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    /*#####################################
    *          전역 변수
    * #####################################*/
    SupportMapFragment mapFragment;
    GoogleMap map; //구글맵 참조 변수
    GoogleApiClient mGoogleApiClient;
    Location myLocation; //구글맵 위치 변수
    LocationRequest mLocationRequest; //구글맵 위치 정보 요청 변수
    PendingResult<LocationSettingsResult> result;
    Geocoder geocoder; //주소 지명 찾는 변수
    String myAdress; //주소값
    SharedPreferences LocalDB; //로컬 db
    SharedPreferences.Editor DBEditor; //로컬 db
    final String LocalDBname = "info";
    //위도 경도
    double latitude = 0;
    double longitude = 0;

    boolean isGPSon = false;
    String user_email; //로그인한 이메일.
    boolean isMyLocationLoad = false; //최초 내 위치 표시 했는지 여부
    boolean canCallTaxi=false;

    //뷰 변수
    TextView txt_myLocation; //내위치 표시 텍스트
    Button btn_myLocation; //내위치 갱신버튼
    LottieAnimationView lottieAni; //로티애니메이션
    TextView txt_findLocation, myarrive, mylocation; //현재 위치를 찾는 중입니다 표시
    LinearLayout maplayout;
    Button call; //콜버튼
    TextView arrive;//도착지


    String arr_name = "";
    String arr_add = "";
    String arr_content_ld = "";

    Realm realm;
    RealmResults<Realm_Data_Model> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_set_destination);

        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        Intent get_data = getIntent();
        arr_add = get_data.getExtras().getString("name","");
        arr_name = get_data.getExtras().getString("address","");
        arr_content_ld = get_data.getExtras().getString("content_id","");

        View view = (View) findViewById(R.id.frag_first);

        maplayout = (LinearLayout) view.findViewById(R.id.mapLayout);
        txt_myLocation = (TextView) view.findViewById(R.id.first_mylocation); //내위치 주소
        btn_myLocation = (Button) view.findViewById(R.id.first_btn_mylocation); //내위치 갱신 버튼
        lottieAni = (LottieAnimationView) view.findViewById(R.id.first_mapAni);
        lottieAni.setBackgroundColor(Color.TRANSPARENT);
        call = (Button) view.findViewById(R.id.call); // 호출하기
        LocalDB = getApplicationContext().getSharedPreferences(LocalDBname, 0);
        DBEditor = LocalDB.edit();
        txt_findLocation = (TextView) view.findViewById(R.id.first_findinglocation); // 현재 위치를 찾고 ~
        mylocation = (TextView) view.findViewById(R.id.mylocation); // 출발
        myarrive = (TextView) view.findViewById(R.id.arrive); // 도착
        arrive = (TextView) view.findViewById(R.id.first_arrive); // 목적지 검색

        setUserVisibleHint(false);

        class send_message_handler extends Handler {//매칭의 모든 것을 Network하나로 하기 때문에,

            //응답후 결과는 모두 핸들 메시지를 사용해야함.
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.arg1) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "서버와의 연결이 원활하지 않습니다.", Toast.LENGTH_SHORT).show();

                    case 1:
                        //요청을 보낸 후에 로딩창으로 넘어간다.
                        // 로딩창에서만 운전자가 보내는 승낙 메시지를 받을 수 있다.

                        startActivity(new Intent(getApplicationContext(), Act_loding_matching.class));
                }
            }
        }

        SharedPreferences pref = getApplicationContext().getSharedPreferences("lang", MODE_PRIVATE);
        int language_num = pref.getInt("country", 0);

        if (language_num == 0) {
            txt_myLocation.setText(R.string.korea_frag_first_startposition); // 내 위치 주소
            btn_myLocation.setText(R.string.korea_frag_first_starticon); // 내 위치 갱신 버튼
            mylocation.setText(R.string.korea_frag_first_start); // 출발
            myarrive.setText(R.string.korea_frag_first_arrive); // 도착
            txt_findLocation.setText(R.string.korea_frag_first_findloca); // 현재 위치~
            arrive.setText(R.string.korea_frag_first_arriveposition); // 목적지 주소
            call.setText(R.string.korea_frag_first_calltaxi); // 호출하기
        } else if (language_num == 1) {
            txt_myLocation.setText(R.string.english_frag_first_startposition); // 내 위치 주소
            btn_myLocation.setText(R.string.english_frag_first_starticon); // 내 위치 갱신 버튼
            mylocation.setText(R.string.english_frag_first_start); // 출발
            myarrive.setText(R.string.english_frag_first_arrive); // 도착
            txt_findLocation.setText(R.string.english_frag_first_findloca); // 현재 위치~
            arrive.setText(R.string.english_frag_first_arriveposition); // 목적지 주소
            call.setText(R.string.english_frag_first_calltaxi); // 호출하기
        } else if (language_num == 2) {
            txt_myLocation.setText(R.string.chinese_frag_first_startposition); // 내 위치 주소
            btn_myLocation.setText(R.string.chinese_frag_first_starticon); // 내 위치 갱신 버튼
            mylocation.setText(R.string.chinese_frag_first_start); // 출발
            myarrive.setText(R.string.chinese_frag_first_arrive); // 도착
            txt_findLocation.setText(R.string.chinese_frag_first_findloca); // 현재 위치~
            arrive.setText(R.string.chinese_frag_first_arriveposition); // 목적지 주소
            call.setText(R.string.chinese_frag_first_calltaxi); // 호출하기
        } else if (language_num == 3) {
            txt_myLocation.setText(R.string.japanese_frag_first_startposition); // 내 위치 주소
            btn_myLocation.setText(R.string.japanese_frag_first_starticon); // 내 위치 갱신 버튼
            mylocation.setText(R.string.japanese_frag_first_start); // 출발
            myarrive.setText(R.string.japanese_frag_first_arrive); // 도착
            txt_findLocation.setText(R.string.japanese_frag_first_findloca); // 현재 위치~
            arrive.setText(R.string.japanese_frag_first_arriveposition); // 목적지 주소
            call.setText(R.string.japanese_frag_first_calltaxi); // 호출하기
        } else if (language_num == 4) {
            txt_myLocation.setText(R.string.french_frag_first_startposition); // 내 위치 주소
            btn_myLocation.setText(R.string.french_frag_first_starticon); // 내 위치 갱신 버튼
            mylocation.setText(R.string.french_frag_first_start); // 출발
            myarrive.setText(R.string.french_frag_first_arrive); // 도착
            txt_findLocation.setText(R.string.french_frag_first_findloca); // 현재 위치~
            arrive.setText(R.string.french_frag_first_arriveposition); // 목적지 주소
            call.setText(R.string.french_frag_first_calltaxi); // 호출하기
        }

        if(!arr_name.equals("") && !arr_add.equals("")) {
            arrive.setText(arr_name + " | " + arr_add);
            canCallTaxi = true;
        }
        else{
            canCallTaxi = false;
        }

        arrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Act_arrive.class);
                startActivityForResult(i, 0);
            }
        });


        //호출 클릭
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(canCallTaxi){
                    FirebaseMessaging.getInstance().subscribeToTopic("news");
                    //결국 한 기기에선 똑같은 토큰이 나오게 되있음.
                    String token = FirebaseInstanceId.getInstance().getToken();
                    Log.i("토큰", token);
                    Log.i("토큰", "왜없어");
                    Gson gson = new Gson();
                    Log.i("아오", "y값 : " + longitude);
                    SharedPreferences sp = getApplicationContext().getSharedPreferences("lang", getApplicationContext().MODE_PRIVATE);
                    String lang = sp.getInt("country", 0) + "";
                    String send = gson.toJson(new item_matching(arr_name, token, latitude + "", longitude + "", arr_add, myAdress, "call_driver", lang));
                    Log.i("아하", send);
                    //한글 주소는 위도 경도를 사용하여 알아온다.
                    //token 은 쉐어드 프리퍼런스로 저장된 것을 가져온다.
                    send_message_handler handler = new send_message_handler();//통신 응답 후 처리할 부분을 핸들러에 정의한다.
                    Network.push(send, Act_set_destination.this, handler); //모든 택시에게 콜 메시지를 보낸다.ㅣ


                }else{
                    Toast.makeText(getApplicationContext(), "목적지를 설정해주세요.", Toast.LENGTH_SHORT).show();
                }

//            SharedPreferences preferences;
//            preferences = getActivity().getSharedPreferences("User",getContext().MODE_PRIVATE);
//            SharedPreferences.Editor editor = preferences.edit();
//
//            String uName = preferences.getString("name","사용자");
//
//
//            push(uName); //모든 택시에게 콜 메시지를 보낸다.ㅣ
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == 0 && data != null) {
            arr_name = data.getStringExtra("name");
            arr_add = data.getStringExtra("address");
            arr_content_ld = data.getStringExtra("content_id");

            arrive.setText(arr_name + " | " + arr_add);
            canCallTaxi = true;

            SharedPreferences pref = getApplicationContext().getSharedPreferences("lang", MODE_PRIVATE);
            int language_num = pref.getInt("country", 0);
            String language = "";

            if (language_num == 0) {
                language = "korean";
            } else if (language_num == 1) {
                language = "english";
            } else if (language_num == 2) {
                language = "chinese";
            } else if (language_num == 3) {
                language = "japanese";
            } else if (language_num == 4) {
                language = "french";
            }

            results = realm.where(Realm_Data_Model.class)
                    .distinct(language) //언어별 컬럼
                    .where()
                    .equalTo("content_id", arr_content_ld)
                    .findAll();
            int size = results.size();
            Log.e("Realm_size", String.valueOf(size));

            ArrayList<Realm_Data_Model> list = new ArrayList<>();
            list.clear();

            for (Realm_Data_Model i : results) {
                list.add(i);
            }

            String d = "";

            if(list.size() > 0) {
                try {
                    Realm_Data_Model item = list.get(0);
                    d = item.getKorean();

                    JSONObject jo = new JSONObject(d);
                    arr_name = jo.getString("subject");
                    arr_add = jo.getString("address");
                    Log.e("Realm", arr_name + " / " + arr_add);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!isMyLocationLoad) {
            if (checkGPSService()) {
                initGoogleMap();
                if (mGoogleApiClient != null) {
                    mGoogleApiClient.connect();
                }
            } else {
                checkGPSDialog();
            }
        }


    }

    @Override
    public void onStart() {
        super.onStart();

        //내 위치 갱신 버튼
        btn_myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkGPSService()) {
                    if (latitude != 0 && longitude != 0) {
                        updateMapForMyLocation(latitude, longitude);
                        if (myAdress != null) {
                            txt_myLocation.setText(myAdress);
                        } else {
                            Toast.makeText(getApplicationContext(), "내위치를 다시 갱신해주세요.", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else {
                    checkGPSDialog();
                }


            }
        });
    }


    @Override
    public void onStop() {
        super.onStop();
        //구글 플레이서비스에 연결 해제
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }

    }

    public void setUserVisibleHint(boolean isVisibleToUser) {

        if (!isVisibleToUser) {
            //지도 나타내기
            if (maplayout != null && maplayout.getVisibility() == View.VISIBLE) {
                maplayout.setVisibility(View.INVISIBLE);
            }

            //애니메이션 및 텍스트 사라지기
            if (lottieAni != null && lottieAni.getVisibility() == View.GONE) {
                lottieAni.playAnimation();
                lottieAni.setVisibility(View.VISIBLE);
                txt_findLocation.setVisibility(View.VISIBLE);
            }

        } else {
            if (isMyLocationLoad) {
                if (checkGPSService()) {
                    if (latitude != 0 && longitude != 0) {
                        updateMapForMyLocation(latitude, longitude);
                        if (myAdress != null) {
                            txt_myLocation.setText(myAdress);
                        } else {
                            Toast.makeText(getApplicationContext(), "내위치를 다시 갱신해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    checkGPSDialog();
                }


            }


        }

    }
    //구글 맵 생성
    private void initGoogleMap() {
//        mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.first_map);
        Log.v("##Frag_first", "initgooglemap");

        //지명 찾기용
        geocoder = new Geocoder(getApplicationContext(), Locale.KOREAN);

        mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.first_map, mapFragment).commit();
        mapFragment.getMapAsync(Act_set_destination.this);


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                    .addConnectionCallbacks(Act_set_destination.this)
                    .addOnConnectionFailedListener(Act_set_destination.this)
                    .addApi(LocationServices.API)
                    .build();
        }

        //위치정보 요청 설정
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//                .setInterval(10000) //업데이트 간격
//                .setFastestInterval(5000) //가장 빠른 업데이트 간격
//                .setSmallestDisplacement(100); //이동 거리를 기준으로 업데이트 (미터)

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        result = LocationServices.SettingsApi
                .checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                Log.v("##구글맵 설정", "" + locationSettingsResult.getStatus().toString());
            }
        });


    }

    //구글맵 로딩완료시
    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        UiSettings mapUi = map.getUiSettings();
        mapUi.setZoomControlsEnabled(true); //줌 기능

//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            //현재 위치 컨트롤러 표시
//            map.setMyLocationEnabled(true);
//        }

    }


    private void updateMapForMyLocation(double myLatitude, double myLongtitude) {

        //지도 나타내기
        if (maplayout != null && maplayout.getVisibility() == View.VISIBLE) {
            maplayout.setVisibility(View.INVISIBLE);
        }

        //애니메이션 및 텍스트 사라지기
        if (lottieAni != null && lottieAni.getVisibility() == View.GONE) {
            lottieAni.playAnimation();
            lottieAni.setVisibility(View.VISIBLE);
            txt_findLocation.setVisibility(View.VISIBLE);
        }


        LatLng Loc = new LatLng(myLatitude, myLongtitude); //위도 경도 지정
        if (map != null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(Loc, 17)); //이동

            try {
                List<Address> where = geocoder.getFromLocation(myLatitude, myLongtitude, 1);
                myAdress = where.get(0).getAddressLine(0);

                Log.v("##주소값 : ", "" + where.get(0).getAddressLine(0));
                txt_myLocation.setText(myAdress);

            } catch (IOException e) {
                e.printStackTrace();
            }


            //마커 달기
            map.clear();
            MarkerOptions options = new MarkerOptions();
            options.position(Loc);
            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.my_pindpi)); //BitmapDescriptorFactory.fromResource(R.drawable.station))
            options.title("출발"); // info window의 타이틀
            map.addMarker(options);

            Marker mk1 = map.addMarker(options);
            mk1.showInfoWindow();
        }


        Log.v("##맵 로딩끝!", "로딩됫다!!");

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //지도 나타내기
                if (maplayout != null && maplayout.getVisibility() == View.INVISIBLE) {
                    maplayout.setVisibility(View.VISIBLE);
                }

                //애니메이션 및 텍스트 사라지기
                if (lottieAni != null && lottieAni.getVisibility() == View.VISIBLE) {
                    lottieAni.cancelAnimation();
                    lottieAni.setVisibility(View.GONE);
                    txt_findLocation.setVisibility(View.GONE);
                }
            }
        }, 1000);


    }


    //구글 플레이 서비스 커넥트
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.v("##위치요청", "커넥트됨");
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.v("##위치요청", "정보요청!!!");
            //위치정보 요청
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, Act_set_destination.this);

        }

    }

    //구글 플레이 서비스 커넥트 지연 (타임아웃)
    @Override
    public void onConnectionSuspended(int i) {

    }

    //구글 플레이 서비스 커넥트 실패
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //위치 변경될 경우
    @Override
    public void onLocationChanged(Location location) {

        myLocation = location;
        Log.v("##위치요청", "정보갱신!!");
        if (myLocation != null) {
            //위치받아오기
            latitude = myLocation.getLatitude();
            longitude = myLocation.getLongitude();

            Log.v("##위치요청", "갱신 좌표 위도 : " + myLocation.getLatitude());
            Log.v("##위치요청", "갱신 좌표 경도 : " + myLocation.getLongitude());

            if (!isMyLocationLoad) {
                //처음 한번만 내위치 표시로딩
                updateMapForMyLocation(latitude, longitude);
                isMyLocationLoad = true;
            }


        }
    }


    public boolean checkGPSService() {
        LocationManager manager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            return false;

        } else {
            return true;
        }


    }

    private void checkGPSDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());

        alertDialogBuilder.setTitle("위치 서비스(GPS)를 설정해주세요")
                .setCancelable(false)
                .setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 확인 버튼

                                // GPS설정 화면으로 이동
                                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                startActivity(intent);
                                dialog.dismiss();

                            }
                        })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    private void push(final String send) {
        class checkEmail extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            Response response;
            String get;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getApplicationContext(), "Please Wait", null, true, true); //서버접속하기 전에 로딩 띄우기
                Log.i("Register_Activity", "백그라운드 시작");
            }

            @Override
            protected void onPostExecute(String s) { //서버접속했다면 로딩끄기
                super.onPostExecute(s);
                Log.i("Register_Activity", "백그라운드 끝");
                loading.dismiss();

            }

            @Override
            protected String doInBackground(String... params) {
                //이부분에서 data를 key로  메시지를 보내준다.
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("email", send)
                        .build();

                //request
                Request request = new Request.Builder()
                        .url("http://teampinky.vps.phps.kr/API/call_driver.php")
                        .post(body)
                        .build();

                try {
                    response = client.newCall(request).execute();

                    get = response.body().string();
                    Log.i("Frag_first", get);
                } catch (IOException error) {
                    error.printStackTrace();
                }

                return get;
            }
        }
        checkEmail task = new checkEmail();
        task.execute();
    }
}
