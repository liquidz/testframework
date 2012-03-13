(ns misaki.server.handler
  "misaki server utilities"
  (:use
    [misaki.core              :only [dev-mode? misaki-app-symbol]]
    [misaki.server.middleware :only [wrap-validation wrap-exception]]
    [compojure.handler        :only [site]]
    [compojure.core           :only [routes HEAD ANY]])
  (:require
    [compojure.route    :as route]
    [ring.util.response :as res]))

(defmacro misaki-handler
  "return ring handler with misaki's default route and arguments

  :files
      path to bind static files
      ex) :files \"/\"
  :not-found
      function to return not found page.
      ex) :not-found (fn [req] \"not found\")
  :server-error
      function to return server error page
      ex) :server-error (fn [req error] \"server error\")
  :handler
      list of user handlers
      ex) :handler [my-handler1 my-handler2]"
  [& {:keys [files not-found server-error]
      :or   {files "/"}}]

  `(let [app# (if ~dev-mode? #(~misaki-app-symbol %) ~misaki-app-symbol)]
     (routes
       ; main routes
       (-> app#
         site
         wrap-validation
         (wrap-exception
           (fn [req# err#]
             (res/status (res/response (~server-error req# err#)) 500))))
       ; static file routes
       (route/files ~files)
       ; not found routes
       (HEAD "*" req# (res/status (res/response (~not-found req#)) 404))
       (ANY  "*" req# (res/status (res/response (~not-found req#)) 404)))))

