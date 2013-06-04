FCKConfig.FontNames = '宋体;黑体;楷体_GB2312;Arial;Comic Sans MS;Courier New;Tahoma;Times New Roman;Verdana';
FCKConfig.AutoDetectLanguage = false;
// 默认语言
FCKConfig.DefaultLanguage = 'zh-cn';
// 是否自动聚焦到编辑器内
FCKConfig.StartupFocus = false;
// 是否支持Tab空格1支持0不支持
FCKConfig.TabSpaces = 1;

// 隐藏"插入图片"对话框中的"链接"
FCKConfig.ImageDlgHideLink = true;
// 隐藏"插入图片"对话框中的"高级"
FCKConfig.ImageDlgHideAdvanced = true;

FCKConfig.Plugins.Add('simplecommands');
FCKConfig.Plugins.Add('Media', 'zh-cn');
//FCKConfig.Plugins.Add('flvPlayer','zh-cn');
//FCKConfig.Plugins.Add('CharsCounter');
FCKConfig.ToolbarSets["My"] = [
  ['Source', 'FitWindow', 'Preview'],
  ['Undo', 'Redo', '-', 'Find', 'Replace', '-', 'SelectAll', 'RemoveFormat'],
  ['Image', 'SpecialChar'],
  ['PageBreak']
];
//FCKConfig.ToolbarSets["My"] = [
//	['Source','FitWindow','Preview','Templates'],
//	['PasteText','PasteWord'],
//	['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
//	['Bold','Italic','Underline','StrikeThrough','-','Subscript','Superscript'],
//	['OrderedList','UnorderedList','-','Outdent','Indent'],
//	['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],
//	['Link','Unlink','Anchor'],
//	['Image','Flash','Media','Table','SpecialChar'],
//	['StyleSimple','FontFormatSimple','FontNameSimple','FontSizeSimple'],
//	['TextColor','BGColor'],
//	['PageBreak']
//];
FCKConfig.ToolbarSets["Simple"] = [
  ['Bold', 'Italic', 'Underline', 'StrikeThrough'],
  ['StyleSimple', 'FontFormatSimple', 'FontNameSimple', 'FontSizeSimple'],
  ['TextColor', 'BGColor', 'RemoveFormat'],
  ['OrderedList', 'UnorderedList', 'Outdent', 'Indent'],
  ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyFull'],
  ['Link', 'Unlink', 'Image'],
  ['Source']
];