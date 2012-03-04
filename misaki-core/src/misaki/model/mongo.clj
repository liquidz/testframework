(ns misaki.model.mongo
  (:use
    [misaki.model.common :only [parse-db-url]]
    somnium.congomongo
    [somnium.congomongo.config :only [*mongo-config*]]))

(defn not-initialized? []
  (nil? (:mongo *mongo-config*)))

(defn init-mongodb [mongo-url]
  "Checks if connection and collection exist, otherwise initialize."
  (when (not (connection? *mongo-config*))
    (let [config (parse-db-url mongo-url)]
      (mongo! :db (:db config) :host (:host config) :port (Integer. (:port config)))
      (authenticate (:user config) (:password config)))))

(defn create-mongo-collections [collections]
  (doseq [col-name collections]
    (if-not (collection-exists? col-name)
      (create-collection! col-name))))

