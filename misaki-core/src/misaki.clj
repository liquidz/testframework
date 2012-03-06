(ns misaki
  "misaki library package core"
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
    [ring.util.response :as res]))

; application config file (config.properties)
(def ^{:dynamic true
       :doc "config property data (config.properties)"}
  *config* (load-config "config"))

(def develop? (= "develop" (:mode *config*)))
(def debug? (= "true" (:debug *config*)))

; load validation
(load "misaki/validation")

; clone compojure.core/defroutes
(clone-macros
  compojure.core
  defroutes
; GET POST PUT DELETE HEAD ANY
  )

(defmacro defapp
  "define default application routes

  this is alias for:
    (defroutes misaki-app
      (GET ...))"
  [& routes]
  (concat '(defroutes misaki-app) routes))

; =OUTPUT FUNCTIONS= {{{
; hiccup html compiler {{{
(defmacro html4
  "compile shtml as HTML4"
  [& args]
  `(page/html4 ~@args))

(defmacro html
  "compile shtml as HTML5"
  [& args]
  `(page/html5 ~@args))

(defmacro xhtml
  "compile shtml as XHTML"
  [& args]
  `(page/xhtml ~@args))
; }}}

; json writer
(def ^{:doc "compile clojure data to JSON"} json json-str)

; xml writer

; }}}

; compojure route wrapper {{{
(defn compile-html
  "auto shtml compiler. output format is depend on (:default-output *config*)"
  [res]
  (if (vector? res)
    (case (get *config* :default-output "html5")
      "html5" (html res)
      "xhtml" (xhtml res)
      "html4" (html4 res))
     res))

(defmacro GET
  "define GET route"
  [path args & body]
  `(compojure.core/GET ~path ~args (compile-html (do ~@body))))

(defmacro POST
  "define POST route"
  [path args & body]
  `(compojure.core/POST ~path ~args (compile-html (do ~@body))))

(defmacro PUT
  "define PUT route"
  [path args & body]
  `(compojure.core/PUT ~path ~args (compile-html (do ~@body))))

(defmacro DELETE
  "define DELETE route"
  [path args & body]
  `(compojure.core/DELETE ~path ~args (compile-html (do ~@body))))

(defmacro HEAD
  "define HEAD route"
  [path args & body]
  `(compojure.core/HEAD ~path ~args (compile-html (do ~@body))))

(defmacro ANY
  "define ANY route"
  [path args & body]
  `(compojure.core/ANY ~path ~args (compile-html (do ~@body))))

(defmacro GET-JSON
  "define GET route with JSON response"
  [path args & body]
  `(compojure.core/GET ~path ~args
     (res/content-type
       (res/response (json (do ~@body)))
       "application/json")))

(defmacro JSON
  "alias GET-JSON"
  [& args] `(GET-JSON ~@args))
; }}}


(defn redirect
  "redirect specified url

  opt must be key-value pair, and merge to response map"
  [url & opt]
  (let [loc (res/redirect url)]
    (if (nil? opt)
      loc
      (merge loc (apply hash-map opt)))))

