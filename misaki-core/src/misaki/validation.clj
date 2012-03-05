(in-ns 'misaki)
;(ns misaki.validation)

; TODO
; 単一の関数を定義して、その中にバリデーション用の関数をもたせると
; misaki内に含んでも関数名の衝突が防げそう

(defmacro defvalidate
  "define validation macro"
  [name params default-msg pred-body]
  (let [data-name (first params)
        fn-params (rest params)]
    `(defn ~name
       ([~@fn-params]
        (fn [~data-name]
          (if (not ~pred-body) ~default-msg)))
       ([~@fn-params msg#]
        (fn [~data-name]
          (if (not ~pred-body) msg#))))))

(defn validate-comp
  "combine validations"
  [error-msg & asserts]
  (if (not (string? error-msg))
    (apply validate-comp "validation error" error-msg asserts)
    (fn [param]
      (if (some #(->> param % nil? not)  asserts) error-msg))))

(defn validate
  "evaluate validations"
  [param & asserts]
  (let [res (remove nil? (map #(% param) asserts))]
    (if (not (empty? res)) res)))

(defn val2str
  "convert value to string"
  [x]
  (cond
    (nil? x) "nil"
    (string? x) (str "\"" x "\"")
    (keyword? x) (name x)
    :else (str x)))

;; TODO
;; defvalidate can get document string
(defvalidate required [param key]
  (str (val2str key) " is required")
  (and (not (nil? (get param key)))
       (not= "" (get param key))))

(defvalidate equals [param key val]
  (str (val2str key) " is not " (val2str val) ", but " (val2str (get param key)))
  (= (get param key) val))



