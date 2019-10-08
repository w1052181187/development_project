// pages/footer/footer.js
Component({
  properties: {
    // 这里定义t属性，属性值可以在组件使用时指定
    
  },
  data: {
    // 这里是一些组件内部数据

  },
  methods: {
    // 这里是一个自定义方法
    handleClick() {
      this.triggerEvent('showDialog')
    }
  }
})