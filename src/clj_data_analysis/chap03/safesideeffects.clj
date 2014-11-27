(ns clj-data-analysis.chap03.safesideeffects
  (:use
   clj-data-analysis.helpers
   [clj-data-analysis.chap02.lazylarge :only [lazy-read-csv]]
   [clj-data-analysis.chap03.stm :only [with-header ->int sum-item sum-items thunk-update-totals-for data-file]]
   [clj-data-analysis.chap03.agent :only [accum-sums div-vec force-val]])
  (:import [java.lang Thread]))

(def counter (ref 0))

;; Agents will try to read & inc the counter ref simultaneously
(def a1 (agent :a1))
(def a2 (agent :a2))

(defn start-agents
  "Start both agents on the same message function with different
  sleep periods"
  [msg a1-sleep a2-sleep]
  (send a1 msg a1-sleep)
  (send a2 msg a2-sleep))

(defn debug
  [msg]
  (print (str msg \newline))
  (.flush *out*))

(defn starve-out
  [tag sleep-for]
  (let [retries (atom 0)]
    (dosync
     (let [c @counter]
       (when-not (zero? @retries)
         (debug (str ":starve-out " tag
                     ", :try " @retries
                     ", :counter " c)))
       (swap! retries inc)
       (Thread/sleep sleep-for)
       (ref-set counter (inc c))
       (send *agent* starve-out sleep-for)
       tag))))

;; a2 consistently gets starve out
(start-agents starve-out 0 0)

(defn debug! [msg]
  (io! (print (str msg \newline)) (.flush *out*)))

(defn starve-safe [tag sleep-for]
  (let [retries (atom 0)]
    (dosync (let [c @counter]
              (swap! retries inc)
              (Thread/sleep sleep-for)
              (ref-set counter (inc c))))
    (when-not (zero? @retries)
      (debug! (str ":safe-starve " tag
                   ", :try " @retries ", " @counter)))
    (send *agent* starve-safe sleep-for)
    tag))

;; (start-agents starve-safe 0 0)
