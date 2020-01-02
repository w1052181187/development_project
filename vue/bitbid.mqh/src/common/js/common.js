/**
 * 通用Js
 */
const commonJs = {
  // 文件上传路径
  fileUploadUrl: process.env.BASE_API + '/file/upload',
  // 文件下载路径,需要在路径后添加?fileName=${name}&filePath=${path}
  fileDownloadUrl: process.env.BASE_API + '/file/download',
  // 图片打开路径,需要在路径后添加?fileName=${name}&filePath=${path}
  imageReviewUrl: process.env.BASE_API + '/file/review',
  // 采购建议导入路径
  excelUploadUrl: process.env.BASE_API + 'purchase-suggested/export',
  // 抽签情况备案表导出PDF路径
  drawResultExportUrl: process.env.BASE_API + 'draw-lots-results/exportPDF',
  // 建议计划导入路径
  investExcelUploadUrl: process.env.BASE_API + 'suggestedPlan-infos/export',
  // ueditor上传文件路径
  ueditorUploadUrl: process.env.BASE_API + 'file/ueditor-upload',
  // 建议计划统计完成情况导出excel路径
  suggestedPlanExportUrl: process.env.BASE_API + 'suggestedPlan-infos/exportExcel',
  // 年度计划汇总导出excel路径
  yearPlanExportUrl: process.env.BASE_API + 'yearPlan-summarys/exportExcel',
  // 煤安费完成情况导出excel路径
  coalSafetyFeeSummaryExportUrl: process.env.BASE_API + 'coalSafetyFeeSummary-infos/exportExcel',
  // 建议计划模板导出excel路径
  planTemplateExportUrl: process.env.BASE_API + 'suggestedPlan-infos/exportTemplate'
}

const downloadFile = (fileName, filePath) => {
  window.open(`${commonJs.fileDownloadUrl}?fileName=${encodeURI(fileName)}&filePath=${filePath}`)
}

export {commonJs, downloadFile}
