package com.voice.test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.voice.bean.RecentChat;
import com.voice.util.FileUtil;

public class TestData {
	private static String[] names={"songhuiqiao.jpg","zhangzetian.jpg","songqian.jpg","hangxiaozhu.jpg","jingtian.jpg"
		,"liuyifei.jpg","kangyikun.jpg","dengziqi.jpg","sysd.jpg"};
	private static String dir=FileUtil.getRecentChatPath();
	public static List<RecentChat> getRecentChats(){
		String path=FileUtil.getRecentChatPath();
		List<RecentChat> chats=new ArrayList<RecentChat>();
		chats.add(new RecentChat("共同关注", "http://tv.cctv.com/lm/gtgz/", "新闻栏目", path+names[0],
				"中央电视台新闻频道一档以公益慈善为品牌特色的日播专题栏目"));
		chats.add(new RecentChat("大爱无声", "http://dianshiju.cntv.cn/program/daaiwusheng/shouye/", "电视剧", path+names[1],
				"主人公杨文军爱上聋哑女孩柳丝语，并最终克服重重阻力，组建家庭、孕育生命"));
		chats.add(new RecentChat("深情密码", "http://v.youku.com/v_show/id_XMTgwNjgyMjk2.html?tpa=dW5pb25faWQ9MTAzNzUzXzEwMDAwMV8wMV8wMQ&x=1", 
				"电视剧", path+names[2],
				"身为天之骄子的戚伟易与不幸女孩赵深深之间的爱情故事"));
		chats.add(new RecentChat("橙色岁月", "http://m.qiqibu.com/neirongye/neirongye75510.htm", "电视剧", path+names[3],
				"攻读社会福利心理学专业的大四学生结成棹，为了毕业后就职的事情四处应聘，他的两位好友矢岛启太工作内定，而整日悠哉地等待毕业；而一向喜欢自由、讨厌刻板上班族生活的相田翔平则在摄影室做助手的故事。"));
		chats.add(new RecentChat("听说", "http://m.ku6.com/show/5aomv1FDbhZ_7AmFuIGV-w...html?ucbrowser", "电影", path+names[4],
				"秧秧和便当店男孩黄天阔互相以为对方是听障人，发生的一段奇妙美好的爱情"));
		chats.add(new RecentChat("十月初五的月光", "http://v.youku.com/v_show/id_XMTQwMjgxMjUwNA==.html?tpa=dW5pb25faWQ9MTAzNzUzXzEwMDAwMV8wMV8wMQ&x=1", 
				"电影", path+names[5],"十月初五街是澳门曾经繁华一时的街道，后来没落。本片中所讲述的故事就发生在这条十月初五街"));
		chats.add(new RecentChat("聆听寂静", "http://www.1905.com/vod/play/333362.shtml?__hz=55a7cf9c71f1c9c4&ref=baidu1905com", "电影", path+names[6],
				"楚天慧出生在河北农村的军人家庭，在不到一周岁的时候，由于注射庆大霉素过量，使她成为了一名聋哑人，"));
		chats.add(new RecentChat("无声的河", "http://v.youku.com/v_show/id_XMTI2MzIxMzgw.html?x=1", "电影", path+names[7],
				"一群聋哑学校学生帮助他们的实习老师，一个正常人走出人生阴影，找回生活信心"));
		chats.add(new RecentChat("手语视点", "http://www.csl-press.com/", "手语视频网站", path+names[8],
				"手语视点成立于2011年10月18日，是一家致力于推广手语视频、传播聋人文化的非牟利网站。"));
		return chats;
	}
	
	public static HashMap<String, String> getFriends(){
		HashMap<String, String> maps=new HashMap<String, String>();
		maps.put("共同关注", dir+"songhuiqiao.jpg");
		maps.put("大爱无声", dir+"zhangzetian.jpg");
		maps.put("深情密码", dir+"songqian.jpg");
		maps.put("橙色岁月", dir+"hangxiaozhu.jpg");
		maps.put("听说", dir+"jingtian.jpg");
		maps.put("十月初五的月光", dir+"liuyifei.jpg");
		maps.put("聆听寂静", dir+"kangyikun.jpg");
		maps.put("无声的河", dir+"dengziqi.jpg");
		maps.put("手语视点", dir+"sysd.jpg");
		
		return maps;
	}
}
