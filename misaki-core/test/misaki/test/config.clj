(ns misaki.test.config
  (:use [clojure.test]
        [misaki :only [*config*]]))

(deftest config-test
  (println *config*)

  (is (not (nil? *config*)))
  (is (nil? (:dummy *config*)))
  ; can read properties
  (is (= "hello" (:testdata1 *config*)))

  ; read test data when developing
  (is (= "correct" (:testdata2 *config*)))

  ; read environment variable
  (is (= 0 (.indexOf (:testdata3 *config*) "/home"))))

