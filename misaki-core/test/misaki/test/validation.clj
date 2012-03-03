(ns misaki.test.validation
  (:use [misaki.validation])
  (:use [clojure.test]))

(deftest required-test
  (let [x nil, y "", z 10, f (constantly nil)]
    ; invalid pattern
    (is (= "a is required"     ((required :a) {})))
    (is (= "test"              ((required :a "test") {})))
    (is (= "\"a\" is required" ((required "a") {})))
    (is (= "nil is required"   ((required nil) {})))
    (is (= "nil is required"   ((required (f)) {})))

    ; valid pattern
    (is (nil? ((required :a) {:a 1})))
    (is (nil? ((required 'a) {'a 1})))
    (is (nil? ((required "a") {"a" 1})))

    ))


(deftest equals-test
  (let [f (constantly nil)]
    ; invalid pattern
    (is (= "nil is not nil, but 0"   ((equals nil nil) {nil 0})))
    (is (= "a is not false, but 0"   ((equals :a false) {:a 0})))
    (is (= "a is not \"nil\", but 0" ((equals :a "nil") {:a 0})))
    (is (= "test"                    ((equals :a "nil" "test") {:a 0})))
    (is (= "nil is not 1, but 0"     ((equals (f) 1) {nil 0})))

    ; valid pattern
    (is (nil? ((equals :a true) {:a true})))
    (is (nil? ((equals :a nil) {:b "dummy"}))) ; must be checked by "required"
    (is (nil? ((equals :a ((constantly 10))) {:a 10})))

    ))


(deftest validate-test
  ; invalid pattern

  (is (= (validate {:a true} (required :b "test"))
         (list "test")))

  (is (= (validate {:a true} (required :a) (required :b "test"))
         (list "test")))

  (is (= (validate {:a true} (required :b "test") (required :a))
         (list "test")))

  (is (= (validate {:a true} (required :b "test") (equals :a false "test2"))
         (list "test" "test2")))

  ; valid pattern
  (is (nil? (validate {:a true} (required :a))))
  (is (nil? (validate {:a true} (required :a) (equals :a true))))

  )



