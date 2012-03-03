(ns misaki
  (:use
    [clojure.core.incubator :only [defmacro-]]
    [clojure.data.json :only [json-str]]
    [misaki.util.macro :only [clone-macros]]
    [misaki.config :only [load-config]])
  (:require
    compojure.core
    [compojure.route :as route]
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

; compojure route wrapper {{{
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

(def not-found route/not-found)
; }}}

(defn redirect [url & opt]
  (let [loc (response/redirect url)]
    (if (nil? opt)
      loc
      (merge loc (apply hash-map opt)))))

; application config file (config.properties)
(def ^:dynamic *config* (load-config "config"))
