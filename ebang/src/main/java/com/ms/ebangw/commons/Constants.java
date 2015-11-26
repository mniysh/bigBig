package com.ms.ebangw.commons;

import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.utils.PathUtil;

public class Constants {
	/*======友盟相关 --> start =======*/
	public static final String DESCRIPTOR = "com.umeng.share";

	private static final String TIPS = "请移步官方网站 ";
	private static final String END_TIPS = ", 查看相关说明.";
	public static final String TENCENT_OPEN_URL = TIPS + "http://wiki.connect.qq.com/android_sdk使用说明"
		+ END_TIPS;
	public static final String PERMISSION_URL = TIPS + "http://wiki.connect.qq.com/openapi权限申请"
		+ END_TIPS;

	public static final String SOCIAL_LINK = "http://www.umeng.com/social";
	public static final String SOCIAL_TITLE = "友盟社会化组件帮助应用快速整合分享功能";
	public static final String SOCIAL_IMAGE = "http://www.umeng.com/images/pic/banner_module_social.png";

	public static final String SOCIAL_CONTENT = "友盟社会化组件（SDK）让移动应用快速整合社交分享功能，我们简化了社交平台的接入，为开发者提供坚实的基础服务：（一）支持各大主流社交平台，" +
		"（二）支持图片、文字、gif动图、音频、视频；@好友，关注官方微博等功能" +
		"（三）提供详尽的后台用户社交行为分析。http://www.umeng.com/social";


	/*图片相关*/
	/**
	 * Log和图片存放位置
	 */
	public static final String LOGS_AND_IMGS = PathUtil.getLogPath(MyApplication.getInstance().getApplicationContext()) +
		"/ebangLogs";

	public static final int REQUEST_CAMERA = 2;
	public static final int REQUEST_CROP = 4;
	public static final int REQUEST_PICK = 6;
	/**
	 * 退出
	 */
	public static final int REQUEST_EXIT = 11;


	/*=============友盟相关 --> end ====================*/

//

	/**
	 * 用户类型：个人
	 * 个人 investor，农民工 worker，工长 headman，开发商 developers
	 */
	public static final String INVESTOR ="investor";

	/**
	 * 用户类型：农民工
	 * 个人 investor，农民工 worker，工长 headman，开发商 developers
	 */
	public static final String WORKER = "worker";
	/**
	 * 用户类型：工长
	 * 个人 investor，农民工 worker，工长 headman，开发商 developers
	 */
	public static final String HEADMAN = "headman";
	/**
	 * 用户类型：开发商
	 * 个人 investor，农民工 worker，工长 headman，开发商 developers ,劳务公司 company
	 */
	public static final String DEVELOPERS = "developers";
	/**
	 * 用户类型：开发商
	 * 个人 investor，农民工 worker，工长 headman，开发商 developers ,劳务公司 company
	 */
	public static final String COMPANY = "company";


	/**
	 * 性别：男
	 */
	public static final String MALE = "male";
	/**
	 * 性别：女
	 */
	public static final String FEMALE = "female";
	public static final String MONTH = "month";
	public static final String DAY = "day";


	/*Key=========*/
	public final static String KEY_USER = "user";
	public final static String KEY_BANKS = "bank_list";
	public final static String key_phone = "phone";
	public final static String KEY_CATEGORY = "category";
	public final static String KEY_UPLOAD_IMAGE_RESULT = "UploadImageResult";
	/*验证码*/
	public final static String KEY_VERIFY_CODE = "verify_code";
	public final static String KEY_HEAD_IMAGE = "head_image";
	public final static String KEY_HEAD_IMAGE_STR = "head_image_str";
	public final static String KEY_WHICH_PHOTO = "whichPhoto";
	public final static String KEY_CURRENT_IMAGE_PATH = "current_image_path";
	public final static String KEY_FRONT_IMAGE_PATH = "front_image_path";
	public final static String KEY_BACK_IMAGE_PATH = "back_image_path";
	public final static String KEY_AUTHINFO = "authInfo";
	public final static String KEY_CURRENT_STEP = "step";
	public final static String KEY_PUBLIC_IMAGE = "publicImage";


