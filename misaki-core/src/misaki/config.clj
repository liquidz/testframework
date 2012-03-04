(ns misaki.config
  (:use
    clojure.java.io
    [misaki.util :only [get-env]])
  (:require [clojure.string :as str])
  (:import [java.util Properties]))

;; private
(defn- correct-prop-name
  [filename]
  (if (.endsWith filename ".properties")
    filename
    (str filename ".properties")))

(defn- test->real
  "convert test key to real key
  ex) :@test-key => :key"
  [key]
  (keyword (str/replace-first (name key) "@test-" "")))

(defn- merge-test-config
  "merge config setting to real setting if mode is develop"
  [config]
  (if (not= "develop" (:mode config))
    config
    (let [test-keys (filter #(.startsWith (name %) "@test-") (keys config))
          newdata (into {} (map #(vector (test->real %) (% config)) test-keys))]
      (merge config newdata))))

(defn- expand-environmental-variables [config]
  (into {} (for [[k v] config]
             [k (if (.startsWith v "$") (get-env v) v)])))

;; public
(defn load-properties
  "load specified properties file"
  [filename]
  (try
    (with-open [r (reader (correct-prop-name filename))]
      (doto (Properties.) (.load r)))
    (catch Exception _ [])))

(defn property->map
  "convert Properties to map"
  [prop]
  (into {} (for [[k v] prop] [(keyword k) v])))

(def load-config
  (comp
    expand-environmental-variables
    merge-test-config
    property->map load-properties))

