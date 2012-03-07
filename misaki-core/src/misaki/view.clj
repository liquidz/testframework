(ns misaki.view
  "miski view"
  (:use
    [misaki :only [*config*]]
    [misaki.util.macro :only [aif]])
  (:require [hiccup.page-helpers :as page]))

(def ^{:dynamic true
       :doc "default html title"
       } *default-title*
  (get *config* :default-title "no title"))

(defn js
  "expand args as script tag to include javascript"
  [& args]
  (apply page/include-js args))

(defn css
  "epand args as link tag to include style sheet"
  [& args]
  (mapcat #(if (sequential? %)
             (apply css %)
             (page/include-css %))
          args))

(defn ul
  "expand list as ul tag"
  ([ls] (ul identity ls))
  ([f ls]
   [:ul (for [x ls] [:li (f x)])]))

(defn charset
  "expand charset as meta tag"
  ([] (charset "UTF-8"))
  ([cs] [:meta {:charset cs}]))

(defn html5-layout
  "basic layout for HTML5"
  [opt & contents]
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

(def ^{:doc "default layout (html5)"}
  layout html5-layout)
