// pages/userAddress.js
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
    region: ['北京市', '北京市', '东城区'],
    regionCode: ["110000", "110100", "110101"],
    currReceiverName:"",//当前收货人
    currMobile:"",//当前收货人电话
    detailAddress:"",//详细地址
    currAddress:{},//当前被修改的地址
    postCode:"100032",
    imgList: [],
    modalName: null,
    StatusBar: app.globalData.StatusBar,
    CustomBar: app.globalData.CustomBar,
    Custom: app.globalData.Custom,
    TabCur: 0,
    MainCur: 0,
    VerticalNavTop: 0,
    list: [],//
    rootUrl:rootUrl,
    rootImgUrl:rootImgUrl,
    load: true,
    page: 1,
    userId:'',
    hasMoreData:false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.setData({
      page:1,
      list: []
    });
    this.getList();
  },

  //获取列表数据
  getList: function (e) {
    let userData=wx.getStorageSync('userData');
    if(!userData){
       //提示并跳到登录
       Toast.showToLogin(null,null);
       return false;
    }
    let userId = userData.id;
    this.setData({
      userId:userData.id
    });
    let _this = this;
    //加载数据时，提示用户
    wx.showLoading({
      title: "数据加载中...",
      mask: true
    });
    //通过接口获取数据
    wx.request({
      url: rootUrl + '/wx/userAddress/list',
      data: {
        userId: userId,
        pageNum: this.data.page, //分页
        pageSize: 10 //单页数据量
      },
      header: { 'content-type': 'application/json' }, // 默认值
      success: function (ret) {
        //因为是分页加载的列表，因此需要判断，是否是第一页，是则需要将数据清空，否则将之前的列表数据赋值
        let listData = _this.data.page == 1 ? [] : _this.data.list;
        //接口获取数据成功，将下拉刷新的操作停止（动画停止）
        wx.stopPullDownRefresh();
        //设置数据，将获取到的数据接入之前的数据之后
        _this.setData({
          list: listData.concat(ret.data.data.rows),
          hasFail: false
        });
        //获取的数据条数为0或小于10（10是设置的每页数据条数），则说明之后没有数据了
        if (ret.data.data.rows.length == 0 || ret.data.data.rows.length < 10) {
          _this.setData({ hasMoreData: false });
        } else {
          //否则之后还有数据，将页数加1
          _this.setData({
            hasMoreData: true,
            page: ++_this.data.page
          })
        };
      },
      fail: function () {
        _this.setData({
          hasFail: true
        });
      },
      complete: function () {
        wx.hideLoading();
      }
    })
  },


  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {
    wx.hideLoading()
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  onPullDownRefresh: function () {
    //下拉刷新，由于我的列表数据是分页的，所以下拉刷新时，需要将page置为1，再调用获取列表数据的方法
    this.setData({ hasMoreData: true, page: 1 ,totalPrice:0}, () => this.getList());

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    //上拉加载，判断是否还有数据，如果没有数据，则不执行获取数据的方法，避免重复无用的调用接口
    this.data.hasMoreData ? this.getList() : null;

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  //弹出编辑地址框
  editAddressShow(e){
    console.log(e);
    let currItem=e.currentTarget.dataset.item;
    let region=Array.of(currItem.provinceName,currItem.cityName,currItem.areaName);
    let regionCode=Array.of(currItem.provinceCode,currItem.cityCode,currItem.areaCode);
    let currMobile=currItem.mobile;
    let currReceiverName=currItem.receiverName;
    console.log(region);
    this.setData({
      modalName:'editAddress',
      currAddress:e.currentTarget.dataset.item,
      postCode:currItem.postCode,
      region:region,
      regionCode:regionCode,
      detailAddress:currItem.field0,
      currMobile:currMobile,
      currReceiverName:currReceiverName
    });
  },
  hideModal(e) {
    this.setData({
      modalName: null
    })
  },
  // 收货人
  changeReceiverName(e){
    console.log("changeReceiverName : ",e);
    this.setData({
      currReceiverName:e.detail.value
    });
  },

  //详细地址
  changeDetailAddress(e){
    this.setData({
      detailAddress:e.detail.value
    });
  },
  // 收货人电话
  changeMobile(e){
    console.log("changeMobile : ",e);
    this.setData({
      currMobile:e.detail.value
    });
  },
  //地址选择回调
  RegionChange: function(e) {
    console.log("RegionChange  : ",e);
    this.setData({
      region: e.detail.value,
      postCode:e.detail.postcode,
      regionCode:e.detail.code
    })
  },
  //添加地址
  addUserAddress(e){
    this.setData({
      modalName: e.currentTarget.dataset.target
    })
  },
  // 设置默认地址
  setDefault(e){
    console.log("setDefault : ",e);
    let id=e.currentTarget.dataset.id;
    let _this=this;
    wx.request({
      url: rootUrl + '/wx/userAddress/updateDefault',
      data: {
        id:id,
        userId:_this.data.userId
      },
      success: function (res) {
        console.log(res);
        if (res.data.code == '0000') {
          wx.showToast({
            title: '操作成功！',
            duration: 2000,
            mask: true,
            success: function () {
              setTimeout(function () {
                _this.onLoad();
              }, 2000);
            }
          });
        }else{
          Toast.show('fail',res.data.msg,2000);
        }
      }
    });
  },

  //编辑收货地址
  confrimEditAddress(e){
    let _this=this;
    if(!_this.data.currReceiverName){
      Toast.show('fail','请输入收货人！',2000);
      return false;
    }
    if(!_this.data.currMobile){
      Toast.show('fail','请输入联系方式！',2000);
      return false;
    }
    wx.request({
      url: rootUrl + '/wx/userAddress/wxedit',
      data: {
        id:_this.data.currAddress.id,
        userId:_this.data.userId,
        receiverName:_this.data.currReceiverName,
        mobile:_this.data.currMobile,
        postCode:_this.data.postCode,
        regionCode:_this.data.regionCode.join(),
        region:_this.data.region.join(),
        detailAddress:_this.data.detailAddress,
        field0:_this.data.detailAddress
      },
      success: function (res) {
        console.log(res);
        if (res.data.code == '0000') {
          wx.showToast({
            title: '操作成功！',
            duration: 2000,
            mask: true,
            success: function () {
              setTimeout(function () {
                _this.onLoad();
              }, 2000);
            }
          });
        }else{
          Toast.show('fail',res.data.msg,2000);
        }
      },
      fail: function () {
        Toast.show('fail', '请求失败', 2000);
      },
      complete: function () {
      }
    });
    this.setData({
      modalName: null
    })

  },
// 增加收货地址
  confrimAddAddress(e){
    let _this=this;
    if(!_this.data.currReceiverName){
      Toast.show('fail','请输入收货人！',2000);
      return false;
    }
    if(!_this.data.currMobile){
      Toast.show('fail','请输入联系方式！',2000);
      return false;
    }
    wx.request({
      url: rootUrl + '/wx/userAddress/add',
      data: {
        userId:_this.data.userId,
        receiverName:_this.data.currReceiverName,
        mobile:_this.data.currMobile,
        postCode:_this.data.postCode,
        regionCode:_this.data.regionCode.join(),
        region:_this.data.region.join(),
        detailAddress:_this.data.detailAddress,
        field0:_this.data.detailAddress
      },
      success: function (res) {
        console.log(res);
        if (res.data.code == '0000') {
          wx.showToast({
            title: '操作成功！',
            duration: 2000,
            mask: true,
            success: function () {
              setTimeout(function () {
                _this.onLoad();
              }, 2000);
            }
          });
        }else{
          Toast.show('fail',res.data.msg,2000);
        }
      },
      fail: function () {
        Toast.show('fail', '请求失败', 2000);
      },
      complete: function () {
      }
    });
    this.setData({
      modalName: null
    })
  },

  // 删除用户收货地址
  delUserAddress(e){
    let _this=this;
    let addressId=e.currentTarget.dataset.id;
    wx.request({
      url: rootUrl + '/wx/userAddress/delete',
      data: {
        idList:addressId
      },
      success: function (res) {
        console.log(res);
        if (res.data.code == '0000') {
          wx.showToast({
            title: '操作成功！',
            duration: 2000,
            mask: true,
            success: function () {
              setTimeout(function () {
                _this.onLoad();
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
  // ListTouch触摸开始
  ListTouchStart(e) {
    this.setData({
      ListTouchStart: e.touches[0].pageX
    })
  },

  // ListTouch计算方向
  ListTouchMove(e) {
    this.setData({
      ListTouchDirection: e.touches[0].pageX - this.data.ListTouchStart > 0 ? 'right' : 'left'
    })
  },

  // ListTouch计算滚动
  ListTouchEnd(e) {
    console.log("ListTouchEnd  : ",e);
    if (this.data.ListTouchDirection =='left'){
      this.setData({
        modalName: e.currentTarget.dataset.target
      })
    } else {
      this.setData({
        modalName: null
      })
    }
    this.setData({
      ListTouchDirection: null
    })
  },


})