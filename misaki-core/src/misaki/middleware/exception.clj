(ns misaki.middleware.exception
  (:use [misaki :only [apply-filters]])
  (:require [ring.util.response :as res]))

(defn- get-error-page [res]
  (let [res (apply-filters res)]
    (if (map? res)
      res
      (res/response res))))

(defn wrap-exception
  [handler server-error-handler]
  (fn [req]
    (try
      (handler req)
      (catch Exception e
        (get-error-page
          (server-error-handler req e))))))
