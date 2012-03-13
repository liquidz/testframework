(ns misaki.core
  "misaki library package core"
  (:use
    [clojure.data.json :only [json-str]]
    [misaki.util.macro :only [clone-macros]]
    [misaki.config :only [load-config]])
  (:require
    compojure.core
    [ring.util.response :as res]))

; application config file (config.properties)
(def ^{:dynamic true
       :doc "config property data (misaki.properties)"}
  *config* (load-config "conf/misaki"))

(def ^{:doc "whether develop mode or not"}
  dev-mode? (= "develop" (:mode *config*)))

(def; ^{:dynamic true}
  misaki-app-symbol 'misaki-app)

(def ^{:dynamic true} *message* (load-config "conf/message"))
(def message (if dev-mode?
               (fn [key] (get (load-config "conf/message") key ""))
               (fn [key] (get *message* key ""))))

; clone compojure routes
(clone-macros
  compojure.core
  defroutes GET POST PUT DELETE HEAD ANY)

; clone ring.util.response
; =content-type
(def content-type res/content-type)
; =status
(def status res/status)

; =defapp
(defmacro defapp
  "define default application routes

  this is alias for:
    (defroutes misaki-app
      (GET ...))"
  [& routes]
  `(compojure.core/defroutes ~misaki-app-symbol ~@routes))

; =json
(defn json [x]
  (res/content-type
    (res/response (json-str x))
    "application/json"))

; =xml
(defn xml [x]
  x)

; =redirect
(defn redirect
  "redirect specified url
  opt must be key-value pair, and merge to response map"
  [url & opt]
  (let [loc (res/redirect url)]
    (if (nil? opt)
      loc
      (merge loc (apply hash-map opt)))))




