//app.js
import Toast from "/utils/toast.js";
App({
  globalData: {
    userInfo: null,//微信用户信息
    userData:null, //登录后返回的用户信息
    hasUserInfo: false,//标记是否拥有微信用户信息
    canIUse: wx.canIUse('button.open-type.getUserProfile'),//getUserProfile 是否可用的全局变量
    cartNum:0,//购物车商品数量
    orderNum:{},//当前用户订单数量
    rootUrl: "http://localhost:8080"//后端接口地址
  },
  onLaunch: function() {
    let userDataObj = wx.getStorageSync('userData');//获取本地缓存中userdata数据
    if (userDataObj && userDataObj.id){
      this.globalData.userData = userDataObj;
    }
    //从storage中获取微信用户信息
    let userInfoObj = wx.getStorageSync('userInfo');
    if (userInfoObj && userInfoObj.nickName){
      //说明用户已经授权
        this.globalData.userInfo=userInfoObj;
    }
    else{
      // 如果用户已经授权则获取用户信息，进行授权检测
      wx.getSetting({
        success: res => {
          if(!res.authSetting['scope.userInfo']){
            wx.authorize({ scope: 'scope.userInfo', 
                    success(res) {
                      console.log("wx.authorize res 222: ",res);
                    } 
            })
          }
        }
      })
    }
    this.countOrder();
    this.countCartNum();
    // 获取系统状态栏信息
    wx.getSystemInfo({
      success: e => {
        console.log('e:',e)
        this.globalData.StatusBar = e.statusBarHeight;//状态栏的高度
        let capsule = wx.getMenuButtonBoundingClientRect();//获取菜单按钮（右上角胶囊按钮）的布局位置信息
        console.log('capsule:',capsule)
        if (capsule) {
         	this.globalData.Custom = capsule;
        	this.globalData.CustomBar = capsule.bottom + capsule.top - e.statusBarHeight;
        } else {
        	this.globalData.CustomBar = e.statusBarHeight + 50;
        }
      }
    })
  },

  checkLogin:function(e){
    console.log("进入app.checkLogin");
    if(this.globalData.userData){
      return true;
    }else{
      //提示并跳到登录
      Toast.showToLogin(null,null);
    }
  },
  //计算用户订单数量：待付款 待发货 已发货 待评价 
  countOrder(e){
    let rootUrl = this.globalData.rootUrl;
    if (this.globalData.userData){
      //说明用户已经登录，可以查找
      wx.request({
        url: rootUrl + '/wx/order/countOrderNum',
        data: {
          userId: this.globalData.userData.id
        },
        header: {
          'content-type': 'application/json' // 默认值，请求的主体部分是一个 JSON 格式
        },
        success: (ret) => {
          if (ret.data.code == '0000') {
            this.globalData.orderNum=ret.data.data
          } 
        }
      })
    }
  },
  //统计购物车数量
  countCartNum(e){
    let rootUrl = this.globalData.rootUrl;
    if (this.globalData.userData){
      //说明用户已经登录，可以查找
      wx.request({
        url: rootUrl + '/wx/cart/countCartNum',
        data: {
          userId: this.globalData.userData.id
        },
        header: {
          'content-type': 'application/json' // 指定请求格式为json
        },
        success: (ret) => {
          if (ret.data.code == '0000') {
            this.globalData.cartNum=ret.data.data
            let myObject=wx.getStorageSync('myObject');
            if(!myObject){
              myObject={};
            }
            myObject.cartNum=ret.data.data;
            console.log("本次计算购物车数量为：",myObject.cartNum);
            // 放入本地缓存 localstorage
            wx.setStorageSync('myObject', myObject)
          } 
        }
      })
    }
  }
  
})