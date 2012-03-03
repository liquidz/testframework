(defproject misaki "0.0.1-alpha"
  :description "misaki - library package to build web application easily"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [compojure "1.0.1"]
                 [org.clojure/data.json "0.1.1"]
                 [ring/ring-jetty-adapter "1.0.1"]
                 [hiccup "0.3.8"]
;                 [org.clojure/tools.namespace "0.1.0"]
                 ]

  :aot [misaki]
  )
