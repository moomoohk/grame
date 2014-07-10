/* Slideshow widget */
var selectedIndex = 0;
var speed = 5000;
var timer;
var images = new Array(); 

$(document).ready(function() {
	$("img.slide").each(function() {
		images.push($(this).attr("src"));
		$(this).css("display", "none");
		$(this).height(Math.min(300, ($("body").width - 10)));
	});
	
});

$(document).ready(function() {
	timer = setInterval(function() {
		changeImage(++selectedIndex);
	}, speed);

	$("#screenies").after("<p id=\"thumbs\"></p>");
	$("#screenies img").each(function() {
		$("#thumbs").append("<img src=\"" + $(this).attr("src") + "\"/>");
	});
	changeImage(selectedIndex);
	$("#thumbs img").on("mouseenter", function() {
		$(this).addClass("mouseover");
		if ($(this).hasClass("selected")) {
			toggleTimer(false);
		}
	}).on("mouseleave", function() {
		$(this).removeClass("mouseover");
		if ($(this).hasClass("selected")) {
			toggleTimer(true);
		}
	}).on("click", function() {
		changeImage( selectedIndex = $(this).index());
	});
});

function changeImage(index) {
	toggleTimer(false);
	$("#screenies img").removeClass("opaque");
	index = index % $("#thumbs img").length;

	$("#screenies img:nth(" + index + ")").height($("#screenies").height());
	var ratio = $("#screenies").height() / $("#screenies img:nth(" + index + ")").height();
	$("#screenies img:nth(" + index + ")").width(ratio * $("#screenies img:nth(" + index + ")").width());
	$("#screenies").width(ratio * $("#screenies img:nth(" + index + ")").width());
	$("#screenies img:nth(" + index + ")").addClass("opaque");
	$("#thumbs img").removeClass("selected");
	$("#thumbs img:nth(" + index + ")").addClass("selected");
	if (!$("#thumbs img:nth(" + index + ")").hasClass("mouseover")) {
		toggleTimer(true);
	}
}

function toggleTimer(f) {
	if (f) {
		timer = setInterval(function() {
			changeImage(++selectedIndex);
		}, speed);
	} else {
		clearInterval(timer);
	}
}