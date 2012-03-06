(ns __PROJ__.app
  (:use misaki
        misaki.view))

(defapp
  (GET "/" _
    (layout
      (if (has-error?)
        [:div [:h1 "error"] (ul (get-errors))])
      [:form {:method "POST" :action "/hello"}
       [:label "a: " [:input {:type "text" :name "a"}]]
       [:label "b: " [:input {:type "text" :name "b"}]]
       [:input {:type "submit" :value "send"}]]))

  (POST "/hello" {{:keys [a b]} :params}
    (validate! (required? a) "a is required")
    (validate! (required? b) "b is required")

    (if (has-error?)
      (do (keep-validation!)
          (redirect "/"))
      (layout [:div
               [:p (str "a = " a)]
               [:p (str "b = " b)]])))
  
  (JSON "/api" _ {:a 1 :b "2" :c [3 4]}))

