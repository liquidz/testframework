(ns misaki.config.common)

(defn parse-db-url
  "parses db url. eg. mongodb://user:password@localhost:1234/db"
  [url]
  (let [matcher (re-matcher #"^(.*):(//(.*?):(.*?)@(.*?):(\d+)/(.*))$" url)]
    (when (.find matcher)
      (zipmap [:match :subprotocol :subname :user :password :host :port :db]
              (re-groups matcher)))))
