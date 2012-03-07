(ns misaki.middleware.validation
  (:use [misaki :only [with-validation]]))

(defn wrap-validation
  "ring middleware for validation"
  [handler]
  (fn [request]
    (with-validation (handler request))))
