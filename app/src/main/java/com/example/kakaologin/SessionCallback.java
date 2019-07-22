package com.example.kakaologin;

import android.util.Log;

import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SessionCallback implements ISessionCallback {
    // 로그인에 성공한 상태
    @Override
    public void onSessionOpened() {
        requestMe();
    }

    // 로그인에 실패한 상태
    @Override
    public void onSessionOpenFailed(KakaoException exception) {
        Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());
    }

    // 사용자 정보 요청
    protected void requestMe() {
        List<String> keys = new ArrayList<>();
        keys.add("properties.nickname");
        keys.add("properties.profile_image");
        keys.add("properties.thumbnail_image");
        keys.add("kakao_account.email");
        keys.add("kakao_account.age_range");
        keys.add("kakao_account.gender");

        UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                //redirectLoginActivity();
            }

            @Override
            public void onSuccess(MeV2Response response) {
                UserAccount user = response.getKakaoAccount();
//                Map<String, String> map = response.getProperties();
//                for(String key : map.keySet()){
//
//                    String value = map.get(key);
//
//                    Log.d("property",key+" : "+value);
//
//                }
                //nickname  : response.getNickname()        ex) 김영주
                //userId    : response.getId()              ex) 11xxxxx292
                //email     : user.getEmail()               ex) ejexxx2xx9@gmail.com
                //gender    : user.getGender                ex) MALE
                //age_range : user.getAgeRange              ex) AGE_15_19
                Log.d("Profile name: " , response.getNickname()+"");
                Log.d("Profile user id : " , Long.toString(response.getId())+"");
                Log.d("Profile email: " , user.getEmail()+"");
                Log.d("Profile image: " , response.getProfileImagePath()+"");
                Log.d("Profile thumb: " , response.getThumbnailImagePath()+"");
                Log.d("Profile gender: " , user.getGender()+"");
                Log.d("Profile age: " , user.getAgeRange()+"");

                //redirectMainActivity();
            }

        });
    }


}