(ns server
  (:use
    __PROJ__.app
    [ring.adapter.jetty :only [run-jetty]]
    [compojure.core :only [defroutes]]
    [compojure.handler :only [site api]]
    [compojure.route :only [files not-found]]))

(load "404")

(defroutes handler
  (site #(misaki-app %))
  (files "/")
  (not-found (not-found-page)))

(defn -main []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "8080"))]
    (run-jetty handler {:port port})))

