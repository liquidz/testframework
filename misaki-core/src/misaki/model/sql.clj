(ns misaki.model.sql
  (:use
    [misaki.model.common :only [parse-db-url]])
  (:require
    [clojureql.core :as sql]))

(defn not-initialized?
  "return whether mysql is initialized or not"
  []
  (= {} @sql/global-connections))

(defn init-mysql
  "initialize MySQL with DB url"
  [db-url]
  (let [config (parse-db-url db-url)]

    (sql/open-global
      {:classname "com.mysql.jdbc.Driver"
       :subprotocol (:subprotocol config)
       :user (:user config)
       :password (:password config)
       :subname (str "//" (:host config)
                     ":"  (:port config)
                     "/"  (:db config))})))
