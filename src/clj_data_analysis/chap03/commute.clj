(ns clj-data-analysis.chap03.commute
  (:use
   clj-data-analysis.helpers
   [clj-data-analysis.chap02.lazylarge :only [lazy-read-csv]]
   [clj-data-analysis.chap03.stm :only [with-header ->int sum-item sum-items thunk-update-totals-for main]]))

;; instead of alter, commute queues the update to happen later
;; when the reference isn't engaged. Prevents contentions on
;; those references and can make the system faster

(def data-file (get-data-src "all_160_in_51.P35.csv"))

;; total housing units
(def total-hu (ref 0))
;; total families
(def total-fams (ref 0))


(defn update-totals
  "Total fields from housing unit, family data and collection of items
  and update the STM"
  [fields items]
  (let [mzero (mapv (constantly 0) fields)
        [sum-hu sum-fams] (sum-items mzero fields items)]
    (dosync (commute total-hu #(+ sum-hu %))
            (commute total-fams #(+ sum-fams %)))))

(main data-file)
