package com.kansa.cjs.kansa.widge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.kansa.cjs.kansa.R;

/**
 * Created by cjs on 2017/2/18.
 * 带删除的edittext
 */

public class MyEditextWithDele extends EditText{
    private Drawable imgInable;
    private Drawable imgAble;
    private Context context;
    public MyEditextWithDele(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    public MyEditextWithDele(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public MyEditextWithDele(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }
    private void init() {
        imgInable=ContextCompat.getDrawable(context, R.mipmap.delete_text_gray);
        imgAble=ContextCompat.getDrawable(context,R.mipmap.delete_text_black);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setDrawable();
            }
        });
        setDrawable();
    }
    //设置删除图片
    private void setDrawable(){
        if (length()<1){
            setCompoundDrawablesWithIntrinsicBounds(null,null,imgInable,null);
        }else {
            setCompoundDrawablesWithIntrinsicBounds(null,null,imgAble,null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (imgAble!=null&&event.getAction()==MotionEvent.ACTION_UP){
            int eventx= (int) event.getRawX();
            int eventy= (int) event.getRawY();
            Rect rect=new Rect();
            getGlobalVisibleRect(rect);
            rect.left=rect.right-200;
            if (rect.contains(eventx,eventy)){
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
