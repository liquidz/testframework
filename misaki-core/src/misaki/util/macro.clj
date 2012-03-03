(ns misaki.util.macro)

(defmacro clone-macros
  "clone macros in specified namespace"
  [namespace & names]
  (cons 'do
    (for [name names :let [origin (symbol (str namespace "/" name))]]
      `(defmacro ~name [& args#]
         (cons '~origin args#)))))
