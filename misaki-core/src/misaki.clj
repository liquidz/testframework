(ns misaki
  (:use
    [clojure.core.incubator :only [defmacro-]]
    [clojure.data.json :only [json-str]]
    [misaki.util.macro :only [clone-macros]])
  (:require
    compojure.core
    [hiccup.core :as hiccup]
    [hiccup.page-helpers :as page]
    [ring.util.response :as response]))

; clone
(clone-macros compojure.core defroutes)

(defmacro defapp
  "define default application routes"
  [& routes]
  (concat '(defroutes misaki-app) routes))


; =OUTPUT FUNCTIONS= {{{
; hiccup html compiler {{{
(defmacro html4 [& args]
  `(page/html4 ~@args))

(defmacro html [& args]
  `(page/html5 ~@args))

(defmacro xhtml [& args]
  `(page/xhtml ~@args))
; }}}

; json writer
(def json json-str)

; xml writer

; }}}

; compojure GET, POST, PUT, DELETE, HEAD, ANY wrapper {{{
(defn compile-html [res] (if (vector? res) (html res) res))

(defmacro GET [path args & body]
  `(compojure.core/GET ~path ~args (compile-html (do ~@body))))

(defmacro POST [path args & body]
  `(compojure.core/POST ~path ~args (compile-html (do ~@body))))

(defmacro PUT [path args & body]
  `(compojure.core/PUT ~path ~args (compile-html (do ~@body))))

(defmacro DELETE [path args & body]
  `(compojure.core/DELETE ~path ~args (compile-html (do ~@body))))

(defmacro HEAD [path args & body]
  `(compojure.core/HEAD ~path ~args (compile-html (do ~@body))))

(defmacro ANY [path args & body]
  `(compojure.core/ANY ~path ~args (compile-html (do ~@body))))
; }}}

(defn redirect [url & opt]
  (let [loc (response/redirect url)]
    (if (nil? opt)
      loc
      (merge loc (apply hash-map opt)))))
