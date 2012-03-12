(ns misaki.test.model
  (:use [clojure.test]
        [misaki.core :only [*config*]]
        [misaki.config congomongo korma]
;        misaki.model
        somnium.congomongo
        korma.core
        )
 ; (:require
 ;   [clojureql.core :as sql])
  )

(deftest mongo-model-test
  (init-congomongo (:test-mongo-url *config*))

  (drop-coll! :test)
  (create-collection! :test)

  (insert! :test {:hello "world"})

  (is (= 1 (count (fetch :test))))
  (is (contains? (fetch-one :test) :hello))
  (is (= "world" (:hello (fetch-one :test)))))

(defentity test-tbl (table :test))

(deftest mysql-model-test
  (init-korma (:test-mysql-url *config*))

  (delete test-tbl)
  (is (zero? (count (select test-tbl))))

  (insert test-tbl (values {:name "hello"}))
  (insert test-tbl (values {:name "world"}))
  (is (= 2 (count (select test-tbl))))

  (is (= "hello" (:name (first (select test-tbl (order :id :ASC)))))))
