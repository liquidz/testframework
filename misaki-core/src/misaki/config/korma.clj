(ns misaki.config.korma
  (:use
    [misaki.core :only [*config*]]
    [misaki.config.common :only [parse-db-url]]
    [korma.db :only [defdb postgres mysql]]
    ))

(def ^{:dynamic true} *db* (atom nil))

(defn init-korma [db-url]
  (let [config (parse-db-url db-url)
        db-fn (case (:subprotocol config)
                "mysql" mysql
                "postgres" postgres
                nil)]
    (when db-fn
      (defdb db (db-fn {:db (:db config)
                      :host (:host config)
                      :port (:port config)
                      :user (:user config)
                      :password (:password config)}))
      (reset! *db* db))))

(aif (:db-url *config*)
  (init-korma it))


