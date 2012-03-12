(ns misaki.view
  "miski view"
  (:use
    [misaki.core :only [*config*]]
    [misaki.util.macro :only [aif fn-alias]]
    [hiccup.core :only [defhtml]]
    )
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


(defn charset
  "expand charset as meta tag"
  ([] (charset "UTF-8"))
  ([cs] [:meta {:charset cs}]))

(defhtml html5-layout
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

(defn button [opt & label]
  (if-not (map? opt)
    (apply button {} opt label)
    [:button opt label]))

(defn- tag? [x]
  (and (vector? x) (keyword? (first x))))

(defn ul
  "expand list as ul tag"
  [opt & ls]
  (if-not (map? opt)
    (apply ul {} opt ls)
    [:ul opt (for [x ls]
               (if (tag? x)
                 x
                 [:li x]))]))

(defn ol
  "expand list as ol tag"
  [opt & ls]
  (if-not (map? opt)
    (apply ol {} opt ls)
    [:ol opt (for [x ls]
               (if (tag? x)
                 x
                 [:li x]))]))

(defn img
  [opt & [src]]
  (if-not (map? opt)
    (apply img {} opt src)
    [:img (assoc opt :alt (aif (:alt opt) it "no alt") :src src)]))

(defn table
  [opt & rows]
  (if-not (map? opt)
    (apply table {} opt rows)
    [:table
     (dissoc opt :head)
     ; head
     (aif (:head opt)
          [:thead [:tr (for [h it] [:th h])]])
     ; body
     [:tbody
      (for [row rows]
        [:tr (for [column row] [:td column])])]]))

(defn form
  [opt & items]
  (if-not (map? opt)
    (apply form {} opt items)
    (let [opt (assoc opt :method (aif (:method opt) it "GET"))]
      [:form opt items])))

(defn submit
  ([text] (submit {} text))
  ([opt text]
   [:input (merge opt {:type "submit" :value text})]))

(defn reset
  ([text] (reset {} text))
  ([opt text]
   [:input (merge opt {:type "reset" :value text})]))

(defn input-field
  ([type name]
   (input-field type {} name nil))
  ([type name value]
   (input-field type {} name value))
  ([type opt name value]
   [:input (merge opt {:type type :name name :value value})]))


(def input-text (partial input-field "text"))
(fn-alias input-text
          text-field)

(def input-password (partial input-field "password"))
(fn-alias input-password
          password-field password)

(def input-checkbox (partial input-field "checkbox"))
(fn-alias input-checkbox
          checkbox)

(def input-radio (partial input-field "radio"))
(fn-alias input-radio
          input-radio-button radio radio-button)

(def input-file (partial input-field "file"))
(fn-alias input-file file-upload)

(def input-hidden (partial input-field "hidden"))
(fn-alias input-hidden hidden)

(defn textarea
  ([name] (textarea name nil))
  ([name value]
   [:textarea {:name name} value]))
(fn-alias textarea text-area)

(defn label [opt & elems]
  (if-not (map? opt)
    (apply label {} opt elems)
    [:label opt elems]))

(defn option
  ([text value] (option {} text value false))
  ([text value selected?]
   (if (map? text)
     (option text value selected? false)
     (option {} text value selected?)))
  ([opt text value selected?]
   (let [opt (merge opt (if selected? {:selected "selected"} {}) {:value value})]
     [:option opt text])))

(defn select [opt & options]
  (if-not (map? opt)
    (apply select {} opt options)
    [:select opt options]))

(defn anchor
  ([href text] (anchor {} link text))
  ([opt href text] [:a (merge opt {:href href}) text]))
(ns-alias anchor link)


; navigation
; inline label
; alert [:p {:class "alert"} "alert"]
; form
;; input text / password / checkbox / radio / textarea / select
;; input file input
;; submit / reset

