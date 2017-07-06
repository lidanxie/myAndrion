package com.example.mychat;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.ShareAdapter;
import com.example.common.AtyContainer;
import com.example.common.MyURL;
import com.example.entity.Share;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyFragment extends Fragment {
	private Button logout_button;
	private TextView myinfo_button;
	private TextView mygallery_button;
	private LinearLayout layout1;
	private LinearLayout layout2;
	private LinearLayout layout3;
	private LinearLayout layout4;
	private SharedPreferences sp;
	private TextView returnview;
	private ImageView ico;
	private TextView name;
	private TextView id;
	private TextView sex;
	private TextView date;
	private ListView mygallerylist;
	private LinearLayout selectico;
	private LinearLayout chengenanme;
	private LinearLayout chengedate;
	private LinearLayout chengesex;
	private TextView textview;
	private Button change;
	private EditText edittext;
	private Button changeinfo;
	private Context context;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//�������ǵĲ���
		return inflater.inflate(R.layout.my, container, false);
	}

	public void onViewCreated(View view, Bundle savedInstanceState) {
		context = this.getActivity().getApplicationContext();
		sp = this.getActivity().getSharedPreferences("userinfo", Context.MODE_WORLD_READABLE);
		logout_button = (Button) view.findViewById(R.id.logout);
		myinfo_button = (TextView) view.findViewById(R.id.myinfo);
		mygallery_button = (TextView) view.findViewById(R.id.mygallery);
		layout1 = (LinearLayout) view.findViewById(R.id.my1);  
		layout2 = (LinearLayout) view.findViewById(R.id.my2);
		layout3 = (LinearLayout) view.findViewById(R.id.my3);
		layout4 = (LinearLayout) view.findViewById(R.id.my4);
		returnview = (TextView) this.getActivity().findViewById(R.id.returnview);
		ico = (ImageView) view.findViewById(R.id.ico);
		name = (TextView) view.findViewById(R.id.info_name);
		id = (TextView) view.findViewById(R.id.info_id);
		sex = (TextView) view.findViewById(R.id.info_sex);
		date = (TextView) view.findViewById(R.id.info_date);
		mygallerylist = (ListView) view.findViewById(R.id.mygallerylist);
		selectico = (LinearLayout) view.findViewById(R.id.selectico);
		chengenanme = (LinearLayout) view.findViewById(R.id.chengenanme);
		chengedate= (LinearLayout) view.findViewById(R.id.chengedate);
		chengesex= (LinearLayout) view.findViewById(R.id.chengesex);
		textview = (TextView) view.findViewById(R.id.textview);
		change = (Button) view.findViewById(R.id.change);
		edittext = (EditText) view.findViewById(R.id.edittext);
		changeinfo = (Button) view.findViewById(R.id.changeinfo);
		logout_button.setOnClickListener(new OnClickListener() {
			//�˳���¼
			public void onClick(View v) {			
				String username = sp.getString("USERNAME", "");
				AsyncHttpClient client = new AsyncHttpClient();
				RequestParams params = new RequestParams();
				params.put("userid", username);
				client.post(MyURL.LogoutURL, params, new JsonHttpResponseHandler(){
					public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
						Editor editor = sp.edit();
						try {
							int returnCode =  (Integer) response.get("returnCode");
							//��¼�ɹ�
							if(returnCode == 1){
								//��ס���빴ѡ
								editor.putString("USERNAME", "");
								editor.putString("PASSWORD", "");			
								editor.putBoolean("REMEMBER", false);
								AtyContainer.getInstance().finishAllActivity();
							}
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					};
				});	
			}
			
		});
		//ѡ��ͷ�񡣻ص�������indexactivity��
		selectico.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				Intent intent = new Intent();  
                /* ����Pictures����Type�趨Ϊimage */  
                intent.setType("image/*");  
                /* ʹ��Intent.ACTION_GET_CONTENT���Action */  
                intent.setAction(Intent.ACTION_GET_CONTENT);   
                /* ȡ����Ƭ�󷵻ر����� */  
                getActivity().startActivityForResult(intent, 1);  
				}
			});
		
		//�޸��û���
		chengenanme.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				textview.setText("�û���");	
				layout2.setVisibility(View.GONE);
				edittext.setText("");
				layout4.setVisibility(View.VISIBLE);
				}
			});
		
		//�޸�����		
		chengedate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				textview.setText("����");
				layout2.setVisibility(View.GONE);
				edittext.setText("");
				layout4.setVisibility(View.VISIBLE);
				}
			});		
		
		//�޸��Ա�
		chengesex.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				textview.setText("�Ա�");
				layout2.setVisibility(View.GONE);
				edittext.setText("");
				layout4.setVisibility(View.VISIBLE);
				}
			});		
		
		//ȷ���޸�
		change.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				String object = (String) textview.getText();
				if(object == "�Ա�"){
					sex.setText(edittext.getText());
				}
				if(object == "�û���"){
					name.setText(edittext.getText());				
				}
				if(object == "����"){
					date.setText(edittext.getText());
				}
				layout4.setVisibility(View.GONE);
				layout2.setVisibility(View.VISIBLE);
				}
			});		
		
		
		//������Ϣ��ȡ
		myinfo_button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {	
//				   AsyncHttpClient client = new AsyncHttpClient();
//				   RequestParams params = new RequestParams();
//				   String username_text = sp.getString("USERNAME", "");
//				   params.put("userid", username_text);
//				   client.post(MyURL.LoginURL, params, new JsonHttpResponseHandler(){
//					   ArrayList<Share>  result = null;
//					   public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
//						   try {
//							  //response.get("img");
//							ico.setImageResource(R.drawable.myinfo);
//							name.setText((String) response.get("name"));
//							id.setText((String) response.get("id"));
//							sex.setText((String) response.get("sex"));
//							date.setText((String) response.get("date"));
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						};
//					});
				layout1.setVisibility(View.GONE);	
				layout2.setVisibility(View.VISIBLE);
				returnview.setVisibility(View.VISIBLE);
			}
		});	
		
		//���ذ�ť
		returnview.setOnClickListener(new OnClickListener() {		
			public void onClick(View v) {
				
				layout1.setVisibility(View.VISIBLE);  
				layout2.setVisibility(View.GONE); 
				layout3.setVisibility(View.GONE);
				layout4.setVisibility(View.GONE);
				returnview.setVisibility(View.GONE);
				
			}
		});
		
		
		
		//�ύ�޸ĵ���Ϣ
		changeinfo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
