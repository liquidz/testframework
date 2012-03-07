(ns misaki.test.config
  (:use [clojure.test]
        [misaki :only [*config*]]))

(deftest config-test
  (is (not (nil? *config*)))
  (is (nil? (:dummy *config*)))

  (testing "can read properties"
    (is (= "hello" (:testdata1 *config*))))

  (testing "read test data when dev mode"
    (is (= "correct" (:testdata2 *config*))))

  (testing "read environmental variable"
    (is (= 0 (.indexOf (:testdata3 *config*) "/home")))
    ; read environmental variable with default value
    (is (= "default_value" (:testdata4 *config*)))
    (is (= "http://foo.bar.com:80" (:testdata5 *config*)))))

