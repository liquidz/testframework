(ns misaki.validation)

(declare
  ^{:dynamic true
    :doc "atom for validation errors"}
  *validate-errors*)

(def ^{:dynamic true
       :doc "atom for last validation errors"}
  *last-validate-errors* (atom nil))

(defmacro validate!
  "validate predication"
  [pred msg]
  `(when (not ~pred)
     (swap! *validate-errors* conj ~msg)))

(defn keep-validation!
  "keep validation result to next binding"
  []
  (reset! *last-validate-errors* @*validate-errors*))

(defn has-error?
  "return whether errors are raised or not"
  []
  (-> @*validate-errors* empty? not))

(defn get-errors
  "return validation errors"
  []
  @*validate-errors*)

(defn make-validation-atom
  "return validation atom considering keeped errors"
  []
  (if-let [last-error @*last-validate-errors*]
    (do (reset! *last-validate-errors* nil)
        (atom last-error))
    (atom [])))

(defmacro with-validation
  "validation error binding macro"
  [& body]
  `(binding [*validate-errors* (make-validation-atom)]
     ~@body))

(defn required? [x]
  (and (not (nil? x))
       (not= x "")))
