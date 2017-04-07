package com.voice.business;

import java.util.Locale;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

public class TTS {
	private TextToSpeech tts;
	public void speak(Context context,String toSay){
		tts = new TextToSpeech(context, ttsInitListener);
		tts.speak(toSay, TextToSpeech.QUEUE_FLUSH,
	              null);
		Log.i("speak", toSay);
	}
	
	private TextToSpeech.OnInitListener ttsInitListener = new TextToSpeech.OnInitListener()
	  {

	    @Override
	    public void onInit(int status)
	    {
	      // TODO Auto-generated method stub
	      /* ʹ������ʱ��Ŀǰ��֧������ */
	      Locale loc = new Locale("us", "", "");
	      /* ����Ƿ�֧�������ʱ�� */
	      if (tts.isLanguageAvailable(loc) == TextToSpeech.LANG_AVAILABLE)
	      {
	        /* �趨���� */
	        tts.setLanguage(loc);
	      }
	      tts.setOnUtteranceCompletedListener(ttsUtteranceCompletedListener);
	    }

	  };
	  private TextToSpeech.OnUtteranceCompletedListener ttsUtteranceCompletedListener = new TextToSpeech.OnUtteranceCompletedListener()
	  {
	    @Override
	    public void onUtteranceCompleted(String utteranceId)
	    {
	    }
	  };

}