	public final static int KEY_IMAGE_FLODER = R.drawable.back_ima;

	public final static String IMAGE_FLODER = "image_floder";


	public final static String KEY_PHOTO = "photo";
	/**
	 *  身份证正面
	 */
	public final static String PHOTO_FRONT = "photo_front";
	/**
	 *  身份证背面
	 */
	public final static String PHOTO_BACK = "photo_back";

	/**
	 * 省市区
	 */
	public final static String KEY_TOTAL_REGION = "total_region";

	public final static int KEY_WORK_TYPE = R.drawable.back;

	public final static String KEY_SELECTED_WORKTYPES = "SELECTED_WORKTYPES";


	public final static String KEY_URL = "url";

	/**
	 * 认证状态
	 * //状态游客 guest
	 * 认证中auth_developers(认证开发者中)/
	 * auth_worker(认证工人中)/
	 * auth_headman(认证工头中)/
	 * auth_investor(认证个人中)/
	 * complete（完成认证）
	 */

	/**
	 *
	 */
	public final static String AUTH_GUEST = "guest";
	public final static String AUTH_WORKER = "auth_worker";
	public final static String AUTH_HEADMAN = "auth_headman";
	public final static String AUTH_DEVELOPERS = "auth_developers";
	public final static String AUTH_INVESTOR = "auth_investor";
	public final static String AUTH_COMPLETE = "complete";

	public final  static String LOGOUT = "logout";

	public final static String RELEASE_WORKTYPE_KEY = "release_workType_key";
	public final static String KEY_RELEASE_PROJECT = "release_project";

	/**
	 * 四大工种
	 */
	public final static String CRAFT_BUILDING = "craft_building";
	public final static String CRAFT_DEVORATOR = "craft_devorator";
	public final static String CRAFT_PROJECTMANAGE = "craft_projectmanage";
	public final static String CRAFT_OTHER = "craft_other";
	public static final int KEY_WORKER = R.drawable.back;
	/**
	 * 发布的key
	 */
	public final static String KEY_RELEASE_INFO = "release_info";


	public static final int KEY_POIINFO = R.drawable.back_star;
	public static final String  KEY_POIINFO_STR = "poiInfo";
	public static final int KEY_RELEASED_PROJECT = R.drawable.head;

	public static final String  KEY_RELEASED_PROJECT_STR = "ReleaseProject";

	public static final String KEY_RELEASED_PROJECT_STAFF = "RleaseProjectStaff";
	public static final String KEY_PROJECT_IMAGES = "project_images";

	public static final String KEY_PROJECT_IMAGE_URL = "project_image_url";

	public static final String KEY_MESSAGE_SETTING_ALERT = "message_setting_alert";
	public static final String KEY_MESSAGE_SETTING_RING = "ring";
	public static final String KEY_MESSAGE_SETTING_VIBRATE = "vibrate";
	public static final String KEY_QIANGDAN_SUCCEED = "succeed";
	/**
	 * 首页工程展示工人抢单的状态
	 */
	public static final String KEY_WORKER_SHOW_FILL = "fill";
	public static final String KEY_WORKER_SHOW_NO = "no";
	public static final String KEY_WORKER_SHOW_CONTEND = "contend";
	public static final String KEY_WORKER_SHOW_FAILED = "failed";
	public static final String KEY_WORKER_SHOW_SUCCEED = "succeed";

	public static final String KEY_PROJECT_STATUS = "project_status";
	public static final String KEY_PROJECT_TYPE = "type";
	public static final String KEY_PROJECT_CATEGORY = "category";
	public static final String KEY_PROJECT_TYPE_INVITE = "invite";


	public static final String KEY_INVITE_CATEGORY = "invite_category";
	public static final String KEY_CATEGORY_LIST_TYPE = "category_list_type";
	public static final String KEY_PROJECT_ID = "project_id";

	public static final String CONTEND_STATUS_CONTEND = "contend";
	public static final String CONTEND_STATUS_SUCCEED = "succeed";
	public static final String CONTEND_STATUS_FAILED = "failed";
	public static final int KEY_PEOPLE = R.drawable.border_bg;
	public static final int KEY_SHOW_CRAFT = R.drawable.border_bg;
}
