package com.voice.sidebar;




import com.voice.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * User: special
 * Date: 13-12-10
 * Time: 下午11:05
 * Mail: specialcyci@gmail.com
 */
public class ResideMenuItem extends LinearLayout{

    /** menu item  icon  */
    private ImageView iv_icon;
    /** menu item  title */
    private TextView tv_title;
    
    private int id;

    public ResideMenuItem(Context context) {
        super(context);
        initViews(context);
    }

    public ResideMenuItem(Context context, int icon, int title,int id) {
        super(context);
        initViews(context);
        iv_icon.setImageResource(icon);
        tv_title.setText(title);
        this.id=id;
    }

    public ResideMenuItem(Context context, int icon, String title,int id) {
        super(context);
        initViews(context);
        iv_icon.setImageResource(icon);
        tv_title.setText(title);
        this.id=id;
    }

    private void initViews(Context context){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.residemenu_item, this);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        tv_title = (TextView) findViewById(R.id.tv_title);
        this.id=id;
    }

    /**
     * set the icon color;
     *
     * @param icon
     */
    public void setIcon(int icon){
        iv_icon.setImageResource(icon);
    }

    /**
     * set the title with resource
     * ;
     * @param title
     */
    public void setTitle(int title){
        tv_title.setText(title);
    }

    /**
     * set the title with string;
     *
     * @param title
     */
    public void setTitle(String title){
        tv_title.setText(title);
    }
    public int getid(){
    	return this.id;
    }
}
