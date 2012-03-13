(ns misaki.test.route
  (:use
    clojure.test
    misaki.core
    misaki.util.test
    ;ring.mock.request
    ))

(defapp
  (GET "/" _ "hello"))

(deftest routeing-test
  (let [res (send-request "/")]
    (is (status-ok? res))
    (is (content-type= res "text/html"))
    (is (body-contains? res "hel"))))

