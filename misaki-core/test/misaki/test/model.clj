(ns misaki.test.model
  (:use [clojure.test]
        misaki.model
        somnium.congomongo))

(deftest mongo-model-test
  (drop-coll! :test)
  (create-collection! :test)

  (insert! :test {:hello "world"})

  (is (= 1 (count (fetch :test))))
  (is (contains? (fetch-one :test) :hello))
  (is (= "world" (:hello (fetch-one :test)))))

