tinyMCEPopup.requireLangPack();

var action, orgTableWidth, orgTableHeight, dom = tinyMCEPopup.editor.dom;

function insertTable() {
	var formObj = document.forms[0];
	var inst = tinyMCEPopup.editor, dom = inst.dom;
	var cols = 2, rows = 2, border = 0, cellpadding = -1, cellspacing = -1, align, width, height, className, caption, frame, rules;
	var html = '', capEl, elm;
	var cellLimit, rowLimit, colLimit, editorWidth;

	tinyMCEPopup.restoreSelection();

	if (!AutoValidator.validate(formObj)) {
		tinyMCEPopup.showErrorMsg(inst.getLang('invalid_data'));
		return false;
	}

	elm = dom.getParent(inst.selection.getNode(), 'table');

	// Get form data
	cols = formObj.elements['cols'].value;
	rows = formObj.elements['rows'].value;
	border = formObj.elements['border'].value != "" ? formObj.elements['border'].value  : 0;
	cellpadding = formObj.elements['cellpadding'].value != "" ? formObj.elements['cellpadding'].value : "";
	cellspacing = formObj.elements['cellspacing'].value != "" ? formObj.elements['cellspacing'].value : "";
	align = getSelectValue(formObj, "align");
	frame = getSelectValue(formObj, "tframe");
	rules = getSelectValue(formObj, "rules");
	width = formObj.elements['width'].value;
	height = formObj.elements['height'].value;
	bordercolor = formObj.elements['bordercolor'].value;
	bgcolor = formObj.elements['bgcolor'].value;
	className = getSelectValue(formObj, "class");
	id = formObj.elements['id'].value;
	summary = formObj.elements['summary'].value;
	style = formObj.elements['style'].value;
	dir = formObj.elements['dir'].value;
	lang = formObj.elements['lang'].value;
	background = formObj.elements['backgroundimage'].value;
	caption = formObj.elements['caption'].checked;

	cellLimit = tinyMCEPopup.getParam('table_cell_limit', false);
	rowLimit = tinyMCEPopup.getParam('table_row_limit', false);
	colLimit = tinyMCEPopup.getParam('table_col_limit', false);
	editorWidth = tinyMCEPopup.getParam('width', false);

	// Validate table size
	if (colLimit && cols > colLimit) {
		tinyMCEPopup.showErrorMsg(inst.getLang('table_dlg.col_limit').replace(/\{\$cols\}/g, colLimit));
		return false;
	} else if (rowLimit && rows > rowLimit) {
		tinyMCEPopup.showErrorMsg(inst.getLang('table_dlg.row_limit').replace(/\{\$rows\}/g, rowLimit));
		return false;
	} else if (cellLimit && cols * rows > cellLimit) {
		tinyMCEPopup.showErrorMsg(inst.getLang('table_dlg.cell_limit').replace(/\{\$cells\}/g, cellLimit));
		return false;
	}

	// Update table
	if (action == "update") {
		inst.execCommand('mceBeginUndoLevel');

		dom.setAttrib(elm, 'cellPadding', cellpadding, true);
		dom.setAttrib(elm, 'cellSpacing', cellspacing, true);
		dom.setAttrib(elm, 'border', border);
		dom.setAttrib(elm, 'align', align);
		dom.setAttrib(elm, 'frame', frame);
		dom.setAttrib(elm, 'rules', rules);
		if(border == 0){
			html += dom.setAttrib(elm, 'class', className + ' no-bor');
		}
		else{
			html += dom.setAttrib(elm, 'class', className + ' aliRTE-table-bor' + border);
		}
		dom.setAttrib(elm, 'style', style);
		dom.setAttrib(elm, 'id', id);
		dom.setAttrib(elm, 'summary', summary);
		dom.setAttrib(elm, 'dir', dir);
		dom.setAttrib(elm, 'lang', lang);

		capEl = inst.dom.select('.caption', elm)[0];

		if (capEl && !caption){
			inst.dom.removeClass(capEl, 'caption');
			for(var i = 0; i < cols; i++){
				var oldCell = capEl.childNodes[i];
				var newCell = capEl.ownerDocument.createElement('td');
				newCell.innerHTML = oldCell.innerHTML;
				capEl.insertBefore(newCell, oldCell);
				capEl.removeChild(oldCell);
			}
		}

		if (!capEl && caption) {
			capEl = inst.dom.select('tr', elm)[0];
			inst.dom.addClass(capEl, 'caption');
			for(var i = 0; i < cols; i++){
				var oldCell = capEl.childNodes[i];
				var newCell = capEl.ownerDocument.createElement('th');
				newCell.innerHTML = oldCell.innerHTML;
				capEl.insertBefore(newCell, oldCell);
				capEl.removeChild(oldCell);
			}
		}

		if (width && inst.settings.inline_styles) {
			dom.setStyle(elm, 'width', width);
			dom.setAttrib(elm, 'width', '');
		} else {
			dom.setAttrib(elm, 'width', width, true);
			dom.setStyle(elm, 'width', '');
		}

		// Remove these since they are not valid XHTML
		dom.setAttrib(elm, 'borderColor', '');
		dom.setAttrib(elm, 'bgColor', '');
		dom.setAttrib(elm, 'background', '');

		if (height && inst.settings.inline_styles) {
			dom.setStyle(elm, 'height', height);
			dom.setAttrib(elm, 'height', '');
		} else {
			dom.setAttrib(elm, 'height', height, true);
			dom.setStyle(elm, 'height', '');
 		}

		if (background != '')
			elm.style.backgroundImage = "url('" + background + "')";
		else
			elm.style.backgroundImage = '';

/*		if (tinyMCEPopup.getParam("inline_styles")) {
			if (width != '')
				elm.style.width = getCSSSize(width);
		}*/

		if (bordercolor != "") {
			elm.style.borderColor = bordercolor;
			elm.style.borderStyle = elm.style.borderStyle == "" ? "solid" : elm.style.borderStyle;
			elm.style.borderWidth = border == "" ? "1px" : border;
		} else
			elm.style.borderColor = '';

		elm.style.backgroundColor = bgcolor;
		elm.style.height = getCSSSize(height);

		inst.addVisual();

		// Fix for stange MSIE align bug
		//elm.outerHTML = elm.outerHTML;

		inst.nodeChanged();
		inst.execCommand('mceEndUndoLevel');

		// Repaint if dimensions changed
		if (formObj.width.value != orgTableWidth || formObj.height.value != orgTableHeight)
			inst.execCommand('mceRepaint');

		tinyMCEPopup.close();
		return true;
	}

	// Create new table
	html += '<table';

	html += makeAttrib('id', id);
	html += makeAttrib('border', border);
	html += makeAttrib('cellpadding', cellpadding);
	html += makeAttrib('cellspacing', cellspacing);

	if(editorWidth && cols * 100 >= editorWidth){
		width = '100%';
	}
	else{
		width = cols * 100;
	}
	
	if (width && inst.settings.inline_styles) {
		if (style)
			style += '; ';

		// Force px
		if (/^[0-9\.]+$/.test(width))
			width += 'px';

		style += 'width: ' + width;
	} else
		html += makeAttrib('width', width);

/*	if (height) {
		if (style)
			style += '; ';

		style += 'height: ' + height;
	}*/

	//html += makeAttrib('height', height);
	//html += makeAttrib('bordercolor', bordercolor);
	//html += makeAttrib('bgcolor', bgcolor);
	html += makeAttrib('align', align);
	html += makeAttrib('frame', frame);
	html += makeAttrib('rules', rules);
	if(border == 0){
		html += makeAttrib('class', className + ' no-bor');
	}
	else{
		html += makeAttrib('class', className + ' aliRTE-table-bor' + border);
	}
	html += makeAttrib('class', className);
	html += makeAttrib('style', style);
	html += makeAttrib('summary', summary);
	html += makeAttrib('dir', dir);
	html += makeAttrib('lang', lang);
	html += '>';

	if (caption) {
		html += '<tr class="caption">';
		
		for (var x=0; x<cols; x++) {
			if (!tinymce.isIE)
				html += '<th><br mce_bogus="1"/></th>';
			else
				html += '<th></th>';
		}
		
		html += '</tr>';
		
		rows--;
	}

	for (var y=0; y<rows; y++) {
		html += "<tr>";

		for (var x=0; x<cols; x++) {
			if (!tinymce.isIE)
				html += '<td><br mce_bogus="1"/></td>';
			else
				html += '<td></td>';
		}

		html += "</tr>";
	}

	html += "</table>";

	inst.execCommand('mceBeginUndoLevel');

	// Move table
	if (inst.settings.fix_table_elements) {
		var bm = inst.selection.getBookmark(), patt = '';

		inst.execCommand('mceInsertContent', false, '<br class="_mce_marker" />');

		tinymce.each('h1,h2,h3,h4,h5,h6,p'.split(','), function(n) {
			if (patt)
				patt += ',';

			patt += n + ' ._mce_marker';
		});

		tinymce.each(inst.dom.select(patt), function(n) {
			inst.dom.split(inst.dom.getParent(n, 'h1,h2,h3,h4,h5,h6,p'), n);
		});

		dom.setOuterHTML(dom.select('._mce_marker')[0], html);

		inst.selection.moveToBookmark(bm);
	} else
		inst.execCommand('mceInsertContent', false, html);

	inst.addVisual();
	inst.execCommand('mceEndUndoLevel');

	tinyMCEPopup.close();
}

