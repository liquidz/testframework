(ns misaki.test.route
  (:use
    clojure.test
    misaki
    ring.mock.request))

; sample
(defapp
  (GET "/" _ "hello"))

(defn send-request [& args]
  (if (keyword? (first args))
    (apply request args)
    (apply request :get args)))

(deftest route-test
  (let [{:keys [status body]} (send-request "/")]
    (is (= 200 status))
    (is (= "hello" body))))
