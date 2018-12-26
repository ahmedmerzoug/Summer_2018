/*width : menu header datatable*/

var withMenuDataTable = 180;

$(document).on('click', 'button[id$="UIMenuDataTableId_button"]', function() {
	$('div[id$="UIMenuDataTableId_menu"]').each(function() {
		$(this)[0].style.width = withMenuDataTable + 'px';
	});
});

/* width and position panel : menu header datatable */
$(document).on(
		'click',
		'button[id$="UIMenuDataTableIdAr_button"]',
		function() {
			var widthbutton = $(this).width();
			$('div[id$="UIMenuDataTableIdAr_menu"]').each(
					function() {
						$(this)[0].style.width = withMenuDataTable + 'px';
						var left = (parseInt($(this)[0].style.left, 10))
								- withMenuDataTable + widthbutton - 10;
						$(this)[0].style.left = left + "px";
					});
		});

$(document).ready(function() {
	$('#title').click(function() {
		$('#tags_check').toggle();
	});
});

jQuery(document).ready(function() {

	var sH = $(window).height() - 60;
	var gh = $(window).height();
	var gw = $(window).width();
	$('.content').css('height', sH + 'px');
});

function toggle() {
	alert('ahmed berred');
	var ele = document.getElementById("toggleText");
	var text = document.getElementById("displayText");
	if (ele.style.display == "none") {
		ele.style.display = "block";
	} else {
		ele.style.display = "none";
	}
}

function fixIdSelector(id){
	return id.replace(/\./g, "\\.").replace(/\:/g, "\\:");
}

function genericErrorMessage(source){
	var cmpt = $("#" + fixIdSelector(source));
	var msg = cmpt.parent().find(".ui-message");
	if(msg.hasClass("ui-message-error")){
		cmpt.find("input").tooltip({
			title: msg.find("span").html(),
			template: '<div class="tooltip error-tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>',
			trigger: 'manual',
			placement: 'top' // update CSS .tooltip.top.error-tooltip
		}).tooltip('show');
	} else {
		cmpt.find("input").tooltip('destroy')
	}
}
