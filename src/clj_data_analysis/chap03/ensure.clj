(ns clj-data-analysis.chap03.ensure
  (:use
   clj-data-analysis.helpers
   [clj-data-analysis.chap02.lazylarge :only [lazy-read-csv]]
   [clj-data-analysis.chap03.stm :only [with-header ->int sum-item sum-items thunk-update-totals-for data-file]]
   [clj-data-analysis.chap03.agent :only [accum-sums div-vec force-val]])
  (:import [java.lang Thread]
           [java.io File])
  (:require [clojure.string :as string]))

;; Get a sequence of files to process
(def input-files
  (filter #(.isFile %)
          (file-seq (File. (get-data-src "brown")))))

;; Processing is done/not
(def finished (ref false))
;; running totals
(def total-docs (ref 0))
(def total-words (ref 0))
;; map tokens to their frequencies
(def freqs (ref {}))
;; agent that contains current state of the report
(def running-report (agent {:term nil :frequency 0 :ratio 0.0}))


(defn tokenize-brown
  "Remove unwanted stuffs from Brown"
  [input-str]
  (->> (string/split input-str #"\s+")
       (map
        #(first (string/split input-str % #"/" 2)))
       (filter #(> (count %) 0))
       (map string/lower-case)
       (map keyword)))

(defn accum-freq
  "Increments frequency map for a token"
  [m token]
  (assoc m token (inc (m token 0))))

(defn compute-file
  [fs]
  (dosync
   (if-let [[s & ss] (seq fs)]
     (let [tokens (tokenize-brown (slurp s))
           tc (count tokens)
           fq (reduce accum-freq {} tokens)]
       (commute total-docs inc)
       (commute total-words #(+ tc %))
       (commute freqs #(merge-with + % fq))
       (send-off *agent* compute-file)
       ss)
     (do (alter finished (constantly true)) '()))))

(defn compute-report
  "updates the report in parallel"
  [{term :term :as report}]
  (dosync
   (when-not @finished
     (send *agent* compute-report))
   (let [term-freq (term (ensure freqs) 0)
         tw (ensure total-words)]
     (assoc report :frequency term-freq
       :ratio (if (zero? tw)
                nil
                (float (/ term-freq tw)))))))

(defn compute-frequencies
  [inputs term]
  (let [a (agent inputs)]
    (send running-report
          #(assoc % :term term))
    (send running-report compute-report)
    (send-off a compute-file)))

;; Compute the frequencies with input files and term
(compute-frequencies input-files :committee)
[@finished @running-report]
[@finished @running-report]

(defn get-report
  ""
  [term]
  (send running-report #(assoc % :term term))
  (send running-report compute-report)
  (await running-report)
  @running-report)

(get-report :committee)
