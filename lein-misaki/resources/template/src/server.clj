(ns server
  (:use
    __PROJ__.app
    [misaki :only [develop? wrap-validation]]
    [ring.adapter.jetty :only [run-jetty]]
    [compojure.core :only [defroutes]]
    [compojure.handler :only [site api]]
    [compojure.route :only [files not-found]]))

(load "404")

(def _app (if develop? #(misaki-app %) misaki-app))

(defroutes handler
  (-> _app site wrap-validation)
  (files "/")
  (not-found (not-found-page)))

(defn -main []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "8080"))]
    (run-jetty handler {:port port})))

