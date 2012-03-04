(ns misaki.model
  (:use
    [clojure.string :only [split]]
    [misaki :only [*config*]]
    [misaki.util :only [aif]])
  (:require
    [misaki.model.mongo :as mongo]
    [misaki.model.sql :as sql]))

(defn initialize-mongodb
  ([] (initialize-mongodb (:db-url *config*)))
  ([db-url]
   (when (and (mongo/not-initialized?)
              db-url)
     (mongo/init-mongodb db-url)
     (aif (:mongo-collections *config*)
          (mongo/create-mongo-collections (map keyword (split it #"\s*,\s*")))))))

(defn initialize-mysql
  ([] (initialize-mysql (:db-url *config*)))
  ([db-url]
   (when (and (sql/not-initialized?)
              db-url)
     (sql/init-mysql db-url))))

; auto initialize
(case (:db *config*)
  "mongo" (initialize-mongodb)
  "mysql" (initialize-mysql)
  nil)
