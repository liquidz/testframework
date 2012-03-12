(ns misaki.test.validation
  (:use misaki.validation)
  (:use [clojure.test]))

(deftest validation-test
  (with-validation
    (validate! (nil? nil) "dummy")
    (is (not (has-error?)))

    (validate! (nil? 1) "test")
    (is (has-error?))
    (is (= ["test"] (get-errors)))

    (validate! (= 1 2) "test2")
    (is (= ["test" "test2"] (get-errors)))))

(defn myvalidate [x]
  (validate! (= x 10) "x is not 10"))

(deftest outer-validation-test
  (with-validation
    (myvalidate 10)
    (is (not (has-error?)))

    (myvalidate 5)
    (is (= ["x is not 10"] (get-errors)))))

(deftest keep-validation-test
  (with-validation
    (is (not (has-error?)))
    (validate! (= 1 2) "test")
    (keep-validation!))

  (with-validation
    (is (has-error?))
    (is (= ["test"] (get-errors)))

    (validate! false "test2")
    (is (= ["test" "test2"] (get-errors)))))

