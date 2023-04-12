//index.js
import Toast from "../../utils/toast.js";
let toast=new Toast();
//获取应用实例
const app = getApp()
const rootUrl = app.globalData.rootUrl;
const rootImgUrl = rootUrl + "/file/fileDown/?saveName=";
Page({
  data: {
    rootUrl: rootUrl,
    rootImgUrl: rootImgUrl,
    StatusBar: app.globalData.StatusBar,
    CustomBar: app.globalData.CustomBar,
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    cardCur: 0,
    goodsList: [
      {}, {}, {}
    ]
  },
  onLaunch: function () {
    let userDataObj = wx.getStorageSync('userData');
    if (userDataObj && userDataObj.id){
      this.globalData.userData = userDataObj;
    }
    let userInfoObj = wx.getStorageSync('userInfo');
    if (userInfoObj && userInfoObj.nickName){
      //说明用户已经授权
        this.globalData.userInfo=userInfoObj;
    }else{
      // 如果用户已经授权则获取用户信息
      wx.getSetting({
        success: res => {
          if (res.authSetting['scope.userInfo']) {
            // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
            wx.getUserInfo({
              success: res => {
                // 可以将 res 发送给后台解码出 unionId
                this.globalData.userInfo = res.userInfo
                // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
                // 所以此处加入 callback 以防止这种情况
                if (this.userInfoReadyCallback) {
                  this.userInfoReadyCallback(res)
                }
              }
            })
          }
        }
      })
    }
  },
  onLoad: function () {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse){
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
    let url="/wx/goods/recommend?pageNum=1&pageSize=6&userId=";
    if(app.globalData.userData){
      url="/wx/goods/recommend?pageNum=1&pageSize=6&userId="+app.globalData.userData.id
    }
    wx.request({
      url: rootUrl + url,
      success: (response) => {
        console.log(response)
        if (response.data.code == '0000') {
          this.setData({
            goodsList: response.data.data.rows
          });
        }
      }
    });
  },
  //跳到商品详情页
  toGoodsDetail: function (event) {
    let goodsId = event.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/goodsDetail/goodsDetail?id=' + goodsId
    })
  },
  getUserInfo: function(e) {
    console.log('getUserInfo.....',e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  }
})
