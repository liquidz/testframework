(ns misaki.config
  (:use clojure.java.io)
  (:import [java.util Properties]))

(defn- correct-prop-name
  [filename]
  (if (not= -1 (.lastIndexOf filename ".properties"))
    filename
    (str filename ".properties")))

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

(def load-config (comp property->map load-properties))

