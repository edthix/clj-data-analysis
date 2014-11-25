(ns clj-data-analysis.chap03.watchers
  (:use
   clj-data-analysis.helpers
   [clj-data-analysis.chap02.lazylarge :only [lazy-read-csv]]
   [clj-data-analysis.chap03.stm :only [with-header ->int sum-item sum-items thunk-update-totals-for data-file]]
   [clj-data-analysis.chap03.validators :only [int-rows try-read-string coerce-row]])
  (:import [java.lang Thread]))

;; We'll add watchers to keep track how many rows are converted.

(defn read-row
  [rows caster sink done]
  (if-let [[item & items] (seq rows)]
    (do
      (send caster coerce-row item sink)
      (send *agent* read-row caster sink done)
      items)
    (do
      (dosync (commute done (constantly true)))
      '())))

(defn watch-caster
  "Update a counter"
  [counter watch-key watch-agents old-state new-state]
  (when-not (nil? new-state)
    (dosync (commute counter inc))))

(defn wait-for-it
  "Polls until processing is finished"
  [sleep-for ref-var]
  (loop []
    (when-not @ref-var
      (Thread/sleep sleep-for)
      (recur))))

(defn watch-processing
  "Creates all agents and refs and dispatches them"
  [input-file]
  (let [reader (agent (seque
                       (with-header
                         (lazy-read-csv
                          input-file))))
        caster (agent nil)
        sink (agent [])
        counter (ref 0)
        done (ref false)]
    (add-watch caster :counter
               (partial watch-caster counter))
    (send reader read-row caster sink done)
    (wait-for-it 250 done)
    {
     :results @sink
     :count-watcher @counter
     }))


;; Run this
(:count-watcher (watch-processing data-file))
