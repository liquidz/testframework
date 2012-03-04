(ns misaki.view
  (:use
    [misaki :only [*config*]]
    [misaki.util :only [aif]])
  (:require [hiccup.page-helpers :as page]))

(def ^:dynamic *default-title*
  (get *config* :default-title "no title"))

(defn js [& args]
  (apply page/include-js args))

(defn css [& args]
  (mapcat #(if (sequential? %)
             (apply css %)
             (page/include-css %))
          args))

(defn ul
  ([ls] (ul identity ls))
  ([f ls]
   [:ul (for [x ls] [:li (f x)])]))

(defn charset
  ([] (charset "UTF-8"))
  ([cs] [:meta {:charset cs}]))

(defn html5-layout [opt & contents]
  (if (not (map? opt))
    (apply html5-layout {} opt contents)
    [:html
     [:head
      (aif (:charset opt) (charset it) (charset))
      (aif (:css opt) (css it))
      [:title (aif (:title opt) it *default-title*)]
      (aif (:title opt) [:title it])
      (aif (:head opt) it)]
     [:body contents]]))

(def layout html5-layout)
