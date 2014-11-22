(ns clj-data-analysis.helpers)

(def data-src "C:\\xampp\\htdocs\\CLJ\\books\\clj-data-analysis\\src\\clj_data_analysis\\data\\")

(defn get-data-src
  ;; Get data source
  [filename]
  (str data-src filename))
