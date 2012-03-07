(in-ns 'misaki)

(require
  '[hiccup.page-helpers :as page]
  '[ring.util.response :as res])

(declare ^{:dynamic true} *response-filters*)

(defn add-response-filter!
  "add response filter to last of *response-filters*"
  [fltr]
  (swap! *response-filters* conj fltr))

(defn set-response-filter!
  "set response filters"
  [fltrs]
  (reset! *response-filters* fltrs))

(defn apply-filters
  "apply response filters"
  [response]
  (reduce (fn [res fltr] (fltr res))
          response
          @*response-filters*))

(defn compile-html
  "auto shtml compiler. output format is depend on (:default-output *config*)"
  [res]
  (if (vector? res)
    (case (get *config* :default-output "html5")
      "html5" (page/html5 res)
      "xhtml" (page/xhtml res)
      "html4" (page/html4 res))
     res))

(def ^{:dynamic true}
  *response-filters* (atom [compile-html]))

(defn json
  "compile clojure data to JSON"
  [res]
  (res/content-type
    (res/response (json-str res))
    "application/json"))

(defn redirect
  "redirect specified url

  opt must be key-value pair, and merge to response map"
  [url & opt]
  (let [loc (res/redirect url)]
    (if (nil? opt)
      loc
      (merge loc (apply hash-map opt)))))
