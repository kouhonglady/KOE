package com.voice.sidebar_mylistview;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterData {
	private static String[] function={"��������","����ģʽ","���帨��","���Ի�����","ƴд��ǿ"
		,"�����ǿ"};
	private static String[] detal={"�����������þ�����������","�������ѧϰ���ȣ���̬���Ÿ�ϰ"
		,"��ϰʱ��ͼƬ����ʾ�ִʶ�Ӧ��˼","���������Ȥ�Ż�ѧϰ����","����������ִ���д","�������ס�ִ�����"};

	
	private static  List<Map<String,Object>> getFriends(){
		 List<Map<String,Object>> listItems=new ArrayList <Map<String,Object>>(); 
		for(int i=0;i<6;i++){
		HashMap<String, Object> maps=new HashMap<String, Object>();
		maps.put("character_name",function[i]);
		maps.put("character_shuoming",detal[i]);
		listItems.add(maps);
		}
		return  listItems;
	}
}
