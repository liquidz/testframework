(ns misaki.model
  (:use
    [clojure.string :only [split]]
    [misaki :only [*config* debug?]]
    [misaki.util :only [aif]]
    somnium.congomongo
    [somnium.congomongo.config :only [*mongo-config*]]
    )
  )

; MONGO DB {{{
(defn- split-mongo-url [url] ; {{{
  "Parses mongodb url from heroku, eg. mongodb://user:pass@localhost:1234/db"
  (let [matcher (re-matcher #"^.*://(.*?):(.*?)@(.*?):(\d+)/(.*)$" url)]
    (when (.find matcher)
      (zipmap [:match :user :pass :host :port :db] (re-groups matcher))))) ; }}}

(defn- init-mongodb [mongo-url]
  "Checks if connection and collection exist, otherwise initialize."
  (when (not (connection? *mongo-config*))
    (let [mongo-url (get (System/getenv) "MONGOHQ_URL" mongo-url)
          config    (split-mongo-url mongo-url)]

      (if debug? (println "Initializing mongo @ " mongo-url))

      (mongo! :db (:db config) :host (:host config) :port (Integer. (:port config)))
      (authenticate (:user config) (:pass config)))))


(defn- create-mongo-collections [collections]
  (println "collections = " collections)
  (doseq [col-name collections]
    (if-not (collection-exists? col-name)
      (do
        (println "## creating collection: " col-name)
        (create-collection! col-name)))))
; }}}

(when (and (nil? (:mongo *mongo-config*))
           (= "mongo" (:db *config*)) (:mongo-url *config*))
  (init-mongodb (:mongo-url *config*))
  (aif (:mongo-collections *config*)
       (create-mongo-collections (map keyword (split it #"\s*,\s*")))))

