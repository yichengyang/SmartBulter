package com.example.fiver.smart_butler.fragment;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.fragment
 *  文件名:  UserFragment
 *  创建者:  YYC
 *  创建时间:  17/6/27 下午7:35
 *  描述:  TODO
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fiver.smart_butler.R;
import com.example.fiver.smart_butler.entity.MyUser;
import com.example.fiver.smart_butler.ui.CourierActivity;
import com.example.fiver.smart_butler.ui.LoginActivity;
import com.example.fiver.smart_butler.ui.PhoneActivity;
import com.example.fiver.smart_butler.utils.L;
import com.example.fiver.smart_butler.utils.ShareUtils;
import com.example.fiver.smart_butler.utils.UtilTools;
import com.example.fiver.smart_butler.view.CustomDialog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserFragment extends Fragment implements View.OnClickListener {
    private Button btn_exit_user;
    private TextView edit_user;
    private EditText et_username;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_desc;
    private Button btn_update_ok;
    private CircleImageView profile_image;
    private CustomDialog dialog;
    //初始化dialog的按钮
    private Button btn_camera;
    private Button btn_picture;
    private Button btn_cancel;
    //物流查询
    private TextView tv_courier;
    //归属地查询
    private TextView tv_phone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        findView(view);
        return view;
    }

    //初始化View
    private void findView(View view) {
        btn_exit_user = (Button) view.findViewById(R.id.btn_exit_user);
        btn_exit_user.setOnClickListener(this);
        edit_user = (TextView) view.findViewById(R.id.edit_user);
        edit_user.setOnClickListener(this);
        et_username = (EditText) view.findViewById(R.id.et_username);
        et_sex = (EditText) view.findViewById(R.id.et_sex);
        et_age = (EditText) view.findViewById(R.id.et_age);
        et_desc = (EditText) view.findViewById(R.id.et_desc);
        btn_update_ok = (Button) view.findViewById(R.id.btn_update_ok);
        btn_update_ok.setOnClickListener(this);
        profile_image = (CircleImageView) view.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);
        UtilTools.getImageToShare(getActivity(),profile_image);

        //物流
        tv_courier = (TextView)view.findViewById(R.id.tv_courier);
        tv_courier.setOnClickListener(this);
        //归属地
        tv_phone = (TextView)view.findViewById(R.id.tv_phone);
        tv_phone.setOnClickListener(this);

        //初始化dialog
        dialog = new CustomDialog(getActivity(), 0, 0,
                R.layout.dialog_photo, R.style.pop_anmi_style, Gravity.BOTTOM, 0);
        //屏幕点击无效
        dialog.setCancelable(false);
        btn_camera = (Button) dialog.findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(this);
        btn_picture = (Button) dialog.findViewById(R.id.btn_picture);
        btn_picture.setOnClickListener(this);
        btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);

        //默认是不可点击的
        setEnabled(false);

        //设置具体的值
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        et_username.setText(userInfo.getUsername());
        et_sex.setText(userInfo.isSex() ? "男" : "女");
        et_age.setText(userInfo.getAge() + "");
        et_desc.setText(userInfo.getDesc());
    }

    //控制焦点
    private void setEnabled(boolean is) {
        et_username.setEnabled(is);
        et_sex.setEnabled(is);
        et_age.setEnabled(is);
        et_desc.setEnabled(is);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //头像编辑
            case R.id.profile_image:
                dialog.show();
                break;
            case R.id.btn_exit_user:
                //退出登录
                MyUser.logOut();
                BmobUser currentUser = MyUser.getCurrentUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;

            case R.id.edit_user:
                btn_update_ok.setVisibility(View.VISIBLE);
                setEnabled(true);
                break;
            case R.id.btn_update_ok:
                //拿到输入框的值
                String username = et_username.getText().toString();
                String age = et_age.getText().toString();
                String sex = et_sex.getText().toString();
                String desc = et_desc.getText().toString();
                //判断是否为空
                if (!TextUtils.isEmpty(username) &
                        !TextUtils.isEmpty(age) &
                        !TextUtils.isEmpty(sex)) {
                    //更新属性
                    MyUser user = new MyUser();
                    user.setUsername(username);
                    user.setAge(Integer.parseInt(age));
                    if (sex.equals("男")) {
                        user.setSex(true);
                    } else {
                        user.setSex(false);
                    }
                    if (!TextUtils.isEmpty(desc)) {
                        user.setDesc(desc);
                    } else {
                        user.setDesc("这个人很懒，什么都没留下！！");
                    }

                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                setEnabled(false);
                                btn_update_ok.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "更新成功", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {

                }
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            case R.id.btn_camera:
                toCamera();
                break;
            case R.id.btn_picture:
                toPicture();
                break;
            case R.id.tv_courier:
                startActivity(new Intent(getActivity(),CourierActivity.class));
                break;
            case R.id.tv_phone:
                startActivity(new Intent(getActivity(),PhoneActivity.class));
        }
    }
    public static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;
    private File tempFile = null;
    //跳转相机
    private void toCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内存卡是否可用，可用直接存储
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent,CAMERA_REQUEST_CODE);
        dialog.dismiss();

    }
    //跳转相册
    private void toPicture(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_REQUEST_CODE);
        dialog.dismiss();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode!=getActivity().RESULT_CANCELED){
            switch (requestCode){
                //相册的数据
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(),PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case RESULT_REQUEST_CODE:
                    if (data!=null){
                        setImageToView(data);
                        if (tempFile!=null){
                            tempFile.delete();
                        }
                    }

                    break;
            }
        }
    }


    private void startPhotoZoom(Uri uri){
        if (uri == null){
            L.e("uri==null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        //设置裁减
        intent.putExtra("crop",true);
        //设置宽高比例
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        //裁减图片质量
        intent.putExtra("outputX",320);
        intent.putExtra("outputY",320);
        //发送数据
        intent.putExtra("return-data",true);
        startActivityForResult(intent,RESULT_REQUEST_CODE);
    }
   //设置图片
    private void setImageToView(Intent data){
        Bundle bundle = data.getExtras();
        if (bundle!=null){
            Bitmap bitmap=bundle.getParcelable("data");
            profile_image.setImageBitmap(bitmap);

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UtilTools.putImageToShare(getContext(),profile_image);
    }
}
