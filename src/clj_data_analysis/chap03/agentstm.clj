(ns clj-data-analysis.chap03.agentstm
  (:use
   clj-data-analysis.helpers
   [clj-data-analysis.chap02.lazylarge :only [lazy-read-csv]]
   [clj-data-analysis.chap03.stm :only [with-header ->int sum-item sum-items thunk-update-totals-for data-file]]
   [clj-data-analysis.chap03.agent :only [accum-sums div-vec force-val]])
  (:import [java.lang Thread]))



(defn get-chunk
  "Pop an item off the input sequence and set the reference to
  the rest of the sequence"
  [data-ref]
  (dosync
   (when-let [[s & ss] (seq @data-ref)]
     (ref-set data-ref ss) s)))

(defn update-totals
  "Retrieves new data, processess it and calls itself recursively.
  If there's no data, increments the count of agents are done and stop processing"
  [totals fields coll-ref counter-ref]
  (if-let [items (get-chunk coll-ref)]
    (do
      (send *agent* update-totals fields coll-ref counter-ref)
      (sum-items totals fields items))
    (do
      (dosync (commute counter-ref inc)) totals)))

(defn block-to-done
  "Use finished agent's counter to block until report is done"
  [counter agent-count]
  (loop []
    (when-not (= agent-count @counter)
      (Thread/sleep 500)
      (recur))))


(defn get-results
  "Takes the agents (finished) and forces their values and accumulates their sums and then
  divides the two counts in order"
  [agents fields]
  (->> agents
       (map force-val)
       (reduce accum-sums (mapv (constantly 0) fields))
       (div-vec)))


(defn main
  ([data-file] (main data-file [:P035001 :HU100] 5 5)))
