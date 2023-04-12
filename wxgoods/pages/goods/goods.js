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
    TabCur: 0,
    MainCur: 0,
    VerticalNavTop: 0,
    list: [],//更新indexId后的商品list
    rootUrl:rootUrl,
    rootImgUrl:rootImgUrl,
    categoryList:null,
    goodsList:null,//商品列表
    load: true,
    listCur:{},//当前选中的类目对象
    goodsName:null//搜索商品名称
  },
  onLoad() {
    wx.showLoading({
      title: '加载中...',
      mask: true
    });
    this.getCategoryList();
  },
  //获取类目列表
  getCategoryList:function(e){
    wx.request({
      url: rootUrl + "/wx/category/list?pageNum=1&pageSize=1000",
      success: (response) => {
        if (response.data.code == '0000') {
          this.setData({
            categoryList: response.data.data.rows
          });
          let list =[{'name':'全部商品'}]
          list=list.concat(response.data.data.rows);
          console.log('list:',list)
          for (let i = 0; i < list.length; i++) {
            list[i].indexId = i;
          }
          this.setData({
            list: list,
            listCur: list[0]
          })
          this.getGoodsList(null,null);
        }
      }
    });
  },
  onReady() {
    wx.hideLoading()
  },
  //左侧类目标签选中
  tabSelect(e) {
    console.log(e);
    this.setData({
      TabCur: e.currentTarget.dataset.id,//选中第几个标签，下标从0开始
    },()=>{
      let indexId=e.currentTarget.dataset.id;
      let categoryId=this.data.list[indexId].id;//类目ID 
      this.getGoodsList(categoryId,null);//获取对应商品列表
      this.setData({
        listCur: this.data.list[indexId]
      })
    })
  },
  //获取商品列表
  getGoodsList:function(categoryId,goodsName){
    if(goodsName==null||goodsName==''){
      goodsName==this.data.goodsName
    }
    wx.request({
      url: rootUrl + '/wx/goods/list/?pageNum=1&pageSize=1000',
      data: {
        categoryId: categoryId?categoryId:'',
        state: 1,
        name:goodsName?goodsName:""
      },
      success: res => {
        console.log('res',res);
        if (res.data.code == '0000') {
          this.setData({
            goodsList: res.data.data.rows
          });
        }else{
          Toast.show('fail',res.data.msg,2000);
        }
      },
      fail: function () {
        Toast.show('fail', res.data.msg, 2000);
      }
    })
  },
  //保存输入的搜索商品名称处
  setGoodsName(e){
    this.setData({
      goodsName:e.detail.value?e.detail.value:""
    });
  },
  //搜索商品
  searchGoodsList(e){
    let categoryId=this.data.listCur.id;
    let goodsName=this.data.goodsName;
    this.getGoodsList(categoryId,goodsName);
  },
  //跳到商品详情页
  toGoodsDetail: function (event) {
    let goodsId = event.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/goodsDetail/goodsDetail?id=' + goodsId
    })
  },
  VerticalMain(e) {
    let that = this;
    let list = this.data.list;
    let tabHeight = 0;
    if (this.data.load) {
      for (let i = 0; i < list.length; i++) {
        let view = wx.createSelectorQuery().select("#main-" + list[i].indexId);
        view.fields({
          size: true
        }, data => {
          list[i].top = tabHeight;
          tabHeight = tabHeight + data.height;
          list[i].bottom = tabHeight;
        }).exec();
      }
      that.setData({
        load: false,
        list: list
      })
    }
    //实现垂直导航栏和内容区域联动
    let scrollTop = e.detail.scrollTop + 20;
    //遍历垂直导航栏数据 list,对每个选项进行判断是否处于当前浏览的内容区域
    for (let i = 0; i < list.length; i++) {
      if (scrollTop > list[i].top && scrollTop < list[i].bottom) {
        that.setData({
          VerticalNavTop: (list[i].indexId - 1) * 50,
          TabCur: list[i].indexId
        })
        return false
      }
    }
  }
})