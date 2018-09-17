# 用高德sdk做一个滴滴司机端的导航。


### 第一步:集成高德sdk
请看这篇文章
[集成Android高德SDK](https://github.com/jikun2008/CustomNaviByGaode/blob/master/%E7%94%B3%E8%AF%B7%E9%AB%98%E5%BE%B7sdk%E7%9A%84ApiKey%E7%9A%84%E6%AD%A5%E9%AA%A4.md)


### 第二步:集成好高德sdk后。我们先了解四个重要的类和它们的一些重要方法。



#### 1. AMapNaviView 导航地图控件。
> 导航路线都是在这个上面绘制的

```java
    //AMapNaviView一些重要方法
    //获取绘制路线所需的Amap类。
    AMapNaviView.getMap()。

```


#### 2. RouteOverLay  用来在AMapNaviView上绘制导航的路线的类。
```java
   //RouteOverLay一些重要方法
   //根据数据创建一个RouteOverLay
   RouteOverLay routeOverLay = new RouteOverLay(Amap, AMapNaviPath, Context);
   //添加到地图上。
   RouteOverLay.addToMap();

```

#### 3. AMapNavi 导航功能类。(这个类是单例模式的) 


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


#### 4. AMapNaviListener 导航信息监听类。

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
### 第三步: 如何使用这4个类。先写个简单的流程。

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


### 第四步: 导航信息的显示。
 
    导航信息就是包括剩余公里 预估时间
    导航信息 需要我们展现出来如下图.
    
    
    
![Alt text](http://pic1.win4000.com/wallpaper/e/526c9f87129d9.jpg)
    
    
  


