/*
 * Author: Meshulam Silk (moomoohk@ymail.com)
 * Description: Slideshow widget
 */

var selectedIndex = 0;
var speed = 5000;
var timer;

$(document).ready(function() {
	timer = setInterval(function() {
		changeImage($(".slide:nth(" + ((++selectedIndex) % $(".slide").length()) + ")"));
	}, speed);

	$(".slideshow").append("<img class=\"currSlide\"/><div class=\"slides\"></div>");
	$(".slideshow").css({
		"height" : "500px",
		"background-color" : "white",
		"overflow" : "auto",
		"border" : "1px solid #505050",
		"border-radius" : "5px"
	});

	$(".currSlide").css({
		"height" : "400px",
		"display" : "block",
		"margin" : "auto"
	});

	$(".slides").css({
		"vertical-align" : "middle",
		"height" : "90px",
		"background-color" : "#505050",
		"border-radius" : "5px",
		"padding" : "5px",
		"overflow-x" : "auto",
		"white-space" : "nowrap"
	});

	$("img.slide").each(function() {
		var thumbSlide = $(this).clone();
		$(this).removeClass("slide");
		$(".slides").append(thumbSlide);

		thumbSlide.css({
			"height" : (thumbSlide.parent().height() - 10) + "px",
			"margin-left" : "2px",
			"margin-right" : "2px",
			"cursor" : "pointer",
			"background-color" : "white",
			"border-style" : "solid",
			"border-width" : "0px",
			"border-color" : "rgb(168, 168, 168)",
			"vertical-align" : "middle"
		});

		thumbSlide.hover(function() {
			$(this).addClass("mouseover");
			if ($(this).hasClass("selected")) {
				setTimer(false);
			}
			$(this).animate({
				"height" : ($(this).parent().height() - 5) + "px",
			}, 100);
		}, function() {
			$(this).removeClass("mouseover");
			if ($(this).hasClass("selected")) {
				setTimer(true);
			}
			$(this).animate({
				"height" : ($(this).parent().height() - 10) + "px",
			}, 100);
		});
		thumbSlide.click(function() {
			changeImage($(this));
		});
		$(this).css("display", "none");
		$(this).height(Math.min(300, ($("body").width - 10)));
	});

	$(".slides").prepend("<div class=\"slideshowControl left\"></div>");
	$(".slides").append("<div class=\"slideshowControl right\"></div>");

	$(".slideshowControl").css({
		"width" : "30px",
		"height" : "90px",
		"display" : "inline-block",
		"vertical-align" : "middle",
		"margin-left" : "2px",
		"margin-right" : "2px",
		"background-color" : "#E6E6E6",
		"border-radius" : "5px",
		"cursor" : "pointer"
	});

	changeImage($(".slide:nth(0)"));
});

function changeImage(thumbnail) {
	if (thumbnail.hasClass("selected")) {
		return;
	}
	setTimer(false);
	$(".currSlide").fadeOut(100, function() {
		$(".currSlide").attr("src", thumbnail.attr("src"));
	});
	$(".currSlide").fadeIn(100);
	if (!thumbnail.hasClass("mouseover")) {
		setTimer(true);
	}

	$(".selected").animate({
		borderWidth : "0px"
	}, 100);

	$(".selected").removeClass("selected");
	thumbnail.addClass("selected");

	$(".selected").animate({
		borderWidth : "3px"
	}, 100);
}

function setTimer(f) {
	if (f) {
		timer = setInterval(function() {
			if (selectedIndex == $(".slide").length - 1) {
				selectedIndex = 0;
			} else {
				selectedIndex++;
			}
			changeImage($(".slide:nth(" + selectedIndex + ")"));
		}, speed);
	} else {
		clearInterval(timer);
	}
}