//			   AsyncHttpClient client = new AsyncHttpClient();
//			   RequestParams params = new RequestParams();
//			   String username_text = sp.getString("USERNAME", "");
//				String id = id.getText();
//				String sex = sex.getText();
//				String date = date.getText();
//			   params.put("userid", username_text);
//				params.put("id", id);
//				params.put("sex", sex);
//				params.put("date", date);
//			   client.post(MyURL.LoginURL, params, new JsonHttpResponseHandler(){
//				   public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
//					Toast.makeText(context, "�޸ĳɹ�",
//					     Toast.LENGTH_SHORT).show();				
//						}   
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					};
//				});
				
			});	
		
		
		//�ҵĸ������
		mygallery_button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {	
				
//			   AsyncHttpClient client = new AsyncHttpClient();
//			   RequestParams params = new RequestParams();
//			   String username_text = sp.getString("USERNAME", "");
//			   params.put("userid", username_text);
//			   client.post(MyURL.LoginURL, params, new JsonHttpResponseHandler(){
//				   ArrayList<Share>  result = null;
//				   public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
//					   try { 
//						   Share s = new Share();
//						    JSONArray jsonArray = (JSONArray) response.get("data");
//						    for(int i=0;i<jsonArray.length();i++){
//						    	JSONObject ob = (JSONObject) jsonArray.get(i);
//						    	s.setIco(ob.getInt("ico"));
//						    	s.setImg(ob.getInt("img"));
//						    	s.setTitle(ob.getString("title"));
//						    	s.setUsername(ob.getString("name"));
//						        result.add(s);
//						    }
//						    ShareAdapter adapter = new ShareAdapter(context,result);
//						    mygallerylist.setAdapter(adapter);  
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					};
//				});
				
				
				//mygallerylist ����
				ArrayList<Share> data= getData();
				ShareAdapter adapter = new ShareAdapter(getActivity(), data);
				mygallerylist.setAdapter(adapter);
				
				
				
				
				
				layout1.setVisibility(View.GONE);  
				layout3.setVisibility(View.VISIBLE);  
				returnview.setVisibility(View.VISIBLE);
			}
			
			private ArrayList<Share> getData() {
				     
				   //����
				   ArrayList<Share> list = new ArrayList<Share>();
			        Share share = new Share();
			        share.setTitle("123");
			        share.setUsername("34455");
			        list.add(share);
			        Share share2 = new Share();
			        share2.setTitle("123");
			        share2.setUsername("34455");
			        share2.setImg(2130837518);
			        list.add(share2);
			        return list;
			    }
		});	
		
		
		
	}
}
