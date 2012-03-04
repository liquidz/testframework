(ns leiningen.misaki
  (:use [clojure.java.io :only [file]]
        [leiningen.help :only [help-for]])
  (:require [clojure.string :as str]))

; project name
(def proj (atom ""))
(def _proj (atom ""))

(def ^{:dynamic true} *directories*
  ["__PROJ__/src/__PROJ__" "__PROJ__/test/__PROJ__/test"])

(def ^{:dynamic true} *files*
  ["template/.gitignore" "template/project.clj" "template/config.properties" "template/README.md"
   "template/src/server.clj" "template/src/__PROJ__/app.clj"
   "template/test/__PROJ__/test/app.clj"])

;;From the maginalia source: http://fogus.me/fun/marginalia/ {{{
(defn slurp-resource
  [resource-name]
  (try
    (-> (.getContextClassLoader (Thread/currentThread))
        (.getResourceAsStream resource-name)
        (java.io.InputStreamReader.)
        (slurp))
    (catch java.lang.NullPointerException npe
      (println (str "Could not locate resources at " resource-name))
      (println "    ... attempting to fix.")
      (let [resource-name (str "./resources/" resource-name)]
        (try
          (-> (.getContextClassLoader (Thread/currentThread))
              (.getResourceAsStream resource-name)
              (java.io.InputStreamReader.)
              (slurp))
          (catch java.lang.NullPointerException npe
            (println (str "    STILL could not locate resources at " resource-name ". Giving up!"))))))))
; }}}

(defn print-help []
  (println "this is help"))

; {{{
(defn mkdir [path]
  (.mkdirs (file path)))

(defn write-file [path content]
  (spit (file path) content))

(defn replace-var
  ([s] (replace-var s true))
  ([s flag] (str/replace s "__PROJ__" (if flag @_proj @proj))))

(defn template-path->project-path [path]
  (replace-var (str/replace-first path "template" "__PROJ__")))
; }}}

(defn create-directories []
  (doseq [dir *directories*]
    (mkdir (replace-var dir))))


(defn copy-files []
  (doseq [path *files*]
    (write-file (template-path->project-path path)
                (replace-var (slurp-resource path) false))))

(defn new
  "Create a new \"misaki\" project."
  [project-name]
  (when-not project-name
    (println "Project name is not specified.\n# lein misaki new PROJECT_NAME")
    (System/exit 0) )

  (reset! proj project-name)
  (reset! _proj (str/replace project-name "-" "_"))

  (println "Creating new misaki project:" project-name)
  (println "* Making directories")
  (create-directories)
  (println "* Copy files")
  (copy-files)
  (println "Done"))

(defn misaki
  "Manage \"misaki\" projects."
  {:help-arglists '([new PROJECT_NAME])
   :subtasks [#'new]}
  [& args]
  (case (first args)
    "new" (leiningen.misaki/new (second args))
    (println (help-for "misaki"))))

