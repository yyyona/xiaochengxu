import Toast from "../../utils/toast.js";
let toast=new Toast();
//获取应用实例
const app = getApp()
const rootUrl = app.globalData.rootUrl;
Page({
  /**
   * 页面的初始数据
   */
  data: {
    rootUrl: rootUrl,
    isShowBtn: true,//是否显示按钮
    userInfo: null,
    hasUserInfo: false,
    canIUseGetUserProfile:true,//是否支持 getUserProfile 接口的布尔值
    userData: null,
    storageSize: 0,//当前小程序本地缓存的总大小
    orderNum: {},
    storageUseSize: 0//当前小程序本地缓存已经使用的大小
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //获取缓存
    this.getCache();
    if (app.globalData.userInfo && app.globalData.userInfo.avatarUrl) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      });
    }
    console.log(app.globalData)
    if (app.globalData.userData && app.globalData.userData.id>0) {
      this.setData({
        userData: app.globalData.userData
      });
      console.log("要开始调用countOrder")
      this.countOrder();
    }
  },
  //计算订单数量
  countOrder(e) {
    if (app.globalData.userData) {
      //说明用户已经登录，可以查找
      wx.request({
        url: rootUrl + '/wx/order/countOrderNum',
        data: {
          userId: app.globalData.userData.id
        },
        header: {
          'content-type': 'application/json' // 默认值
        },
        success: (ret) => {
          if (ret.data.code == '0000') {
            app.globalData.orderNum = ret.data.data
            this.setData({
              orderNum:ret.data.data
            })
          }
        }
      })
    }
  },
  //跳到我的订单页面
  toMyOrder(e){
    if (!app.checkLogin()) {
      return false;
    }
    wx.navigateTo({
      url: '/pages/order/order',
    })
  },
  getCache: function (e) {
    //获取当前缓存数据大小
    try {
      const res = wx.getStorageInfoSync()
      this.setData({
        storageSize: res.limitSize,
        storageUseSize: res.currentSize
      });
    } catch (e) {
      // Do something when catch error
    }
  },

// 微信官方提供的获取用户授权接口
  getUserProfile(e) {
    console.log("getUserProfile.....")
    let _this=this;
    // 使用wx.getUserProfile获取用户信息，通过该接口获取用户个人信息均需用户确认
    wx.getUserProfile({
      desc: '用于完善用户资料', // 声明获取用户个人信息后的用途
      success: (res) => {
        console.log("getUserProfile.....",res)
        wx.setStorageSync('userInfo', res.userInfo);
        app.globalData.userInfo=res.userInfo;
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        }, () => {
          if (_this.data.userInfo) {
            wx.navigateTo({
              url: '/pages/login/login',
            })
          }
        })
      }
    })
  },
  
  //跳转到用户个人信息详情页
  toUserInfo: function (event) {
    if (!app.checkLogin()) {
      return false;
    }
    let userId = app.globalData.userData.id;
    wx.navigateTo({
      url: '/pages/userDetail/userDetail?id=' + userId,
    })
  },
  // 调到用户收货地址列表
  toUserAddress:function(event){
    let userId = wx.getStorageSync('userData').id;
    wx.navigateTo({
      url: '/pages/userAddress/userAddress?id=' + userId
    })
  },
  //清除缓存
  toClearCache: function (event) {
    try {
      wx.clearStorage({
        success: res => {
          wx.showToast({
            title: '清理成功！',
            icon: 'success',
            success: (e) => {
              //获取缓存
              this.getCache();
              console.log("清理缓存成功！");
              this.onLoad();
            }
          })
        }
      });
    } catch (e) {
      // Do something when catch error
    }
  },

  //退出登录
  exit(e) {
    app.globalData.userData = null;
    wx.removeStorageSync('userData')
    wx.navigateTo({
      url: '/pages/login/login',
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    console.log(app.globalData)
    if (app.globalData.userData && app.globalData.userData.id > 0) {
      this.setData({
        userData: app.globalData.userData
      });
      console.log("要开始调用countOrder")
      this.countOrder();
    }


  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})
