(ns misaki.config.common
  (:use
    [misaki.util.macro :only [aif]]
    [misaki.core :only [*config*]]))

(defn parse-db-url
  "parses db url. eg. mongodb://user:password@localhost:1234/db"
  [url]
  (let [matcher (re-matcher #"^(.*):(//(.*?):(.*?)@(.*?):(\d+)/(.*))$" url)]
    (when (.find matcher)
      (zipmap [:match :subprotocol :subname :user :password :host :port :db]
              (re-groups matcher)))))


(defmacro with-db-url [url & body]
  `(binding [*db-url* ~url] ~@body))


(defn get-db-url []
  (aif (:db-url *config*) it))
