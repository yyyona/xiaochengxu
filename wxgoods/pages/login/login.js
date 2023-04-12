// pages/login/login.js
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
    userInfo:null,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
      });
    }
    app.globalData.userData=null
  },

  formSubmit: function (e) {
    let _this = this;
    console.log('form发生了submit事件，携带数据为：', e.detail.value)
    // 登录
    wx.login({
      success: res => {
        console.log(res);
        let appcode=res.code;
        wx.request({
          url: rootUrl + '/wx/user/wxlogin',
          data: {
            userInfo:app.globalData.userInfo,
            appcode: appcode,
          },
          success: function (res) {
            console.log(res);
            if (res.data.code == '0000') {
              wx.showToast({
                title: '登录成功！',
                duration: 2000,
                mask: true,
                success: function () {
                  app.globalData.userData = res.data.data;
                  wx.setStorageSync('userData', res.data.data)
                  setTimeout(function () {
                    wx.switchTab({
                      url: '/pages/my/my',
                    })
                  }, 2000);
                }
              });
            }else{
              Toast.show('fail',res.data.msg,2000);
            }
          },
          fail: function () {
            Toast.show('fail', res.data.msg, 2000);
          },
          complete: function () {

          }
        })
      }
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


})