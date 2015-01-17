(ns clj-data-analysis.chap04.simulatedanealing
  (:use [criterium core]
        clj-data-analysis.helpers
        [clj-data-analysis.chap04.montecarlo :only [mc-pi-part]])
  (:import [java.lang Math]))

(defn annealing
  "The main simulated annealing function"
  [initial max-iter max-cost
   neighbor-fn cost-fn p-fn temp-fn]
  (let [get-cost (memoize cost-fn)
        cost (get-cost initial)]
    (loop [state initial
           cost cost
           k 1
           best-seq [{:state state :cost cost}]]
      (println '>>> 'sa k \. state \$ cost)
      (if (and (< k max-iter)
               (or (nil? max-cost)
                   (> cost max-cost)))
        (let [t (temp-fn (/ k max-iter))
              next-state (neighbor-fn state)
              next-cost (get-cost next-state)
              next-place {:state next-state,
                          :cost next-cost}]
          (if (> (p-fn cost next-cost t) (rand))
            (recur next-state next-cost (inc k)
                   (conj best-seq next-place))
            (recur state cost (inc k) best-seq)))
        best-seq))))


(defn get-neighbor
  "Between 0 and 20. Randomly slide the state value up or down at most 5."
  [state]
  (max 0 (min 20 (+ state (- (rand-int 11) 5)))))

(defn get-pi-cost

  [n state]
  (let [chunk-size (long (Math/pow 2 state))]
    (first (:mean (quick-benchmark
                   (mc-pi-part chunk-size n))))))

(defn get-pi-cost
  "Cost function. Takes the state and return the value we're trying to minimize.
  Note: criterium.core/quick-benchmark arguments differen than in the book"
  [n state]
  (let [chunk-size (long (Math/pow 2 state))]
    (first (:mean (quick-benchmark
                   mc-pi-part [chunk-size n])))))

(defn should-move
  "Takes the current state's cost, a potential new state's cost and the current
  energy in the system (0 to 1)"
  [c0 c1 t]
  (* t (if (< c0 c1) 0.25 1.0)))

(defn get-temp
  "Takes the current percent through the iteration count
  and returns the energy or temperature as a number from 0 to 1"
  [r]
  (- 1.0 (float r)))

;; Running it - warning - slow

(annealing 12 10 nil get-neighbor
           (partial get-pi-cost 1000000)
           should-move get-temp)