function previewTable() {
	var formObj = document.forms[0];
	var inst = tinyMCEPopup.editor, dom = inst.dom;
	var cols = 2, rows = 2, border = 0, cellpadding = -1, cellspacing = -1, align, width, height, className, caption, frame, rules;
	var html = '', capEl;
	var cellLimit, rowLimit, colLimit;

	if (!AutoValidator.validate(formObj)) {
		tinyMCEPopup.showErrorMsg(inst.getLang('invalid_data'));
		return false;
	}

	// Get form data
	cols = formObj.elements['cols'].value;
	rows = formObj.elements['rows'].value;
	border = formObj.elements['border'].value != "" ? formObj.elements['border'].value  : 0;
	cellpadding = formObj.elements['cellpadding'].value != "" ? formObj.elements['cellpadding'].value : "";
	cellspacing = formObj.elements['cellspacing'].value != "" ? formObj.elements['cellspacing'].value : "";
	align = getSelectValue(formObj, "align");
	frame = getSelectValue(formObj, "tframe");
	rules = getSelectValue(formObj, "rules");
	width = formObj.elements['width'].value;
	height = formObj.elements['height'].value;
	bordercolor = formObj.elements['bordercolor'].value;
	bgcolor = formObj.elements['bgcolor'].value;
	className = getSelectValue(formObj, "class");
	id = formObj.elements['id'].value;
	summary = formObj.elements['summary'].value;
	style = formObj.elements['style'].value;
	dir = formObj.elements['dir'].value;
	lang = formObj.elements['lang'].value;
	background = formObj.elements['backgroundimage'].value;
	caption = formObj.elements['caption'].checked;

	cellLimit = tinyMCEPopup.getParam('table_cell_limit', false);
	rowLimit = tinyMCEPopup.getParam('table_row_limit', false);
	colLimit = tinyMCEPopup.getParam('table_col_limit', false);

	// Validate table size
	if (colLimit && cols > colLimit) {
		tinyMCEPopup.showErrorMsg(inst.getLang('table_dlg.col_limit').replace(/\{\$cols\}/g, colLimit));
		cols = colLimit;
		formObj.elements['cols'].value = colLimit;
	} else if (rowLimit && rows > rowLimit) {
		tinyMCEPopup.showErrorMsg(inst.getLang('table_dlg.row_limit').replace(/\{\$rows\}/g, rowLimit));
		rows = rowLimit;
		formObj.elements['rows'].value = rowLimit;
	} else if (border > 6) {
		border = '6';
		formObj.elements['border'].value = border;
	} else if (cellLimit && cols * rows > cellLimit) {
		tinyMCEPopup.showErrorMsg(inst.getLang('table_dlg.cell_limit').replace(/\{\$cells\}/g, cellLimit));
		return false;
	}

	document.getElementById('btn_add_col').className = (cols >= colLimit) ? 'disabled' : '';
	document.getElementById('btn_sub_col').className = (cols <= 1) ? 'disabled' : '';
	document.getElementById('btn_add_row').className = (rows >= rowLimit) ? 'disabled' : '';
	document.getElementById('btn_sub_row').className = (rows <= 1) ? 'disabled' : '';
	document.getElementById('btn_add_bor').className = (border >= 6) ? 'disabled' : '';
	document.getElementById('btn_sub_bor').className = (border <= 1) ? 'disabled' : '';
	
	// Create new table
	html += '<table';

	html += makeAttrib('id', id);
	html += makeAttrib('border', border);
	html += makeAttrib('cellpadding', cellpadding);
	html += makeAttrib('cellspacing', cellspacing);
	html += makeAttrib('align', align);
	html += makeAttrib('frame', frame);
	html += makeAttrib('rules', rules);
	//html += makeAttrib('style', style);
	html += makeAttrib('summary', summary);
	html += makeAttrib('dir', dir);
	html += makeAttrib('lang', lang);
	html += makeAttrib('width', '100%');
	
	if(border == 0){
		html += makeAttrib('class', 'no-bor');
	}
	else{
		html += makeAttrib('class', 'aliRTE-table-bor' + border);
	}
	
	html += '>';
	
	var maxRow = caption ? 9 : 10,
		maxCol = caption ? 9 : 10;

	if (caption) {
		html += "<tr>";
		
		for (var x=0, c=Math.min(cols, maxCol); x<c; x++) {
			if(cols > maxCol && x == maxCol - 1)
				html += '<th width="10%" class="no-bor">...</th>';
			else
				html += '<th>&nbsp;</th>';
		}
		
		html += "</tr>";
		
		rows--;
	}

	var tdText = '&nbsp;';
	
	for (var y=0, r=Math.min(rows, maxRow); y<r; y++) {
		if(rows > maxRow && y == maxRow - 1){
			html += '<tr class="no-bor">';
			tdText = '...';
		}
		else{
			tdText = '&nbsp;';
		}

		for (var x=0, c=Math.min(cols, maxCol); x<c; x++) {
			if(cols > maxCol && x == maxCol - 1)
				html += '<td width="10%" class="no-bor">...</td>';
			else
				html += '<td>' + tdText + '</td>';
		}

		html += '</tr>';
	}

	html += "</table>";
	
	document.getElementById('previewTable').innerHTML = html;
}

