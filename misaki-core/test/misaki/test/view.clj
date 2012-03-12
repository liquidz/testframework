(ns misaki.test.view
  (:use misaki.view
        [hiccup.core :only [html]])
  (:use [clojure.test]))


(deftest css-test
  (is (= 1 (count (css "a.css"))))
  (is (= 2 (count (css "a.css" "b.css"))))
  (is (= 2 (count (css ["a.css" "b.css"])))))


(deftest ul-test
  (is (= "<ul><li>1</li><li>2</li></ul>"
         (html (ul 1 2))))

  (is (= "<ul><li>1</li><ul><li>2</li></ul></ul>"
         (html (ul 1 (ul 2)))))

  (is (= "<ul id=\"a\"><li>1</li></ul>"
         (html (ul {:id "a"} 1))))

  (is  (= "<ul><li><a href=\"a\">b</a></li></ul>"
          (html (ul (link "a" "b"))))))


(deftest set-class-test
  (is (= "<p class=\"a\">a</p>"
         (html (set-class [:p "a"] "a"))))
  (is (= "<p class=\"b\">a</p>"
         (html (set-class [:p {:class "a"} "a"] "b"))))
  (is (= "<p class=\"b c\">a</p>"
         (html (set-class [:p {:class "a"} "a"] "b c")))))


(deftest set-id-test
  (is (= "<p id=\"a\">a</p>"
         (html (set-id [:p "a"] "a"))))
  (is (= "<p id=\"b\">a</p>"
         (html (set-id [:p {:id "a"} "a"] "b")))))
