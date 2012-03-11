(ns misaki.server.middleware
  (:use
    [misaki.validation :only [with-validation]])
  (:require
    [ring.util.response :as res]))

(defn wrap-validation
  "ring middleware for validation"
  [handler]
  (fn [request]
    (with-validation (handler request))))

(defn wrap-exception
  "ring middleware for exception"
  [handler server-error-handler]
  (fn [req]
    (try
      (handler req)
      (catch Exception e
        (server-error-handler req e)))))
