(ns misaki.util.macro)

(defmacro aif [pred then-part & [else-part]]
  `(if-let [~'it ~pred]
    ~then-part
    ~else-part))

(defmacro clone-macros
  "clone macros in specified namespace."
  [namespace & names]
  (cons 'do
    (for [name names :let [origin (symbol (str namespace "/" name))]]
      `(defmacro ~name [& args#]
         (cons '~origin args#)))))


(defmacro fn-alias [original-fs & alias-names]
  (cons 'do (map (fn [name]
                   `(def ~name ~original-fs)
                   ) alias-names)))

(defmacro wrap-compojure-method [method]
  (let [sym (symbol (str "compojure.core/" method))]
    `(defmacro ~method
       [path# args# & body#]
       `(~'~sym ~path# ~args# (compile-html (do ~@body#))))))


(defmacro wrap-compojure-methods [& method-names]
  (cons
    'do
    (for [method method-names]
      (let [sym (symbol (str "compojure.core/" method))]
        `(defmacro ~method
           [path# args# & body#]
           `(~'~sym ~path# ~args# (compile-html (do ~@body#))))))))
