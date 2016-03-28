(ns clj-data-analysis.helpers)

(def data-src-folder
  (if (re-matches #"Mac.*" (System/getProperty "os.name"))
    "/Users/ed/public_html/clj/clj-data-analysis/src/clj_data_analysis/data/"
    "C:\\xampp\\htdocs\\CLJ\\books\\clj-data-analysis\\src\\clj_data_analysis\\data\\"))

;; How to use
;; (get-data-src "data.csv")
(defn get-data-src
  "Get data source"
  [filename]
  (str data-src-folder filename))
