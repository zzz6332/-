# 红岩网校Androidf方向暑假考核

## 一.APP简要介绍

### 1.界面简要展示（gif可能加载不出来）
![gif](https://github.com/zzz6332/RedrockSummerExam/blob/master/gif/1.gif)

![gif](https://github.com/zzz6332/RedrockSummerExam/blob/master/gif/2.gif)

![gif](https://github.com/zzz6332/RedrockSummerExam/blob/master/gif/3.gif)

![gif](https://github.com/zzz6332/RedrockSummerExam/blob/master/gif/4.gif)

![gif](https://github.com/zzz6332/RedrockSummerExam/blob/master/gif/5.gif)

![gif](https://github.com/zzz6332/RedrockSummerExam/blob/master/gif/6.gif)

![gif](https://github.com/zzz6332/RedrockSummerExam/blob/master/gif/7.gif)

![gif](https://github.com/zzz6332/RedrockSummerExam/blob/master/gif/8.gif)

![gif](https://github.com/zzz6332/RedrockSummerExam/blob/master/gif/9.gif)

![gif](https://github.com/zzz6332/RedrockSummerExam/blob/master/gif/10.gif)

![gif](https://github.com/zzz6332/RedrockSummerExam/blob/master/gif/11.gif)

![gif](https://github.com/zzz6332/RedrockSummerExam/blob/master/gif/12.gif)

![gif](https://github.com/zzz6332/RedrockSummerExam/blob/master/gif/13.gif)

### 2.功能介绍
1.进入app时有欢迎界面

2.阅读文章模块banner实现了轮播效果，可以无限切换。

3.下拉刷新和上拉刷新也可以无限刷新，都有一个自定义的动画效果。

4.点击相应文章会进入阅读文章的页面，上方有进度条显示当前页面的加载进度。

5.备忘录模块可以添加和编辑，删除备忘录，不同的备忘录类型有不同的视觉效果。

6.天气模块在搜索城市时会将搜索历史存在数据库，并流式布局显示出来，用户可也以点击历史搜索。

7.天气还会以前台服务的方式显示。

## 二.主要技术要点
- 1.使用MVVM模式
- 2.使用kotlim编写
- 3.使用Retrofit进行网络请求，并且有3个baseurl，学会了如何在多个baseurl的情况下进行使用Retrofit
- 4.使用了APP类继承Application全局获取context
- 5.使用kotlin的顶层函数替代了以前的工具类
- 6.自定义了一个下拉刷新的PullToDo继承ViewGroup，从而可以实现下拉刷新，下拉添加等一系列下拉之后的动作。让视觉效果更加
- 7.使用ViewPager实现banner的无限轮播效果
- 8.使用Room将数据保存在数据库。备忘录和搜索城市的历史搜索都是存在数据库中，有一个memo表和city表，学会了如何使用Room
- 9.大多数Activity的跳转都有动画
- 10.天气数据还以前台服务的形式显示，学会了如何使用前台服务和更新内容
- 11.使用AppBarLayout，CoordinatorLayout和CollapsingToolbarLayout等实现标题栏错位的视觉效果
- 12.自定义了一个流式布局的控件继承自ViewGroup

# 三.心得体会
在经历了这十天左右的考核后，学到了许多新知识也巩固了旧的知识。使用kotlin更加地熟练了也切身体会到了kotlin的一些优势。学到了如何实现banner的无限轮播和下拉添加，下拉刷新等效果。学习了如何使用Glide，Retrofit，Room这样的第三方库。对MVVM框架的理解也更深了，这次写的时候多加了一个repository包，以前写的时候没有写这个。学到了如何用前台服务显示在通知界面上和更新显示的内容。自定义了4个View，其中2个是继承自Viewgroup的控件，一个是下拉刷新（添加）等动作，一个是流式布局，对View绘制流程的理解更加深刻了。然后所有的图标都是在阿里巴巴矢量图标库下找的，有许多非常实用和好看的图标。[阿里巴巴矢量图标库](https://www.iconfont.cn/)。也遇到了许多坑，比如添加依赖时kotlin要用kapt。总的来说，这一年在红岩网校学到了很多，在学长学姐们的指导与帮助下有了学习的方向和动力，收获了许多东西。接下来的时间里我也会一直努力！