function makeAttrib(attrib, value) {
	var formObj = document.forms[0];
	var valueElm = formObj.elements[attrib];

	if (typeof(value) == "undefined" || value == null) {
		value = "";

		if (valueElm)
			value = valueElm.value;
	}

	if (value == "")
		return "";

	// XML encode it
	value = value.replace(/&/g, '&amp;');
	value = value.replace(/\"/g, '&quot;');
	value = value.replace(/</g, '&lt;');
	value = value.replace(/>/g, '&gt;');

	return ' ' + attrib + '="' + value + '"';
}

function init() {
	tinyMCEPopup.resizeToInnerSize();

	document.getElementById('backgroundimagebrowsercontainer').innerHTML = getBrowserHTML('backgroundimagebrowser','backgroundimage','image','table');
	document.getElementById('backgroundimagebrowsercontainer').innerHTML = getBrowserHTML('backgroundimagebrowser','backgroundimage','image','table');
	document.getElementById('bordercolor_pickcontainer').innerHTML = getColorPickerHTML('bordercolor_pick','bordercolor');
	document.getElementById('bgcolor_pickcontainer').innerHTML = getColorPickerHTML('bgcolor_pick','bgcolor');

	var cols = 3, rows = 3, border = tinyMCEPopup.getParam('table_default_border', '0'), cellpadding = tinyMCEPopup.getParam('table_default_cellpadding', '0'), cellspacing = tinyMCEPopup.getParam('table_default_cellspacing', '0');
	var align = "", width = tinyMCEPopup.getParam('table_default_width', ''), height = "", bordercolor = "", bgcolor = "", className = tinyMCEPopup.getParam('table_default_class', '');
	var id = "", summary = "", style = "", dir = "", lang = "", background = "", bgcolor = "", bordercolor = "", rules, frame;
	var inst = tinyMCEPopup.editor, dom = inst.dom;
	var formObj = document.forms[0];
	var elm = dom.getParent(inst.selection.getNode(), "table");

	action = tinyMCEPopup.getWindowArg('action');

	if (!action)
		action = elm ? "update" : "insert";

	if (elm && action != "insert") {
		var rowsAr = elm.rows;
		var cols = 0;
		for (var i=0; i<rowsAr.length; i++)
			if (rowsAr[i].cells.length > cols)
				cols = rowsAr[i].cells.length;

		cols = cols;
		rows = rowsAr.length;

		st = dom.parseStyle(dom.getAttrib(elm, "style"));
		border = trimSize(getStyle(elm, 'border', 'borderWidth'));
		cellpadding = dom.getAttrib(elm, 'cellpadding', "");
		cellspacing = dom.getAttrib(elm, 'cellspacing', "");
		width = trimSize(getStyle(elm, 'width', 'width'));
		height = trimSize(getStyle(elm, 'height', 'height'));
		bordercolor = convertRGBToHex(getStyle(elm, 'bordercolor', 'borderLeftColor'));
		bgcolor = convertRGBToHex(getStyle(elm, 'bgcolor', 'backgroundColor'));
		align = dom.getAttrib(elm, 'align', align);
		frame = dom.getAttrib(elm, 'frame');
		rules = dom.getAttrib(elm, 'rules');
		className = tinymce.trim(dom.getAttrib(elm, 'class').replace(/mceItem.+/g, ''));
		id = dom.getAttrib(elm, 'id');
		summary = dom.getAttrib(elm, 'summary');
		style = dom.serializeStyle(st);
		dir = dom.getAttrib(elm, 'dir');
		lang = dom.getAttrib(elm, 'lang');
		background = getStyle(elm, 'background', 'backgroundImage').replace(new RegExp("url\\('?([^']*)'?\\)", 'gi'), "$1");
		formObj.caption.checked = inst.dom.select('.caption', elm).length > 0;

		orgTableWidth = width;
		orgTableHeight = height;

		action = "update";
		formObj.insert.value = inst.getLang('update');
	}

	addClassesToList('class', "table_styles");
	TinyMCE_EditableSelects.init();

	// Update form
	selectByValue(formObj, 'align', align);
	selectByValue(formObj, 'tframe', frame);
	selectByValue(formObj, 'rules', rules);
	selectByValue(formObj, 'class', className, true, true);
	formObj.cols.value = cols;
	formObj.rows.value = rows;
	formObj.border.value = border;
	formObj.cellpadding.value = cellpadding;
	formObj.cellspacing.value = cellspacing;
	formObj.width.value = width;
	formObj.height.value = height;
	formObj.bordercolor.value = bordercolor;
	formObj.bgcolor.value = bgcolor;
	formObj.id.value = id;
	formObj.summary.value = summary;
	formObj.style.value = style;
	formObj.dir.value = dir;
	formObj.lang.value = lang;
	formObj.backgroundimage.value = background;

	updateColor('bordercolor_pick', 'bordercolor');
	updateColor('bgcolor_pick', 'bgcolor');

	// Resize some elements
	if (isVisible('backgroundimagebrowser'))
		document.getElementById('backgroundimage').style.width = '180px';

	// Disable some fields in update mode
	if (action == "update"){
		formObj.cols.disabled = true;
		formObj.rows.disabled = true;
		
		setTimeout(function(){
			document.getElementById('btn_sub_col').className = 'disabled';
			document.getElementById('btn_add_col').className = 'disabled';
			document.getElementById('btn_sub_row').className = 'disabled';
			document.getElementById('btn_add_row').className = 'disabled';
			document.getElementById('btn_sub_bor').className = 'disabled';
			document.getElementById('btn_add_bor').className = 'disabled';
		}, 0);
	}
	else{
		document.getElementById('btn_add_col').onclick = function(){
			var _col = document.forms[0].elements['cols'];
			var _c = _col.value;
			var colLimit = tinyMCEPopup.getParam('table_col_limit', false);
			_c++;
			
			if(_c > colLimit){
				return;
			}
			else{
				_col.value = _c;
				document.getElementById('btn_sub_col').className = '';
				if(_c == colLimit){
					document.getElementById('btn_add_col').className = 'disabled';
				}
			}
			previewTable();
		};
		document.getElementById('btn_sub_col').onclick = function(){
			var _col = document.forms[0].elements['cols'];
			var _c = _col.value;
			_c--;
			
			if(_c < 1){
				return;
			}
			else{
				_col.value = _c;
				document.getElementById('btn_add_col').className = '';
				if(_c == 1){
					document.getElementById('btn_sub_col').className = 'disabled';
				}
			}
			previewTable();
		};
		document.getElementById('btn_add_row').onclick = function(){
			var _row = document.forms[0].elements['rows'];
			var _r = _row.value;
			var rowLimit = tinyMCEPopup.getParam('table_row_limit', false);
			_r++;
			
			if(_r > rowLimit){
				return;
			}
			else{
				_row.value = _r;
				document.getElementById('btn_sub_row').className = '';
				if(_r == rowLimit){
					document.getElementById('btn_add_row').className = 'disabled';
				}
			}
			previewTable();
		};
		document.getElementById('btn_sub_row').onclick = function(){
			var _row = document.forms[0].elements['rows'];
			var _r = _row.value;
			_r--;
			
			if(_r < 1){
				return;
			}
			else{
				_row.value = _r;
				document.getElementById('btn_add_row').className = '';
				if(_r == 1){
					document.getElementById('btn_sub_row').className = 'disabled';
				}
			}
			previewTable();
		};
		document.getElementById('btn_add_bor').onclick = function(){
			var _bor = document.forms[0].elements['border'];
			var _b = _bor.value;
			_b++;
			
			if(_b > 6){
				return;
			}
			else{
				_bor.value = _b;
				document.getElementById('btn_sub_bor').className = '';
				if(_b == 6){
					document.getElementById('btn_add_bor').className = 'disabled';
				}
			}
			previewTable();
		};
		document.getElementById('btn_sub_bor').onclick = function(){
			var _bor = document.forms[0].elements['border'];
			var _b = _bor.value;
			_b--;
			
			if(_b < 0){
				return;
			}
			else{
				_bor.value = _b;
				document.getElementById('btn_add_bor').className = '';
				if(_b == 0){
					document.getElementById('btn_sub_bor').className = 'disabled';
				}
			}
			previewTable();
		};
	}
	previewTable();
}

function changedSize() {
	var formObj = document.forms[0];
	var st = dom.parseStyle(formObj.style.value);

/*	var width = formObj.width.value;
	if (width != "")
		st['width'] = tinyMCEPopup.getParam("inline_styles") ? getCSSSize(width) : "";
	else
		st['width'] = "";*/

	var height = formObj.height.value;
	if (height != "")
		st['height'] = getCSSSize(height);
	else
		st['height'] = "";

	formObj.style.value = dom.serializeStyle(st);
}

function changedBackgroundImage() {
	var formObj = document.forms[0];
	var st = dom.parseStyle(formObj.style.value);

	st['background-image'] = "url('" + formObj.backgroundimage.value + "')";

	formObj.style.value = dom.serializeStyle(st);
}

function changedBorder() {
	var formObj = document.forms[0];
	var st = dom.parseStyle(formObj.style.value);

	// Update border width if the element has a color
	if (formObj.border.value != "" && formObj.bordercolor.value != "")
		st['border-width'] = formObj.border.value + "px";

	formObj.style.value = dom.serializeStyle(st);
}

function changedColor() {
	var formObj = document.forms[0];
	var st = dom.parseStyle(formObj.style.value);

	st['background-color'] = formObj.bgcolor.value;

	if (formObj.bordercolor.value != "") {
		st['border-color'] = formObj.bordercolor.value;

		// Add border-width if it's missing
		if (!st['border-width'])
			st['border-width'] = formObj.border.value == "" ? "1px" : formObj.border.value + "px";
	}

	formObj.style.value = dom.serializeStyle(st);
}

function changedStyle() {
	var formObj = document.forms[0];
	var st = dom.parseStyle(formObj.style.value);

	if (st['background-image'])
		formObj.backgroundimage.value = st['background-image'].replace(new RegExp("url\\('?([^']*)'?\\)", 'gi'), "$1");
	else
		formObj.backgroundimage.value = '';

	if (st['width'])
		formObj.width.value = trimSize(st['width']);

	if (st['height'])
		formObj.height.value = trimSize(st['height']);

	if (st['background-color']) {
		formObj.bgcolor.value = st['background-color'];
		updateColor('bgcolor_pick','bgcolor');
	}

	if (st['border-color']) {
		formObj.bordercolor.value = st['border-color'];
		updateColor('bordercolor_pick','bordercolor');
	}
}

tinyMCEPopup.onInit.add(init);
