(ns __PROJ__.app
  (:use
    misaki
    misaki.view
    misaki.validation
    )
  )

(defapp
  (GET "/" {flash :flash}
      (layout
        (if (seq? flash)
          [:div [:h1 "error"] (ul flash)])
        [:form {:method "POST" :action "/hello"}
         [:label "a: " [:input {:type "text" :name "a"}]]
         [:label "b: " [:input {:type "text" :name "b"}]]
         [:input {:type "submit" :value "send"}]]
        ))

  (POST "/hello" {{:keys [a b], :as params} :params}
    (let [err (validate params
                (required :a)
                (required :b))]
      (if (seq? err)
        (redirect "/" :flash err)
        (layout [:div
                       [:p (str "a = " a)]
                       [:p (str "b = " b)]]))))

  (not-found "404")
  )

