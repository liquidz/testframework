(ns misaki.test.model
  (:use [clojure.test]
        [misaki :only [*config*]]
        misaki.model
        somnium.congomongo)
  (:require
    [clojureql.core :as sql]))

(deftest mongo-model-test
  (initialize-mongodb (:test-mongo-url *config*))

  (drop-coll! :test)
  (create-collection! :test)

  (insert! :test {:hello "world"})

  (is (= 1 (count (fetch :test))))
  (is (contains? (fetch-one :test) :hello))
  (is (= "world" (:hello (fetch-one :test)))))

(deftest mysql-model-test
  (initialize-mysql (:test-mysql-url *config*))

  (let [tbl (sql/table :test)]
    (sql/disj! tbl (sql/where (>= :id 0)))
    (is (zero? (count @tbl)))

    (sql/conj! tbl {:name "hello"})
    (sql/conj! tbl {:name "world"})

    (is (= 2 (count @tbl)))
    (is (= "hello" (:name (first @(sql/take (sql/sort tbl [:id]) 1)))))))
