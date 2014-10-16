import 'dart:html';
import "dart:async";

import 'package:widget/effects.dart';

/*
 * Author: Meshulam Silk (moomoohk@ymail.com)
 *
 * A slideshow widget I wrote for my site.
 */

List<Slideshow> slideshows = new List<Slideshow>();

void main() {
  querySelectorAll(".slideshow").forEach((Element element) {
    if (!element.classes.contains("ignore")) slideshows.add(new Slideshow(element));
  });
}

class Slideshow {
  Timer _timer;
  List<Element> _thumbnails;
  Element _slideshowContainer, _currentSlide, _selectedThumbnail, _currentSlideText;
  num _pauseDuration;

  Slideshow(DivElement container, {num pauseDuration: 5000}) : _pauseDuration = pauseDuration {
    if (container.children.every((Element element) => element is ImageElement && element.classes.contains("slide"))) {
      _thumbnails = container.children.toList();
      container.children.clear();
      DivElement ribbon, thumbnails;
      container.children
          ..add(_slideshowContainer = new DivElement()
              ..classes.add("currentSlideContainer")
              ..children.add(_currentSlide = new ImageElement()..classes.add("currentSlide"))
              ..children.add(_currentSlideText = new SpanElement()
                  ..classes.add("currentSlideText")
                  ..onMouseEnter.listen((event) => setTimer(false))
                  ..onMouseLeave.listen((event) => setTimer(true))))
          ..add(ribbon = new DivElement()..classes.add("ribbon"));
      ribbon.children
          ..add(new DivElement()
              ..text = "<"
              ..classes.add("slideshowControl left")
              ..onClick.listen((event) => _previousImage()))
          ..add(thumbnails = new DivElement()..classes.add("thumbnails"))
          ..add(new DivElement()
              ..text = ">"
              ..classes.add("slideshowControl right")
              ..onClick.listen((event) => _nextImage()));
      _thumbnails.forEach((ImageElement slide) {
        slide
            ..classes.clear()
            ..onMouseEnter.listen((event) {
              slide.classes.add("mouseOver");
              if (slide.classes.contains("selectedThumbnail")) setTimer(false);
            })
            ..onMouseLeave.listen((event) {
              slide.classes.remove("mouseOver");
              if (slide.classes.contains("selectedThumbnail")) setTimer(true);
            })
            ..classes.add("thumbnail")
            ..onClick.listen((event) => _changeImage(slide));
        thumbnails.children.add(slide);
      });
      _slideshowContainer.onClick.listen((event) {
        if (_selectedThumbnail.attributes["href"] != null) window.location.assign(_selectedThumbnail.attributes["href"]);
      });
      _changeImage(_thumbnails[0]);
      _initTimer();
    }
  }

  void _nextImage() {
    num index = _thumbnails.indexOf(_selectedThumbnail);
    if (index == _thumbnails.length - 1) _changeImage(_thumbnails.first); else _changeImage(_thumbnails[index + 1]);
    _initTimer();
  }

  void _previousImage() {
    num index = _thumbnails.indexOf(_selectedThumbnail);
    if (index == 0) _changeImage(_thumbnails.last); else _changeImage(_thumbnails[index - 1]);
    _initTimer();
  }

  void _changeImage(ImageElement thumbnail) {
    if (thumbnail == _selectedThumbnail) {
      return;
    }
    if (_currentSlide.attributes["src"] != "") ShowHide.begin(ShowHideAction.HIDE, _currentSlide, duration: 500, effectTiming: EffectTiming.ease).then((result) => _currentSlide.attributes["src"] = thumbnail.src); else _currentSlide.attributes["src"] = thumbnail.src;
    ShowHide.show(_currentSlide, effect: new FadeEffect(), duration: 500);
    if (thumbnail.classes.contains("mouseOver")) setTimer(false);
    if (_selectedThumbnail != null) _selectedThumbnail.classes.remove("selectedThumbnail");
    _selectedThumbnail = thumbnail;
    _selectedThumbnail.classes.add("selectedThumbnail");
    if (_currentSlideText.text != "") ShowHide.begin(ShowHideAction.HIDE, _currentSlideText, duration: 500, effectTiming: EffectTiming.ease).then((result) {
      _currentSlideText.text = thumbnail.alt;
    }); else _currentSlideText.text = thumbnail.alt;
    if (thumbnail.attributes["href"] != null) _slideshowContainer.classes.add("link"); else _slideshowContainer.classes.remove("link");
    ShowHide.show(_currentSlideText, effect: new FadeEffect(), duration: 500);
  }

  void setTimer(bool f) {
    if (_timer != null) if (f) _initTimer(); else if (_timer.isActive) _timer.cancel();
  }

  void _initTimer() {
    if (_timer != null && _timer.isActive) _timer.cancel();
    _timer = new Timer(new Duration(milliseconds: _pauseDuration), () => _nextImage());
  }
}
