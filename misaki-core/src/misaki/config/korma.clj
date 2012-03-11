(ns misaki.config.korma
  (:use
    [misaki.core :only [*config*]]
    [misaki.config.common :only [parse-db-url]]
    [korma.db :only [defdb postgres mysql]]
    ))

(def ^{:dynamic true} *db* (atom nil))

(defn init-korma [db-fn db-url]
  (println "db-fn :" db-fn)
  (println "db-url:" db-url)

  (let [config (parse-db-url db-url)]
    (defdb db (db-fn {:db (:db config)
                      :host (:host config)
                      :port (:port config)
                      :user (:user config)
                      :password (:password config)}))
    (reset! *db* db)))

(let [db (:db *config*)
      db-url (:db-url *config*)
      db-fn (case db "mysql" mysql
                     "postgres" postgres
                     nil)]
  (when (and db-fn db-url)
    (init-korma db-fn db-url)))


