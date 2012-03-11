(ns misaki.config.congomongo
  (:use
    [misaki.core :only [*config*]]
    [misaki.config.common :only [parse-db-url]]
    somnium.congomongo
    [somnium.congomongo.config :only [*mongo-config*]]))

(defn init-mongodb [mongo-url]
  "Checks if connection and collection exist, otherwise initialize."
  (when (not (connection? *mongo-config*))
    (let [config (parse-db-url mongo-url)]
      (mongo! :db (:db config) :host (:host config) :port (Integer. (:port config)))
      (authenticate (:user config) (:password config)))))

; auto initialize
(when-let [db-url (:db-url *config*)]
  (init-mongodb db-url))
