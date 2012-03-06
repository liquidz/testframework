(ns misaki.test.core
  (:use misaki)
;  (:use [misaki.server])
  (:use [clojure.test]))

(deftest replace-me

  (println (json 1))
  (println (json "hello"))
  (println (json [1 2 "3"]))
  (println (json {:a 1 :b "2"}))

  (is true))
