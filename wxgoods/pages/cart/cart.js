// pages/cart/cart.js
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
    StatusBar: app.globalData.StatusBar,
    CustomBar: app.globalData.CustomBar,
    Custom: app.globalData.Custom,
    list: [],//购物车列表
    cartIdList:[],//选中的购物车ID
    page: 1,
    hasMoreData: true,//是否还有更多数据
    hasFail: false,//获取列表数据是否出错
    rootUrl: rootUrl,
    rootImgUrl: rootImgUrl,
    totalPrice:0,//当前购物车所有商品总价
    modalName:"",
    addressList:[],
    chooseAddress:"",
    userId:null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log("页面加载")
    if (!app.checkLogin()) {
      return false;
    }
    this.setData({
      userId:app.globalData.userData.id,
      totalPrice:0
    })
   
  },
//选中/取消订单
checkboxChange(e){
  console.log(e);
  let newArray=e.detail.value;
  this.setData({
    cartIdList: newArray
  })
  let list=this.data.list;
  let money=0;
  //计算此时订单价格
  newArray.forEach(e=>{
    list.forEach(e2=>{
      if(e==e2.id){
        money = money+e2.goodsNum*e2.price;
      }
    })
  })
  //重新设置totalPrice
  this.setData({
    totalPrice:money
  })
},
  //提交订单
  toOrder(e){
    let _this=this;
    wx.request({
      url: rootUrl + '/wx/order/divAdd',
      data: { 
        addressId:this.data.chooseAddress,
        userId: this.data.userId,
        cartId:this.data.cartIdList.join(',') 
      },
      success: res => {
        if (res.data.code == '0000') {
          app.countCartNum();
          wx.showToast({
            title: '请求成功！',
            duration: 2000,
            mask: true,
            icon: "success",
            success: function () {
              // _this.onLoad()
              wx.navigateTo({
                url: '/pages/order/order',
              })
            }
          });
        } else {
          Toast.show('none', res.data.msg, 2000);
        }
      },
      fail: function () {
        Toast.show('none', res.data.msg, 2000);
      }
    });
    this.setData({
      modalName: null
    })
  },
  //跳到商品列表
  toGoodsList(e){
    wx.switchTab({
      url: '/pages/goods/goods',
      success: function(res) {},
      fail: function(res) {},
      complete: function(res) {},
    })
  },

//获取列表数据
getList: function (e) {
  let userId = this.data.userId;
  let _this = this;
  //加载数据时，提示用户
  wx.showLoading({
    title: "数据加载中...",
    mask: true
  });
  //通过接口获取数据
  wx.request({
    url: rootUrl + '/wx/cart/list',
    data: {
      userId: userId,
      pageNum: this.data.page, //分页
      pageSize: 20 //单页数据量
    },
    header: {
      'content-type': 'application/json' // 默认值
    },
    success: function (ret) {
      console.log(ret);
      let array=ret.data.data.rows;
      let money=0;
      array.forEach(e=>{
        money = money+e.goodsNum*e.price*e.goods.field1/10;
      })
   
      _this.setData({
        totalPrice: _this.data.totalPrice+money
      })
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
      _this.calculateCartIdList();
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
//计算选中的cartIdList
calculateCartIdList(e){
  let array=[];
  this.data.list.forEach(value=>{
    array.push(value.id)
  })
  this.setData({
    cartIdList: array
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
    if (this.data.ListTouchDirection == 'left') {
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

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    console.log("页面渲染完成")
    if (!app.checkLogin()) {
      return false;
    }
    this.setData({
    
      totalPrice:0
    },()=>{
      this.getList();
    })
   
  },

  //从购物车删除选中的商品
  deleteCart(e){
    let _this=this;
    let id=e.currentTarget.dataset.id;
    let array=new Array();
    array.push(id);
    //发请求从购物车删除商品
    wx.request({
      url: rootUrl + '/wx/cart/delete',
      data: {idList: array.join(",")},
      success: res => {
        if (res.data.code == '0000') {
          wx.showToast({
            title: '请求成功！',
            duration:2000,
            mask: true,
            icon: "success",
            success: function () {
              _this.onShow()
            }
          });

        }else{
          Toast.show('none', res.data.msg, 2000);
        }
      },
      fail: function () {
        Toast.show('none', res.data.msg, 2000);
      }
    });
    app.countCartNum();
  },

  onPullDownRefresh: function () {
    //下拉刷新，由于列表数据是分页的，所以下拉刷新时，需要将page置为1，再调用获取列表数据的方法
    this.setData({ hasMoreData: true, page: 1,totalPrice:0 }, () => this.getList());


  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    //上拉加载，判断是否还有数据，如果没有数据，则不执行获取数据的方法，避免重复无用的调用接口
    this.data.hasMoreData ? this.getList() : null;

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
  hideModal(e) {
    this.setData({
      modalName: null
    })
  },
    // 选择收货地址
    radioChange(e){
      console.log("radioChange : ",e);
      this.setData({
        chooseAddress:e.detail.value
      });
  },
    // 弹出立即订购地址模态框
    addOrderModel(e){
      let _this=this;
      let userId=wx.getStorageSync('userData').id;
      wx.request({
        url: rootUrl + '/wx/userAddress/list',
        data: {
          pageSize:1000,
          pageNum:1,
          userId:userId
        },
        success: function (res) {
          console.log("addOrderModel",res);
          if (res.data.code == '0000') {
           _this.setData({
             addressList:res.data.data.rows
           });
          }else{
            Toast.show('fail',res.data.msg,2000);
          }
        }
      });
      this.setData({
        modalName: e.currentTarget.dataset.target
      })
    },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }

})