let path = require('path')
let gulp = require('gulp')
let cleanCSS = require('gulp-clean-css')
let cssWrap = require('gulp-css-wrap')
// 当前主题（purchase:采购商 supplier:供应商）
let themeName = 'purchase'
// let themeName = 'supplier'
// let componentArray = [
//   'progress',
//   'cascader'
// ]
/** 部分组件（当前项目所需要的）  **/
let componentArray = [
  'button',
  'checkbox',
  'dialog',
  'input',
  'date-picker',
  'pagination',
  'dropdown',
  'upload',
  'radio',
  'tabs',
  'loading',
  'breadcrumb',
  'select',
  'progress',
  'cascader'
]
const handleTransform = function () {
  for (let name of componentArray) {
    gulp.src(path.resolve(`./theme/${name}.css`))/* 找需要添加命名空间的css文件，支持正则表达式 */
      .pipe(cssWrap({
        /* 添加的命名空间 */
        // selector: '.custom-purchase'
        selector: `.custom-${themeName}`
      }))
      .pipe(cleanCSS())
      /* 存放的目录 */
      // .pipe(gulp.dest('src/assets/css/theme/purchase'))
      .pipe(gulp.dest(`src/assets/css/theme/${themeName}`))
  }
  // 全部组件直接用index
  // gulp.src(path.resolve(`./theme/index.css`))/* 找需要添加命名空间的css文件，支持正则表达式 */
  //   .pipe(cssWrap({
  //     /* 添加的命名空间 */
  //     // selector: '.custom-purchase'
  //     selector: `.custom-${themeName}`
  //   }))
  //   .pipe(cleanCSS())
  //   /* 存放的目录 */
  //   // .pipe(gulp.dest('src/assets/css/theme/purchase'))
  //   .pipe(gulp.dest(`src/assets/css/theme/${themeName}`))
}
gulp.task('css-wrap', function () {
  return handleTransform()
})
gulp.task('default',['css-wrap'])
