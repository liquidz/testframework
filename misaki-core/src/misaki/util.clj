(ns misaki.util
  (:require [clojure.string :as str]))

(defmacro aif [pred then-part & [else-part]]
  `(if-let [~'it ~pred]
    ~then-part
    ~else-part))

(defn get-env
  ([name]
   (let [[name & value] (str/split name #":")
         value (if value (str/join ":" value))]
     (get-env name value)))
  ([name default-value]
   (let [_name (str/replace-first name "$" "")]
     (get (System/getenv) _name default-value))))


;(defn- head-tag? [tag]
;  (and (vector? tag) (= :head (first tag))))
;
;(defn add-meta
;  "add meta tag to shtml"
;  [meta-tag shtml]
;  (let [[html [head] body] (partition-by head-tag? shtml)
;        new-head (apply conj [(first head) meta-tag] (rest head))]
;    (apply conj (vec html) new-head body)))
