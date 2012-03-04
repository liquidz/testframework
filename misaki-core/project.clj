(defproject misaki "0.0.3-alpha"
  :description "misaki - library package to build web application easily"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [compojure "1.0.1"]
                 [org.clojure/data.json "0.1.1"]
                 [ring/ring-jetty-adapter "1.0.1"]
                 [hiccup "0.3.8"]]

  ; for example
;  :dev-dependencies [[congomongo "0.1.8"]
;                     [lein-ring "0.5.4"]
;                     [ring-mock "0.1.1"]]
;  :ring {:handler example.server/handler}
;  :main example.server
  ; /for example

  :aot [misaki])
