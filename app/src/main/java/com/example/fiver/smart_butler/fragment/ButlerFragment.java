package com.example.fiver.smart_butler.fragment;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.fragment
 *  文件名:  ButlerFragment
 *  创建者:  YYC
 *  创建时间:  17/6/27 下午7:35
 *  描述:  TODO
 */

import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fiver.smart_butler.R;
import com.example.fiver.smart_butler.adapter.ChatListAdapter;
import com.example.fiver.smart_butler.entity.ChatListData;
import com.example.fiver.smart_butler.utils.L;
import com.example.fiver.smart_butler.utils.ShareUtils;
import com.example.fiver.smart_butler.utils.StaticClass;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ButlerFragment extends Fragment implements View.OnClickListener {
    private ListView mChatListView;
    //private Button btn_left,btn_right;
    private List<ChatListData> mList = new ArrayList<>();
    private ChatListAdapter adapter;
    private EditText et_text;
    private Button btn_send;
    //TTS
    private SpeechSynthesizer mTts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_butler, null);
        findView(view);
        return view;
    }

    //初始化view
    private void findView(View view) {
        //语音合成初始化
        mTts = SpeechSynthesizer.createSynthesizer(getActivity(),null);
        //2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”






        mChatListView = (ListView) view.findViewById(R.id.mChatListView);
//        btn_left = (Button)view.findViewById(R.id.btn_left);
//        btn_left.setOnClickListener(this);
//        btn_right = (Button)view.findViewById(R.id.btn_right);
//        btn_right.setOnClickListener(this);
        et_text = (EditText) view.findViewById(R.id.et_text);
        btn_send = (Button) view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);


        //设置适配器

        adapter = new ChatListAdapter(getActivity(), mList);
        mChatListView.setAdapter(adapter);
        addLeftText("你好，我是小优");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                /**
                 * 逻辑
                 * 1.获取输入框内容
                 * 2.判断是否为空
                 * 3.判断长度不能大于30
                 * 4.清空输入框
                 * 5.添加输入的内容到right item
                 * 6.发送机器人请求并返回内容
                 * 7.拿到机器人的返回值后到left item
                 */

                String text = et_text.getText().toString();
                if (!TextUtils.isEmpty(text)){
                    if (text.length()<30){
                        et_text.setText("");
                        addRightText(text);
                        //发送机器人请求并返回内容
                        String url = "http://op.juhe.cn/robot/index?info="+text+"&key="+ StaticClass.CHAT_LIST_KEY;
                        RxVolley.get(url, new HttpCallback() {
                            @Override
                            public void onSuccess(String t) {
                                L.i(t);
                                ParsingText(t);
                            }
                        });

                    }else{
                        Toast.makeText(getActivity(),"输入长度不能超过30",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //添加左边文本
    private void addLeftText(String text) {
        boolean isSpeak = ShareUtils.getBoolean(getActivity(),"isSpeak",false);
        if (isSpeak==true){
            startSpeck(text );
        }

        ChatListData data = new ChatListData();
        data.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        data.setText(text);
        mList.add(data);
        adapter.notifyDataSetChanged();
        mChatListView.setSelection(mChatListView.getBottom());
    }

    //添加右边文本
    private void addRightText(String text) {
        ChatListData data = new ChatListData();
        data.setType(ChatListAdapter.VALUE_RIGHT_TEXT);
        data.setText(text);
        mList.add(data);
        adapter.notifyDataSetChanged();
        mChatListView.setSelection(mChatListView.getBottom());
    }
    //解析Json数据
    public void ParsingText(String t){
        try {
            JSONObject jsonObject = new JSONObject(t);
            //String text = jsonObject.getString("text");
            //L.i(text);
            JSONObject json = jsonObject.getJSONObject("result");
            String text = json.getString("text");
            addLeftText(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    //开始说话
    private void startSpeck(String text){
        mTts.startSpeaking(text,mSynListener);
    }
    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
        }

        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        //开始播放
        public void onSpeakBegin() {
        }

        //暂停播放
        public void onSpeakPaused() {
        }

        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        //恢复播放回调接口
        public void onSpeakResumed() {
        }

        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };
}
