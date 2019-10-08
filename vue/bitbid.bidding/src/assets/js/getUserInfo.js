//response处理
const locator = new ActiveXObject ('WbemScripting.SWbemLocator')
const service = locator.ConnectServer('.')
const getCPU = () => {
  var properties = service.ExecQuery("SELECT * FROM Win32_Processor");
  var e = new Enumerator (properties);
  var p = e.item ();
  document.write(p.ProcessorID);
}
export  { getCPU }
