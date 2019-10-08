function handlePdfDisplay (isHide) {
  let pdf1 = document.getElementById('pdf_file1')
  let pdf2 = document.getElementById('pdf_file2')
  if (pdf1) {
    pdf1.style.display = isHide ? 'none' : 'block'
  }
  if (pdf2) {
    pdf2.style.display = isHide ? 'none' : 'block'
  }
}
export {
  handlePdfDisplay
}
