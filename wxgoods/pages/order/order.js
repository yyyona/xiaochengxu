import Toast from "../../utils/toast.js";
//获取应用实例
const app = getApp()
const rootUrl = app.globalData.rootUrl;
const rootImgUrl = rootUrl + "/file/fileDown/?saveName=";
Page({
  data: {
    StatusBar: app.globalData.StatusBar,
    CustomBar: app.globalData.CustomBar,
    Custom: app.globalData.Custom,
    TabCurTop: 0,//订单状态
    page: 1,
    tablist: [{ 'name': '待付款' }, { 'name': '待发货' }, { 'name': '已发货' }, { 'name': '已评价' }],//状态数组
    rootUrl: rootUrl,
    rootImgUrl: rootImgUrl,
    totalPrice:0,//总价格
    state:0,//订单状态 0-待付款
    orderIdList:[],//选中的订单ID
    userInfo:null,//登录后的用户信息
    list: [] ,//订单列表
    id1imagesrc:'/images/star-grey.png',
    id2imagesrc:'/images/star-grey.png',
    id3imagesrc:'/images/star-grey.png',
    id4imagesrc:'/images/star-grey.png',
    id5imagesrc:'/images/star-grey.png',
    score:1,//用户评分
    appraise:null,//订单评价内容
    currAppraise:"",//点击查看评价时当前订单评价内容
    currScore:1,//点击查看评价时当前订单评分，默认为1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!app.checkLogin()) {
      return false;
    }
    this.setData({
      totalPrice:0,
      orderIdList:[],
      list:[],     
      page: 1,
      userId: app.globalData.userData.id,
      userInfo: app.globalData.userData
    }, () => {
      this.getList();
    })
  },
//切换列表
  tabTopSelect(e) {
    this.setData({
      orderIdList:[],
      list:[],   
      page: 1,
      TabCurTop: e.currentTarget.dataset.id,
      state: e.currentTarget.dataset.id,
      totalPrice:0
    },()=>{
      this.getList();
    })
  },
  //选中/取消订单
  checkboxChange(e){
    console.log(e);
    let newArray=e.detail.value;
    this.setData({
      orderIdList: newArray
    })
    let list=this.data.list;
    let money=0;
    //计算此时订单价格
    newArray.forEach(e=>{
      list.forEach(e2=>{
        if(e==e2.id){
          money = money+e2.money;
        }
      })
    })
    //重新设置totalPrice
    this.setData({
      totalPrice:money
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
      url: rootUrl + '/wx/order/list',
      data: {
        userId: userId,
        state:this.data.state,
        pageNum: this.data.page, //分页
        pageSize: 10 //单页数据量
      },
      header: { 'content-type': 'application/json' }, // 默认值
      success: function (ret) {
        var array=ret.data.data.rows;
        let moneyAll=0;
        array.forEach(e=>{
          moneyAll =moneyAll+e.money;
        })
        _this.setData({
          totalPrice: _this.data.totalPrice+moneyAll
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
        _this.calculateOrderIdList();
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
//计算选中的orderIdList
  calculateOrderIdList(e){
    let array=[];
    this.data.list.forEach(value=>{
      array.push(value.id)
    })
    this.setData({
      orderIdList: array
    })
  },
  //展示模态框
  showModal(e) {
    this.setData({
      modalName: e.currentTarget.dataset.target
    })
  },

 //查看评价
 showModalLook(e){
  this.setData({
    currAppraise:e.currentTarget.dataset.appraise,//评价
    modalName: e.currentTarget.dataset.target,//评价框
    currScore:e.currentTarget.dataset.score //点击查看评价时保存的当前订单的评分
  });
  switch(this.data.currScore){
    case "1":
      this.setData({
        id1imagesrc:'/images/star-yellow.png',
        id2imagesrc:'/images/star-grey.png',
        id3imagesrc:'/images/star-grey.png',
        id4imagesrc:'/images/star-grey.png',
        id5imagesrc:'/images/star-grey.png',
      })
      break;
    case "2":
      this.setData({
        id1imagesrc:'/images/star-yellow.png',
        id2imagesrc:'/images/star-yellow.png',
        id3imagesrc:'/images/star-grey.png',
        id4imagesrc:'/images/star-grey.png',
        id5imagesrc:'/images/star-grey.png',
      })
      break;
    case "3":
      this.setData({
        id1imagesrc:'/images/star-yellow.png',
        id2imagesrc:'/images/star-yellow.png',
        id3imagesrc:'/images/star-yellow.png',
        id4imagesrc:'/images/star-grey.png',
        id5imagesrc:'/images/star-grey.png',
      })
      break;
    case "4":
      this.setData({
        id1imagesrc:'/images/star-yellow.png',
        id2imagesrc:'/images/star-yellow.png',
        id3imagesrc:'/images/star-yellow.png',
        id4imagesrc:'/images/star-yellow.png',
        id5imagesrc:'/images/star-grey.png',
      })
      break;
    case "5":
      this.setData({
        id1imagesrc:'/images/star-yellow.png',
        id2imagesrc:'/images/star-yellow.png',
        id3imagesrc:'/images/star-yellow.png',
        id4imagesrc:'/images/star-yellow.png',
        id5imagesrc:'/images/star-yellow.png',
      })
      break;
      default:
        this.setData({
          id1imagesrc:'/images/star-grey.png',
          id2imagesrc:'/images/star-grey.png',
          id3imagesrc:'/images/star-grey.png',
          id4imagesrc:'/images/star-grey.png',
          id5imagesrc:'/images/star-grey.png',
        });
        break;
  }

},

//评分
appraiseScore(e){
  let id=e.currentTarget.dataset.id;//1 2 3 4 5
  switch(id){
    case "1":
      this.setData({
        score:1,
        id1imagesrc:'/images/star-yellow.png',
        id2imagesrc:'/images/star-grey.png',
        id3imagesrc:'/images/star-grey.png',
        id4imagesrc:'/images/star-grey.png',
        id5imagesrc:'/images/star-grey.png',
      })
      break;
    case "2":
      this.setData({
        score:2,
        id1imagesrc:'/images/star-yellow.png',
        id2imagesrc:'/images/star-yellow.png',
        id3imagesrc:'/images/star-grey.png',
        id4imagesrc:'/images/star-grey.png',
        id5imagesrc:'/images/star-grey.png',
      })
      break;
    case "3":
      this.setData({
        score:3,
        id1imagesrc:'/images/star-yellow.png',
        id2imagesrc:'/images/star-yellow.png',
        id3imagesrc:'/images/star-yellow.png',
        id4imagesrc:'/images/star-grey.png',
        id5imagesrc:'/images/star-grey.png',
      })
      break;
    case "4":
      this.setData({
        score:4,
        id1imagesrc:'/images/star-yellow.png',
        id2imagesrc:'/images/star-yellow.png',
        id3imagesrc:'/images/star-yellow.png',
        id4imagesrc:'/images/star-yellow.png',
        id5imagesrc:'/images/star-grey.png',
      })
      break;
    case "5":
      this.setData({
        score:5,
        id1imagesrc:'/images/star-yellow.png',
        id2imagesrc:'/images/star-yellow.png',
        id3imagesrc:'/images/star-yellow.png',
        id4imagesrc:'/images/star-yellow.png',
        id5imagesrc:'/images/star-yellow.png',
      })
      break;
  }
},

  //隐藏模态框
  hideModal(e) {
    this.setData({
      modalName: null
    })
  },
  //订单评价内容
  appraiseOrder(e){
    this.setData({
      appraise: e.detail.value
    })
  },
  //去评价
  toAppraise(e){
    let idList = this.data.orderIdList;
    if (idList == null || idList.length < 1) {
      Toast.show('none', '请选择订单！', 2000);
      return false;
    }
    //修改订单状态为已评价
    wx.request({
      url: rootUrl + '/wx/order/appraiseOrder',
      data: { 
        orderList: idList.join(','), 
        appraise: this.data.appraise,
        score:this.data.score
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: (ret) => {
        this.hideModal();
        if (ret.data.code == '0000') {
          Toast.show('success', null, 2000);
          this.setData({
            TabCurTop: 3,
            state: 3
          }, () => {
            this.getList()
          })
        } else {
          Toast.show('none', ret.data.msg, 2000);
        }
      }
    })
  },
  //取消订单
  toCancel(e){
    let idList=this.data.orderIdList;
    if (idList==null||idList.length<1){
      Toast.show('none','请选择订单！',2000);
      return false;
    }
    //查询待付款的订单
    wx.request({
      url: rootUrl + '/wx/order/cancelOrder',
      data: { orderList: idList.join(',')},
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: (ret) => {
        if (ret.data.code == '0000') {
          Toast.show('success',null,2000);
          this.setData({
            TabCurTop:0,
            state:0
          },()=>{
            this.getList()
          })
        }else{
          Toast.show('none',ret.data.msg,2000);
        }
      }
    })
  },

  //去支付
  toPay(e){
    let idList=this.data.orderIdList;
    if (idList==null||idList.length<1){
      Toast.show('none','请选择订单！',2000);
      return false;
    }
    //修改订单状态为待发货
    wx.request({
      url: rootUrl + '/wx/order/pay',
      data: { orderList: idList.join(','),  state:1,userId: this.data.userId},
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: (ret) => {
        if (ret.data.code == '0000') {
          Toast.show('success',null,2000);
          this.setData({
            TabCurTop:1,
            state:1,
            totalPrice:0
          },()=>{
            this.getList()
          })
        }else{
          Toast.show('none',ret.data.msg,2000);
        }
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
    if (!app.checkLogin()) {
      return false;
    }
    this.setData({ totalPrice:0 });
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

  onPullDownRefresh: function () {
    //下拉刷新，列表数据是分页的，所以下拉刷新时，需要将page置为1，再调用获取列表数据的方法
    this.setData({ hasMoreData: true, page: 1 ,totalPrice:0}, () => this.getList());

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    //上拉加载，判断是否还有数据，如果没有数据，则不执行获取数据的方法，避免重复无用的调用接口
    this.data.hasMoreData ? this.getList() : null;

  },

  
})