package weixin.popular.api;

import weixin.popular.bean.FollowResult;
import weixin.popular.bean.User;

public class UserAPI extends BaseAPI{

	/**
	 * 获取用户基本信息
	 * @param access_token
	 * @param openid
	 * @return
	 */
	public User userInfo(String access_token,String openid){
		return super.restTemplate.postForObject(
				BASE_URI+"/user/info?access_token={access_token}&openid={openid}&lang=zh_CN",
				null,
				User.class,
				access_token,openid);
	}
	
	/**
	 * 获取关注列表
	 * @param access_token
	 * @param next_openid 第一次获取使用null
	 * @return
	 */
	public FollowResult userGet(String access_token,String next_openid){
		return super.restTemplate.postForObject(
				BASE_URI+"/user/get?access_token={access_token}&next_openid={next_openid}",
				null,
				FollowResult.class,
				access_token,next_openid==null?"":next_openid);
	}
	
}
