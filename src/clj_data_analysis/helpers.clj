(ns clj-data-analysis.helpers)

(def data-src
  (if (re-matches #"Mac.*" (System/getProperty "os.name"))
    "/Users/ed/public_html/clj/clj-data-analysis/src/clj_data_analysis/data/"
    "C:\\xampp\\htdocs\\CLJ\\books\\clj-data-analysis\\src\\clj_data_analysis\\data\\"))

(defn get-data-src
  ;; Get data source
  [filename]
  (str data-src filename))
