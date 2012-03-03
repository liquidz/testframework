(ns misaki.test.view
  (:use [misaki.view])
  (:use [clojure.test]))


(deftest css-test
  (is (= 1 (count (css "a.css"))))
  (is (= 2 (count (css "a.css" "b.css"))))
  (is (= 2 (count (css ["a.css" "b.css"])))))

