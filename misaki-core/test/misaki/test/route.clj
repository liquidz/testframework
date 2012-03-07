(ns misaki.test.route
  (:use
    clojure.test
    misaki
    ring.mock.request))

; sample routing
(defapp
  (GET "/" _ "hello")
  (GET "/html" _ [:h1 "hello"])
  (POST "/" _ "world")
  (JSON "/api" _ {:a "b"}))

(defn send-request [& args]
  (misaki-app
    (if (keyword? (first args))
      (apply request args)
      (apply request :get args))))

(deftest route-test
  (testing "GET routing response"
    (is (= 200 (:status (send-request "/"))))
    (is (= "hello" (:body (send-request "/"))))
    (is (= "<!DOCTYPE html>\n<html><h1>hello</h1></html>"
           (:body (send-request "/html")))))

  (testing "POST routing response"
    (is (= 200 (:status (send-request :post "/"))))
    (is (= "world" (:body (send-request :post "/")))))

  (testing "JSON routing response"
    (is (= 200 (:status (send-request "/api"))))
    (is (= "{\"a\":\"b\"}" (:body (send-request "/api"))))))
