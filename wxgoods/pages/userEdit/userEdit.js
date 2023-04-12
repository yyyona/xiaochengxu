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
    userData:null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options);
    let userId=options.id;

    wx.getStorage({
      key: 'userInfo',
      success: res =>{
        this.setData({
          userInfo: res.data
        });
      }
    })
    wx.getStorage({
      key: 'userData',
      success: res =>{
        this.setData({
          userData: res.data
        });
      }
    })
  },



//更新页面中输入框的内容
  textareaAInput(e) {
    this.setData({
      textareaAValue: e.detail.value
    })
  },
  textareaBInput(e) {
    this.setData({
      textareaBValue: e.detail.value
    })
  },



  formSubmit: function (e) {

    let _this = this;
    let userId=_this.data.userData.id;
    console.log('form发生了submit事件，携带数据为：', e.detail.value);
    wx.request({
      url: rootUrl + '/wx/user/edit',
      data: {
        name:e.detail.value.name,
        id:_this.data.userData.id,
        mobile:e.detail.value.mobile,
        age:e.detail.value.age
      },
      success: function (res) {
        console.log(res);
        if (res.data.code == '0000') {
          _this.setData({
            userData:res.data.data
          },()=>{
            wx.setStorage({
              data: res.data.data,
              key: 'userData',
            })
            app.globalData.userData = res.data.data;
          })
         
          wx.showToast({
            title: '操作成功！',
            duration: 2000,
            mask: true,
            success: function () {
              setTimeout(function () {
                wx.navigateTo({
                  url: '/pages/userDetail/userDetail?id='+userId,
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

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})