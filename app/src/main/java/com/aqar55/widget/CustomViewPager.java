package com.aqar55.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.Stack;

/**
 * Created by promatics on 10/8/2017.
 */

public class CustomViewPager extends ViewPager {

    private boolean enabled;
    private Stack<Integer> stack = new Stack<>();
    //  public final int EMPTY = -1;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }

  /*  @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        stack.push(getCurrentItem());
        super.setCurrentItem(item, smoothScroll);
    }

    public int popFromBackStack(boolean smoothScroll) {
        if (stack.size()>0)
        {
            super.setCurrentItem(stack.pop(), smoothScroll);
            return getCurrentItem();
        } else return EMPTY;
    }*/
}