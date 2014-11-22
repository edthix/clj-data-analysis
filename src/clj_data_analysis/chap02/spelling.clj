(ns clj-data-analysis.chap02.spelling
  (:require [clojure.string :as string])
  (:use [clojure.set :only [union]]
        [clj-data-analysis.helpers]))

;; Based on Peter Norvig's code

(defn words
  "Tokenize a string into words"
  [text]
  (re-seq #"[a-z]+" (string/lower-case text)))

(defn train
  "Training data structure"
  [feats]
  (frequencies feats))

(def n-words
  (train (words (slurp (get-data-src "big.txt")))))

(def alphabet
  "abcdefghijklmnopqrstuvwxyz")


(defn split-word
  "Split a word into two parts at position i."
  [word i]
  [(.substring word 0 i) (.substring word i)])

(defn delete-char
  "Delete the first character in the second part."
  [[w1 w2]]
  (str w1 (.substring w2 1)))

(defn transpose-split
  "Transpose the first two characters of the second part."
  [[w1 w2]]
  (str w1 (second w2) (first w2) (.substring w2 2)))

(defn replace-split
  "Replace the first character of the second part with every letter."
  [[w1 w2]]
  (let [w2-0 (.substring w2 1)]
    (map #(str w1 % w2-0) alphabet)))

(defn insert-split
  "Insert every letter into the word at the split."
  [[w1 w2]]
  (map #(str w1 % w2) alphabet))

;; Here comes the goodies

(defn edits-1
  "Calculates all the possible edits that can be made to a word, based operators"
  [word]
  (let [splits (map (partial split-word word)
                    (range (inc (count word))))
        long-splits (filter #(> (count (second %)) 1)
                            splits)
        deletes (map delete-char long-splits)
        transposes (map transpose-split long-splits)
        replaces (mapcat replace-split long-splits)
        inserts (remove nil? (mapcat insert-split splits))]
    (set (concat deletes transposes replaces inserts))))

(defn known-edits-2
  "Gets the edits of a word, only if they're known in the training set"
  [word]
  (set (filter (partial contains? n-words)
               (apply union
                      (map #(edits-1 %)
                           (edits-1 word))))))

(defn known
  "Takes a sequence of words and returns set of those seen in the training corpus"
  [words]
  (set (filter (partial contains? n-words) words)))

(defn correct
  "Correct the word"
  [word]
  (let [candidate-thunks [#(known (list word))
                          #(known (edits-1 word))
                          #(known-edits-2 word)
                          #(list word)]]
    (->> candidate-thunks
         (map (fn [f] (f)))
         (filter #(> (count %) 0))
         first (map (fn [w] [(get n-words w 1) w]))
         (reduce (partial max-key first)) second)))

;; Time to correct some words
(correct "editor")
(correct "edtor")
(correct "eidtor")
(correct "whch")

(n-words "transpose")
(n-words "editor")
(n-words "which")
(n-words "about")
