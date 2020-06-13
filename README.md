# 用高德sdk做一个滴滴司机端的导航。

> 主要的导航功能是在NaviFragment中。

效果如下:

![image](https://user-gold-cdn.xitu.io/2018/9/18/165eaecd1990b142?w=400&h=711&f=gif&s=583495)

下载apk:[下载地址](https://github.com/jikun2008/JustLikeDidiNavi/blob/master/pic/app-debug.apk)

扫一扫下载apk

![image](https://user-gold-cdn.xitu.io/2018/9/18/165eae455c9ec4b0?w=300&h=300&f=png&s=2653)



## 第一步:集成高德sdk
请看这篇文章
[集成Android高德SDK](https://github.com/jikun2008/CustomNaviByGaode/blob/master/%E7%94%B3%E8%AF%B7%E9%AB%98%E5%BE%B7sdk%E7%9A%84ApiKey%E7%9A%84%E6%AD%A5%E9%AA%A4.md)


## 第二步:四个重要的类


**1.AMapNaviView 导航地图控件,导航路线都是在这个上面绘制的**
 

```java
    //AMapNaviView一些重要方法
    //获取绘制路线所需的Amap类。
    AMapNaviView.getMap()。
    
  
    //AMapNaviView有生命周期方法 //需要我们和Activity或者Fragment的生命周期保持一致。
    //在Activity的onCreate调用 在Fragment的onViewCreate调用
    AMapNaviView.onCreate(savedInstanceState)。
    
    //在Activity或者Fragment的onResume中调用
    AMapNaviView.onResume();   
    
    //在Activity或者Fragment的onPause中调用
    AMapNaviView.onPause();    

    //在Activity或者Fragment的onDestory中调用
    //提示:AMapNaviView是没有onStop方法的,所以不用写。
    AMapNaviView.onDestory();  
    
   
```


**2. RouteOverLay  用来在AMapNaviView上绘制导航的路线的类**

```java
   //RouteOverLay一些重要方法
   //根据数据创建一个RouteOverLay
   RouteOverLay routeOverLay = new RouteOverLay(Amap, AMapNaviPath, Context);
   //添加到地图上。
   RouteOverLay.addToMap();

```

**3. AMapNavi 导航功能类。(这个类是单例模式的)** 


```java
    //主要用来发起导航,可以向AMapNavi设置监听器监听导航中一些信息回调
    //AMapNavi一些重要方法 
    //计算驾车路径(包含起点)。
    AMapNavi.calculateDriveRoute(from, to,wayPoints,strategy); 
    // 添加导航事件回调监听。
    AMapNavi.addAMapNaviListener(listener);
    //开始导航。
    AMapNavi.startNavi(type);
```


**4. AMapNaviListener 导航信息监听类**

```java
    //可以通过AMapNavi.addAMapNaviListener(AMapNaviListener)
    //AMapNaviListener有很多回调方法。这里介绍两个非常重要的回调方法。

    //路线计算成功的回调方法。我们需要在这里做绘制导航路线。
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult)
    {
        
    }
    //路线计算失败的回调方法。我们需要在这里失败的逻辑  比如显示一个按钮 告诉用户重试,重试也是调用AMapNavi.calculateDriveRoute(); 
    public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult){
        
    }

```

**四种触发AMapNaviListener的回调方法onCalculateRouteSuccess 或onCalculateRouteFailure的情况**
    
     
- 第一种情况: 我们主动调用AMapNavi.calculateDriveRoute()方法。前面已经介绍过了。
     
     
- 第二种情况: 我们主动调用AMapNavi.switchParallelRoad()方法。(切换主路或辅路) AMapNaviListener 可以通过notifyParallelRoad(int parallelRoadType)告诉我们是在主路还是在辅路上。
     
     
- 第三种情况: SDK内部通过AMapNaviListener的回调方法onReCalculateRouteForYaw()通知我们准备开始偏航了重新计算路线，
     这个时候也回调onCalculateRouteSuccess或onCalculateRouteFailure。
     
- 第四种情况: SDK内部通过AMapNaviListener的回调方法onReCalculateRouteForTrafficJam()方法通知我们准备开始拥堵重新计算路线，
    这个时候也会回调onCalculateRouteSuccess或onCalculateRouteFailure。
    （非常拥堵重新计算路线这种况其实非常少见）
    
## 第三步: 如何使用这4个类。先写个简单的流程。

```
//获取AMapNaviView地图view
    AMapNaviView naviView=findViewById(R.id.naviView);
    AMapNaviViewOptions options = naviView.getViewOptions();


    //关闭自动绘制路线（如果你想自行绘制路线的话，必须关闭！！！）非常重要
    options.setAutoDrawRoute(false);
    //设置导航UI是否显示
    options.setLayoutVisible(false);
    
    //重新设置一下。
    naviView.setViewOptions(options);


    mAMapNavi=AMapNavi.getInstance(getContext);
   //向AMapNavi设置监听器。
    mAMapNavi.addAMapNaviListener(new AMapNaviListener() {
        @Override
        public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {
            //返回路线成功 这里通过AMapCalcRouteResult绘制路线
            
            //获取RouteOverLay所需要的Amap 如果要把路线绘制到AMapNaviView 请获取AMapNaviView的Amap
            aMap= AMapNaviView.getMap()
            
            //获取返回路线的数组routIDs aMapCalcRouteResult会返回一条或者多条路线。
            //ps:多条路线是用来做多路线选择的功能但是这里我们只做简单导航。所以我们只绘制一条。
            int[] routIds = aMapCalcRouteResult.getRouteid();
            int routeId=routIds[0].
            //通过routeId获取AMapNaviPath数据。
            AMapNaviPath aMapNaviPath=AMapNavi.getNaviPaths().get(routeId);
            //然后就可以创建RouteOverLay了
            RouteOverLay routeOverLay = new RouteOverLay(aMap, aMapNaviPath, context);
            
            
            
            //添加到AMapNaviView上。
            routeOverLay.addToMap();
            
            
            //绘制路线成功后。调用startNavi开始导航。
            //当然你也可以在别的地方调用  AMapNavi.startNavi(); //但是一定要在onCalculateRouteSuccess之后调用。
            mAMapNavi.startNavi();
    
        }

        @Override
        public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {
            //路线计算失败的回调方法。我们需要在这里做失败的逻辑  比如显示一个按钮 告诉用户重试,重试也是重新调用AMapNavi.calculateDriveRoute(); 
            ....
        }
        
    })
    
     //计算驾车路径(包含起点)。会回调 onCalculateRouteSuccess或 onCalculateRouteFailure方法。
     AMapNavi.calculateDriveRoute();
     
```


## 第四步: 导航信息的显示
 
**导航信息:包括剩余公里 预估时间等 需要我们展现出来如下图。**
    
    
![Alt text](https://user-gold-cdn.xitu.io/2018/9/18/165ead5f9d655798?w=401&h=712&f=png&s=174389)

> 其实这些信息我们只需要到 AMapNaviListener 去实现 onNaviInfoUpdate(NaviInfo naviInfo) 就可以拿到

代码如下:
```java
    public void onNaviInfoUpdate(NaviInfo naviInfo) {
        if (null != naviInfo) {
            //获取当前路段剩余距离
            int distance =naviInfo.getCurStepRetainDistance();
            //下一个街道名称
            String roadName = naviInfo.getNextRoadName();
            //获取路线剩余距离(总的路程剩余距离)
            int allDitance =naviInfo.getPathRetainDistance(); 
            //获取路线剩余时间(总的路程剩余时间)
            String allTime = naviInfo.getPathRetainTime()
        
            //获取导航转向图标类型
            int iconType=naviInfo.getIconType()
        }
```

#### 实景图与模型图。

> 什么是实景图和模型图看下面的图片大家就明白了

![image](https://user-gold-cdn.xitu.io/2018/9/18/165ead5f97f533d2?w=500&h=403&f=png&s=166185)


实景图和模型图的也是 需要在AMapNaviListener 中实现下面的方法就可以实现了



```java

   首先我们需要在
    AMapNaviViewOptions options = getNaviView().getViewOptions();
    //设置是否自动显示模型图 这里我们设置为false 
    options.setModeCrossDisplayShow(false);
    AMapNaviView.setViewOptions(options);
    
    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
        //实景图显示 回调

        //展示实景图
        zmLittleInIntersectionView.setImageBitmap(aMapNaviCross.getBitmap());


    }

    @Override
    public void hideCross() {
        //实景图隐藏 回调
  
       
    }

    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {
     
        //模型图显示  回调

        //展示模型图
        modeCrossOverlay.createModelCrossBitMap(aMapModelCross.getPicBuf1(), new AMapModeCrossOverlay.OnCreateBitmapFinish() {
            @Override
            public void onGenerateComplete(Bitmap bitmap, int i) {
                zmLittleInIntersectionView.setImageBitmap(bitmap);
            }
        });

    }

    @Override
    public void hideModeCross() {
    
        //模型图隐藏 回调
        
    }

```

###  锁定自车与全览。

> 什么是锁定自车和全览 


请看下图:



![image](https://user-gold-cdn.xitu.io/2018/9/18/165ead5f98d67152?w=400&h=711&f=gif&s=501075)


> 可以看到 当我们点击全览按钮的时候调用 displayOverview()方法   从锁定自车模式进入了全览路线的模式
点击定位按钮的时候 调用 recoverLockMode()方法进入锁车模式

方法如下

```java

    //恢复锁车状态:用于用户主动恢复之前的导航锁车状态（比如从全览画面，挪动地图后画面返回）
    AMapNaviView.recoverLockMode();
         
  
    //全览可以通过下面的方式设置全览方法的上下左右的范围
    AMapNaviViewOptions options = getNaviView().getViewOptions();
    RouteOverlayOptions routeOverlayOptions = new RouteOverlayOptions();
    //int left, int top, int right, int bottom
    routeOverlayOptions.setRect(new Rect(100, 400, 100, 100));
    options.setRouteOverlayOptions(routeOverlayOptions);
    AMapNaviView.setViewOptions(options);
    //
    
    //全览模式  展示全览：成功算路获得路径之后，可将地图缩放到完全展示该路径
    AMapNaviView.displayOverview();

```


### 导航路线上箭头和走过的灰色路线。

请看下图:

![image](https://user-gold-cdn.xitu.io/2018/9/18/165ead5f9a024ea8?w=450&h=400&f=png&s=148191)


> 这两个效果的实现都要使用 RouteOverLay中的drawArrow方法和 updatePolyline方法
并且要与AMapNaviListener 中的 
onNaviInfoUpdate(NaviInfo naviInfo), 
onLocationChange(AMapNaviLocation aMapNaviLocation)  
配合使用

代码如下:


```java


    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {
        List<NaviLatLng> naviLatLngList = routeOverLay.getArrowPoints(naviInfo.getCurStep());
        //画导航的箭头。
        routeOverLay.drawArrow(naviLatLngList);
    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {
        super.onLocationChange(aMapNaviLocation);
        //画走过的灰色路线
        routeOverLay.updatePolyline(naviLocation);
    }



```


## 结束  

在这里我大概介绍完了总体流程,

其实还有一个切换主路辅路功能没介绍,

当然还有很多细节问题，可以看下代码我写了很多注释。很简单

[高德导航sdk文档](http://a.amap.com/lbs/static/unzip/Android_Navi_Doc/index.html)

或者看下这个工程的代码,主要代码在NaviFragment中。

[源码github](https://github.com/jikun2008/JustLikeDidiNavi)


谢谢大家


--------------------------------------------------------------------------------------------------------------------

    

    

    

        
    
    
  


