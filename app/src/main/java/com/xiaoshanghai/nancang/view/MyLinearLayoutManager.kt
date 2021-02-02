package com.xiaoshanghai.nancang.view

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

class MyLinearLayoutManager : LinearLayoutManager {

     private lateinit var mPagerSnapHelper: PagerSnapHelper
     private var mOnViewPagerListener: OnViewPagerListener? = null
     private lateinit var mRecyclerView: RecyclerView
     private var mDrift: Int = 0//位移，用来判断移动方向

     constructor(context: Context) : this(context, OrientationHelper.VERTICAL)
     constructor(context: Context, orientation: Int) : this(context, orientation, false)
     constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout) {
         mPagerSnapHelper = PagerSnapHelper()
     }

     override fun onAttachedToWindow(view: RecyclerView) {
         super.onAttachedToWindow(view)
         mPagerSnapHelper.attachToRecyclerView(view)//设置RecyclerView每次滚动一页
         mRecyclerView = view
         mRecyclerView.addOnChildAttachStateChangeListener(mChildAttachStateChangeListener)
     }


     /**
      * 滑动状态的改变
      * 缓慢拖拽-> SCROLL_STATE_DRAGGING
      * 快速滚动-> SCROLL_STATE_SETTLING
      * 空闲状态-> SCROLL_STATE_IDLE
      * @param state
      */
     override fun onScrollStateChanged(state: Int) {
             val viewIdle = mPagerSnapHelper.findSnapView(this)
                 val positionIdle = viewIdle?.let { getPosition(it) }
                 if (mOnViewPagerListener != null && childCount == 1) {
                     if (positionIdle != null) {
                         mOnViewPagerListener!!.onPageSelected(positionIdle, positionIdle == itemCount - 1)
                 }
         }
     }

     /**
      * 布局完成后调用
      * @param state
      */
     override fun onLayoutCompleted(state: RecyclerView.State?) {
         super.onLayoutCompleted(state)
         if (mOnViewPagerListener != null) mOnViewPagerListener!!.onLayoutComplete()
     }

     /**
      * 监听竖直方向的相对偏移量
      */
     override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
         this.mDrift = dy
         return super.scrollVerticallyBy(dy, recycler, state)
     }


     /**
      * 监听水平方向的相对偏移量
      */
     override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
         this.mDrift = dx
         return super.scrollHorizontallyBy(dx, recycler, state)
     }

     /**
      * 设置监听
      * @param listener
      */
     fun setOnViewPagerListener(listener: OnViewPagerListener) {
         this.mOnViewPagerListener = listener
     }

     private val mChildAttachStateChangeListener = object : RecyclerView.OnChildAttachStateChangeListener {
         override fun onChildViewAttachedToWindow(view: View) {
         }

         override fun onChildViewDetachedFromWindow(view: View) {
             if (mDrift >= 0) {
                 if (mOnViewPagerListener != null) mOnViewPagerListener!!.onPageRelease(true, getPosition(view))
             } else {
                 if (mOnViewPagerListener != null) mOnViewPagerListener!!.onPageRelease(false, getPosition(view))
             }

         }
     }

     interface OnViewPagerListener{
         /*释放的监听*/
         fun onPageRelease(isNext: Boolean, position: Int)

         /*选中的监听以及判断是否滑动到底部*/
         fun onPageSelected(position: Int, isBottom: Boolean)

         /*布局完成的监听*/
         fun onLayoutComplete()
     }
 }