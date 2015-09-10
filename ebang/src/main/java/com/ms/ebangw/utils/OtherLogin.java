package com.ms.ebangw.utils;

import android.content.Context;


public class OtherLogin {
	//微博登录
	/*public static void login_weibo(Context context) {
		// TODO Auto-generated method stub
//		new Thread(){
//			public void run() {
//
//			}
//		}.start();
		//发起授权请求
		String appkey="2827731967";//申请的appkey
		String redirectUrl="http://www.baidu.com/";//回调的网址
		String scope="all";//权限方面的，all就是所有权限，如果不是所有的每个权限之间用逗号隔开
		WeiboAuth wa=new WeiboAuth(context, appkey, redirectUrl, scope);

		wa.authorize(new WeiboAuthListener() {

			@Override
			public void onWeiboException(WeiboException arg0) {
				// TODO Auto-generated method stub

			}


			//在这个方法里获取code值
			@Override
			public void onComplete(Bundle arg0) {
				// TODO Auto-generated method stub
				//这个code就相当于是口谕，第一步完成
				String code=arg0.getString("code");
				//下面这个接口是认证接口，一定要注意不要弄混
				String url="https://api.weibo.com/oauth2/access_token";
				//下面设置一些微博的参数
				WeiboParameters params=new WeiboParameters();
				params.add("client_id", "2827731967");
				params.add("client_secret", "3ed4807bcc69f4b034d74f9d6467921a");
				params.add("grant_type", "authorization_code");
				params.add("code", code);
				params.add("redirect_uri", "http://www.baidu.com/");//回调的网址，随便填的一个但是；要保证网址是正确的才能使用

				AsyncWeiboRunner.request(url, params, "POST", new RequestListener() {

					@Override
					public void onIOException(IOException e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onError(WeiboException e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onComplete4binary(ByteArrayOutputStream responseOS) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onComplete(String response) {
						// TODO Auto-generated method stub
						//这个response是一个json数据
						Log.i("xxx", response);
						try {
							JSONObject jo = new JSONObject(response);
							String uid = jo.getString("uid");
							String token = jo.getString("access_token");
							postWeibo(token);//发一条微博
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					private void postWeibo(String token) {
						// TODO Auto-generated method stub
						HttpClient hc = new DefaultHttpClient();
						HttpPost hp = new HttpPost("https://api.weibo.com/2/statuses/update.json");
						List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
						parameters.add(new BasicNameValuePair("access_token", token));
						parameters.add(new BasicNameValuePair("status", "你好，你是测试信息"));
						//设置post请求的表单信息
						HttpEntity he = null;
						try {
							he = new UrlEncodedFormEntity(parameters, "UTF-8");
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						hp.setEntity(he);
						try {
							//响应执行好的httppost，发送请求体，创建一个浏览器对象，以把POST对象向服务器发送，并返回响应消息
							HttpResponse hr = hc.execute(hp);
							HttpEntity entity = hr.getEntity();
							String content = EntityUtils.toString(entity, "UTF-8");
							Log.i("xxx", content + "=================");
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}

			@Override
			public void onCancel() {
				// TODO Auto-generated method stub

			}
		}, WeiboAuth.OBTAIN_AUTH_CODE);
	}*/

	public static void login_qq(Context context) {
		// TODO Auto-generated method stub

	}
	public static void login_weixin(Context context) {
		// TODO Auto-generated method stub

	}


}

