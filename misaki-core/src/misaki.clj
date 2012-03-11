(ns misaki
  "misaki library package core"
  (:use
    [clojure.core.incubator :only [defmacro-]]
    [clojure.data.json :only [json-str]]
    [misaki.util.macro :only [clone-macros]]
    [misaki.config :only [load-config]]
    )
  (:require
    compojure.core))

;; application config file (config.properties)
;(def ^{:dynamic true
;       :doc "config property data (config.properties)"}
;  *config* (load-config "config"))
;
;(def develop? (= "develop" (:mode *config*)))
;(def debug? (= "true" (:debug *config*)))
;
;; load validation
;(load "misaki/validation")
;; load response
;(load "misaki/response")
;
;; clone compojure.core/defroutes
;(clone-macros
;  compojure.core
;  defroutes)
;
;(defmacro defapp
;  "define default application routes
;
;  this is alias for:
;    (defroutes misaki-app
;      (GET ...))"
;  [& routes]
;  (concat '(defroutes misaki-app) routes))
;
;(defmacro GET
;  "define GET route"
;  [path args & body]
;  `(compojure.core/GET ~path ~args (apply-filters (do ~@body))))
;
;(defmacro POST
;  "define POST route"
;  [path args & body]
;  `(compojure.core/POST ~path ~args (apply-filters (do ~@body))))
;
;(defmacro PUT
;  "define PUT route"
;  [path args & body]
;  `(compojure.core/PUT ~path ~args (apply-filters (do ~@body))))
;
;(defmacro DELETE
;  "define DELETE route"
;  [path args & body]
;  `(compojure.core/DELETE ~path ~args (apply-filters (do ~@body))))
;
;(defmacro HEAD
;  "define HEAD route"
;  [path args & body]
;  `(compojure.core/HEAD ~path ~args (apply-filters (do ~@body))))
;
;(defmacro ANY
;  "define ANY route"
;  [path args & body]
;  `(compojure.core/ANY ~path ~args (apply-filters (do ~@body))))
;
;(defmacro GET-JSON
;  "define GET route with JSON response"
;  [path args & body]
;  `(compojure.core/GET ~path ~args (json (do ~@body))))
;
;(defmacro JSON
;  "alias GET-JSON"
;  [& args] `(GET-JSON ~@args))


