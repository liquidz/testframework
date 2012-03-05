(defproject misaki "0.0.5.2-alpha"
  :description "misaki - library package to build web application easily"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [compojure "1.0.1"]
                 [org.clojure/data.json "0.1.1"]
                 [ring/ring-jetty-adapter "1.0.1"]
                 [hiccup "0.3.8"]]

  :dev-dependencies [[codox "0.4.1"]]

  ; for example
;  :dev-dependencies [[lein-ring "0.5.4"]
;                     [ring-mock "0.1.1"]
;                     ; for mongo
;                     [congomongo "0.1.8"]
;                     ; for sql
;                     [clojureql "1.0.3"]
;                     ; for mysql
;                     [mysql/mysql-connector-java "5.1.13"]
;                     ; doc
;                     [codox "0.4.1"]
;                     ]
;  :ring {:handler server/handler}
;  :main server
;  :codox {:exclude [example.app server]}
  ; /for example


  :aot [misaki])
