package com.example.exam.view.fragment

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.exam.R
import com.example.exam.bean.BannerArticle
import com.example.exam.viewmodel.ReadArticleViewModel
import com.example.exam.base.BaseFragment
import com.example.exam.bean.RecomArticle
import com.example.exam.view.adapter.BannerViewPagerAdpater
import com.example.exam.view.adapter.RecomAdapter
import com.example.exam.view.widget.PullToDo
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_read_article.*


class ReadArticleFragment : BaseFragment() {

    private var tl: TabLayout? = null
    private lateinit var rv: RecyclerView
    private lateinit var pullToDo: PullToDo
    private lateinit var viewModel: ReadArticleViewModel
    private lateinit var bannerList: ArrayList<BannerArticle>
    private lateinit var recomList: ArrayList<RecomArticle>
    private lateinit var tvBanner: TextView
    private lateinit var ivPointOne: ImageView
    private lateinit var ivPointTwo: ImageView
    private lateinit var ivPointThree: ImageView
    private lateinit var ivPointFour: ImageView
    private val pointList = ArrayList<ImageView>()
    private var isAnimated = false
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        first = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val window = activity?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val color = activity?.resources?.getColor(R.color.colorPrimary)
        if (color != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window?.statusBarColor = color
            }
        }
        tl = activity?.findViewById(R.id.tl_activity_main)
        inArticle = true
        val view = inflater.inflate(R.layout.fragment_read_article, container, false)
        vpBanner = view.findViewById(R.id.vp_fragment_read_article)
        viewModel = ReadArticleViewModel()
        tvBanner = view.findViewById(R.id.tv_read_article_banner)
        // val typeface = Typeface.createFromAsset(activity?.assets,"baloo_bhai_regular.ttf")
        // tvBanner.typeface = typeface
        pullToDo = view.findViewById(R.id.read_pull_to_refresh)
        ivPointOne = view.findViewById(R.id.iv_point_one)
        ivPointTwo = view.findViewById(R.id.iv_point_two)
        ivPointThree = view.findViewById(R.id.iv_point_three)
        ivPointFour = view.findViewById(R.id.iv_point_four)
        pointList.add(ivPointOne)
        pointList.add(ivPointTwo)
        pointList.add(ivPointThree)
        pointList.add(ivPointFour)
        rv = view.findViewById(R.id.rv_read)
        recomList = ArrayList()
        bannerList = ArrayList()
        val mActivity = activity
        val adapter = RecomAdapter(activity, recomList)
        ImageCarouseHandler.switched = true
        val listener = object : PullToDo.OnPullListener {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onPullUp() {
                //刷新操作
                iv_refresh.visibility = View.VISIBLE
                iv_refresh.startAnim()
                viewModel.isRefresh.value = true
                page++
                Handler().postDelayed({
                    viewModel.getRecomData(BASEURL, page)
                }, 2000)
            }

            override fun onPullMuch() {
                tv_pull_to_refresh.text =
                    activity?.resources?.getString(R.string.pull_to_refresh_much)
                iv_pull_to_refresh.setImageResource(R.drawable.ic_up_to_refresh)
                if (!isAnimated) {
                    val animation = AnimationUtils.loadAnimation(activity, R.anim.anim_pull_much)
                    iv_pull_to_refresh.startAnimation(animation)
                    isAnimated = true
                }
            }

            override fun onPullLittle() {
                tv_pull_to_refresh.text =
                    activity?.resources?.getString(R.string.pull_to_refresh_little)
                iv_pull_to_refresh.setImageResource(R.drawable.ic_down_refresh)
                isAnimated = false
            }
        }
        pullToDo.listener = listener
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(activity)
        var seted = false
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var isToLast = false//判断是否是垂直向下方向滑动
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                isToLast = dy > 0
                pullToDo.canPull = rv.childCount == 0 || rv.getChildAt(0).top >= 0
            }

            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int
            ) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val manger = recyclerView.layoutManager as LinearLayoutManager
                    val last = manger.findLastCompletelyVisibleItemPosition()
                    val total = manger.itemCount
                    if (last == (total - 1)) {
                        page++
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            iv_refresh.startAnim()
                        }
                        Handler().postDelayed({ viewModel.getRecomData(BASEURL, page) }, 2000)
                    }
                }
            }
        })

        if (mActivity != null) {
            viewModel.recomData.observe(this, Observer {
                val refresh = viewModel.isRefresh.value
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    iv_refresh.cancleAnim()
                }
                if (refresh != null) {
                    if (refresh) {
                        recomList.clear()
                        recomList.addAll(it)
                        viewModel.isRefresh.postValue(false)
                        iv_refresh.visibility = View.INVISIBLE
                        adapter.notifyDataSetChanged()
                    } else {
                        recomList.addAll(it)
                        adapter.notifyDataSetChanged()
                    }
                    pullToDo.up = true
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        pullToDo.requestLa()
                    }
                }
            })

            viewModel.bannerData.observe(mActivity,
                Observer {
                    /*  for (element in it){
                          val view = LayoutInflater.from(mActivity).inflate(R.layout.item_vp_banner,null)
                          val iv = view.findViewById<RoundImageView>(R.id.item_vp_banner)
                          loadImage(iv, element.url)
                          list.add(iv)
                      }*/
                    bannerList.clear()
                    bannerList.addAll(it)
                    if (!seted) {
                        val adapter = BannerViewPagerAdpater(activity, bannerList)
                        vpBanner.adapter = adapter
                        seted = false
                    } else {
                        adapter.notifyDataSetChanged()
                    }
                    if (first) {
                        pointList[0].setImageResource(R.drawable.ic_point_selected)
                    }
                })
            viewModel.getBannerData(BASEURL)
            viewModel.getRecomData(BASEURL, page)
            vpBanner.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                    when (state) {
                        ViewPager.SCROLL_STATE_DRAGGING -> {
                            handler.sendEmptyMessage(ImageCarouseHandler.MSG_KEEP_SILGENT)
                        }
                        ViewPager.SCROLL_STATE_IDLE -> {
                            handler.sendEmptyMessageDelayed(
                                ImageCarouseHandler.MSG_UPDATA_IMAGE,
                                ImageCarouseHandler.MSG_DELAY
                            )
                        }
                    }
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {
                    showPoint(position)
                    handler.sendMessage(
                        Message.obtain(
                            handler,
                            ImageCarouseHandler.MSG_PAGE_CHANGED,
                            position,
                            0
                        )
                    )
                }
            })
        }
        handler.sendEmptyMessageDelayed(
            ImageCarouseHandler.MSG_UPDATA_IMAGE,
            ImageCarouseHandler.MSG_DELAY
        )
        return view
    }

    companion object {
        private val BASEURL = "https://www.wanandroid.com/"
        var page = 0
        var inArticle = false
        var first = false
        private lateinit var vpBanner: ViewPager
        val handler: ImageCarouseHandler =
            ImageCarouseHandler { position -> vpBanner.currentItem = position }
    }

    class ImageCarouseHandler(val listener: (position: Int) -> Unit) : Handler() {
        companion object {
            val MSG_UPDATA_IMAGE = 1
            val MSG_KEEP_SILGENT = 2
            val MSG_BREAK_SILENT = 3
            val MSG_PAGE_CHANGED = 4
            val MSG_DELAY: Long = 9000
            var currentItem = 0
            var switched = false
        }

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (!inArticle) {
                return
            }
            if (handler.hasMessages(MSG_UPDATA_IMAGE) && !switched) {
                handler.removeMessages(MSG_UPDATA_IMAGE)
            }
            when (msg.what) {
                MSG_UPDATA_IMAGE -> {
                    currentItem++
                    listener(currentItem)
                    Log.d("handler", "更新banner")
                    handler.sendEmptyMessageDelayed(MSG_UPDATA_IMAGE, MSG_DELAY)
                    switched = false
                }
                MSG_KEEP_SILGENT -> {
                }
                MSG_BREAK_SILENT -> {
                    handler.sendEmptyMessageDelayed(MSG_UPDATA_IMAGE, MSG_DELAY)
                }
                MSG_PAGE_CHANGED -> {
                    currentItem = msg.arg1
                }
            }
        }
    }
    override fun changeBackground() {
        val color = activity?.resources?.getColor(R.color.colorThickGray)
        if (color != null) {
            tl?.setBackgroundColor(color)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun showPoint(position: Int) {
        if ((position % 4) == 0) {
            pointList[0].setImageResource(R.drawable.ic_point_selected)
            for (i in 1..3) {
                pointList[i].setImageResource(R.drawable.ic_point_unselected)
            }
        } else {
            pointList[position % 4].setImageResource(R.drawable.ic_point_selected)
            for (i in 0..3) {
                if (i != position % 4)
                    pointList[i % 4].setImageResource(R.drawable.ic_point_unselected)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        pointList.clear()
        ImageCarouseHandler.switched = false
        inArticle = false
        first = false
    }
}