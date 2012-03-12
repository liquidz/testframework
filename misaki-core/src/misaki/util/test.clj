(ns misaki.util.test
  (:use ring.mock.request))

(defn send-request
  "send request to misaki's default route(misaki-app)"
  [& args]
  (misaki-app (apply request args)))

(defn status-ok? [req]
  (= 200 (:status req)))

(defn header= [key value]
  (let [key (if (keyword? key) (name key) key)]
    (= value (get (:header req) key))))

(defn body-contains? [s]
  (not= -1 (.indexOf (:body req) s)))

