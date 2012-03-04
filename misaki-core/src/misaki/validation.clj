(in-ns 'misaki)
;(ns misaki.validation)

(defmacro defvalidate [name params default-msg pred-body]
  (let [data-name (first params)
        fn-params (rest params)]
    `(defn ~name
       ([~@fn-params]
        (fn [~data-name]
          (if (not ~pred-body) ~default-msg)))
       ([~@fn-params msg#]
        (fn [~data-name]
          (if (not ~pred-body) msg#))))))

(defn validate-comp [error-msg & asserts]
  (if (not (string? error-msg))
    (apply validate-comp "validation error" error-msg asserts)
    (fn [param]
      (if (some #(->> param % nil? not)  asserts) error-msg))))

(defn validate [param & asserts]
  (let [res (remove nil? (map #(% param) asserts))]
    (if (not (empty? res)) res)))

(defn val2str [x]
  (cond
    (nil? x) "nil"
    (string? x) (str "\"" x "\"")
    (keyword? x) (name x)
    :else (str x)))

(defvalidate required [param key]
  (str (val2str key) " is required")
  (and (not (nil? (get param key)))
       (not= "" (get param key))))

(defvalidate equals [param key val]
  (str (val2str key) " is not " (val2str val) ", but " (val2str (get param key)))
  (= (get param key) val))



