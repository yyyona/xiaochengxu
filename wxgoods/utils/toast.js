export default class Toast{
    static showToLogin( title, duration) {
        wx.showToast({
            title: title?title:'请先登录！',
            duration: duration ? duration : 2000,
            mask: true,//需要遮罩层
            image: "/images/icon/fail.png",
            success: function () {
              setTimeout(function () {
                wx.switchTab({
                  url: '/pages/my/my',
                })
              }, duration ? duration : 2000,);
            }
          });
    }
    static show(icon, title, duration) {
        if (icon === 'loading') {
            wx.showToast({
                title: title?title:'加载中...',
                icon: 'loading',
                duration: duration ? duration : 2000,
            })
        } else if (icon === 'success') {
            wx.showToast({
                title: title?title:'请求成功！',
                icon: 'success',
                duration: duration ? duration : 2000,
            })
        } else if (icon === 'warn') {
            wx.showToast({
                title: title,
                duration: duration ? duration : 2000,
                image: '/images/icon/fail.png'
            })
        } else if (icon === 'fail') {
            wx.showToast({
                title: title?title:'请求失败！',
                duration: duration ? duration : 2000,
                image: '/images/icon/fail.png'
            })
        }else if(icon==='none'){
          wx.showToast({
            title: title ? title : '请求失败！',
            duration: 2000,
            icon: 'none'
          });
        } else if(!icon){
          wx.showToast({
            title: title ? title : '请求失败！',
            duration: duration ? duration : 2000,
            image: '/images/icon/fail.png'
          })
        }
    }

    //加载提示框
    static showNormalLoading(text) {
        if (wx.showLoading) {
            wx.showLoading({
                title: text ? text : '加载中...',
                mask: true
            })
        } else {
            wx.showToast({
                title: text,
                icon: 'loading',
                duration: 2000,
            })
        }
    }

//隐藏toast提示框
    static hidden() {
        wx.hideToast();
        if (wx.hideLoading) {
            wx.hideLoading();
        }
    }

}