(in-ns 'misaki)

(declare
  ^{:dynamic true}
  *validate-errors*)

(def ^{:dynamic true}
  *last-validate-errors* (atom nil))

(defmacro validate! [pred msg]
  `(when (not ~pred)
     (swap! *validate-errors* conj ~msg)))

(defn validation-keep!
  "keep validation result to next access"
  []
  (reset! *last-validate-errors* @*validate-errors*))

(defn has-error?
  ([]     (has-error? *validate-errors*))
  ([atom] (not (empty? @atom))))

(defn get-errors []
  @*validate-errors*)

(defn make-validation-atom []
  (if-let [last-error @*last-validate-errors*]
    (do (reset! *last-validate-errors* nil)
        (atom last-error))
    (atom [])))

(defn wrap-validation
  "middleware for ring"
  [handler]
  (fn [request]
    (binding [*validate-errors* (make-validation-atom)]
      (handler request))))


(defn required? [x]
  (and (not (nil? x))
       (not= x "")))
