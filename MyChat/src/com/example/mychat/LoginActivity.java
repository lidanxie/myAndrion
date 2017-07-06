package com.example.mychat;

import org.json.JSONException;

import com.example.common.AtyContainer;
import com.example.common.MyURL;
import com.loopj.android.http.*;

import android.R.bool;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class LoginActivity extends Activity {

	private EditText username;
	private EditText password;
	private CheckBox remember;
	private String username_text;
	private String password_text;
	private boolean remember_text;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȡ��������
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		AtyContainer.getInstance().addActivity(this);
		//��ȡ�ؼ� 
	    username = (EditText) findViewById(R.id.username);
	    password = (EditText) findViewById(R.id.password);
	    remember = (CheckBox) findViewById(R.id.remember);
	    //��ȡsharePreference
		sp = this.getSharedPreferences("userinfo", Context.MODE_WORLD_READABLE);
		//����Ѿ���ס����
		if(sp.getBoolean("REMEMBER", false)){
			username.setText(sp.getString("USERNAME", ""));
			password.setText(sp.getString("PASSWORD", ""));
			remember.setChecked(true);
            Intent intent = new Intent(LoginActivity.this,IndexActivity.class);  
            LoginActivity.this.startActivity(intent);  
		}
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		AtyContainer.getInstance().removeActivity(this);
	}
	//ע���¼�����
	public void register(View view){
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);  
        LoginActivity.this.startActivity(intent);  		
	}
	//��¼�¼�����
	public void login(View view){
	    username_text = username.getText().toString();
	    password_text = password.getText().toString();
	    remember_text = remember.isChecked();
		//http post����
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("userid", username_text);
		params.put("pwd", password_text);
		
		//����ǲ��ԵĶ���
		//TODO
		Intent intent = new Intent(LoginActivity.this,IndexActivity.class);  
        LoginActivity.this.startActivity(intent); 
        
        //��������
		client.post(MyURL.LoginURL, params, new JsonHttpResponseHandler(){
			public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
				Editor editor = sp.edit();
				try {
					int returnCode =  (Integer) response.get("returnCode");
					//��¼�ɹ�
					if(returnCode == 1){
						//��ס���빴ѡ
						editor.putString("USERNAME", username_text);
						editor.putString("PASSWORD", password_text);
						if(remember_text){				
							editor.putBoolean("REMEMBER", true);
						}
						editor.commit();
			            Intent intent = new Intent(LoginActivity.this,IndexActivity.class);  
			            LoginActivity.this.startActivity(intent);  
					}else{

						Toast.makeText(getApplicationContext(), "��¼ʧ��",
							     Toast.LENGTH_SHORT).show();
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			};
		});
		
	}
}

