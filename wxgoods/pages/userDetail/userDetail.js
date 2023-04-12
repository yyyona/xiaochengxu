// pages/userDetail/userDetail.js
import Toast from "../../utils/toast.js";
//获取应用实例
const app = getApp()
const rootUrl = app.globalData.rootUrl;
const rootImgUrl = rootUrl + "/file/fileDown/?saveName=";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    uerDataObj:app.globalData.userData
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if(!this.userDataObj){
      let uerData = wx.getStorageSync('userData');
      this.setData({
        uerDataObj:uerData
      })
     }
   

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },
  ToBack:function(){
    wx.navigateBack({
      delta: 2,
    })
  },
  // delta:表示需要返回的页面数，默认为1
  ToEdit:function(e){

    wx.navigateTo({
      url: '/pages/userEdit/userEdit?id='+this.data.uerDataObj.id,
    })
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