(ns misaki.util.test
  (:use
    [misaki.core :only [misaki-app-symbol]]
    ring.mock.request)
  (:require [clojure.string :as str]))


(defmacro send-request
  "send request to misaki's default route(misaki-app)"
  [& args]
  (let [args (if (keyword? (first args))
               args (cons :get args))]
    `(~misaki-app-symbol (request ~@args))))

(defn status-ok? [res]
  (= 200 (:status res)))

(defn content-type= [res value]
  (= value (first (str/split (get (:headers res) "Content-Type") #";"))))

(defn header= [res key value]
  (let [key (if (keyword? key) (name key) key)]
    (= value (get (:headers res) key))))

(defn header-contains? [res key value]
  (let [key (if (keyword? key) (name key) key)]
    (println "a:" (get (:headers res) key))
    (println "b:" (:headers res))
    (not= -1 (.indexOf (get (:headers res) key "") value))))

(defn body-contains? [req s]
  (not= -1 (.indexOf (:body req) s)))

