<template>
  <div class="calculator" id="science-calculator">
    <div class="calculator-main">
      <div class="results">
        <el-input v-model="resultVal" readonly ></el-input>
      </div>
      <div class="mode">
        <el-row type="flex" class="row-bg calculator-row" justify="space-around">
          <el-col :span="4">
            <el-button class="button button-big" @click="press">sin</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button button-big" @click="press">cos</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button button-big" @click="press">tan</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">x^</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">&#60;=</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">C</el-button>
          </el-col>
        </el-row>
        <el-row type="flex" class="row-bg calculator-row" justify="space-around">
          <el-col :span="4">
            <el-button class="button button-big" @click="press">log</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">ln</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">e</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">∘</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button button-big" @click="press">rad</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">√</el-button>
          </el-col>
        </el-row>
        <el-row type="flex" class="row-bg calculator-row" justify="space-around">
          <el-col :span="4">
            <el-button class="button" @click="press">7</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">8</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">9</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">/</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">x ²</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">x !</el-button>
          </el-col>
        </el-row>
        <el-row type="flex" class="row-bg calculator-row" justify="space-around">
          <el-col :span="4">
            <el-button class="button" @click="press">4</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">5</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">6</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">*</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">(</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">)</el-button>
          </el-col>
        </el-row>
        <el-row type="flex" class="row-bg calculator-row" justify="space-around">
          <el-col :span="4">
            <el-button class="button" @click="press">1</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">2</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">3</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">-</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">%</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">±</el-button>
          </el-col>
        </el-row>
        <el-row type="flex" class="row-bg calculator-row" justify="space-around">
          <el-col :span="4">
            <el-button class="button" @click="press">0</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">.</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">&#x003C0;</el-button>
          </el-col>
          <el-col :span="4">
            <el-button class="button" @click="press">+</el-button>     
          </el-col>
          <el-col :span="8">
            <el-button class="button equal-sign" @click="press" style="width: 112px;">=</el-button>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'calculator',
  data () {
    return {
      resultVal: ''
    }
  },
  methods: {
    // 点击按钮
    press (event) {
      // console.log(event)
      let me = this
      let key = event.target.textContent
      if (
        key != '=' && 
        key != 'C' &&
        key != '*' &&
        key != '/' &&
        key != '√' &&
        key != "x ²" &&
        key != "%" &&
        key != "<=" && 
        key != "±" && 
        key != "sin" && 
        key != "cos" && 
        key != "tan" && 
        key != "log" && 
        key != "ln" && 
        key != "x^" && 
        key != "x !" && 
        key != "π" && 
        key != "e" && 
        key != "rad" && 
        key != "∘"
      ) {
        me.resultVal += key
        
      } else if (key === '=') {
        
        if ((me.resultVal).indexOf('^') > -1) {
          let base = (me.resultVal).slice(0, (me.resultVal).indexOf('^'))
          let exponent = (me.resultVal).slice((me.resultVal).indexOf('^') + 1)
          me.resultVal = eval('Math.pow(' + base + ',' + exponent + ')')
        } else {
          me.resultVal = eval(me.resultVal)
        }
      
      } else if (key === 'C') {
        
        me.resultVal = ''

      } else if (key === '*') {
        
        me.resultVal += '*'

      } else if (key === '/') {
        
        me.resultVal += '/'

      } else if (key === '+') {
        
        me.resultVal += '+'

      } else if (key === '-') {
        
        me.resultVal += '-'

      } else if (key === '±') {
        
        if ((me.resultVal).charAt(0) === '-') {
          me.resultVal = (me.resultVal).slice(1)
        } else {
          me.resultVal = '-' + me.resultVal
        }

      } else if (key === '<=') {
        
        me.resultVal = me.resultVal.substring(0, me.resultVal.length - 1)

      } else if (key === '%') {
        
        me.resultVal = me.resultVal / 100

      } else if (key === 'π') {
        
        me.resultVal = me.resultVal * Math.PI

      } else if (key === 'x ²') {
        
        me.resultVal = eval(me.resultVal * me.resultVal)

      } else if (key === '√') {
        
        me.resultVal = Math.sqrt(me.resultVal)

      } else if (key === 'sin') {
        
        me.resultVal = Math.sin(me.resultVal)

      } else if (key === 'cos') {
        
        me.resultVal = Math.cos(me.resultVal)

      } else if (key === 'tan') {
        
        me.resultVal = Math.tan(me.resultVal)

      } else if (key === 'log') {
        
        me.resultVal = Math.log10(me.resultVal)

      } else if (key === 'ln') {
        
        me.resultVal = Math.log(me.resultVal)

      } else if (key === 'x^') {
        
        me.resultVal += '^'

      } else if (key === 'x !') {

        let number = 1
        if (me.resultVal === 0) {
          me.resultVal = '1'
        } else if (me.resultVal < 0) {
          me.resultVal = NaN
        } else {
          let number = 1
          for (let i = me.resultVal; i > 0; i--) {
            number *= i
          }
          me.resultVal = number
        }

      } else if (key === 'e') {
        
        me.resultVal = Math.exp(me.resultVal)

      } else if (key === 'rad') {
        
        me.resultVal = me.resultVal * (Math.PI / 180)

      } else if (key === '∘') {

        me.resultVal = me.resultVal * (180 / Math.PI)

      }
    }
  }
}
</script>
<style lang="less">
#science-calculator {
  margin: 20px 0;
  padding: 20px 0;
  background: #fff;
  .calculator-main {
    width: 380px;
    margin: 0 auto;
    padding: 20px;
    background: #545454;
    border-radius: 5px;
    .calculator-row {
      margin: 10px 0;
    }
    .mode {
      .button {
        // width: 60px;
      }
      .button-big {
        width: 50px;
        padding: 12px;
      }
    }
  }
  .results {
    .el-input__inner {
      text-align: right;
    }
  }
}
</style>


