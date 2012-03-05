(ns misaki.model
  "model for misaki"
  (:use
    [clojure.string :only [split]]
    [misaki :only [*config*]]
    [misaki.util :only [aif]])
  (:require
    [misaki.model.mongo :as mongo]
    [misaki.model.sql :as sql]))

(defn initialize-mongodb
  "initialize mongodb with DB url.
  if DB url is not specified, misaki/*config* data is used"
  ([] (initialize-mongodb (:db-url *config*)))
  ([db-url]
   (when (and (mongo/not-initialized?)
              db-url)
     (mongo/init-mongodb db-url)
     (aif (:mongo-collections *config*)
          (mongo/create-mongo-collections (map keyword (split it #"\s*,\s*")))))))

(defn initialize-mysql
  "initialize MySQL with DB url.
  is DB url is not specified, misaki/*config* data is used"
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
