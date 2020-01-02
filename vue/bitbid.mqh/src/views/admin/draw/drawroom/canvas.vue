<template>
  <!-- 抽签canvas动画 -->
  <div class="admin-layout" id="drawcanvas-page">
    <!--动画区域-->
    <canvas id="canvas"></canvas>
  </div>
</template>
<script>
// 实例
export default {
  data () {
    return {
    }
  },
  created () {
  },
  methods: {
    /** 初始化 */
    init () {
      let wcan = document.getElementById('drawcanvas-page').offsetWidth
      let oW = wcan
      let oH = 330
      let M = Math
      let Cos = M.cos
      let Sin = M.sin
      let PI = M.PI
      let tPI = 2 * M.PI
      let oC = document.getElementById('canvas')
      oC.width = oW
      oC.height = oH
      let ctx = oC.getContext('2d')

      let cradius = 15 // 圆角半径
      let w = [] // 圆运动圆角矩形轨迹

      let gap = 10

      let fx = gap
      let fy = 10

      let fx2 = gap + 10
      let fy2 = 20

      let bw = (oW - 2 * gap)
      let bh = (oH + gap) * 0.875

      let sw = bw - 20
      let sh = bh - 20

      function getTrail () {
        let sx = fx + 15
        let sy = fy + 5
        let w = bw - 10
        let h = bh - 10
        let result = []
        let section = 2 // 轨迹点间隔区间
        let csection = 0.1 // 圆轨迹区间
        let ccp = [0, 0] // 圆角圆心点
        let coffset = -(PI / 2) // 圆轨迹起始位置

        // 上边
        for (var i = sx; i <= w; i += section) {
          result.push([i, sy])
        }
        sx = i
        ccp = [sx, sy + cradius]
        // 右上圆角
        for (var j = coffset; j <= coffset + tPI / 4; j += csection) {
          result.push([ccp[0] + cradius * Cos(j), ccp[1] + cradius * Sin(j)])
        }
        sx = ccp[0] + cradius * Cos(j)
        sy = ccp[1] + cradius * Sin(j)
        // 右边
        for (var k = sy; k <= h; k += section) {
          result.push([sx, k])
        }
        sy = k
        ccp = [sx - cradius, sy]
        // 右下圆角
        for (var l = coffset + tPI / 4; l < coffset + tPI / 2; l += csection) {
          result.push([ccp[0] + cradius * Cos(l), ccp[1] + cradius * Sin(l)])
        }
        sx = ccp[0] + cradius * Cos(l)
        sy = ccp[1] + cradius * Sin(l)

        // 下边
        for (var m = sx; m >= 30; m -= section) {
          result.push([m, sy])
        }
        sx = m
        ccp = [sx, sy - cradius]

        // 左下圆角
        for (var n = coffset + tPI / 2; n < coffset + (3 * tPI) / 4; n += csection) {
          result.push([ccp[0] + cradius * Cos(n), ccp[1] + cradius * Sin(n)])
        }
        sx = ccp[0] + cradius * Cos(n)
        sy = ccp[1] + cradius * Sin(n)

        // 左边
        for (var o = sy; o >= 15 + cradius; o -= section) {
          result.push([sx, o])
        }
        sy = o
        ccp = [sx + cradius, sy]
        // 左上角
        for (var p = coffset + (3 * tPI) / 4; p < coffset + tPI; p += csection) {
          result.push([ccp[0] + cradius * Cos(p), ccp[1] + cradius * Sin(p)])
        }
        result.push([fx + 15, fy + 5])
        return result
      }
      // 绘制圆角矩形
      function drawRoundedRect (x, y, w, h, r, bdWidth = 3, bdColor, bgcolor) {
        ctx.beginPath()
        ctx.moveTo(x + r, y)
        ctx.lineWidth = bdWidth
        ctx.strokeStyle = bdColor
        ctx.arcTo(x + w, y, x + w, y + h, r)
        ctx.arcTo(x + w, y + h, x, y + h, r)
        ctx.arcTo(x, y + h, x, y, r)
        ctx.arcTo(x, y, x + w, y, r)
        ctx.stroke()
        if (bgcolor) {
          ctx.fillStyle = bgcolor
          ctx.fill()
        }
        ctx.closePath()
      }
      // 绘制圆
      function drawCircle (x, y, bg = 'rgba(238,232,255,1)', isshadow) {
        var r = (bw / 2 - sw / 2) / 2
        ctx.beginPath()
        if (isshadow) {
          ctx.save()
          ctx.shadowColor = 'rgba(255, 255, 255, 1)'
          ctx.shadowOffsetX = 0 // 阴影Y轴偏移
          ctx.shadowOffsetY = 1 // 阴影X轴偏移
          ctx.shadowBlur = 5 // 模糊尺寸
        } else {
          ctx.save()
          ctx.shadowColor = 'transparent'
        }
        ctx.fillStyle = bg
        ctx.arc(x, y, r, 0, tPI)
        ctx.fill()
        ctx.closePath()
        ctx.restore()
      }
      w = getTrail()
      var re = 16 // 区间位置点

      // 用于表示运动的位置
      var nowkey = 0
      var cflag = 1 // 灯光顺序改变

      render()

      function drawCricleGroup (v) {
        let t = v
        let k = 0
        let m = 0 // m 控制绘制灯的个数

        // 当 k 差不多累计一圈轨迹之后，停止循环
        while (k + re <= w.length - 1) {
          var tmp = w[t]
          m++
          if (cflag) {
            if (m % 2 === 0) {
              drawCircle(tmp[0], tmp[1], 'rgba(255,255,255,' + 1 + ')', 1)
            } else {
              drawCircle(tmp[0], tmp[1], 'rgba(255,255,255,' + 0.2 + ')', 0)
            }
          } else {
            if (m % 2 === 0) {
              drawCircle(tmp[0], tmp[1], 'rgba(255,255,255,' + 0.2 + ')', 0)
            } else {
              drawCircle(tmp[0], tmp[1], 'rgba(255,255,255,' + 1 + ')', 1)
            }
          }
          if ((t + re) > w.length - 1) {
            t = t + re - w.length
          } else {
            t += re
          }
          k += re
        }
      }

      function render () {
        ctx.clearRect(0, 0, oW, oH)
        ctx.save()
        ctx.shadowBlur = 10
        ctx.shadowOffsetX = 0
        ctx.shadowOffsetY = 3
        ctx.shadowColor = 'rgba(255,255,255,1)'
        drawRoundedRect(fx, fy, bw, bh, cradius, 3, '#FF9C00', '#FF9C00')
        drawRoundedRect(fx2, fy2, sw, sh, cradius, 3, '#fff', '#fff')
        ctx.restore()

        if (nowkey >= w.length - 1) {
          nowkey = 0
        } else {
          nowkey++
        }

        drawCricleGroup(nowkey)
        if (nowkey % re === 0) {
          cflag ^= 1
        }

        requestAnimationFrame(render)
      }
    }
  },
  filters: {
  },
  computed: {},
  // 实例创建完成
  mounted () {
    this.init()
  }
}
</script>
<style lang="less">
#drawcanvas-page {
  position: relative;
  display: block;
  width: 100%;
  justify-content: center;
  background: #fff;
}
</style>
