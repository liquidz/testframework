(ns misaki.server
  "misaki server utilities"
  (:use
    [misaki.core       :only [dev-mode? misaki-app-symbol]]
    [misaki.middleware :only [wrap-validation wrap-exception]]
    [compojure.handler :only [site]]
    [compojure.core    :only [routes HEAD ANY]])
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
  [& {:keys [files not-found server-error handler]
      :or   {files "/", handler []}}]

  `(let [app# (if ~dev-mode? #(~misaki-app-symbol %) ~misaki-app-symbol)
         nf#  (if ~dev-mode? #(~not-found %) ~not-found)
         se#  (if ~dev-mode? #(~server-error % %2) ~server-error)
         hs#  (map (fn [h#] (if ~dev-mode? #(h# %) h#)) ~handler)]

     (routes
       ; main routes
       (-> app#
         site wrap-validation
         (wrap-exception
           (fn [req# err#]
             (res/status (res/response (se# req# err#)) 500))))
       ; user specified routes
       (apply routes hs#)
       ; static file routes
       (route/files ~files)
       ; not found routes
       (HEAD "*" req# (res/status (res/response (nf# req#)) 404))
       (ANY  "*" req# (res/status (res/response (nf# req#)) 404)))))


