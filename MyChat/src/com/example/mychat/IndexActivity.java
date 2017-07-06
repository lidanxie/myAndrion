package com.example.mychat;

import java.io.FileNotFoundException;

import com.example.common.AtyContainer;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

	//����ʹ�õ���android v4���µ�fragment,�������Ҫ�̳���FragmentActivity��������Activity
	public class IndexActivity extends FragmentActivity implements OnClickListener{
		//�ײ���4�������ؼ�
		private LinearLayout mTabWeixin;
		private LinearLayout mTabFrd;
		private LinearLayout mTabAddress;
		private LinearLayout mTabSetting;
		//�ײ�4�������ؼ��е�ͼƬ��ť
		private ImageButton mImgWeixin;
		private ImageButton mImgFrd;
		private ImageButton mImgAddress;
		private ImageButton mImgSetting;
		//��ʼ��4��Fragment
		private android.support.v4.app.Fragment tab01;
		private android.support.v4.app.Fragment tab02;
		private android.support.v4.app.Fragment tab03;
		private android.support.v4.app.Fragment tab04;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_index);
			 AtyContainer.getInstance().addActivity(this);
			initView();//��ʼ�����е�view
			initEvents();
			setSelect(0);//Ĭ����ʾ΢���������
		}

		private void initEvents() {
			mTabWeixin.setOnClickListener(this);
			mTabFrd.setOnClickListener(this);
			mTabAddress.setOnClickListener(this);
			mTabSetting.setOnClickListener(this);
			
		}
		
		@Override
		protected void onDestroy() {
			super.onDestroy();
	        // ����Activity&��ջ���Ƴ���Activity
	        AtyContainer.getInstance().removeActivity(this);
		}
   
	    //��ʼ�ؼ� 
		private void initView() {
			mTabWeixin = (LinearLayout)findViewById(R.id.id_tab_weixin);
			mTabFrd = (LinearLayout)findViewById(R.id.id_tab_frd);
			mTabAddress = (LinearLayout)findViewById(R.id.id_tab_address);
			mTabSetting = (LinearLayout)findViewById(R.id.id_tab_setting);
			mImgWeixin = (ImageButton)findViewById(R.id.id_tab_weixin_img);
			mImgFrd = (ImageButton)findViewById(R.id.id_tab_frd_img);
			mImgAddress = (ImageButton)findViewById(R.id.id_tab_address_img);
			mImgSetting = (ImageButton)findViewById(R.id.id_tab_setting_img);
			
		}


		public void onClick(View v) {
			resetImg();
			switch (v.getId()) {
			case R.id.id_tab_weixin://�����΢�Ű�ťʱ���л�ͼƬΪ��ɫ���л�fragmentΪ΢���������
				setSelect(0);
				break;
			case R.id.id_tab_frd:
				setSelect(1);
				break;
			case R.id.id_tab_address:
				setSelect(2);
				break;
			case R.id.id_tab_setting:
				setSelect(3);
				break;

			default:
				break;
			}
			
		}

		/*
		 * ��ͼƬ����Ϊ��ɫ�ģ��л���ʾ���ݵ�fragment
		 * */
		private void setSelect(int i) {
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction transaction = fm.beginTransaction();//����һ������
			hideFragment(transaction);//�����Ȱ����е�Fragment�����ˣ�Ȼ�������ٿ�ʼ�������Ҫ��ʾ��Fragment
			switch (i) {
			case 0:
				if (tab01 == null) {
					tab01 = new WeixinFragment();
					/*
					 * ��Fragment��ӵ���У�public abstract FragmentTransaction add (int containerViewId, Fragment fragment)
					*containerViewId��ΪOptional identifier of the container this fragment is to be placed in. If 0, it will not be placed in a container.
					 * */
					transaction.add(R.id.id_content, tab01);//��΢����������Fragment��ӵ�Activity��
				}else {
					transaction.show(tab01);
				}
				mImgWeixin.setImageResource(R.drawable.tab_weixin_pressed);
				break;
			case 1:
				if (tab02 == null) {
					tab02 = new FrdFragment();
					transaction.add(R.id.id_content, tab02);
				}else {
					transaction.show(tab02);
				}
				mImgFrd.setImageResource(R.drawable.tab_find_frd_pressed);
				break;
			case 2:
				if (tab03 == null) {
					tab03 = new ShareFragment();
					transaction.add(R.id.id_content, tab03);
				}else {
					transaction.show(tab03);
				}
				mImgAddress.setImageResource(R.drawable.tab_address_pressed);
				break;
			case 3:
				if (tab04 == null) {
					tab04 = new MyFragment();
					transaction.add(R.id.id_content, tab04);
				}else {
					transaction.show(tab04);
				}
				mImgSetting.setImageResource(R.drawable.tab_settings_pressed);
				break;

			default:
				break;
			}
			transaction.commit();//�ύ����
		}

		/*
		 * �������е�Fragment
		 * */
		private void hideFragment(FragmentTransaction transaction) {
			if (tab01 != null) {
				transaction.hide(tab01);
			}
			if (tab02 != null) {
				transaction.hide(tab02);
			}
			if (tab03 != null) {
				transaction.hide(tab03);
			}
			if (tab04 != null) {
				transaction.hide(tab04);
			}
			
		}

		private void resetImg() {
			mImgWeixin.setImageResource(R.drawable.tab_weixin_normal);
			mImgFrd.setImageResource(R.drawable.tab_find_frd_normal);
			mImgAddress.setImageResource(R.drawable.tab_address_normal);
			mImgSetting.setImageResource(R.drawable.tab_settings_normal);
		}
		
		//���ؼ��������˳�����ȷ��
		  @Override  
		    public boolean onKeyDown(int keyCode, KeyEvent event)  
		    {  
		        if (keyCode == KeyEvent.KEYCODE_BACK )  
		        {  
		            // �����˳��Ի���  
		            AlertDialog isExit = new AlertDialog.Builder(this).create();  
		            // ���öԻ������  
		            isExit.setTitle("ϵͳ��ʾ");  
		            // ���öԻ�����Ϣ  
		            isExit.setMessage("ȷ��Ҫ�˳���");  
		            // ���ѡ��ť��ע�����  
		            isExit.setButton("ȷ��", listener);  
		            isExit.setButton2("ȡ��", listener);  
		            // ��ʾ�Ի���  
		            isExit.show();  
		  
		        }  
		          
		        return false;  
		          
		    }  
		  //�˳�����Ի���ļ����¼�
		  DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()  
		    {  
		        public void onClick(DialogInterface dialog, int which)  
		        {  
		            switch (which)  
		            {  
		            case AlertDialog.BUTTON_POSITIVE:// "ȷ��"��ť�˳�����  
		            	AtyContainer.getInstance().finishAllActivity();
		                break;  
		            case AlertDialog.BUTTON_NEGATIVE:// "ȡ��"�ڶ�����ťȡ���Ի���  
		                break;  
		            default:  
		                break;  
		            }  
		        }  
		    }; 
		    
		//ѡ����Ƭ�ص�����
		@Override  
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	        if (resultCode == RESULT_OK) {  
	        	Log.e("uri", requestCode+""); 
	        	if(requestCode == 2){
	        		Uri uri = data.getData();  
		            Log.e("uri", uri.toString());  
		            ContentResolver cr = this.getContentResolver();  
		            try {  
		                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));  
		                ImageView imageView = (ImageView) tab03.getView().findViewById(R.id.shareimg);
		                imageView.setImageBitmap(bitmap);  
		            } catch (FileNotFoundException e) {  
		                Log.e("Exception", e.getMessage(),e);  
		            }  
	        	}
	        	if(requestCode==1){
	        		Uri uri = data.getData();   
		            ContentResolver cr = this.getContentResolver();  
		            try {  
		                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri)); 
		               
		                ImageView imageView = (ImageView) tab04.getView().findViewById(R.id.ico);  
		                imageView.setImageBitmap(bitmap);  
		            } catch (FileNotFoundException e) {  
		                Log.e("Exception", e.getMessage(),e);  
		            }  
	        	}
	            
	        }
	    }  
	}
