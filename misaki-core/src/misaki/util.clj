(ns misaki.util)

(defmacro aif [pred then-part & [else-part]]
  `(if-let [~'it ~pred]
    ~then-part
    ~else-part))


;(defn- head-tag? [tag]
;  (and (vector? tag) (= :head (first tag))))
;
;(defn add-meta
;  "add meta tag to shtml"
;  [meta-tag shtml]
;  (let [[html [head] body] (partition-by head-tag? shtml)
;        new-head (apply conj [(first head) meta-tag] (rest head))]
;    (apply conj (vec html) new-head body)))
