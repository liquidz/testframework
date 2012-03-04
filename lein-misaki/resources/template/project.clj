(defproject __PROJ__ "1.0.0-SNAPSHOT"
  :description "FIXME: write description"

  :dependencies [[org.clojure/clojure "1.3.0"]
                 [compojure "1.0.1"]
                 [org.clojure/data.json "0.1.1"]
                 [ring/ring-jetty-adapter "1.0.1"]
                 [hiccup "0.3.8"]
                 ;[misaki "0.0.4"]
                 ;[org.clojure/tools.namespace "0.1.0"]
                 ]

  :dev-dependencies [[lein-ring "0.5.4"]
                     [ring-mock "0.1.1"]]

  :ring {:handler server/handler}
  :main server)
